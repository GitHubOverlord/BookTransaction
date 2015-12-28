package com.jun.booktransaction.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_order_item")
public class OrderItemBean {
	private String id;// 唯一标识ID
	public static final String ATTR_ID = "id";
	private String belongOrder;// 所属的订单ID
	public static final String ATTR_BELONG_ORDER = "belongOrder";
	private String belongUserName;// 所属的用户ID
	public static final String ATTR_BELONG_USER_NAME = "belongUserName";
	private String belongMajorId;// 所属的课程
	public static final String ATTR_BELONG_MAJOR_ID = "belongMajorId";
	private int belongJuniorClass;// 所属的年级
	public static final String ATTR_BELONG_JUNIOR_CLASS = "belongJuniorClass";
	private String bookName;// 书名
	public static final String ATTR_BOOK_NAME = "bookName";
	private int oldDegree;// 新旧程度
	public static final String ATTR_OLD_DEGREE = "oldDegree";
	private int haveExerciseBook;// 是否有练习册
	public static final String ATTR_HAVE_EXERCISE_BOOK = "haveExerciseBook";
	private double price;// 价格
	public static final String ATTR_PRICE = "price";
	private String describle;// 描述
	public static final String ATTR_DESCRIBLE = "describle";
	private String projectId;
	public static final String ATTR_PROJECT_ID = "projectId";

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

	@Column(name = "belongOrder")
	public String getBelongOrder() {
		return belongOrder;
	}

	public void setBelongOrder(String belongOrder) {
		this.belongOrder = belongOrder;
	}

	@Column(name = "belongUserName")
	public String getBelongUserName() {
		return belongUserName;
	}

	public void setBelongUserName(String belongUserName) {
		this.belongUserName = belongUserName;
	}

	@Column(name = "belongJuniorClass")
	public int getBelongJuniorClass() {
		return belongJuniorClass;
	}

	public void setBelongJuniorClass(int belongJuniorClass) {
		this.belongJuniorClass = belongJuniorClass;
	}

	@Column(name = "bookName")
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	@Column(name = "oldDegree")
	public int getOldDegree() {
		return oldDegree;
	}

	public void setOldDegree(int oldDegree) {
		this.oldDegree = oldDegree;
	}

	@Column(name = "haveExerciseBook")
	public int getHaveExerciseBook() {
		return haveExerciseBook;
	}

	public void setHaveExerciseBook(int haveExerciseBook) {
		this.haveExerciseBook = haveExerciseBook;
	}

	@Column(name = "price")
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "belongMajorId")
	public String getBelongMajorId() {
		return belongMajorId;
	}

	public void setBelongMajorId(String belongMajorId) {
		this.belongMajorId = belongMajorId;
	}

	@Column(name = "projectId")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "describle")
	public String getDescrible() {
		return describle;
	}

	public void setDescrible(String describle) {
		this.describle = describle;
	}

}
