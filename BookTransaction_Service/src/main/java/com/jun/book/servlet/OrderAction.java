package com.jun.book.servlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
	private String orderItemBeans;
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
	private String searchJuniorClassValue;// 查询的年级ID
	private String searchDepartmentValue;// 查询系的ID
	private String searchMajorValue;// 查询专业的ID
	private String searchProjectValue;// 查询课程的ID
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
	 * 发布订单,status为1表示发布成功，status为2表示发布失败
	 */
	public void publishOrder() {
		int status = 0;
		UserBean userBean = SessionUtils.getSession();
		if (userBean == null) {// 没有登录的情况下
			status = 2;
			remind = "您未登录";
		} else {// 登录了,先删除订单信息，再添加订单信息
			status = 1;
			OrderDao orderDao = new OrderDao();
			OrderItemDao orderItemDao = new OrderItemDao();
			HashMap<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put(OrderBean.ATTR_BELONG_USER_NAME,
					userBean.getUserName());
			List<OrderBean> orderBeans = orderDao.findList(orderMap);
			if (orderBeans != null && orderBeans.size() != 0) {// 存在数据，就删除
				for (OrderBean orderBean2 : orderBeans) {
					Set<OrderItemBean> set = orderBean2.getSet();
					for (OrderItemBean orderItemBean : set) {
						orderItemDao.delete(orderItemBean.getId());
					}
					orderDao.delete(orderBean2.getId());
				}
			}
			System.out.println(orderItemBeans);
			OrderBean orderBean = new OrderBean();
			orderBean.setBelongUserName(userBean.getUserName());
			orderBean.setContactPhone(orderContactPhone);
			orderBean.setContactQQ(orderContactQQ);
			orderBean.setCreateDate(new Date().getTime());
			orderBean.setOrderDescribe(orderDescribe);
			orderBean.setBelongUserMajorName(userBean.getMajor());
			orderBean.setPublishStatus(true);
			List<OrderItemBean> jsonOrderItemBeans = new ArrayList<OrderItemBean>();
			System.err.println("===============json 开始解析=====================");
			try {
				Gson gson = new Gson();
				jsonOrderItemBeans = gson.fromJson(orderItemBeans,
						new TypeToken<List<OrderItemBean>>() {
						}.getType());
				orderBean
						.setSet(new HashSet<OrderItemBean>(jsonOrderItemBeans));
			} catch (Exception e) {
				System.err.println(e.toString());
			}

			System.err.println("===============json 解析完成=====================");
			System.err.println("===============" + orderBean.getSet().size()
					+ "=====================");
			String includeBookName = "";
			String includeJuniorClass = "";
			for (OrderItemBean orderItemBean : jsonOrderItemBeans) {
				includeBookName = includeBookName + orderItemBean.getBookName()
						+ ",";
				includeJuniorClass = includeJuniorClass
						+ orderItemBean.getBelongJuniorClass() + ",";
			}
			orderBean.setIncludeBookName(includeBookName);
			orderBean.setIncludeJuniorClass(includeJuniorClass);
			orderBean.setOrderItemSize(jsonOrderItemBeans == null ? 0
					: jsonOrderItemBeans.size());
			orderDao.save(orderBean);// 填充订单
		}
		PrintObjectToJson.print(response, status, remind, "");
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
		if (orderBeans != null || orderBeans.size() >0) {
			PrintObjectToJson.print(response, status, remind, orderBeans.get(0));
		}else {
			PrintObjectToJson.print(response, status, remind, new OrderBean());
		}
		
	}

	/**
	 * 根据条件获取订单,如果需要这个条件的全部，那么就 不添加這個搜索條件
	 */
	public void getAllOrderByCondition() {
		String hql = "select orders from OrderBean as orders where orders.publishStatus=true and orders.orderItemSize>0";
		System.out.println("================"+searchMajorValue+"=============");
		if (searchMajorValue== null || searchMajorValue.equals("-1") || searchMajorValue.equals("")) {//搜索的专业,如果为-1或者为空，就表示返回所有的值
			
		}else {//表示筛选年级和课程
			hql = hql+" and orders.belongUserMajorName like '%"+searchMajorValue+"%'";
			if (searchJuniorClassValue != null && !searchJuniorClassValue.equals("-1") && !searchJuniorClassValue.equals("")) {//搜索的年级,-1表示搜索所有的值,不等于-1则表示需要添加限定条件
				hql = hql+" and orders.includeJuniorClass like '%"+searchJuniorClassValue+"%'";
				if (searchProjectValue!=null && !searchProjectValue.equals("-1") && !searchProjectValue.equals("")) {//搜索的课程，01表示搜索所有的值，不等于-1表示需要添加限定条件
					hql = hql+ " and orders.includeBookName like '%"+searchProjectValue+"%'";
				}
			}
		}
		Session session= HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(offset);
		query.setMaxResults(offset+limit);
		@SuppressWarnings("unchecked")
		List<OrderBean> orderBeans = query.list();
		int status = 1;
		remind = "获取数据成功";
		PrintObjectToJson.print(response, status, remind, orderBeans);
		System.out.println("----------------"+query.toString()+"--------------------");
		System.out.println("----------------"+hql+"--------------------");
	}

	/**
	 * 搜索订单
	 */
	public void searchOrder() {
		String sql = "select orders from OrderBean as orders where orders.publishStatus=true and orders.orderItemSize>0 and orders.includeBookName like ?";
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(sql);
		query.setString(0, "%" + searchKey + "%");
		@SuppressWarnings("unchecked")
		List<OrderBean> orderBeans = query.list();
		PrintObjectToJson.print(response, 1, remind, orderBeans == null ? ""
				: orderBeans);
		System.out.println(query.toString());
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
			where.put(OrderBean.ATTR_PUBLISH_STATUS, false);
			where.put(OrderBean.ATTR_BELONG_USER_NAME, userBean.getUserName());
			HashMap<String, Object> fiedls = new HashMap<String, Object>();
			fiedls.put(OrderBean.ATTR_PUBLISH_STATUS, publishStatus);
			new OrderDao().update(where, fiedls);
			remind = "修改成功";
		}
		PrintObjectToJson.print(response, status, remind, "");
	}

	public String getOrderItemBeans() {
		return orderItemBeans;
	}

	public void setOrderItemBeans(String orderItemBeans) {
		this.orderItemBeans = orderItemBeans;
	}

}
