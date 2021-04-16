package com.cg.fms.dto;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="contractDetails")
public class Contract {
	
	@Id
//	@Column(name="contractNumber",length=20)
	@Column(name="contractNumber")
	private String contractNumber;
	
	
	
//	@Column(name="quotation",length=20)
//	@Size(min=3, max=8)
	@Column(name="quotation")
	private Long quotation;
	
//	@Column(name="startDate",length=6)
	@Column(name="startDate")
	private Date startDate;
	
//	@Column(name="endDate",length=6)
	@Column(name="endDate")
	private Date endDate;
	
//	@Column(name="contractStatus",length=20)
//	@Size(min=3, max=10)
	@Column(name="contractStatus")
	private String contractStatus;
	
	@OneToOne
	@JoinColumn(name="landId")
	@JsonIgnore
	private Land land;
	
	@ManyToOne
	@JoinColumn(name="customerId")
	@JsonIgnore
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="adminId")
//	@Size(min=3, max=8)
	private Admin admin;

	public Contract() {
		super();
	}

	

	public Contract(String contractNumber,long quotation, Date startDate, Date endDate, String contractStatus,
			Customer customer, Admin admin) {
		super();
		this.contractNumber = contractNumber;
		this.quotation = quotation;
		this.startDate = startDate;
		this.endDate = endDate;
		this.contractStatus = contractStatus;
		this.customer = customer;
		this.admin = admin;
	}
	
	



	public Contract(String contractNumber, long quotation, Date startDate, Date endDate) {
		super();
		this.contractNumber = contractNumber;
		this.quotation = quotation;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	



	public Contract(String contractNumber, long quotation) {
		super();
		this.contractNumber = contractNumber;
		this.quotation = quotation;
	}



	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public long getQuotation() {
		return quotation;
	}

	public void setQuotation(long quotation) {
		this.quotation = quotation;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public Land getLand() {
		return land;
	}

	public void setLand(Land land) {
		this.land = land;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "Contract [contractNumber=" + contractNumber + ", quotation=" + quotation + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", contractStatus=" + contractStatus + ", land=" + land + ", customer="
				+ customer + ", admin=" + admin + "]";
	}
	

}
