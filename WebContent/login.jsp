<%@ page language="java" pageEncoding="utf-8"%>

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<title>暴雪后台登录</title>

<link href="css/login-box.css" rel="stylesheet" type="text/css" />
</head>

<body>

	<s:form id="form1" action="login" method="post">
		<div style="padding: 100px 0 0 250px;">

			<div id="login-box">

				<h2>暴雪后台</h2>
				<br /> <br />
				<div id="login-box-name" style="margin-top: 20px;">用户名:</div>
				<div id="login-box-field" style="margin-top: 20px;">
					<input name="userName" class="form-login" title="Username" value=""
						size="30" maxlength="2048" />
				</div>
				<div id="login-box-name">密码:</div>
				<div id="login-box-field">
					<input type="password" class="form-login" title="Password" value=""
						size="30" name="password" maxlength="2048" />
				</div>
				<div id="login-box-name">验证码:</div>
				<div id="login-box-field">
					<input name="imgCode" class="form-login" value="" size="30"	maxlength="2048"/> 
					<img src="<s:url action="imgCode" ></s:url>" />
				</div>
				<br /> <br /> <br /> <input type="image"
					src="images/login-btn.png" style="margin-left: 90px;"></input>
				<s:label name="msg" cssStyle="color:red;"></s:label>
			</div>
		</div>
	</s:form>

</body>
</html>
