package me.hncn.dhtspider.util;

import java.util.List;

public class Pagination<T> {

	private boolean hasPrePage;
	private boolean hasNextPage;
	private String id;
	private long totalRecords;
	private List<T> pageList;
	private int pageSize;
	private long totalPage;
	private int currentPage;
	private int beginIndex;
	
	public Pagination(int pageSize, long totalRecords, int curPage){
		this.pageSize=pageSize;
		this.currentPage=curPage;
		this.totalRecords=totalRecords;
		this.totalPage = getTotalPage(this.pageSize,this.totalRecords);
		
		this.hasPrePage=true;
		this.hasNextPage=true;
		
		if(currentPage==1||totalPage==0)
			this.hasPrePage=false;
		
		if(currentPage==totalPage||totalPage==0)
			this.hasNextPage=false;

		if( curPage == 0) {
			this.beginIndex = 0;
		} else {
			this.beginIndex = (curPage - 1) * pageSize;
		}
		
	}
	
	public boolean getHasPrePage() {
		return hasPrePage;
	}
	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}
	public boolean getHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<T> getPageList() {
		return pageList;
	}
	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getBeginIndex() {
		return beginIndex;
	}
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	
	private static long getTotalPage(int everyPage, long totalRecords) {
		long totalPage = 0;

	    if (totalRecords % everyPage == 0)
	      totalPage = totalRecords / everyPage;
	    else {
	      totalPage = totalRecords / everyPage + 1;
	    }
	    return totalPage;
	  }

	@Override
	public String toString() {
		return "PageBean [hasPrePage=" + hasPrePage + ", hasNextPage="
				+ hasNextPage + ", id=" + id + ", totalRecords=" + totalRecords
				+ ", pageList=" + pageList + ", pageSize=" + pageSize
				+ ", totalPage=" + totalPage + ", currentPage=" + currentPage
				+ ", beginIndex=" + beginIndex + "]";
	}
}
