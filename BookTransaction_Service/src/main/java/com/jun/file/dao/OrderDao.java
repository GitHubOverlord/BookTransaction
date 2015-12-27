package com.jun.file.dao;

import com.jun.booktransaction.bean.OrderBean;

public class OrderDao extends BaseDao{

	@Override
	public Class<OrderBean> getT() {
		return OrderBean.class;
	}

}
