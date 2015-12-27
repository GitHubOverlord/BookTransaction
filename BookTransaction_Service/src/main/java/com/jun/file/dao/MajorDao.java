package com.jun.file.dao;

import com.jun.booktransaction.bean.MajorBean;

public class MajorDao extends BaseDao{
	@Override
	public Class<MajorBean> getT() {
		return MajorBean.class;
	}
}
