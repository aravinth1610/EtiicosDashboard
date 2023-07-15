package com.etiicos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="opd_pending_approval_request")
public class OPDPendingApprovalRequest {

	
	@Id
	@Column(name="user_id")
	private String userId;
	private String gmail;
	private String hospital;
	@Column(name="hospital_id")
	private String hospitalId;
	private String password;
	@Column(name="request_time")
	private String requestTime;
	@Column(name="approved_time")
	private String approvedTime;
	private Boolean request;
	private String state;
	private String city;
	@Column(name="payment_status")
	private String paymentStatus;
	@Column(name="next_payment_date")
	private String nextPaymentDate;
	
}
