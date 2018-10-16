package com.aiyi.util.table;

/**
 * DateTable服务端处理封装体
 * @author 郭胜凯
 * @time 2016年3月23日下午1:55:18
 * @email 719348277@qq.com
 */
public class TableDate {

	private int draw;
	
	/**
	 * 记录总数
	 */
	private long recordsTotal;
	
	/**
	 * 过滤后的总数
	 */
	private long recordsFiltered;
	
	/**
	 * 记录对象
	 */
	private Object data;
	
	private long length;
	
	private long start;

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}
	
	
}
