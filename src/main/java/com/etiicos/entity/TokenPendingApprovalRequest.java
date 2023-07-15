package com.etiicos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="token_pending_approval_request")
public class TokenPendingApprovalRequest {

	@Id
	@Column(name="user_id")
	private String userId;
	private String gmail;
	private String password;
	@Column(name="hospital_name")
	private String hospitalName;
	@Column(name="hospital_id")
	private String hospitalID;
	private String state;
	private String city;
	private String address;
	@Column(name="request_date_time")
	private String requestDateTime;
	@Column(name="approved_time")
	private String approvedTime;
	@Column(name="request_status")
	private Boolean requestStatus;
	

	
}
