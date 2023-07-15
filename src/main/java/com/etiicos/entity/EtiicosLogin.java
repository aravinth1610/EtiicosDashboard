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
@Table(name = "etiicos_login")
public class EtiicosLogin {

	
	@Id
	private String gmail;
	@Column(name="user_name")
	private String userName;
	private String password;
	
}
