package com.yagubkov.baraholka.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "information")
public class Information {

    @Id
    @Column(name = "order_id", length = 10)
    private String orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "placement_id", nullable = false)
    private Rental rental;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    // Конструкторы
    public Information() {}

    public Information(String orderId, Rental rental, LocalDate saleDate) {
        this.orderId = orderId;
        this.rental = rental;
        this.saleDate = saleDate;
    }

    // Геттеры и сеттеры
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public Rental getRental() { return rental; }
    public void setRental(Rental rental) { this.rental = rental; }

    public LocalDate getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }

    @Override
    public String toString() {
        return "Information{orderId='" + orderId + "', saleDate=" + saleDate + "}";
    }
}