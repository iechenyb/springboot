package com.kiiik.vas.example.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.exception.MyException1;
import com.kiiik.pub.exception.KiiikException;
import com.kiiik.vas.example.model.MyUser;
import com.kiiik.vas.example.param.ValidBean;
import com.kiiik.vas.example.service.MyUserServiceImpl;
/**
 * 
 * @author DHUser
 *  样例控制层代码
 */
@RestController
@RequestMapping("common")
public class ExampleController {
	Log log = LogFactory.getLog(ExampleController.class);
	@Autowired
	MyUserServiceImpl userService;
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 单个属性是否可行，测试结果是否否定的！<br>
	 *单个或者若干个属性，仍然需要进行单独判断
	 *创建时间: 2017年7月15日hj12
	 *@param name
	 *@param bindingResult
	 *@return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/jsr303")
	@ResponseBody
	public ResultBean<String> validate(@Valid @Size(min=3) @ModelAttribute String name
			,BindingResult bindingResult){
		    List<ObjectError> errorList = bindingResult.getAllErrors();
	        if (bindingResult.hasErrors()) {
	            for (int i = 0; i < errorList.size(); i++) {
	                System.out.println("校验字段信息"+errorList.get(i).getDefaultMessage());
	            }
	        }
		return new ResultBean<String>("hello "+name).success();
	}
	/*swagger 与jsr303不兼容
	 *在浏览器中单独访问
	 *http://localhost:8080/common/jsr303form?name=123&age=10
	 *可以 
	 *解决方案：
	 *在对象属性前  添加@ModelAttribute注解成功解决！
	 * **/
	@SuppressWarnings("unchecked")
	@GetMapping("/jsr303form")
	public ResultBean<List<ObjectError>> validateForm(
			@Valid  @ModelAttribute  ValidBean bean,
			BindingResult bindingResult) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 List<ObjectError> errorList = bindingResult.getAllErrors();
	        if (bindingResult.hasErrors()) {
	            List<String> mesList=new ArrayList<String>();
	            for (int i = 0; i < errorList.size(); i++) {
	                mesList.add(errorList.get(i).getDefaultMessage());
	            }
	            map.put("status", false);
	            map.put("error", mesList);
	        }
	        if(errorList == null) errorList = new ArrayList<ObjectError>();
	        System.out.println("验证结果："+map);
	     /*		for(FieldError error : bindingResult.getFieldErrors()){
		    System.out.println(error.getField()+"=="+error.getDefaultMessage()+"==="+error.getCode());
	     }
        String msg =  bindingResult.hasErrors() ? 
		bindingResult.getFieldError().getDefaultMessage() : "right";*/
		return new ResultBean<List<ObjectError>>().data(errorList).success();
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/jsr303formJson")
	public ResultBean<List<ObjectError>> validateFormJson(
			@Valid  @ModelAttribute  @RequestBody ValidBean bean,
			BindingResult bindingResult) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 List<ObjectError> errorList = bindingResult.getAllErrors();
	        if (bindingResult.hasErrors()) {
	            List<String> mesList=new ArrayList<String>();
	            for (int i = 0; i < errorList.size(); i++) {
	                mesList.add(errorList.get(i).getDefaultMessage());
	            }
	            map.put("status", false);
	            map.put("error", mesList);
	        }
	        if(errorList == null) errorList = new ArrayList<ObjectError>();
	        System.out.println("验证结果："+map);
	     /*		for(FieldError error : bindingResult.getFieldErrors()){
		    System.out.println(error.getField()+"=="+error.getDefaultMessage()+"==="+error.getCode());
	     }
        String msg =  bindingResult.hasErrors() ? 
		bindingResult.getFieldError().getDefaultMessage() : "right";*/
		return new ResultBean<List<ObjectError>>().data(errorList).success();
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 直接定义bean对象，填写指定的错误信息<br>
	 *创建时间: 2017年7月15日hj12
	 *@param name
	 *@param hasEx
	 *@return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/hello")
	public ResultBean<String> sayHello(String name,boolean hasEx) {
		if(hasEx){
			return new ResultBean<String>("类型错误").fail();
		}
		return new ResultBean<String>("hello "+name).success();
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 手动封装异常信息直接返回！<br>
	 *创建时间: 2017年7月15日hj12
	 *@param th
	 *@return
	 *@throws KiiikException
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/exResultBean")//当integer为字符串时，统一处理可以获取异常信息
	public ResultBean<String> exResultBean(boolean th) throws  KiiikException {
		if(th){
			return new ResultBean<String>("类型错误").fail();
		}
		return new ResultBean<String>("hello ").success();
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 运行时异常，自动抛出异常<br>
	 *创建时间: 2017年7月15日hj12
	 *@param th
	 *@return
	 *@throws Exception
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/throwJvmEx")//当integer为字符串时，统一处理可以获取异常信息
	public ResultBean<String> throwJvmEx(boolean th) throws Exception {
		if(th){
			System.out.println("aa="+1/0);
		}
		return new ResultBean<String>("hello ").success();
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 抛出JVM定义的底层的异常<br>
	 *创建时间: 2017年7月15日hj12
	 *@param th
	 *@return
	 *@throws Exception
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/throwRunJvmEx")//当integer为字符串时，统一处理可以获取异常信息
	public ResultBean<String> throwRunJvmEx(boolean th) throws  Exception {
		if(th){
			throw new NullPointerException("空指针");
		}
		return new ResultBean<String>("hello ").success();
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 抛出自定义子类型异常<br>
	 *创建时间: 2017年7月15日hj12
	 *@param th
	 *@return
	 *@throws MyException1
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/exthrow")//当integer为字符串时，统一处理可以获取异常信息
	public ResultBean<String> exThrow(boolean th) throws  MyException1 {
		if(th){
			throw new MyException1("类型错误");//抛出异常后，返回值直接为空！！！！
		}
		return new ResultBean<String>("hello ").success();
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述:抛出自定义基础类型异常<br>
	 *创建时间: 2017年7月15日hj12
	 *@param th
	 *@return
	 *@throws KiiikException
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/exBaseThrow")//当integer为字符串时，统一处理可以获取异常信息
	public ResultBean<String> exBaseThrow(boolean th) throws  KiiikException {
		if(th){
			throw new KiiikException("类型错误");//抛出异常后，返回值直接为空！！！！
		}
		return new ResultBean<String>("hello ").success();
	}
	@GetMapping("get")
	public MyUser getUser(String acc) {
		return userService.getUser(acc);
	}
	
	@GetMapping("list")
	public List<MyUser> listUser() {
		return userService.getUserList();
	}
	
	@GetMapping("save")
	public MyUser save() {
		MyUser user = new MyUser();
		user.setId(UUID.randomUUID().toString());
		user.setAccount("sssss");
		user.setPassword("sfsddf");
		userService.save(user);
		return user;
	}
	
	@GetMapping("update")
	public MyUser update(MyUser user) {
		userService.update(user);
		return user;
	}
	
	@GetMapping("delete")
	@ResponseBody
	public String delete(Integer id) {
		userService.delete(id);
		return "删除成功";
	}
	@SuppressWarnings("unchecked")
	@GetMapping("page")
	@ResponseBody
	public ResultBean<List<MyUser>> queryByPage(int pageIndex,int pageSize) {
		if(pageIndex<=0){
			return new ResultBean<>().fail("页面序号必须大于0");
		}
		if(pageSize<=0){
			return new ResultBean<>().fail("没页记录数必须大于0");
		}
		return new ResultBean<>().data(userService.selectMyUserByPage(pageIndex,pageSize));
	}
}
