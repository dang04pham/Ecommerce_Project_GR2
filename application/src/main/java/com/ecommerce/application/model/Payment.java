package com.ecommerce.application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order;

    private Long amount;

    private String paymentStatus;

    private Date createdAt;

    @OneToMany(mappedBy = "payment")
    private List<PaymentTransaction> transactions;
}
