package com.bearmetaldev.hibernate.demo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "employees", uniqueConstraints = {
		@UniqueConstraint(columnNames = "id"),
		@UniqueConstraint(columnNames = "email") })
public class Employee implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer employeeId;
	
	@Column(name = "email", unique = true, nullable = false, length = 100)
	private String email;
	
	@Column(name = "first_name", unique = false, nullable = false, length = 100)
	private String firstName;
	
	@Column(name = "last_name", unique = false, nullable = false, length = 100)
	private String lastName;

	@Column(name = "salary", unique = false, nullable = false)
	private int salary;

	public Employee(String email, String firstName, String lastName, int salary) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
	}

	public Employee()
	{
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
