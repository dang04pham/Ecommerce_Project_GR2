package com.ecommerce.application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private int amountBeforeTax;

    private int amountAfterTax;

    private int shippingFee;

    private int totalCost;

    private String shipmentStatus;

    private String paymentStatus;

    private String city;

    private String country;

    private String addressLine1;

    private String addressLine2;

    private String postalCode;

    private String recipientName;

    private String recipientPhone;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId", referencedColumnName = "productId", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;
}
