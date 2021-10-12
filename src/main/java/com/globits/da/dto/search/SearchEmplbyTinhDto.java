package com.globits.da.dto.search;

import java.util.UUID;

public class SearchEmplbyTinhDto {

	private UUID id;
	private int pageIndex;
	private int pageSize;
	private String keyword;
	private String orderBy;
	private String text;
	public UUID getId() {
		return id;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public String getKeyword() {
		return keyword;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public String getText() {
		return text;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
