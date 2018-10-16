package com.aiyi.util.html.bean;

import java.io.File;

public class HtmlFile{
	private String src;
	
	private File file;

	public HtmlFile(String src, File file){
		this.file = file;
		this.src = src;
	}
	
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
}
