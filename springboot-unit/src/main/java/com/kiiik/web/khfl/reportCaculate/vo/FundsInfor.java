package com.kiiik.web.khfl.reportCaculate.vo;

public class FundsInfor {
	String fund_account;
	Integer cc;// 是否穿仓
	Double fxd;// 风险度
	Integer zb;// 是否发生追加保证金

	public String getFund_account() {
		return fund_account;
	}

	public void setFund_account(String fund_account) {
		this.fund_account = fund_account;
	}

	public Integer getCc() {
		return cc;
	}

	public void setCc(Integer cc) {
		this.cc = cc;
	}

	public Double getFxd() {
		return fxd;
	}

	public void setFxd(Double fxd) {
		this.fxd = fxd;
	}

	public Integer getZb() {
		return zb;
	}

	public void setZb(Integer zb) {
		this.zb = zb;
	}

}
