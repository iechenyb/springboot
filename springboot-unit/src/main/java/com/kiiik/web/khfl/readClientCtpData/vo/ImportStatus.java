package com.kiiik.web.khfl.readClientCtpData.vo;

public class ImportStatus{
	int importType;//1：日资金数据  2：日强平数据 3：日持仓明细 4：日成交明细 5：客户信息 6：客户属性 7：客户开始交易日期
	int lastestImportStatus; //0:正在导入 1：导入成功 2：导入失败
	String lastestBeginDate;
	String lastestEndDate;
	
	
	public int getImportType() {
		return importType;
	}
	public void setImportType(int importType) {
		this.importType = importType;
	}
	public int getLastestImportStatus() {
		return lastestImportStatus;
	}
	public void setLastestImportStatus(int lastestImportStatus) {
		this.lastestImportStatus = lastestImportStatus;
	}
	public String getLastestBeginDate() {
		return lastestBeginDate;
	}
	public void setLastestBeginDate(String lastestBeginDate) {
		this.lastestBeginDate = lastestBeginDate;
	}
	public String getLastestEndDate() {
		return lastestEndDate;
	}
	public void setLastestEndDate(String lastestEndDate) {
		this.lastestEndDate = lastestEndDate;
	}
	
	
}
