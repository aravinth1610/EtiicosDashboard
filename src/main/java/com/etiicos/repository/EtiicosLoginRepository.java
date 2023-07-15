package com.etiicos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.etiicos.entity.EtiicosLogin;

@Repository
public interface EtiicosLoginRepository extends JpaRepository<EtiicosLogin,String> {

	EtiicosLogin findByGmail(String gmail);

}
