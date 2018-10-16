package com.aiyi.base.dao;

import java.util.List;

import com.aiyi.base.pojo.TestUserPo;

/**
 * UserDao的接口类，也属于爱易CRUD的插件扩展类。这个类一般情况下不用创建。当需要重载或者新增REUD框架的一些方法时，可以声明接口类
 * 本接口类重载了add()方法，使add方法可以一次性插入多条数据，极大的提高了数据批量增加的效率
 * @author 郭胜凯
 * @time 2016年6月28日下午12:51:29
 * @email 719348277@qq.com
 *
 */
public interface UserDaoInterface {

	int add(List<TestUserPo> list);
}
