package com.cyb.web;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月3日上午9:03:14
 */
@Controller
@RequestMapping("api")
public class ApiController {
	
	Log log = LogFactory.getLog(ApiController.class);
	
	@GetMapping("query")
	@ResponseBody
	public String getP(String username) {
		System.out.println("查看权限和角色");
		return "信息列表!";
	}

	@ResponseBody
	@GetMapping("add")
	public String add(String name) {
		return "添加信息成功！";
	}

	@ResponseBody
	@GetMapping("delete")
	public String delete(HttpServletRequest req) {
		return "删除信息成功";
	}

	@ResponseBody
	@GetMapping("update") 
	public String update(String name) {
		return "更新信息成功！";
	}
	@ResponseBody
	@GetMapping("free") 
	public String free(String name) {
		return "free！";
	}
	
	@PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model){
        if (file.isEmpty()){
            model.addAttribute("message", "The file is empty!");
            return "/uploadStatus";
        }
        try{
            byte[] bytes = file.getBytes();
            Path path = Paths.get("D:\\data\\fileUpload/" + file.getOriginalFilename());
            Files.write(path, bytes);
            model.addAttribute("message", "succes");

        }catch (Exception e){
            e.printStackTrace();
        }
        return "/uploadStatus";
    }
}
