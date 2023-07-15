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
@Table(name="token_approval_request")
public class TokenApprovalRequest {

	@Id
	@Column(name="user_id")
	private String userId;
	private String gmail;
	@Column(name="hospital_name")
	private String hospitalName;
	@Column(name="hospital_id")
	private String hospitalID;
	@Column(name="request_date_time")
	private String requestDateTime;
	
}
