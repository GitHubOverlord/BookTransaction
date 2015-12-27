package com.jun.file.dao;

import com.jun.booktransaction.bean.DepartmentBean;


public class DepartmentDao extends BaseDao{

	@Override
	public Class<DepartmentBean> getT() {
		return DepartmentBean.class;
	}
	
}
