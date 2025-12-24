package com.yagubkov.baraholka.repository;

import com.yagubkov.baraholka.entity.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord, String> {

    List<Landlord> findByLandlordNameContainingIgnoreCase(String name);

    List<Landlord> findByLandlordPhoneContaining(String phone);
}