package com.model;

public class AccountInfo {

	private String ccy;

	private String accountNumber;

	private String phoneNumber;

	private String relatedId;

	private String status;

	private String totalDue;

	private String fullName;

	private String khName;

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(String relatedId) {
		this.relatedId = relatedId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalDue() {
		return totalDue;
	}

	public void setTotalDue(String totalDue) {
		this.totalDue = totalDue;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getKhName() {
		return khName;
	}

	public void setKhName(String khName) {
		this.khName = khName;
	}

	@Override
	public String toString() {
		return "ClassPojo [ccy = " + ccy + ", accountNumber = " + accountNumber + ", phoneNumber = " + phoneNumber
				+ ", relatedId = " + relatedId + ", status = " + status + ", totalDue = " + totalDue + ", fullName = "
				+ fullName + ", khName = " + khName + "]";
	}

}
