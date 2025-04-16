package lk.ijse.ecommercebackend.service;



import lk.ijse.ecommercebackend.entity.Payment;
import lk.ijse.ecommercebackend.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Add a new payment
    public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    // Get payments by order ID
    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    // Get all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
