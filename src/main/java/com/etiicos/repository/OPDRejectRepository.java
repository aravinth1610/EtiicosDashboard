package com.etiicos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.etiicos.entity.OPDRejectsRequest;

@Repository
public interface OPDRejectRepository extends JpaRepository<OPDRejectsRequest,String> {
    
    @Query(value = "select count(*) from opd_reject_request",nativeQuery = true)
    Integer rejectCallCount();	

     @Modifying
	 @Transactional
	 @Query(value = "delete from opd_reject_request where gmail=?1",nativeQuery = true)
	 Integer deleteByGmail(String gmail);

     OPDRejectsRequest findByGmail(String gmail);
     
     @Query(value = "select count(distinct state) from opd_reject_request",nativeQuery = true)
	 Integer rejectStateCount();
     
//     @Query(value = "select count(distinct city) from opd_reject_request",nativeQuery = true)
//	 Integer rejectcityCount();
     
     @Query(value = "select distinct state from opd_reject_request order by state ASC",nativeQuery = true)
     List<String> rejectListOState();
     
     @Query(value = "select count(state) from opd_reject_request where state=?1",nativeQuery = true)
     Integer listRejectCount(String state);
     
     @Query(value = "select distinct city from opd_reject_request order by city ASC",nativeQuery = true)
     List<String> rejectListOCities();
     
     @Query(value = "select count(city) from opd_reject_request where city=?1",nativeQuery = true)
     Integer listRejectCitiesCount(String city);
     
     @Query(value="select city from opd_reject_request where state=?1 order by city ASC",nativeQuery = true)
	 List<String> aCityDetailsIState(String state);
	 
	 @Query(value="select hospital from opd_reject_request where state=?1 order by city ASC",nativeQuery = true)
	 List<String> aHospitalDetailsIState(String state);
	 
	 @Query(value="select state from opd_reject_request where city=?1 order by state ASC",nativeQuery = true)
	 List<String> aStateDetailsICity(String city);
	 
	 @Query(value="select hospital from opd_reject_request where city=?1",nativeQuery = true)
	 List<String> aHospitalDetailsICity(String city);
     
     }
