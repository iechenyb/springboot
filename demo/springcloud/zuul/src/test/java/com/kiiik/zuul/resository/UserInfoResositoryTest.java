package com.kiiik.zuul.resository;

import com.kiiik.zuul.dataObject.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoResositoryTest {

    @Autowired
    private UserInfoResository resository;

    @Test
    public void findOneTesr() {
        UserInfo ex = new UserInfo();
        ex.setUserName("admin");
        Example<UserInfo> example = Example.of(ex);
        Optional<UserInfo> opt = resository.findOne(example);
        if (opt.isPresent()) {
            UserInfo userInfo = opt.get();
            System.out.println(userInfo.toString());
        }
    }

    @Test
    public void findOnebyUserNameTesr() {
        List<UserInfo> result = resository.findByuserName("admin");
        Assert.assertNotEquals(null,result.size());
        System.out.println(result.get(0).toString());
    }


    @Test
    @Transactional
    public void saveTest() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("zchq88");
        userInfo.setUserPassword("zchq88");
        userInfo.setUserPhone("13761126503");
        UserInfo result = resository.save(userInfo);
        Assert.assertNotNull(result);
//        Assert.assertNotEquals(null,result);
    }
}