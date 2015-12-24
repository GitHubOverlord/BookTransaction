package com.jun.file.dao;

import com.jun.booktransaction.bean.UserBean;

public class UserDao extends BaseDao {

	@Override
	public Class<UserBean> getT() {
		return UserBean.class;
	}

}
