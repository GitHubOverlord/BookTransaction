package com.jun.file.dao;

import com.jun.booktransaction.bean.OrderItemBean;

public class OrderItemDao extends BaseDao{

	@Override
	public Class<OrderItemBean> getT() {
		return OrderItemBean.class;
	}

}
