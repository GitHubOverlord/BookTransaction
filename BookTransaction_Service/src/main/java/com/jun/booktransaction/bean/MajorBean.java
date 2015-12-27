package com.jun.booktransaction.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_major")
public class MajorBean implements Serializable {
	/**
	 * 专业表
	 */
	private static final long serialVersionUID = 5349026679182550972L;
	/**
	 * 专业表对应的id，他是uuid生成策略
	 */
	private String id;
	public static final String ATTR_ID = "id";
	/**
	 * 专业名字字段
	 */
	private String majorName;
	public static final String ATTR_MAJOR_NAME = "major_name";
	/**
	 * 所属的表的ID
	 */
	private String belongDepartmentId;
	public static final String ATTR_BELONG_DEPARTMENT_ID = "belongDepartmentId";

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

	@Column(name = "major_name")
	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	@Column(name = "belongDepartmentId")
	public String getBelongDepartmentId() {
		return belongDepartmentId;
	}

	public void setBelongDepartmentId(String belongDepartmentId) {
		this.belongDepartmentId = belongDepartmentId;
	}

}
