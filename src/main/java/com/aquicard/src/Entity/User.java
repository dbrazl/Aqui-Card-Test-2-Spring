package com.aquicard.src.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long code;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String birthday;
	
	public void setCode(long code) {
		this.code = code;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public long getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getBirthday() {
		return this.birthday;
	}
}
