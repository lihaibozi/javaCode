﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
					<!-- 检索  -->
					<form action="workcontent/workcontent.do" method="post" name="Form" id="Form">
					<div id="zhongxin" style="padding-top: 13px;">
					<!-- 检索  -->
					<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
						<thead>
							<tr>
								<th class="center" onclick="selectAll()" style="width:35px;">
								<label><input type="checkbox" id="zcheckbox" class="ace" /><span class="lbl"></span></label>
								</th>
								<th class="center" style="width:50px;">序号</th>
								<th class="center" >讲座/讨论/查房内容</th>
								<th class="center" >与会人数</th>
								<th class="center" >讲座/讨论/查房时间</th>
								<th class="center" >课件</th>
								<th class="center" >填报人</th>
								<th class="center" >填报日期</th>
								<th class="center" >讲座/讨论/查房评价</th>
								<th class="center" >科研教学部反馈意见</th>
								<th class="center" >质控科审核意见</th>
								<th class="center" >操作</th>
							</tr>
						</thead>
						<tbody>
						<!-- 开始循环 -->	
						<c:choose>
							<c:when test="${not empty workContents}">
								<c:if test="${QX.cha == 1 }">
								<c:forEach items="${workContents}" var="var" varStatus="vs">
									<tr>
										<td class='center' style="width: 30px;">
											<label><input type='checkbox' name='ids' class="ace" value="${var.id}" /><span class="lbl"></span></label>
										</td>
										
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td class="center">${var.content}</td>
										<td class="center">${var.joinNumber}</td>
										<td class="center"><fmt:formatDate value="${var.conferenceTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td class="center">
										<c:if test="${var.courseware != null && var.courseware!=''}">
										<a href="${var.courseware}" target="_blank"><img src="${var.courseware}" id = "image-show" width="26"/></a>
										<%-- <a id="loadFile" style="cursor:pointer" onclick="loadFile('${var.fileName}');">${var.fileName}</a> --%>
										</c:if>
										</td>
										<td class="center">${var.fillPeople}</td>
										<td class="center"><fmt:formatDate value="${var.fillDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td class="center">${var.apprise}</td>
										<td class="center"><label style="color:#ff0000">${var.opinion}</label></td>
										<td class="center"><label style="color:#ff0000">${var.zkOpinion}</label></td>
										<td>
											<c:if test="${QX.edit == 1 }">
											<a style="cursor:pointer;" class="green" onclick="edit('${var.id}');" title="编辑">
												<i class="ace-icon fa fa-pencil bigger-130"></i>
											</a>
											</c:if>
											&nbsp;
											<c:if test="${QX.del == 1 }">
											<a style="cursor:pointer;" class="red" onclick="del('${var.id}');" title="删除">
												<i class="ace-icon fa fa-trash-o bigger-130"></i>
											</a>
											</c:if>
										</td>
									</tr>
								
								</c:forEach>
								</c:if>
								<c:if test="${QX.cha == 0 }">
									<tr>
										<td colspan="100" class="center">您无权查看</td>
									</tr>
								</c:if>
							</c:when>
							<c:otherwise>
								<tr class="main_info">
									<td colspan="100" class="center" >没有相关数据</td>
								</tr>
							</c:otherwise>
						</c:choose>
						</tbody>
					</table>
					
				<div class="page-header position-relative">
				<table style="width:100%;">
					<tr>
						<td style="vertical-align:top;">
							<c:if test="${QX.del == 1 }">
							<a title="批量删除" class="btn btn-sm btn-danger" onclick="deleteAll('确定要删除选中的数据吗?');" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
							</c:if>
						</td>
						<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
					</tr>
				</table>
				</div>
			
				<div class="page-header position-relative">
				</div>
				</div>
				</form>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
    <div id="showDiv" style="position: absolute;background:#c8ddf7;width:300px; white-space:normal; word-break:break-all;word-wrap:break-word;"></div>
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	</body>
	<script type="text/javascript">
		$(top.hangge());
		
		function loadFile(fileName){
			var url = "<%=basePath%>/user/downFile.do?fileName="+fileName;
			url = encodeURI(url,"UTF-8");
			$("#loadFile").attr("href",url);
		}
		
		//检索
		function searchs(){
			top.jzts();
			$("#Form").submit();
		}
		//全选 （是/否）
		function selectAll(){
			 var checklist = document.getElementsByName ("ids");
			   if(document.getElementById("zcheckbox").checked){
			   for(var i=0;i<checklist.length;i++){
			      checklist[i].checked = 1;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++){
			     checklist[j].checked = 0;
			  }
			 }
		}
		
		function addOrEdit(url,title){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title = title;
			 diag.URL = url;
			 diag.Width = 850;
			 diag.Height = 450;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				window.location.reload();
			 };
			 diag.show();
		}
		
		//修改
		function edit(Id){
			addOrEdit('<%=basePath%>workcontent/goEdit.do?id='+Id,'工作内容修改');
		}
		
		//删除
		function del(Id){
			if(confirm("确定要删除?")){
				top.jzts();
				var url = "<%=basePath%>workcontent/delete.do?id="+Id;
				$.get(url,function(data){
					location.reload();
				});
			}
		}
		
		//批量操作
		function deleteAll(msg){
			if(confirm(msg)){ 
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str==''){
						alert("您没有选择任何内容!"); 
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>workcontent/deleteAll.do',
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
										 location.reload();
									 });
								}
							});
						}
					}
			}
		}

	</script>
	<style type="text/css">
	li {list-style-type:none;}
	.wrap{
	    list-style:none;
		overflow:hidden;
		white-space:nowrap;
		text-overflow:ellipsis;
		max-width: 200px;
		align: center;
		-o-text-overflow:ellipsis; 
		margin-top:5px; 
	}
	.li2{
	list-style:none;
	margin-top:5px;
	}
		
	</style>
	<ul class="navigationTabs">
           <li><a></a></li>
           <li></li>
       </ul>
</html>

