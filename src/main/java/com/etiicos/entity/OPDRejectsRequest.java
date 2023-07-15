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
@Table(name="opd_reject_request")
public class OPDRejectsRequest {

	@Id
	private String user_id;
	private String gmail;
	private String hospital;
	private String hospital_id;
	private String password;
	private String reject_time;
	private String request_time;
	private Boolean request;
	private String state;
	private String city;
	private String reason;
	@Column(name="payment_status")
	private String paymentStatus;
	@Column(name="next_payment_date")
	private String nextPaymentDate;
}
