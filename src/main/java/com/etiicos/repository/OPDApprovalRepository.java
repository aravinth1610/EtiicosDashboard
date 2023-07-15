package com.etiicos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.etiicos.entity.OPDApprovalRequest;
import com.etiicos.entity.OPDPendingApprovalRequest;

@Repository
public interface OPDApprovalRepository extends JpaRepository<OPDApprovalRequest,String> {

	Boolean existsByGmail(String gmail);

}
