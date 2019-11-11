package com.revature.models;

public class Reimbursement {
	private int reimbId;
	private double reimbAmount;
	private String reimbAuthor;
	private int reimbStatus;
	private int reimbType;

	public Reimbursement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reimbursement(int reimb_id, double reimb_amount, String reimb_author, int reimb_status, int reimb_type) {
		super();
		this.reimbId = reimb_id;
		this.reimbAmount = reimb_amount;
		this.reimbAuthor = reimb_author;
		this.reimbStatus = reimb_status;
		this.reimbType = reimb_type;
	}

	public int getReimb_id() {
		return reimbId;
	}

	public void setReimb_id(int reimb_id) {
		this.reimbId = reimb_id;
	}

	public double getReimb_amount() {
		return reimbAmount;
	}

	public void setReimb_amount(int reimb_amount) {
		this.reimbAmount = reimb_amount;
	}

	public String getReimb_author() {
		return reimbAuthor;
	}

	public void setReimb_author(String reimb_author) {
		this.reimbAuthor = reimb_author;
	}

	public int getReimb_status() {
		return reimbStatus;
	}

	public void setReimb_status(int reimb_status) {
		this.reimbStatus = reimb_status;
	}

	public int getReimb_type() {
		return reimbType;
	}

	public void setReimb_type(int reimb_type) {
		this.reimbType = reimb_type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(reimbAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((reimbAuthor == null) ? 0 : reimbAuthor.hashCode());
		result = prime * result + reimbId;
		result = prime * result + reimbStatus;
		result = prime * result + reimbType;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(reimbAmount) != Double.doubleToLongBits(other.reimbAmount))
			return false;
		if (reimbAuthor == null) {
			if (other.reimbAuthor != null)
				return false;
		} else if (!reimbAuthor.equals(other.reimbAuthor))
			return false;
		if (reimbId != other.reimbId)
			return false;
		if (reimbStatus != other.reimbStatus)
			return false;
		if (reimbType != other.reimbType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [reimb_id=" + reimbId + ", reimb_amount=" + reimbAmount + ", reimb_author="
				+ reimbAuthor + ", reimb_status=" + reimbStatus + ", reimb_type=" + reimbType + "]";
	}

}
