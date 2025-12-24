package com.yagubkov.baraholka.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "landlords")
public class Landlord {

    @Id
    @Column(name = "landlord_name", length = 100)
    private String landlordName;

    @Column(name = "landlord_phone", nullable = false, length = 20)
    private String landlordPhone;

    @Column(name = "landlord_card", nullable = false, length = 30)
    private String landlordCard;

    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rental> rentals = new ArrayList<>();

    // Конструкторы
    public Landlord() {}

    public Landlord(String landlordName, String landlordPhone, String landlordCard) {
        this.landlordName = landlordName;
        this.landlordPhone = landlordPhone;
        this.landlordCard = landlordCard;
    }

    // Геттеры и сеттеры
    public String getLandlordName() { return landlordName; }
    public void setLandlordName(String landlordName) { this.landlordName = landlordName; }

    public String getLandlordPhone() { return landlordPhone; }
    public void setLandlordPhone(String landlordPhone) { this.landlordPhone = landlordPhone; }

    public String getLandlordCard() { return landlordCard; }
    public void setLandlordCard(String landlordCard) { this.landlordCard = landlordCard; }

    public List<Rental> getRentals() { return rentals; }
    public void setRentals(List<Rental> rentals) { this.rentals = rentals; }

    @Override
    public String toString() {
        return "Landlord{name='" + landlordName + "', phone='" + landlordPhone + "'}";
    }
}