package com.dinein.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "staff")
public class Staff {
	
	@Id
	String id;
	String employeeId;
	String femployeeName;
	String lemployeeName;
	String phone;
	String emailId;
	String gender;
	String zip;
	String designation;
	String address;
	double salary;
	String password;
	
	
	public Staff() {
		super();
		// TODO Auto-generated constructor stub
	}


	

	public Staff(String id, String employeeId, String femployeeName, String lemployeeName, String phone, String emailId,
			String gender, String zip, String designation, String address, double salary, String password) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.femployeeName = femployeeName;
		this.lemployeeName = lemployeeName;
		this.phone = phone;
		this.emailId = emailId;
		this.gender = gender;
		this.zip = zip;
		this.designation = designation;
		this.address = address;
		this.salary = salary;
		this.password = password;
	}




	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getEmployeeId() {
		return employeeId;
	}




	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}




	public String getFemployeeName() {
		return femployeeName;
	}




	public void setFemployeeName(String femployeeName) {
		this.femployeeName = femployeeName;
	}




	public String getLemployeeName() {
		return lemployeeName;
	}




	public void setLemployeeName(String lemployeeName) {
		this.lemployeeName = lemployeeName;
	}




	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
	}




	public String getEmailId() {
		return emailId;
	}




	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}




	public String getGender() {
		return gender;
	}




	public void setGender(String gender) {
		this.gender = gender;
	}




	public String getZip() {
		return zip;
	}




	public void setZip(String zip) {
		this.zip = zip;
	}




	public String getDesignation() {
		return designation;
	}




	public void setDesignation(String designation) {
		this.designation = designation;
	}




	public String getAddress() {
		return address;
	}




	public void setAddress(String address) {
		this.address = address;
	}




	public double getSalary() {
		return salary;
	}




	public void setSalary(double salary) {
		this.salary = salary;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	


}
