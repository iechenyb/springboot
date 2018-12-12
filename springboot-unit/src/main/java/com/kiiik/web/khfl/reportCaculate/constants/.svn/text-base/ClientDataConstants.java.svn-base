package com.kiiik.web.khfl.reportCaculate.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.khfl.dataImport.bean.QhCurrinvestorsEntity;
import com.kiiik.web.khfl.dataImport.bean.VipClientEntity;
import com.kiiik.web.khfl.reportCaculate.service.impl.ParamCalServiceImpl;

@Component
public class ClientDataConstants {
	
	@Autowired
	GenericService genericService;
	
	@Autowired
	ParamCalServiceImpl paramCalService;

	
	public static Map<String, String> ALL_VALId_CLIENT_INFO;//所有未销户客户信息 <资产账号,中文姓名>
	
	public static List<String> ALL_VALId_CLIENT;//所有有效客户
	
	public static List<String> ALL_VIP_CLIENT;//所有vip客户
	
	public static List<String> ALL_FREEZE_CLIENT;//所有有效休眠客户
	
	
	
	public ClientDataConstants(){
	}
	
	//加载所有未销户客户信息 <资产账号,中文姓名>
	public void initValidClient(){
		ALL_VALId_CLIENT_INFO = new HashMap<String,String>();
		ALL_VALId_CLIENT = new ArrayList<String>();
		QhCurrinvestorsEntity qce = new QhCurrinvestorsEntity();
		qce.setIsActive("1");
		List<QhCurrinvestorsEntity> qcList = genericService.queryDBEntityList(qce," id asc");
	//	System.out.println("-----------lqcList.size----------"+qcList.size());
		if(qcList != null){
			for (int i=0;i<qcList.size();i++){
				QhCurrinvestorsEntity temp = qcList.get(i);
				ALL_VALId_CLIENT_INFO.put(temp.getFundAccount().trim(), temp.getInvestorName().trim());
				ALL_VALId_CLIENT.add(temp.getFundAccount().trim());
			}
		}
	}
	
	//加载所有vip客户
	public void initVipClient(){
		ALL_VIP_CLIENT = new ArrayList<String>();
		VipClientEntity vce = new VipClientEntity();
		List<VipClientEntity> qcList = genericService.queryDBEntityList(vce," id asc");
//		System.out.println("-----------lqcList.size----------"+qcList.size());
		if(qcList != null){
			for (int i=0;i<qcList.size();i++){
				VipClientEntity temp = qcList.get(i);
				ALL_VIP_CLIENT.add(temp.getFundAccount().trim());
			}
		}
	}
	
	//加载所有休眠客户
	public void initFreezeClient(){
		ALL_FREEZE_CLIENT = new ArrayList<String>();
		List<String> qcList = paramCalService.queryFreezeClientList("0","1");
		//System.out.println("-----------lqcList.size----------"+ALL_FREEZE_CLIENT.size());
		if(qcList != null){
			for (int i=0;i<qcList.size();i++){
				ALL_FREEZE_CLIENT.add(qcList.get(i).trim());
			}
		}
	}
	
	
}
