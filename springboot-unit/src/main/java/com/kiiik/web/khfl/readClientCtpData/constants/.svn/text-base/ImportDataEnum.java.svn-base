package com.kiiik.web.khfl.readClientCtpData.constants;


public class ImportDataEnum {
	
	//定义导入状态枚举
	public enum DataImportStatus{    
		IMPORTING(0),
		IMPORT_SUCCESS(1),
		IMPORT_FAILURE(2);
		
		private final int stateNum;
		
		DataImportStatus(int stateNum){
			this.stateNum = stateNum;
		}
		
		public int getStateNum() {
			return stateNum;
		}
	}	
	
	//定义导入方式枚举
	public enum DataImportMode{
			AUTO_IMPORT(0),
			IMPORT_BY_HAND(1);
			
			private final int stateNum;
			
			DataImportMode(int stateNum){
				this.stateNum = stateNum;
			}
			
			public int getStateNum() {
				return stateNum;
			}
	}
	
	//定义导入数据类别枚举
	public enum DataImportType{
			DAY_FUNDS(1),
			DAY_FORCE_TRADE(2),
			DAY_POSITION(3),
			DAY_TRANS(4),
			CURR_INVESTOR(5),
			CURR_INV_PROPERTIES(6),
			CLIENT_START_DATE(7),
			MATCH_RATE(100),
			VIP(101);
			
			private final int stateNum;
			
			DataImportType(int stateNum){
				this.stateNum = stateNum;
			}
			
			public int getStateNum() {
				return stateNum;
			}
	}
	
	public static void main(String args[]){
		System.out.println("IMPORTING="+DataImportStatus.IMPORTING.getStateNum());
		System.out.println("IMPORTING="+DataImportType.DAY_FUNDS.getStateNum());
		
	}
		
}
