package com.jun.booktransaction.bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_order")
public class OrderBean {
	/**
	 * 唯一ID生成策略为UUID
	 */
	private String id;
	public static final String ATTR_ID = "id";
	/**
	 * 所属的用户名
	 */
	private String belongUserName;
	public static final String ATTR_BELONG_USER_NAME = "belongUserName";
	/**
	 * 联系人电话号码
	 */
	private String contactPhone;
	public static final String ATTR_CONTACT_PHONE = "contactPhone";
	/**
	 * 联系人QQ
	 */
	private String contactQQ;
	public static final String ATTR_CONTACT_QQ = "contactQQ";
	/**
	 * 订单的描述
	 */
	private String orderDescribe;
	public static final String ATTR_DESCRIBE = "orderDescribe";
	/**
	 * 发布的日期
	 * 
	 */
	private Long createDate;
	public static final String ATTR_DATE = "createDate";
	/**
	 * 发布的状态
	 */
	private boolean publishStatus;
	public static final String ATTR_PUBLISH_STATUS = "publishStatus";
	/**
	 * 所属人的专业
	 */
	private String belongUserMajorName;
	public static final String ATTR_BELONG_USER_MAJOR_NAME = "belongUserMajorName";
	/**
	 * 有哪几个年级的书
	 * 
	 */
	private String includeJuniorClass;
	public static final String INCLUDE_JUNIOR_CLASS = "includeJuniorClass";
	/**
	 * 包含的书名
	 */
	private String includeBookName;
	public static final String INCLUDE_BOOK_NAME = "includeBookName";
	private String belongUserNickName;
	private String belongUserJunirClass;
	private Set<OrderItemBean> set;
	private int orderItemSize;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "belongUserName")
	public String getBelongUserName() {
		return belongUserName;
	}

	public void setBelongUserName(String belongUserName) {
		this.belongUserName = belongUserName;
	}

	@Column(name = "contactPhone")
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Column(name = "contactQQ")
	public String getContactQQ() {
		return contactQQ;
	}

	public void setContactQQ(String contactQQ) {
		this.contactQQ = contactQQ;
	}

	@Column(name = "orderDescribe")
	public String getOrderDescribe() {
		return orderDescribe;
	}

	public void setOrderDescribe(String orderDescribe) {
		this.orderDescribe = orderDescribe;
	}

	@Column(name = "createDate")
	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	@Column(name = "publishStatus")
	public boolean getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(boolean publishStatus) {
		this.publishStatus = publishStatus;
	}

	@Column(name = "belongUserMajorName")
	public String getBelongUserMajorName() {
		return belongUserMajorName;
	}

	public void setBelongUserMajorName(String belongUserMajorName) {
		this.belongUserMajorName = belongUserMajorName;
	}

	@Column(name = "includeJuniorClass")
	public String getIncludeJuniorClass() {
		return includeJuniorClass;
	}

	public void setIncludeJuniorClass(String includeJuniorClass) {
		this.includeJuniorClass = includeJuniorClass;
	}

	@Column(name = "includeBookName")
	public String getIncludeBookName() {
		return includeBookName;
	}

	public void setIncludeBookName(String includeBookName) {
		this.includeBookName = includeBookName;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	public Set<OrderItemBean> getSet() {
		return set;
	}

	public void setSet(Set<OrderItemBean> set) {
		this.set = set;
	}

	@Column(name = "belongUserNickName")
	public String getBelongUserNickName() {
		return belongUserNickName;
	}

	public void setBelongUserNickName(String belongUserNickName) {
		this.belongUserNickName = belongUserNickName;
	}

	@Column(name = "belongUserJunirClass")
	public String getBelongUserJunirClass() {
		return belongUserJunirClass;
	}

	public void setBelongUserJunirClass(String belongUserJunirClass) {
		this.belongUserJunirClass = belongUserJunirClass;
	}

	@Column(name = "orderItemSize")
	public int getOrderItemSize() {
		return orderItemSize;
	}

	public void setOrderItemSize(int orderItemSize) {
		this.orderItemSize = orderItemSize;
	}

}
