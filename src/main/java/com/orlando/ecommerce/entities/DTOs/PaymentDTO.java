package com.orlando.ecommerce.entities.DTOs;

import com.orlando.ecommerce.entities.Payment;

import java.time.Instant;
import java.time.LocalDateTime;

public class PaymentDTO {
    private Long id;
    private Instant moment;

    public PaymentDTO(Long id, Instant moment) {
        this.id = id;
        this.moment = moment;
    }

    public PaymentDTO(Payment payment) {
        this.id = payment.getId();
        this.moment = payment.getMoment();
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }
}
