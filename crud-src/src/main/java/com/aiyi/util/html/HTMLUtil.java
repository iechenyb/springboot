package com.aiyi.util.html;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aiyi.util.html.bean.HtmlFile;

/**
 * HTML操作工具类
 * @author 郭胜凯
 * @time 2016年5月27日下午12:19:42
 * @email 719348277@qq.com
 *
 */
public class HTMLUtil {

	/**
	 * 取出HTML中的所有资源地址
	 * @param html
	 * @return
	 */
	public static List<String> listSrc(String html){
		
		List<String> list = new ArrayList<>();
		//[".png|jpg|jpeg|gif|bmp"]
		//".flv|swf|mkv|avi|rm|rmvb|mpeg|mpg",
		//".ogg|ogv|mov|wmv|mp4|webm|mp3|wav|mid"]
		//".rar|zip|tar|gz|7z|bz2|cab|iso",
        //".doc|docx|xls|xlsx|ppt|pptx|pdf|txt|md|xml"
		//String regexImage = "<img|<a.+?(src=|href)=\"(.+?(png|jpg|jpeg|gif|bmp|flv|swf|mkv|avi|rm|rmvb|mpeg|mpg|ogg|ogv|mov|wmv|mp4|webm|mp3|wav|mid|rar|zip|tar|gz|7z|bz2|cab|iso|doc|docx|xls|xlsx|ppt|pptx|pdf|txt|md|xml))\".+?/?>";
		String regexImage = "<img.+?src=\"(.+?)\".+?/?>";
		String ImageStr = "";
		String ImageSrcStr = "";

		Pattern p = Pattern.compile(regexImage, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(html);

		while (m.find()) {
			ImageStr = m.group();
			ImageSrcStr = m.group(1);
			System.out.println(ImageStr);
			list.add(ImageSrcStr);
		}
		
		String regexFile = "<a.+?href=\"(.+?(png|jpg|jpeg|gif|bmp|flv|swf|mkv|avi|rm|rmvb|mpeg|mpg|ogg|ogv|mov|wmv|mp4|webm|mp3|wav|mid|rar|zip|tar|gz|7z|bz2|cab|iso|doc|docx|xls|xlsx|ppt|pptx|pdf|txt|md|xml))\".+?/?>";
		String FileStr = "";
		String FileSrcStr = "";

		Pattern f_p = Pattern.compile(regexFile, Pattern.CASE_INSENSITIVE);
		Matcher f_m = f_p.matcher(html);

		while (f_m.find()) {
			FileStr = f_m.group();
			FileSrcStr = f_m.group(1);
			System.out.println(FileStr);
			list.add(FileSrcStr);
		}
		
		return list;

	}
	
	/**
	 * 从HTML代码中获取指向本地文件的路径
	 * @param html
	 * @return
	 */
	public static List<HtmlFile> listLocalFileByHtml(String html, String pojictPath){
		
		//先取到所有文件
		List<String> listSrc = listSrc(html);
		
		List<HtmlFile> files = new ArrayList<>();
		
		for (String src : listSrc) {
			
			if (src.length() > 5 && !src.substring(0, 4).equals("http")) {
				//属于本地文件
				
				File file = new File(pojictPath + "/" + src);
				
				files.add(new HtmlFile(src, file));
				
			}
		}
		
		return files;
	}
	
}
