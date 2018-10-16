package com.aiyi.util.page;

/**
 * 页面封装类
 * @author 郭胜凯
 * @time 2016年6月7日下午5:07:26
 * @email 719348277@qq.com
 *
 */
public class Page {

	/**
	 * 起始条数
	 */
	private int start;
	
	/**
	 * 结束条数
	 */
	private int end;

	/**
	 * 当前页
	 */
	private boolean thisPage = false;
	
	/**
	 * 页码
	 */
	private int pageNum;
	
	public Page(int start, int end, boolean thisPage){
		this.start = start;
		this.end = end;
		this.thisPage = thisPage;
		int length = end - start + 1;
		pageNum = start / length + 1;
	}
	
	public Page(int start, int end){
		this.start = start;
		this.end = end;
		int length = end - start;
		pageNum = start / length + 1;
	}
	
	
	public int getStart() {
		return start < 0 ? 0 : start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return start < 0 ? end - start : end;
	}

	public void setEnd(int end) {
		this.end = end;
	}


	public boolean isThisPage() {
		return thisPage;
	}


	public void setThisPage(boolean thisPage) {
		this.thisPage = thisPage;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	
}
