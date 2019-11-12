package com.revature.models;

import java.sql.Timestamp;

public class Reimbursement {
	private int reimbId;
	private double reimbAmount;
	private Timestamp reimbSubmit;
	private Timestamp reimbResolve;
	private String reimbDesciption;
	private String reimbAuthor;
	private String reimbResolver;
	private String reimbStatus;
	private String reimbType;

	public Reimbursement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reimbursement(int reimbId, double reimbAmount, Timestamp reimbSubmit, Timestamp reimbResolve,
			String reimbDesciption, String reimbAuthor, String reimbResolver, String reimbStatus, String reimbType) {
		super();
		this.reimbId = reimbId;
		this.reimbAmount = reimbAmount;
		this.reimbSubmit = reimbSubmit;
		this.reimbResolve = reimbResolve;
		this.reimbDesciption = reimbDesciption;
		this.reimbAuthor = reimbAuthor;
		this.reimbResolver = reimbResolver;
		this.reimbStatus = reimbStatus;
		this.reimbType = reimbType;
	}

	public int getReimbId() {
		return reimbId;
	}

	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}

	public double getReimbAmount() {
		return reimbAmount;
	}

	public void setReimbAmount(double reimbAmount) {
		this.reimbAmount = reimbAmount;
	}

	public Timestamp getReimbSubmit() {
		return reimbSubmit;
	}

	public void setReimbSubmit(Timestamp reimbSubmit) {
		this.reimbSubmit = reimbSubmit;
	}

	public Timestamp getReimbResolve() {
		return reimbResolve;
	}

	public void setReimbResolve(Timestamp reimbResolve) {
		this.reimbResolve = reimbResolve;
	}

	public String getReimbDesciption() {
		return reimbDesciption;
	}

	public void setReimbDesciption(String reimbDesciption) {
		this.reimbDesciption = reimbDesciption;
	}

	public String getReimbAuthor() {
		return reimbAuthor;
	}

	public void setReimbAuthor(String reimbAuthor) {
		this.reimbAuthor = reimbAuthor;
	}

	public String getReimbResolver() {
		return reimbResolver;
	}

	public void setReimbResolver(String reimbResolver) {
		this.reimbResolver = reimbResolver;
	}

	public String getReimbStatus() {
		return reimbStatus;
	}

	public void setReimbStatus(String reimbStatus) {
		this.reimbStatus = reimbStatus;
	}

	public String getReimbType() {
		return reimbType;
	}

	public void setReimbType(String reimbType) {
		this.reimbType = reimbType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(reimbAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((reimbAuthor == null) ? 0 : reimbAuthor.hashCode());
		result = prime * result + ((reimbDesciption == null) ? 0 : reimbDesciption.hashCode());
		result = prime * result + reimbId;
		result = prime * result + ((reimbResolve == null) ? 0 : reimbResolve.hashCode());
		result = prime * result + ((reimbResolver == null) ? 0 : reimbResolver.hashCode());
		result = prime * result + ((reimbStatus == null) ? 0 : reimbStatus.hashCode());
		result = prime * result + ((reimbSubmit == null) ? 0 : reimbSubmit.hashCode());
		result = prime * result + ((reimbType == null) ? 0 : reimbType.hashCode());
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
		if (reimbDesciption == null) {
			if (other.reimbDesciption != null)
				return false;
		} else if (!reimbDesciption.equals(other.reimbDesciption))
			return false;
		if (reimbId != other.reimbId)
			return false;
		if (reimbResolve == null) {
			if (other.reimbResolve != null)
				return false;
		} else if (!reimbResolve.equals(other.reimbResolve))
			return false;
		if (reimbResolver == null) {
			if (other.reimbResolver != null)
				return false;
		} else if (!reimbResolver.equals(other.reimbResolver))
			return false;
		if (reimbStatus == null) {
			if (other.reimbStatus != null)
				return false;
		} else if (!reimbStatus.equals(other.reimbStatus))
			return false;
		if (reimbSubmit == null) {
			if (other.reimbSubmit != null)
				return false;
		} else if (!reimbSubmit.equals(other.reimbSubmit))
			return false;
		if (reimbType == null) {
			if (other.reimbType != null)
				return false;
		} else if (!reimbType.equals(other.reimbType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [reimbId=" + reimbId + ", reimbAmount=" + reimbAmount + ", reimbSubmit=" + reimbSubmit
				+ ", reimbResolve=" + reimbResolve + ", reimbDesciption=" + reimbDesciption + ", reimbAuthor="
				+ reimbAuthor + ", reimbResolver=" + reimbResolver + ", reimbStatus=" + reimbStatus + ", reimbType="
				+ reimbType + "]";
	}

}
