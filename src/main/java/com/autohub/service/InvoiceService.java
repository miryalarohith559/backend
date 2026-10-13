package com.autohub.service;

import com.autohub.dto.InvoiceRequest;
import com.autohub.model.Invoice;
import com.autohub.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public List<Invoice> getInvoicesByCustomerId(Long customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }

    public Optional<Invoice> getInvoiceByBookingId(Long bookingId) {
        return invoiceRepository.findByBookingId(bookingId);
    }

    public Invoice generateInvoice(InvoiceRequest req) {
        double subtotal = req.getServiceCost() + req.getLabourCost() + req.getPartsCost();
        double taxRate = req.getTaxRatePercent() > 0 ? req.getTaxRatePercent() : 18.0; // default 18% GST
        double tax = Math.round((subtotal * (taxRate / 100.0)) * 100.0) / 100.0;
        double total = Math.round((subtotal + tax) * 100.0) / 100.0;

        Invoice invoice = new Invoice(
                req.getBookingId(),
                req.getCustomerId(),
                req.getVehicleId(),
                LocalDate.now(),
                req.getServiceCost(),
                req.getLabourCost(),
                req.getPartsCost(),
                tax,
                total,
                "UNPAID"
        );
        return invoiceRepository.save(invoice);
    }

    public Invoice updatePaymentStatus(Long id, String status) {
        return invoiceRepository.findById(id).map(invoice -> {
            invoice.setPaymentStatus(status.toUpperCase());
            return invoiceRepository.save(invoice);
        }).orElseThrow(() -> new IllegalArgumentException("Invoice not found with id: " + id));
    }

    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new IllegalArgumentException("Invoice not found with id: " + id);
        }
        invoiceRepository.deleteById(id);
    }
}
