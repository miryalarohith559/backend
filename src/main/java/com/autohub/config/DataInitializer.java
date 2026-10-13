package com.autohub.config;

import com.autohub.model.*;
import com.autohub.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceBookingRepository bookingRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           CustomerRepository customerRepository,
                           VehicleRepository vehicleRepository,
                           ServiceRepository serviceRepository,
                           ServiceBookingRepository bookingRepository,
                           InvoiceRepository invoiceRepository,
                           PaymentRepository paymentRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
        this.serviceRepository = serviceRepository;
        this.bookingRepository = bookingRepository;
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // 1. Seed Admin User
        if (userRepository.findByEmail("admin@autohub.com").isEmpty()) {
            User admin = new User(
                    "AutoHub Admin",
                    "admin@autohub.com",
                    passwordEncoder.encode("admin123"),
                    "ROLE_ADMIN",
                    "ADMIN",
                    "+91 9876543210"
            );
            userRepository.save(admin);
        }

        // 2. Seed Customer User (Tharun Reddy)
        if (userRepository.findByEmail("tharun@gmail.com").isEmpty()) {
            User customerUser = new User(
                    "Tharun Reddy",
                    "tharun@gmail.com",
                    passwordEncoder.encode("tharun123"),
                    "ROLE_CUSTOMER",
                    "CUSTOMER",
                    "9876543210"
            );
            userRepository.save(customerUser);
        }

        // 3. Seed Customers
        Customer c1;
        if (customerRepository.count() == 0) {
            c1 = customerRepository.save(new Customer("Tharun Reddy", "tharun@gmail.com", "9876543210", "Hyderabad, Telangana"));
            customerRepository.save(new Customer("Priya Sharma", "priya.sharma@gmail.com", "9812345678", "Madhapur, Hyderabad"));
            customerRepository.save(new Customer("Rahul Varma", "rahul.varma@gmail.com", "9845612345", "Banjara Hills, Hyderabad"));
            customerRepository.save(new Customer("Ananya Rao", "ananya.rao@gmail.com", "9701234567", "Gachibowli, Hyderabad"));
        } else {
            c1 = customerRepository.findAll().get(0);
        }

        // 4. Seed Services Catalog
        if (serviceRepository.count() == 0) {
            serviceRepository.saveAll(List.of(
                    new ServiceEntity("Periodic Maintenance & Oil Change", "Complete engine oil drain and refill with genuine filter replacement, 40-point safety check", 1899.0, 60),
                    new ServiceEntity("Brake System Inspection & Pad Replacement", "Front and rear brake disc inspection, brake fluid top-up, pad replacement", 2499.0, 90),
                    new ServiceEntity("Full Synthetic Engine Tune-Up", "Premium synthetic oil replacement, spark plug cleaning, throttle body servicing, engine scanning", 4500.0, 120),
                    new ServiceEntity("AC Deep Cleaning & Gas Refill", "Evaporator foam wash, AC condenser cleaning, cabin filter replacement, R134a refrigerant charge", 2199.0, 75),
                    new ServiceEntity("Wheel Alignment & 3D Balancing", "Computerized 4-wheel laser alignment, wheel balancing with counterweights, tire rotation", 999.0, 45),
                    new ServiceEntity("Complete Car Spa & Ceramic Wash", "High-pressure foam wash, interior vacuuming, dashboard polish, glass treatment, underbody wash", 1499.0, 90),
                    new ServiceEntity("Battery Health Check & Replacement", "Cold cranking amps (CCA) test, terminal cleaning, voltage drop analysis, battery swap", 3200.0, 30)
            ));
        }

        // 5. Seed Vehicles
        Vehicle v1, v2;
        if (vehicleRepository.count() == 0) {
            v1 = vehicleRepository.save(new Vehicle("TS09-EA-2024", "Hyundai", "Creta SX(O)", 2023, "Petrol", c1.getId()));
            v2 = vehicleRepository.save(new Vehicle("TS08-HB-1007", "Tata", "Nexon EV Max", 2024, "Electric", c1.getId()));
            vehicleRepository.save(new Vehicle("TS07-FX-4589", "Honda", "City ZX", 2022, "Petrol", c1.getId()));
            vehicleRepository.save(new Vehicle("TS10-AB-9921", "Mahindra", "XUV700 AX7", 2023, "Diesel", c1.getId()));
        } else {
            List<Vehicle> list = vehicleRepository.findAll();
            v1 = list.get(0);
            v2 = list.size() > 1 ? list.get(1) : list.get(0);
        }

        // 6. Seed Bookings
        ServiceBooking b1;
        if (bookingRepository.count() == 0) {
            b1 = bookingRepository.save(new ServiceBooking(
                    c1.getId(),
                    v1.getId(),
                    "Periodic Maintenance & Oil Change",
                    LocalDate.now().minusDays(3),
                    "COMPLETED",
                    "Routine 10,000 KM service. Change engine oil and check air filters."
            ));
            bookingRepository.save(new ServiceBooking(
                    c1.getId(),
                    v2.getId(),
                    "AC Deep Cleaning & Gas Refill",
                    LocalDate.now().plusDays(1),
                    "IN_PROGRESS",
                    "AC cooling slightly weak during daytime driving."
            ));
            bookingRepository.save(new ServiceBooking(
                    c1.getId(),
                    v1.getId(),
                    "Wheel Alignment & 3D Balancing",
                    LocalDate.now().plusDays(4),
                    "PENDING",
                    "Minor steering pull to the left at highway speeds."
            ));
        } else {
            b1 = bookingRepository.findAll().get(0);
        }

        // 7. Seed Invoices & Payments
        if (invoiceRepository.count() == 0) {
            Invoice inv1 = new Invoice(
                    b1.getId(),
                    c1.getId(),
                    v1.getId(),
                    LocalDate.now().minusDays(3),
                    1899.0, // service cost
                    500.0,  // labour cost
                    450.0,  // parts cost
                    512.82, // 18% GST tax
                    3361.82,// total
                    "PAID"
            );
            Invoice savedInv = invoiceRepository.save(inv1);

            // Record payment for invoice
            Payment p1 = new Payment(
                    savedInv.getId(),
                    savedInv.getTotalAmount(),
                    LocalDate.now().minusDays(3),
                    "UPI",
                    "COMPLETED"
            );
            paymentRepository.save(p1);

            // Seed second unpaid invoice
            Invoice inv2 = new Invoice(
                    null,
                    c1.getId(),
                    v2.getId(),
                    LocalDate.now(),
                    2199.0,
                    650.0,
                    300.0,
                    566.82,
                    3715.82,
                    "UNPAID"
            );
            invoiceRepository.save(inv2);
        }
    }
}
