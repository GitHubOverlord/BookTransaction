package com.jun.booktransaction.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_department")
public class DepartmentBean implements Serializable {

	/**
	 * 系表
	 */
	private static final long serialVersionUID = -8194868158291090916L;
	/**
	 * 系表的ID，这不是自动增长的，它会有一个UUID
	 */
	private String id;
	public static final String ATTR_ID = "id";
	/**
	 * 系的名字
	 */
	private String departmentName;
	public static final String ATTR_DEPARTMENT_NAME = "departmentName";

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

	@Column(name = "department_name")
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
