package com.cyb.utils;

import java.io.IOException;

import com.cyb.draw.HttpClientUtil;
import com.cyb.url.UrlUtils;

public class Main {
public static void main(String[] args) throws IOException {
	System.out.println("sfd");
	UrlUtils.downLoadFromUrl("http://www.28ma.net/index.php?get=neirong&username=17NS1246227907&type=housandanshi&time=", "aaa.txt", "d:/data/");
}
}
