package com.cyb.service;
import java.io.IOException;


import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.cyb.file.FileUtils;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月7日
 */
@Service
public class AwareService implements BeanNameAware, ResourceLoaderAware {
    String beanName;
    ResourceLoader resourceLoader;

    @Override
    public void setBeanName(String s) {
        this.beanName = s;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void outputInfo() {
        System.out.println("bean在容器中的名称是："+beanName);
        Resource resource = resourceLoader.getResource("classpath:aware/text.txt");
        try {
            System.out.println("资源文件的内容是："+new String(FileUtils.readInputStream(resource.getInputStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
