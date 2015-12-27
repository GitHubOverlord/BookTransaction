package com.jun.book.servlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.jun.booktransaction.bean.OrderAndOrderItemBean;
import com.jun.booktransaction.bean.OrderBean;
import com.jun.booktransaction.bean.OrderItemBean;
import com.jun.booktransaction.bean.UserBean;
import com.jun.file.dao.OrderDao;
import com.jun.file.dao.OrderItemDao;
import com.jun.file.util.BaseActionSupport;
import com.jun.file.util.HibernateUtil;
import com.jun.file.util.PrintObjectToJson;
import com.jun.file.util.SessionUtils;

/**
 * 订单action
 */
public class OrderAction extends BaseActionSupport {

	private static final long serialVersionUID = 8031997397426997137L;
	/**
	 * 所有订单的Json
	 */
	private String orderJson;
	/**
	 * 联系人的电话号码
	 */
	private String orderContactPhone;
	/**
	 * 联系的QQ
	 */
	private String orderContactQQ;
	/**
	 * 订单描述
	 */
	private String orderDescribe;
	/**
	 * 搜索订单key
	 */
	private String searchKey;
	/**
	 * 订单发布状态
	 */
	private boolean publishStatus;
	private String searchJuniorClassValue;
	private String searchDepartmentValue;
	private String searchMajorValue;
	private String searchProjectValue;
	private String includeJuniorClass;
	private String includeBookName;
	private int limit;
	private int offset;
	private String remind = "";

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public boolean isPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(boolean publishStatus) {
		this.publishStatus = publishStatus;
	}

	public String getSearchJuniorClassValue() {
		return searchJuniorClassValue;
	}

	public void setSearchJuniorClassValue(String searchJuniorClassValue) {
		this.searchJuniorClassValue = searchJuniorClassValue;
	}

	public String getSearchDepartmentValue() {
		return searchDepartmentValue;
	}

	public void setSearchDepartmentValue(String searchDepartmentValue) {
		this.searchDepartmentValue = searchDepartmentValue;
	}

	public String getSearchMajorValue() {
		return searchMajorValue;
	}

	public void setSearchMajorValue(String searchMajorValue) {
		this.searchMajorValue = searchMajorValue;
	}

	public String getSearchProjectValue() {
		return searchProjectValue;
	}

	public void setSearchProjectValue(String searchProjectValue) {
		this.searchProjectValue = searchProjectValue;
	}

	public String getOrderJson() {
		return orderJson;
	}

	public void setOrderJson(String orderJson) {
		this.orderJson = orderJson;
	}

	public String getOrderContactPhone() {
		return orderContactPhone;
	}

	public void setOrderContactPhone(String orderContactPhone) {
		this.orderContactPhone = orderContactPhone;
	}

	public String getOrderContactQQ() {
		return orderContactQQ;
	}

	public void setOrderContactQQ(String orderContactQQ) {
		this.orderContactQQ = orderContactQQ;
	}

	public String getOrderDescribe() {
		return orderDescribe;
	}

