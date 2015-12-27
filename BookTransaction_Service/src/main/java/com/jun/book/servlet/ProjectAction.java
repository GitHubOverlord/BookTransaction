package com.jun.book.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.jun.booktransaction.bean.MajorBean;
import com.jun.booktransaction.bean.ProjectBean;
import com.jun.file.dao.MajorDao;
import com.jun.file.dao.ProjectDao;
import com.jun.file.util.BaseActionSupport;
import com.jun.file.util.PrintObjectToJson;

public class ProjectAction extends BaseActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2689166193391255151L;
	private String majorId;
	private int belongJuniorCalss;
	private String projectName;
	private String majorName;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getMajorId() {
		return majorId;
	}
	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}
	public void findAllProject(){
		PrintObjectToJson.print(response, 1, "返回所有课程成功", new ProjectDao().findList(new HashMap<String, Object>()));
	}
	public int getBelongJuniorCalss() {
		return belongJuniorCalss;
	}
	public void setBelongJuniorCalss(int belongJuniorCalss) {
		this.belongJuniorCalss = belongJuniorCalss;
	}
	public void findProjectByMajorId(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("belongMajorName", majorId);
		map.put("belongJuniorClss", Integer.valueOf(belongJuniorCalss));
		PrintObjectToJson.print(response, 1, "返回查询的课程成功", new ProjectDao().findList(map));
	}
	public void insertProject(){
		ProjectBean projectBean = new ProjectBean();
		projectBean.setBelongJuniorClss(belongJuniorCalss);
		projectBean.setBelongName(majorName);
		projectBean.setProjectName(projectName);
		new ProjectDao().save(projectBean);
		PrintObjectToJson.print(response, 1, "插入成功", projectBean);
	}
	public void createProject(){
	List<MajorBean> majorBeans = new MajorDao().findList(new HashMap<String, Object>());
	for (int i = 0; i < majorBeans.size(); i++) {
		for (int j = 0; j < 5; j++) {
			ProjectBean projectBean = new ProjectBean();
			projectBean.setBelongName(majorBeans.get(i).getId());
			projectBean.setProjectName(majorBeans.get(i).getMajorName()+"课程"+j);
			int[] juniorClass = {1,2,3,4};
			projectBean.setBelongJuniorClss(juniorClass[new Random().nextInt(4)]);
			new ProjectDao().save(projectBean);
		}
		
	}
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	
}
