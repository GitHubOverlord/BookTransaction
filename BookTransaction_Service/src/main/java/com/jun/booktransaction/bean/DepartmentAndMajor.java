package com.jun.booktransaction.bean;

import java.util.List;

public class DepartmentAndMajor {
	private List<DepartmentBean> departmentBeans;
	private List<MajorBean> majorBeans;
	public DepartmentAndMajor(){
		
	}
	public DepartmentAndMajor(List<DepartmentBean> departmentBeans,List<MajorBean> majorBeans){
		this.departmentBeans = departmentBeans;
		this.majorBeans = majorBeans;
	}
	public List<DepartmentBean> getDepartmentBeans() {
		return departmentBeans;
	}
	public void setDepartmentBeans(List<DepartmentBean> departmentBeans) {
		this.departmentBeans = departmentBeans;
	}
	public List<MajorBean> getMajorBeans() {
		return majorBeans;
	}
	public void setMajorBeans(List<MajorBean> majorBeans) {
		this.majorBeans = majorBeans;
	}
	
}