	public void setOrderDescribe(String orderDescribe) {
		this.orderDescribe = orderDescribe;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	/**
	 * 发布订单
	 */
	public void publishOrder() {
		int status = 0;
		UserBean userBean = SessionUtils.getSession();
		OrderBean orderBean = new OrderBean();
		if (userBean == null || userBean.getUserName().equals("")) {// 没有登录的情况下
			status = 2;
			remind = "您未登录";
		} else {// 登录了,先删除订单信息，再添加订单信息
			OrderDao orderDao = new OrderDao();
			HashMap<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put(OrderBean.ATTR_BELONG_USER_NAME,
					userBean.getUserName());
			orderDao.deleteWhere(orderMap);
			OrderItemDao orderItemDao = new OrderItemDao();
			HashMap<String, Object> orderItemMap = new HashMap<String, Object>();
			orderItemMap.put(OrderItemBean.ATTR_BELONG_ORDER,
					userBean.getUserName());
			orderItemDao.deleteWhere(orderItemMap);// 删除订单
			orderBean.setBelongUserName(userBean.getUserName());
			orderBean.setContactPhone(orderContactPhone);
			orderBean.setContactQQ(orderContactQQ);
			orderBean.setCreateDate(new Date().getTime());
			orderBean.setOrderDescribe(orderDescribe);
			orderBean.setBelongUserMajorName(userBean.getMajor());
			orderBean.setIncludeBookName(includeBookName);
			orderBean.setIncludeJuniorClass(includeJuniorClass);
			Set<OrderItemBean> orderItemBeans = new HashSet<OrderItemBean>();
			for (int i = 0; i < 10; i++) {// 填充书籍
				OrderItemBean orderItemBean = new OrderItemBean();
				orderItemBean.setBelongJuniorClass(1);
				orderItemBean.setBelongMajorId(userBean.getMajor());
				orderItemBean.setBelongUserName(userBean.getUserName());
				orderItemBean.setBookName("书名" + i);
				orderItemBean.setDescrible("这是描述" + i);
				orderItemBean.setHaveExerciseBook(1);
				orderItemBean.setOldDegree(90);
				orderItemBean.setPrice(10.00);
				orderItemBeans.add(orderItemBean);
			}
			orderBean.setSet(orderItemBeans);
			orderDao.save(orderBean);// 填充订单
		}
		PrintObjectToJson.print(response, status, remind, orderBean);
	}

	// /**
	// * 生成测试订单
	// */
	// public void createOrder() {
	// int status = 0;
	// UserBean userBean = SessionUtils.getSession();
	// List<OrderItemBean> orderItemBeans = new ArrayList<OrderItemBean>();
	// OrderBean orderBean = new OrderBean();
	// if (userBean == null || userBean.getUserName().equals("")) {// 没有登录的情况下
	// status = 2;
	// remind = "您未登录";
	// } else {// 登录了,先删除订单信息，再添加订单信息
	// System.out.println(userBean.getUserName() + "\n");
	// System.out.println(orderContactPhone + "\n");
	// System.out.println(orderContactQQ + "\n");
	// System.out.println(new Date().getTime() + "\n");
	// System.out.println(orderDescribe + "\n");
	// OrderDao orderDao = new OrderDao();
	// OrderItemDao orderItemDao = new OrderItemDao();
	// orderBean.setBelongUserName(userBean.getUserName());
	// orderBean.setContactPhone(orderContactPhone);
	// orderBean.setContactQQ(orderContactQQ);
	// orderBean.setCreateDate(new Date().getTime());
	// orderBean.setOrderDescribe(orderDescribe);
	// orderBean.setPublishStatus(true);
	// orderDao.save(orderBean);// 填充订单
	// for (int i = 0; i < 10; i++) {// 填充书籍
	// OrderItemBean orderItemBean = new OrderItemBean();
	// orderItemBean.setBelongJuniorClass(1);
	// orderItemBean.setBelongMajorId(userBean.getMajor());
	// orderItemBean.setBelongUserName(userBean.getUserName());
	// orderItemBean.setBookName("书名" + i);
	// orderItemBean.setDescrible("这是描述" + i);
	// orderItemBean.setHaveExerciseBook(1);
	// orderItemBean.setOldDegree(90);
	// orderItemBean.setPrice(10.00);
	// orderItemDao.save(orderItemBean);
	// orderItemBeans.add(orderItemBean);
	// }
	// }
	// OrderAndOrderItemBean orderAndOrderItemBean = new
	// OrderAndOrderItemBean();
	// orderAndOrderItemBean.setOrderBean(orderBean);
	// orderAndOrderItemBean.setOrderItemBeans(orderItemBeans);
	// PrintObjectToJson
	// .print(response, status, remind, orderAndOrderItemBean);
	// }

	/**
	 * 获取我自己的订单,status=1表示获取成功，status=2表示未登录，或者登录失败
	 */
	public void getMyOrder() {
		UserBean userBean = SessionUtils.getSession();
		int status = 0;
		List<OrderBean> orderBeans = null;
		if (userBean == null) {// 没有登录
			status = 2;// 未登录
			remind = "未登录";
		} else {
			System.out.println(userBean.getUserName());
			status = 1;
			remind = "获取数据成功！";
			OrderDao orderDao = new OrderDao();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(OrderBean.ATTR_BELONG_USER_NAME, userBean.getUserName());
			orderBeans = orderDao.findList(map);

		}
		PrintObjectToJson.print(response, status, remind, orderBeans);
	}

	/**
	 * 根据条件获取订单
	 */
	public void getAllOrderByCondition() {
		OrderDao orderDao = new OrderDao();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		List<OrderBean> orderBeans = orderDao.findList(hashMap, offset, limit);
		int status = 1;
		remind = "获取数据成功";
		PrintObjectToJson.print(response, status, remind, orderBeans);
	}

	/**
	 * 搜索订单
	 */
	public void searchOrder() {
		String sql = "select orders from OrderBean as orders where orders.includeBookName like ?";
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(sql);
		query.setString(0, "%" + searchKey + "%");
		List<OrderBean> orderBeans = query.list();
		PrintObjectToJson.print(response, 1, remind, orderBeans == null ? ""
				: orderBeans);
	}

	public void setPublishOrderStatus() {
		UserBean userBean = SessionUtils.getSession();
		int status = 0;
		if (userBean == null) {
			status = 2;
			remind = "您还未登录";
		} else {
			status = 1;
			System.out.println(userBean.getUserName());
			System.out.println(publishStatus);
			HashMap<String, Object> where = new HashMap<String, Object>();
			where.put(OrderBean.ATTR_PUBLISH_STATUS, true);
			where.put(OrderBean.ATTR_BELONG_USER_NAME, userBean.getUserName());
			HashMap<String, Object> fiedls = new HashMap<String, Object>();
			fiedls.put(OrderBean.ATTR_PUBLISH_STATUS, publishStatus);
			new OrderDao().update(where, fiedls);
			remind = "修改成功";
		}
		PrintObjectToJson.print(response, status, remind, "");
	}

	public String getIncludeJuniorClass() {
		return includeJuniorClass;
	}

	public void setIncludeJuniorClass(String includeJuniorClass) {
		this.includeJuniorClass = includeJuniorClass;
	}

	public String getIncludeBookName() {
		return includeBookName;
	}

	public void setIncludeBookName(String includeBookName) {
		this.includeBookName = includeBookName;
	}

}
