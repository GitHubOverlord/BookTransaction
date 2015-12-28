package com.jun.book.servlet;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.jun.booktransaction.bean.UserBean;
import com.jun.file.dao.UserDao;
import com.jun.file.util.BaseActionSupport;
import com.jun.file.util.PrintObjectToJson;
import com.jun.file.util.SessionUtils;

public class UserAction extends BaseActionSupport {
	/**
	 * 用户注册、登录、修改用户信息功能
	 */
	private static final long serialVersionUID = 1558439185764539275L;
	private String userName;// 账号
	private String psw;// 密码
	private String remind = "";// 返回提示文字
	private String verificationCode;// 验证码
	private String majorId;// 专业ID
	private String nickName;// 昵称
	private String grade;
	private String majorName;//专业名字

	public String getRemind() {
		return remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * 用户登录接口
	 * 
	 * @return
	 * @throws Exception
	 */
	public void login() {
		System.out.println("userName=" + userName + " psw=" + psw);
		UserDao userDao = new UserDao();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put(UserBean.ATTR_USER_NAME, userName);
		List<UserBean> users = userDao.findList(hashMap);
		UserBean userBean = null;
		int status = 0;
		/**
		 * Status状态码：1表示成功 2表示账号不存在 3表示密码错误 4表示账号被封
		 */
		if (users == null || users.size() == 0) {// 账号不存在
			status = 2;
			remind = "账号不存在";
		} else {
			userBean = users.get(0);
			System.out.println("userName="+userBean.getUserName()+" psw="+userBean.getPsw());
			if (userBean.getPsw().equals(psw)) {// 如果账号密码相同
				if (userBean.getIsUseAble()) {
					status = 1;// 登录成功
					SessionUtils.putSession(userBean);
					remind = "登录成功";
				} else {
					status = 4;// 账号被封
					remind = "账号被封";
				}
			} else {
				status = 3;// 密码错误
				remind = "密码错误";
			}
		}
		PrintObjectToJson.print(response, status, remind, status==1?userBean:"");
	}

	/**
	 * 用户注册，status 为判断码，1表示注册成功，2表示注册失败
	 */
	public void register() throws Exception {
		HashMap<String, Object> where = new HashMap<String, Object>();
		where.put(UserBean.ATTR_USER_NAME, userName);
		UserDao userDao = new UserDao();
		List<UserBean> userBeans = userDao.findList(where);
		UserBean userBean = null;
		int status = 0;
		if (userBeans != null && userBeans.size() > 0) {// 这里表明，存在这个注册用户，不能注册
			status = 2;
			remind = "已经存在这个用户";
		} else {// 不存在这个用户，可以注册
			status = 1;
			userBean = new UserBean();
			userBean.setGrade(grade);
			userBean.setIsUseAble(true);
			userBean.setIsVip(0);
			userBean.setMajorId(majorId);
			userBean.setNickName(nickName);
			userBean.setPsw(psw);
			userBean.setRegisterDate(new Date().getTime());
			userBean.setUserName(userName);
			userBean.setMajorName(majorName);
			userDao.save(userBean);
			remind = "注册成功";
			SessionUtils.putSession(userBean);
		}
		PrintObjectToJson.print(response, status, remind, userBean == null ? ""
				: userBean);
	}

	/**
	 * 退出登录
	 * 
	 * @throws Exception
	 */
	public void loginOut() throws Exception {
		SessionUtils.clearSession(request);
		remind = "退出登录成功";
		PrintObjectToJson.print(response, 1, remind, "");
	}

	/**
	 * 修改用户信息，status为状态码，1表示修改成功，2表示登录过期或者未登录
	 */
	public void updateUserMessage() throws Exception {
		int status = 0;
		UserBean sessionBean = SessionUtils.getSession();
		UserBean updateUserBean = null;
		if (sessionBean == null || sessionBean.getUserName() == null) {// 这说明未登录,或者没有这个用户
			status = 2;
			remind = "用户未登录，或者登录失效";
		} else {// 已经登录
			UserDao userDao = new UserDao();
			HashMap<String, Object> where = new HashMap<String, Object>();
			where.put(UserBean.ATTR_USER_NAME, sessionBean.getUserName());
			List<UserBean> userBeans = userDao.findList(where);
			updateUserBean = userBeans.get(0);
			updateUserBean.setGrade(grade);
			updateUserBean.setNickName(nickName);
			updateUserBean.setPsw(psw);
			userDao.upsert(updateUserBean);
			remind = "修改成功";
		}
		PrintObjectToJson.print(response, status, remind,
				updateUserBean == null ? "" : updateUserBean);
	}
	/**
	 * 获取用户基本信息，status为1表示获取成功，2表示获取失败
	 */
	public void getUserMessage(){
		UserBean userBean = SessionUtils.getSession();
		int status = 0;
		if (userBean == null) {
			status = 2;
			remind = "您还未登录";
		}else {
			status = 1;
			remind = "登录成功";
		}
		PrintObjectToJson.print(response, status, remind, userBean == null ?"":userBean);
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

}
