package com.autohub.service;

import com.autohub.dto.PaymentRequest;
import com.autohub.model.Invoice;
import com.autohub.model.Payment;
import com.autohub.repository.InvoiceRepository;
import com.autohub.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    public PaymentService(PaymentRepository paymentRepository, InvoiceRepository invoiceRepository) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getPaymentsByInvoice(Long invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId);
    }

    public Payment recordPayment(PaymentRequest req) {
        Invoice invoice = invoiceRepository.findById(req.getInvoiceId())
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found with id: " + req.getInvoiceId()));

        String method = req.getPaymentMethod() != null ? req.getPaymentMethod().toUpperCase() : "UPI";
        Payment payment = new Payment(
                req.getInvoiceId(),
                req.getAmount() > 0 ? req.getAmount() : invoice.getTotalAmount(),
                LocalDate.now(),
                method,
                "COMPLETED"
        );
        Payment saved = paymentRepository.save(payment);

        // Update invoice payment status
        invoice.setPaymentStatus("PAID");
        invoiceRepository.save(invoice);

        return saved;
    }
}
