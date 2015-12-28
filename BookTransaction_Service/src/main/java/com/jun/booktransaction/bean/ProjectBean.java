package com.jun.booktransaction.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_project")
public class ProjectBean {
	private String id;
	public static final String ATTR_ID = "id";
	private String projectName;
	public static final String ATTR_PROJECT_NAME = "projectName";
	private String belongMajorName;
	public static final String ATTR_BELONG_MAJOR_NAME = "belongMajorName";
	private int belongJuniorClss;
	public static final String ATTR_BELONG_JUNIOR_CLSS = "belongJuniorClss";
	private String belongMajorId;
	public static final String ATTR_BELONG_MAJOR_ID = "belongMajorId";

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

	@Column(name = "project_name")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "belongMajorName")
	public String getBelongMajorName() {
		return belongMajorName;
	}

	public void setBelongMajorName(String belongMajorName) {
		this.belongMajorName = belongMajorName;
	}

	@Column(name = "belongJuniorClss")
	public int getBelongJuniorClss() {
		return belongJuniorClss;
	}

	public void setBelongJuniorClss(int belongJuniorClss) {
		this.belongJuniorClss = belongJuniorClss;
	}

	@Column(name = "belongMajorId")
	public String getBelongMajorId() {
		return belongMajorId;
	}

	public void setBelongMajorId(String belongMajorId) {
		this.belongMajorId = belongMajorId;
	}

}
