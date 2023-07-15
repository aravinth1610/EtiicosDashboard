package com.etiicos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.etiicos.entity.OPDPendingApprovalRequest;
import com.etiicos.entity.TokenPendingApprovalRequest;

@Repository
public interface TokenPendingApprovalRequestRepository extends JpaRepository<TokenPendingApprovalRequest, String> {

	@Modifying
    @Transactional
    @Query(value = "update token_pending_approval_request set request_status=?1 where gmail=?2",nativeQuery = true)
	Integer updaterequest(Boolean request_status,String gmail);

	TokenPendingApprovalRequest findByGmail(String gmail);
	
	TokenPendingApprovalRequest findByHospitalID(String hospitalId);
	
	@Modifying
    @Transactional
    @Query(value = "update token_pending_approval_request set approved_time=?1 where gmail=?2",nativeQuery = true)
	Integer updaterequestTime(String approved_time,String gmail);
	
	@Query(value = "select count(*) from token_pending_approval_request where request_status=?1",nativeQuery = true)
	Integer pendingHospitalCount(Boolean request_status);
	
	List<TokenPendingApprovalRequest> findByRequestStatus(Boolean requestStatus);

	@Modifying
	 @Transactional
	 @Query(value = "delete from token_pending_approval_request where hospital_id=?1",nativeQuery = true)
	 Integer deleteByHospitalId(String hospital_id);
	
	 @Modifying
	 @Transactional
	 @Query(value = "delete from token_pending_approval_request where gmail=?1",nativeQuery = true)
	 Integer deleteByGmail(String gmail);

	 @Query(value = "select distinct count(distinct state) from (select state from token_pending_approval_request union select state from token_pending_approval_request) t",nativeQuery = true)
	 Integer registerStateCount();
	 
//	 @Query(value = "select count(distinct city) from token_pending_approval_request",nativeQuery = true)
//	 Integer registerCityCount();

	 @Query(value = "select count(state) from token_pending_approval_request where state=?1",nativeQuery = true)
	 Integer registerCount(String state);

	 @Query(value = "select distinct state from token_pending_approval_request order by state ASC",nativeQuery = true)
	 List<String> listOfState();
	 
	 @Query(value = "select count(state) from token_pending_approval_request where state=?1",nativeQuery = true)
	 Integer stateCount(String state);

	 @Query(value = "select distinct city from token_pending_approval_request order by city ASC",nativeQuery = true)
	 List<String> listOfCities();
	 
	 @Query(value = "select count(city) from token_pending_approval_request where city=?1",nativeQuery = true)
	 Integer citiesCount(String city);

	 
	 @Query(value="select city from token_pending_approval_request where state=?1 order by city ASC",nativeQuery = true)
	 List<String> aCityDetailsIState(String state);
	 
	 @Query(value="select hospital_name from token_pending_approval_request where state=?1 order by city ASC",nativeQuery = true)
	 List<String> aHospitalDetailsIState(String state);
	 
	 @Query(value="select state from token_pending_approval_request where city=?1 order by state ASC",nativeQuery = true)
	 List<String> aStateDetailsICity(String city);
	 
	 @Query(value="select hospital_name from token_pending_approval_request where city=?1",nativeQuery = true)
	 List<String> aHospitalDetailsICity(String city);

}




