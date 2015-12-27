package com.jun.file.dao;

import com.jun.booktransaction.bean.ProjectBean;

public class ProjectDao extends BaseDao{

	@Override
	public Class<ProjectBean> getT() {
		return ProjectBean.class;
	}
	
}
