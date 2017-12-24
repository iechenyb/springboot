package com.cyb.app.filter;

import org.springframework.web.filter.CharacterEncodingFilter;

public class  FiltercharacterEncodingFilter{
	public FiltercharacterEncodingFilter() {
	  CharacterEncodingFilter characterEncodingFilter =new CharacterEncodingFilter();
	  characterEncodingFilter.setEncoding("UTF-8");
	  characterEncodingFilter.setForceEncoding(true);
	  //return characterEncodingFilter;
	  }
}