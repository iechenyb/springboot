package com.aiyi.util.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 * @author 郭胜凯
 * @time 2016年6月7日下午5:07:44
 * @email 719348277@qq.com
 *
 */
public class PageUtil {

	/**
	 * 总条数
	 */
	private int count;
	
	/**
	 * 开始条数
	 */
	private int start;
	
	/**
	 * 每页多少条
	 */
	private int length;
	
	/**
	 * 总页数
	 */
	private int pageCount;
	
	/**
	 * 当前页码
	 */
	private int pageNum;
	
	/**
	 * 上一页
	 */
	private Page previousPage;
	
	/**
	 * 下一页
	 */
	private Page nextPage;
	
	/**
	 * 页码列表
	 */
	private List<Page> page = new ArrayList<>();
	
	
	/**
	 * 初始化分页
	 * @param count 总条数
	 * @param start 起始条数
	 * @param length 每页长度
	 * @param pageSize 页码数量
	 */
	public PageUtil(int count, int start, int length, int pageSize){
		this.count = count;
		this.length = length;
		this.start = start;
		this.setPageNum(start / length + 1);
		//本页结束
		int thisEnd = start + length -1;
		
		
		//总页数
		pageCount = count % length > 0 ? count / length + 1 : count / length;
		
		//上一页
		previousPage = new Page(start - length, thisEnd - length);
		
		//下一页
		nextPage = new Page(start + length, thisEnd + length);
		
		//最大页码数
		int pagelength = pageSize * length > count ? (count % length == 0 ? count / length : count / length + 1)  : pageSize;
		
		//当前页
		int thisPage = ((start + 1) % length == 0 ? (start + 1) / length : (start + 1) / length + 1 );
		
		//左按钮
		for (int i = thisPage - pagelength / 2; i < thisPage; i++) {

			if (i > 0) {
				page.add(new Page((i - 1) * length, i * length -1, false));
			}
		}
		
		//当前按钮
		page.add(new Page(start, thisEnd, true));
		
		//右按钮
		for (int i = thisPage + 1; i <= thisPage + pagelength / 2; i++) {
			page.add(new Page((i - 1) * length, i * length -1, false));
		}
		
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public int getStart() {
		return start;
	}


	public void setStart(int start) {
		this.start = start;
	}


	public int getLength() {
		return length;
	}


	public void setLength(int length) {
		this.length = length;
	}


	public int getPageCount() {
		return pageCount;
	}


	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}


	public Page getPreviousPage() {
		return previousPage;
	}


	public void setPreviousPage(Page previousPage) {
		this.previousPage = previousPage;
	}


	public Page getNextPage() {
		return nextPage;
	}


	public void setNextPage(Page nextPage) {
		this.nextPage = nextPage;
	}


	public List<Page> getPage() {
		return page;
	}


	public void setPage(List<Page> page) {
		this.page = page;
	}


	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	
}
