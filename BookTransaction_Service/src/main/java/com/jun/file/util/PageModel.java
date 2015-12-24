package com.jun.file.util;

import java.util.List;

/**
 * 自定义分页组件
 * @author Li Yong Qiang
 */
public class PageModel {
	private int currPage;		//当前页
	private int totalRecords;	//总记录数
	private List<?> list; 		//结果集
	private int pageSize;		//每页记录数
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * 获取总页数
	 * @return 总页数 
	 */
	public int getTotalPage(){
		return (totalRecords + pageSize- 1) / pageSize;
	}
	/**
	 * 获取第一页
	 * @return 第一页
	 */
	public int getFirstPage(){
		return 1;
	}
	/**
	 * 获取上一页
	 * @return 上一页
	 */
	public int getPreviousPage(){
		return currPage <= 1 ? 1 : currPage - 1;
	}
	/**
	 * 获取下一页
	 * @return 下一页
	 */
	public int getNextPage(){
		if(currPage >= getTotalPage()){
			return getLastPage();
		}
		return currPage + 1;
	}
	/**
	 * 获取最后一页
	 * @return 最后一页
	 */
	public int getLastPage(){
		//如果总页数等于0返回1，否则返回总页数
		return getTotalPage() <= 0 ? 1 : getTotalPage();
	}
}
