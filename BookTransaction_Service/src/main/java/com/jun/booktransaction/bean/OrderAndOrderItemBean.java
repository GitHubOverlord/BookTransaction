package com.jun.booktransaction.bean;

import java.util.List;

public class OrderAndOrderItemBean {
	private OrderBean orderBean;
	private List<OrderItemBean> orderItemBeans;
	
	public List<OrderItemBean> getOrderItemBeans() {
		return orderItemBeans;
	}
	public void setOrderItemBeans(List<OrderItemBean> orderItemBeans) {
		this.orderItemBeans = orderItemBeans;
	}
	public OrderBean getOrderBean() {
		return orderBean;
	}
	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}
	
}
