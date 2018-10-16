package com.aiyi.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.aiyi.base.dao.ImgDao;
import com.aiyi.base.dao.UserDao;
import com.aiyi.base.pojo.TestImgResourcePo;
import com.aiyi.base.pojo.TestUserPo;
import com.aiyi.base.service.TestService;
import com.aiyi.core.beans.Method;
import com.aiyi.core.sql.where.C;
import com.aiyi.core.util.Formatter;
import com.aiyi.core.util.FormatterSql;

@Service
public class TestServiceImpl implements TestService {
	
	@Resource
	private ImgDao imgDao;
	
	@Resource
	private UserDao userDao;

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public int addList() {
		// TODO Auto-generated method stub
		
		List<TestUserPo> users = new ArrayList<>();
		List<TestImgResourcePo> imgs = new ArrayList<>();
		
		for (int i = 0; i < 3000; i++) {
			TestImgResourcePo imgResourcePo = new TestImgResourcePo();
			imgResourcePo.setUrl("http://imgresource.com/url/" + i + ".jpg");
			imgs.add(imgResourcePo);
		}
		int add = imgDao.add(imgs);
		
		for (int i = 0; i < 3000; i++) {
			TestUserPo testUserPo = new TestUserPo();
			testUserPo.setImgId(imgs.get(i).getId());
			testUserPo.setName("用户" + i);
			users.add(testUserPo);
		}
		add += userDao.add(users);
		
		
		return add;
	}

	@Override
	public List<TestUserPo> listUser() {
		// TODO Auto-generated method stub
		
		Formatter fmt = new FormatterSql();
		fmt.addFmt("imgUrl", "url", TestImgResourcePo.class, Method.where("[fmt.R].id", C.EQ, "[fmt.L].img_id"));
		
		return userDao.listFormat(Method.createDefault(), fmt);
	}
}
