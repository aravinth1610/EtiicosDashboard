package com.etiicos.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.etiicos.entity.PerDoctorPrice;

@Repository
public interface PerDoctorPriceRepository extends JpaRepository<PerDoctorPrice,String> {

	@Query(value = "select price from per_doctor_price",nativeQuery = true)
    String perDoctorRupees();
	
	@Modifying
	@Transactional
	@Query(value = "update per_doctor_price set price=?1",nativeQuery = true)
	Integer updateDoctorRupees(String price);
	
}
