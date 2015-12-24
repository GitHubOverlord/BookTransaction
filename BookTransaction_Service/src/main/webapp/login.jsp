<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>登录</title>
<link rel="stylesheet" type="text/css" href="css/index_css.css" />
<script>
	function validateForm() {
		var UserName = document.forms["myForm"]["userName"].value;
		var Password = document.forms["myForm"]["password"].value;
		if (UserName == null || UserName == "") {
			alert("请输入用户名");
			return false;
		}
		if (Password == null || Password == "") {
			alert("请输入密码");
			return false;
		}
		if (UserName.match("^[A-Za-z0-9]+$") == null) {
			alert("请输入正确的用户名");
			return false;
		}
		if (Password.match("^[A-Za-z0-9]+$") == null) {
			alert("请输入正确的密码");
			return false;
		}

		return tru;
	}
</script>
</head>
<body>
	<div class="top">
		<img src="entry/entry 1.png" />
	</div>
	<div class="center">
		<div class="center_top">文件管理系统</div>
		<div class="center_bottom">
			<div class="center_bottom_width">
				<div class="center_bottom_top">
					<a  class="active" >用户登录</a>
				</div>
				<form name="myForm" action="loginAction!login.action"
					onsubmit="return validateForm()" method="post">
					<div class="center_bottom_center">
						<input type="text" id="userName" value="" name="userName"
							placeholder="用户名" /> <input type="password" id="psw" value=""
							name="psw" placeholder="密码" />
					</div>
					<div class="center_bottom_botom">
						<input type="submit" id="entry" value="登录" />
					</div>
				</form>
				<div align="center">
					<s:property value="remind" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>