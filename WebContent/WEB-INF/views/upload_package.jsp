<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<title>上传应用</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/box.css">
</head>

<body>

	<s:form id="form1" action="upload_package" method="post"
		enctype="multipart/form-data">

		<div class="box">
			<div class="title">
				<h1>上传应用</h1>
			</div>
			<div class="line">
				<span class="label">原包名</span> <input type="text"
					name="oldPackageName" class="txt"></input> <span class="rigth">如无需替换，则保持为空。</span>
			</div>
			<div class="line">
				<span class="label">选择文件</span>
				<s:file name="file"></s:file>

			</div>
			<div class="line">
				<s:checkbox name="forces"></s:checkbox>
				<span>强制更新</span>
			</div>
			<div class="line">
				<s:checkbox name="publish"></s:checkbox>
				<span>发布</span>
			</div>
			<div class="bottom">
				<input type="submit" class="button" value="上传" />
			</div>
			<br /> <br /> <br />

		</div>
		<br />
		<br />
	</s:form>
	<s:if test="showMsg">
		<s:form action="upload_package!delete">
			<s:hidden name="id"></s:hidden>
			<div class="msg">
				<p>
					<s:property value="msgTitle" />
				</p>
				<ul>
					<s:iterator value="msgItem">
						<li><s:property /></li>
					</s:iterator>
				</ul>
				<s:if test="showDelete">
			
					<input type="submit" class="button" style="background: #f68b44; color:white;" value="删除" />
				
				<br/>
				</s:if>
			</div>
		</s:form>
	</s:if>
</body>

