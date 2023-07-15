package com.etiicos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.etiicos.entity.TokenApprovalRequest;

@Repository
public interface TokenApprovalRequestRepository extends JpaRepository<TokenApprovalRequest,String> {

	Boolean existsByGmail(String gmail);
}
