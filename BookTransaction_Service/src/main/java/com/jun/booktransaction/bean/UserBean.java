package com.jun.booktransaction.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tab_user")
public class UserBean implements Serializable {
	private static final long serialVersionUID = 2908444282665482090L;
	/**
	 * 自增长ID
	 */
	private int id;
	public static String ATTR_ID = "id";
	/**
	 * 用户名
	 */
	private String userName;
	public static String ATTR_USER_NAME = "userName";
	/**
	 * 密码
	 */
	private String psw;
	public static String ATTR_PSW = "psw";
	/**
	 * 是不是VIP用户,0表示不是，1表示普通VIP
	 */
	private int isVip;
	public static String ATTR_IS_VIP = "isVip";
	/**
	 * 注册时间
	 */
	private Long registerDate;
	public static String ATTR_REGISTER_DATE = "registerDate";
	/**
	 * 专业
	 */
	private String major;
	public static String ATTR_MAJOR = "major";
	/**
	 * 年级
	 */
	private String grade;
	public static String ATTR_GRADE = "grade";
	/**
	 * 昵称
	 */
	private String nickName;
	public static String ATTR_NICK_NAME = "nickName";
	/**
	 * 账号是否可用
	 */
	private boolean isUseAble;
	public static String ATTR_IS_USEABLE = "isUseAble";
	private String majorName;

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "psw")
	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	@Column(name = "isVip")
	public int getIsVip() {
		return isVip;
	}

	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}

	@Column(name = "registerDate")
	public Long getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Long registerDate) {
		this.registerDate = registerDate;
	}

	@Column(name = "major")
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Column(name = "grade")
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Column(name = "nickName")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "isUseAble")
	public boolean getIsUseAble() {
		return isUseAble;
	}

	public void setIsUseAble(boolean isUseAble) {
		this.isUseAble = isUseAble;
	}

	@Column(name = "majorName")
	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
}
