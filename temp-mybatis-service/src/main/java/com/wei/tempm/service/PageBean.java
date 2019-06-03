package com.wei.tempm.service;

import java.util.List;

public class PageBean<T> {
	public PageBean() {

	}

	public PageBean(int allRows, int pageNo, int pageSize) {
		this.allRows = allRows;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	private long allRows;
	private int allPages;
	private int pageNo;
	private int pageSize;
	private List<T> lst;

	public long getAllRows() {
		return allRows;
	}

	public void setAllRows(long allRows) {
		this.allRows = allRows;
	}

	public int getAllPages() {
		return allPages;
	}

	public void setAllPages(int allPages) {
		this.allPages = allPages;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getLst() {
		return lst;
	}

	public void setLst(List<T> lst) {
		this.lst = lst;
	}

}
