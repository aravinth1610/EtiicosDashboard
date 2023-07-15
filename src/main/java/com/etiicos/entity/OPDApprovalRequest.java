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
@Table(name="opd_approval_request")
public class OPDApprovalRequest {

	@Id
	@Column(name="user_id")
	private String userId;
	private String gmail;
	private String hospital;
	@Column(name="hospital_id")
	private String hospitalId;
	
}
