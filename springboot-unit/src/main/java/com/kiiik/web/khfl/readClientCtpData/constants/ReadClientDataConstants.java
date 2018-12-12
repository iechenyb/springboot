package com.kiiik.web.khfl.readClientCtpData.constants;

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
public class ReadClientDataConstants {
	
	public static String AUTO_IMPORT_OPERATOR = "auto_import";
	
	public static String ACCESS_IMPORT_OPERATOR = "wangmeijuan";
	
	
	public ReadClientDataConstants(){
	}
	
}
