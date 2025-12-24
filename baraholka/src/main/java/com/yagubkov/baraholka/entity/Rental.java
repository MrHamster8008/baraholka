package com.yagubkov.baraholka.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "placement_id")
    private Integer placementId;

    @Column(name = "product_category", nullable = false, length = 50)
    private String productCategory;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "rental_days", nullable = false)
    private Integer rentalDays;

    @Column(name = "rental_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal rentalCost;

    @Column(name = "shelf_id", nullable = false, length = 10)
    private String shelfId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "landlord_name", referencedColumnName = "landlord_name", nullable = false)
    private Landlord landlord;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Information> informations = new ArrayList<>();

    // Конструкторы
    public Rental() {}

    public Rental(String productCategory, LocalDate startDate, LocalDate endDate,
                  Integer rentalDays, BigDecimal rentalCost, String shelfId, Landlord landlord) {
        this.productCategory = productCategory;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentalDays = rentalDays;
        this.rentalCost = rentalCost;
        this.shelfId = shelfId;
        this.landlord = landlord;
    }

    // Геттеры и сеттеры
    public Integer getPlacementId() { return placementId; }
    public void setPlacementId(Integer placementId) { this.placementId = placementId; }

    public String getProductCategory() { return productCategory; }
    public void setProductCategory(String productCategory) { this.productCategory = productCategory; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Integer getRentalDays() { return rentalDays; }
    public void setRentalDays(Integer rentalDays) { this.rentalDays = rentalDays; }

    public BigDecimal getRentalCost() { return rentalCost; }
    public void setRentalCost(BigDecimal rentalCost) { this.rentalCost = rentalCost; }

    public String getShelfId() { return shelfId; }
    public void setShelfId(String shelfId) { this.shelfId = shelfId; }

    public Landlord getLandlord() { return landlord; }
    public void setLandlord(Landlord landlord) { this.landlord = landlord; }

    public List<Information> getInformations() { return informations; }
    public void setInformations(List<Information> informations) { this.informations = informations; }

    @Override
    public String toString() {
        return "Rental{id=" + placementId + ", category='" + productCategory + "', cost=" + rentalCost + "}";
    }
}