package com.kiiik.utils;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.contant.KiiikContants;

import net.sf.json.JSONObject;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月15日
 */
public class ResponseUtils {
	static Log log = LogFactory.getLog(ResponseUtils.class);
	public static void writeResult(HttpServletResponse response, ResultBean<String> result) {
        response.setCharacterEncoding(KiiikContants.UTF8);
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSONObject.fromObject(result).toString());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return ;
    }
	
}
