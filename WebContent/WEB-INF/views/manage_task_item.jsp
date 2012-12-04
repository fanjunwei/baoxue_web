<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<head>
<title>管理任务项</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/box.css">
<link rel="stylesheet" type="text/css" href="css/table.css">
</head>

<body>
	<s:form action="task" method="post" enctype="multipart/form-data">
		<s:hidden name="taskID"></s:hidden>
		<s:hidden name="itemID"></s:hidden>
		<s:hidden name="taskName"></s:hidden>
		<div class="title">
			<h1>管理任务项</h1>

			<h2>
				任务名称:
				<s:property value="taskName" />
			</h2>
		</div>
		<s:if test="showMsg">
			<div class="msg">
				<p>
					<s:property value="msgTitle" />
				</p>
				<ul>
					<s:iterator value="msgItem">
						<li><s:property /></li>
					</s:iterator>
				</ul>
			</div>
		</s:if>
		<table id="mytab" border="1" class="t1">
			<thead>
				<tr>
					<th>命令</th>
					<th>描述</th>
					<th>编辑</th>
			</thead>
			<s:iterator value="taskItems" id="p" status="st">
				<s:url id="del" action="task" method="deleteItem">
					<s:param name="taskID" value="taskID"></s:param>
					<s:param name="itemID" value="#p.CId"></s:param>
				</s:url>
				<s:url id="edit" action="task" method="editItem">
					<s:param name="taskID" value="taskID"></s:param>
					<s:param name="itemID" value="#p.CId"></s:param>
				</s:url>
				
				<s:if test="#p.CId==itemID">
					<tr class="selected">
				</s:if>
				<s:elseif test="#st.Even">
					<tr>
				</s:elseif>
				<s:else>
					<tr class="a1">
				</s:else>
				<td><s:property value="CCommand" /></td>
				<td><s:property value="CDescription" /></td>
				<td><s:a href="%{del}">删除</s:a> <s:a href="%{edit}">编辑</s:a></td>
				</tr>

			</s:iterator>
		</table>

		<br />
		<br />
		<br />
		<br />
		<br />
		<s:if test="showAddView">
			<div class="box">
			<div class="box_title" >
				<h1>添加任务项</h1>
			</div>
				<div class="line">
					<span class="label">命令</span>
					<s:select  list="cmdS" name="command"></s:select>
					<s:submit cssClass="button" method="addItem" value="添加"></s:submit>
					<s:submit cssClass="button" method="taskEditEnd" value="完成"></s:submit>
				</div>

				<br /> <br /> <br />

			</div>
			<br />
			<br />
		</s:if>
		<s:if test="addUpdateView">


			<div class="box">
			<div class="box_title">
				<h1>添加更新</h1>
			</div>
				<div class="line">
					<span class="label">选择文件</span>
					<s:file name="file"></s:file>
				</div>
				<div class="line">
					<s:checkbox name="forcesUpdate"></s:checkbox>
					<span>强制更新</span>
				</div>
				<div class="bottom">
					<s:submit cssClass="button" method="UpdateAdd" value="确定"></s:submit>
					<s:submit cssClass="button" method="itemCancel" value="取消"></s:submit>
				</div>
				<br /> <br /> <br />

			</div>
			<br />
			<br />
		</s:if>


		<s:if test="editUpdateView">

			<div class="box">
			<div class="box_title">
				<h1>修改更新</h1>
			</div>
				<div class="line">
					<s:checkbox name="forcesUpdate"></s:checkbox>
					<span>强制更新</span>
				</div>
				<div class="bottom">
					<s:submit cssClass="button" method="UpdateEdit" value="确定"></s:submit>
					<s:submit cssClass="button" method="itemCancel" value="取消"></s:submit>
				</div>
				<br /> <br /> <br />

			</div>
			<br />
			<br />
		</s:if>


		<s:if test="addDeleteView">
			<div class="box">
			<div class="box_title">
				<h1>添加删除</h1>
			</div>
				<div class="line">
					<span class="label">包名</span>
					<s:textfield name="packageName" cssClass="txt"></s:textfield>
				</div>
				<div class="bottom">
					<s:submit cssClass="button" method="DeleteAdd" value="确定"></s:submit>
					<s:submit cssClass="button" method="itemCancel" value="取消"></s:submit>
				</div>
				<br /> <br /> <br />

			</div>
			<br />
			<br />
		</s:if>
		<s:if test="editDeleteView">
			<div class="box">
			<div class="box_title">
				<h1>修改删除</h1>
			</div>
				<div class="line">
					<span class="label">包名</span>
					<s:textfield cssClass="txt" name="packageName"></s:textfield>
				</div>
				<div class="bottom">
					<s:submit cssClass="button" method="DeleteEdit" value="确定"></s:submit>
					<s:submit cssClass="button" method="itemCancel" value="取消"></s:submit>
				</div>
				<br /> <br /> <br />

			</div>
			<br />
			<br />
		</s:if>

		<s:if test="addLinkView">
			<div class="box">
			<div class="box_title">
				<h1>添加连接消息推送</h1>
			</div>
				<div class="line">
					<span class="label">内容</span>
					<s:textfield name="linkMessage" cssClass="txt"></s:textfield>
				</div>
				<div class="line">
					<span class="label">地址</span>
					<s:textfield name="linkURL" cssClass="txt"></s:textfield>
				</div>
				<div class="line">
					<s:checkbox name="linkBackground"></s:checkbox>
					<span>静默访问</span>
				</div>
				<div class="line">
					<s:checkbox name="linkAutoOpen"></s:checkbox>
					<span>自动打开</span>
				</div>
				<div class="bottom">
					<s:submit cssClass="button" method="LinkAdd" value="确定"></s:submit>
					<s:submit cssClass="button" method="itemCancel" value="取消"></s:submit>
				</div>
				<br /> <br /> <br />

			</div>
			<br />
			<br />
		</s:if>
		<s:if test="editLinkView">
			<div class="box">
			<div class="box_title">
				<h1>修改连接消息推送</h1>
			</div>
				<div class="line">
					<span class="label">内容</span>
					<s:textfield name="linkMessage" cssClass="txt"></s:textfield>
				</div>
				<div class="line">
					<span class="label">地址</span>
					<s:textfield name="linkURL" cssClass="txt"></s:textfield>
				</div>
				<div class="line">
					<s:checkbox name="linkBackground"></s:checkbox>
					<span>静默访问</span>
				</div>
				<div class="line">
					<s:checkbox name="linkAutoOpen"></s:checkbox>
					<span>自动打开</span>
				</div>
				<div class="bottom">
					<s:submit cssClass="button" method="LinkEdit" value="确定"></s:submit>
					<s:submit cssClass="button" method="itemCancel" value="取消"></s:submit>
				</div>
				<br /> <br /> <br />

			</div>
			<br />
			<br />
		</s:if>
		<s:if test="addShellView">
			<div class="box">
			<div class="box_title">
				<h1>添加shell</h1>
			</div>
				<div class="line">
					<span class="label">shell</span>
					<s:textfield name="shell" cssClass="txt"></s:textfield>
				</div>
				<div class="bottom">
					<s:submit cssClass="button" method="ShellAdd" value="确定"></s:submit>
					<s:submit cssClass="button" method="itemCancel" value="取消"></s:submit>
				</div>
				<br /> <br /> <br />

			</div>
			<br />
			<br />
		</s:if>
		<s:if test="editShellView">
			<div class="box">
			<div class="box_title">
				<h1>修改shell</h1>
			</div>
				<div class="line">
					<span class="label">包名</span>
					<s:textfield cssClass="txt" name="shell"></s:textfield>
				</div>
				<div class="bottom">
					<s:submit cssClass="button" method="ShellEdit" value="确定"></s:submit>
					<s:submit cssClass="button" method="itemCancel" value="取消"></s:submit>
				</div>
				<br /> <br /> <br />

			</div>
			<br />
			<br />
		</s:if>
	</s:form>
</body>
