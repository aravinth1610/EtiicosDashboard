package com.etiicos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.etiicos.entity.OPDPendingApprovalRequest;



@Repository
public interface OPDRequestRepository extends JpaRepository<OPDPendingApprovalRequest,String> {

	@Modifying
    @Transactional
    @Query(value = "update opd_pending_approval_request set request=?1 where gmail=?2",nativeQuery = true)
	Integer updaterequest(Boolean request,String gmail);
	
	OPDPendingApprovalRequest findByGmail(String gmail);
	
	OPDPendingApprovalRequest findByHospitalId(String hospitalId);
	
	@Modifying
    @Transactional
    @Query(value = "update opd_pending_approval_request set approved_time=?1 where gmail=?2",nativeQuery = true)
	Integer updaterequestTime(String requestTime,String gmail);
	
	
	List<OPDPendingApprovalRequest> findByRequest(Boolean request);
	 
	 @Modifying
	 @Transactional
	 @Query(value = "delete from opd_pending_approval_request where gmail=?1",nativeQuery = true)
	 Integer deleteByGmail(String gmail);
	 
	 @Modifying
	 @Transactional
	 @Query(value = "delete from opd_pending_approval_request where hospital_id=?1",nativeQuery = true)
	 Integer deleteByHospitalId(String hospital_id);
	    
	 @Query(value = "select count(*) from opd_pending_approval_request where request=?1",nativeQuery = true)
	 Integer pendingHospitalCount(Boolean request);
	 
	 @Query(value = "select distinct count(distinct state) from (select state from opd_pending_approval_request union select state from opd_reject_request union select state from token_pending_approval_request union select state from token_reject_request) t",nativeQuery = true)
	 Integer registerOverAllStateCount();
	 
	 @Query(value = "select distinct count(distinct city) from (select city from opd_pending_approval_request union select city from opd_reject_request union select city from token_pending_approval_request union select city from token_reject_request) t",nativeQuery = true)
	 Integer registerOverAllCitiesCount();
	 
	 @Query(value = "select distinct state from opd_pending_approval_request order by state ASC",nativeQuery = true)
	 List<String> findStatesList();
	 
	 @Query(value = "select count(state) from opd_pending_approval_request where state=?1",nativeQuery = true)
	 Integer stateCount(String state);
	 
	 @Query(value = "select distinct city from opd_pending_approval_request order by city ASC",nativeQuery = true)
	 List<String> findCitiesList();
	 
	 @Query(value = "select count(city) from opd_pending_approval_request where city=?1",nativeQuery = true)
	 Integer citiesCount(String city);
	 
	 @Query(value="select city from opd_pending_approval_request where state=?1 order by city ASC",nativeQuery = true)
	 List<String> aCityDetailsIState(String state);
	 
	 @Query(value="select hospital from opd_pending_approval_request where state=?1 order by city ASC",nativeQuery = true)
	 List<String> aHospitalDetailsIState(String state);
	 
	 @Query(value="select state from opd_pending_approval_request where city=?1 order by state ASC",nativeQuery = true)
	 List<String> aStateDetailsICity(String city);
	 
	 @Query(value="select hospital from opd_pending_approval_request where city=?1",nativeQuery = true)
	 List<String> aHospitalDetailsICity(String city);
	 
}
