package com.kiiik.vas.example.service;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.page.Pagination;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.kiiik.vas.example.dao.UserMapper;
import com.kiiik.vas.example.model.MyUser;
/**
 *作者 : iechenyb<br>
 *类描述: sql直接写在接口方法的注解上<br>
 *创建时间: 2018年5月4日
 */
@Service
public class MyUserServiceImpl2 {
	Log log = LogFactory.getLog(MyUserServiceImpl2.class);
	
	@Autowired
	UserMapper userDao;
	
	public MyUser getUser(String account){
		return userDao.findMyUsersByName(account);
	}
	public void save(MyUser user){
		userDao.insert(user);
	}
	public void update(MyUser user){
		userDao.updateMyUser(user);
	}
	public void delete(Integer id){
		userDao.deleteMyUserById(id);
	}
	public List<MyUser> getUserList(){
		return userDao.getAllMyUsers();
	}
	
	public List<MyUser> selectMyUserByPage( int pageNo,int pageSize) {
    	long total = PageHelper.count(new ISelect() {
    	    @Override
    	    public void doSelect() {
    	    	userDao.getAllMyUsers();
    	    }
    	});//获取记录总数
       Pagination page = new Pagination(pageNo, pageSize, total);//重新计算分页数据
      //根据总页数 计算最大页数
       PageHelper.startPage(page.getCurrentPage(),pageSize,true); // 设置分页，参数1=页数，参数2=每页显示条数
	    return userDao.getAllMyUsers();
	}
}
