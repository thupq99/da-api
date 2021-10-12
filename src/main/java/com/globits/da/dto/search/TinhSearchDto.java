package com.globits.da.dto.search;

import java.util.Date;
import java.util.UUID;

public class TinhSearchDto {

	private UUID id;
	private int pageIndex;
	private int pageSize;
	private String keyword;
	private Boolean voided;
	private String orderBy;
	private String text;
	private Date fromDate;
	private Date toDate;

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

	public Boolean getVoided() {
		return voided;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public String getText() {
		return text;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public Date getToDate() {
		return toDate;
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

	public void setVoided(Boolean voided) {
		this.voided = voided;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}
