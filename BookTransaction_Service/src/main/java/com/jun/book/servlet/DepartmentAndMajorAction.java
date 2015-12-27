package com.jun.book.servlet;

import java.util.HashMap;
import java.util.List;

import com.jun.booktransaction.bean.DepartmentAndMajor;
import com.jun.booktransaction.bean.DepartmentBean;
import com.jun.booktransaction.bean.MajorBean;
import com.jun.file.dao.DepartmentDao;
import com.jun.file.dao.MajorDao;
import com.jun.file.util.BaseActionSupport;
import com.jun.file.util.PrintObjectToJson;

public class DepartmentAndMajorAction extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2447875534638626266L;

	public void getAllDeaprtmentAndMajor() {
		DepartmentDao dao = new DepartmentDao();
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<DepartmentBean> departments = dao.findList(map);
		List<MajorBean> majorBeans = new MajorDao().findList(map);
		DepartmentAndMajor departmentAndMajor = new DepartmentAndMajor();
		departmentAndMajor.setDepartmentBeans(departments);
		departmentAndMajor.setMajorBeans(majorBeans);
		PrintObjectToJson.print(response, 1, "获取数据成功", departmentAndMajor);
	}

	public void findDepartmentByMajorId() {

	}

	public void createDepartmentAndMajor() {
		List<DepartmentBean> departmentBeans;
		for (int i = 0; i < 5; i++) {
			DepartmentBean department = new DepartmentBean();
			department.setDepartmentName("测试系" + i);
			new DepartmentDao().save(department);
		}
		departmentBeans = new DepartmentDao()
				.findList(new HashMap<String, Object>());
		MajorDao majorDao = new MajorDao();
		for (DepartmentBean departmentBean : departmentBeans) {
			for (int i = 0; i < 5; i++) {
				MajorBean majorBean = new MajorBean();
				majorBean.setBelongDepartmentId(departmentBean.getId());
				majorBean.setMajorName(departmentBean.getDepartmentName()
						+ "专业" + i);
				majorDao.save(majorBean);
			}
		}
		PrintObjectToJson.print(response, 1, "插入数据成功", "");
	}
}
