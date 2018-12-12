package com.kiiik.web.khfl.reportCaculate.vo;

public class FreezeClientGrade{
	String fundAccount;
	String latestGrade;
	
	public FreezeClientGrade(String fundAccount,String latestGrade){
		this.fundAccount = fundAccount;
		this.latestGrade = latestGrade;
	}

	public String getFundAccount() {
		return fundAccount;
	}

	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}

	public String getLatestGrade() {
		return latestGrade;
	}

	public void setLatestGrade(String latestGrade) {
		this.latestGrade = latestGrade;
	}

	
	
}
