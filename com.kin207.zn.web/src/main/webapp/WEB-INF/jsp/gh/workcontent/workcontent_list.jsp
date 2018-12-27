<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
					<table style="margin-top:5px;">
						<tr>
							<td>
								<div class="nav-search">
								<span class="input-icon">
									<input autocomplete="off" class="nav-search-input"  id="nav-search-input" type="text" name="keyword"  value="${pd.keyword}" placeholder="输入医生名称" />
								</span>
								</div>
							</td>
							<c:if test="${QX.cha == 1 }">
							<td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="searchs();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
							</c:if>
						</tr>
					</table>
					<!-- 检索  -->
					<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
						<thead>
							<tr>
								<th class="center" onclick="selectAll()" style="width:35px;">
								<label><input type="checkbox" id="zcheckbox" class="ace" /><span class="lbl"></span></label>
								</th>
								<th class="center" style="width:50px;">序号</th>
								<th class="center" >所属科室</th>
								<th class="center" >医生名称</th>
								<th class="center" >联盟医院</th>
								<th class="center" >时间</th>
								<th class="center" >工作类别</th>
								<th class="center" >创建人</th>
								<th class="center" >创建时间</th>
								<th class="center" >修改人</th>
								<th class="center" >修改时间</th>
								<th class="center" >审核状态</th>
								<th class="center" >审核意见</th>
								<th class="center" >工作是否填报</th>
								<th class="center" >操作</th>
							</tr>
						</thead>
						<tbody>
						<!-- 开始循环 -->	
						<c:choose>
							<c:when test="${not empty workcontentList}">
								<c:if test="${QX.cha == 1 }">
								<c:forEach items="${workcontentList}" var="var" varStatus="vs">
									<tr>
										<td class='center' style="width: 30px;">
											<label><input type='checkbox' name='ids' class="ace" value="${var.id}" /><span class="lbl"></span></label>
										</td>
										
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td class="center">${var.depName}</td>
										<td class="center">${var.doctorName}</td>
										<td class="center">${var.hosName}</td>
										<td class="center"><fmt:formatDate value="${var.workTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td class="center">${var.workName}</td>
										<td class="center">${var.creator}</td>
										<td class="center"><fmt:formatDate value="${var.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td class="center">${var.modifyPeople}</td>
										<td class="center"><fmt:formatDate value="${var.modifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<c:if test="${var.checkStatus == 1 }">
											<td class="center">未审核</td>
										</c:if>
										<c:if test="${var.checkStatus == 4 }">
											<td class="center"><label style="color:#ff0000">客服部驳回</label></td>
										</c:if>
										<c:if test="${var.checkStatus == 5 }">
											<td class="center"><label style="color:#0000FF">客服部审核通过</label></td>
										</c:if>
										<c:if test="${var.checkStatus == 2 }">
											<td class="center"><label style="color:#ff0000">医学事务部驳回</label></td>
										</c:if>
										<c:if test="${var.checkStatus == 3 }">
											<td class="center"><label style="color:#0000FF">医学事务部审核通过</label></td>
										</c:if>
										<td class="center"><label style="color:#ff00d2">${var.rejectReason}</label></td>
										<c:if test="${var.isFinish == 1 }">
											<td class="center"><label style="color:#0000FF">已填报</label></td>
										</c:if>
										<c:if test="${var.isFinish == 0 }">
											<td class="center"><label style="color:#ff0000">未填报</label></td>
										</c:if>
										<td>
											<c:if test="${QX.add == 1 }">
												<a style="cursor:pointer;" class="green" onclick="add('${var.id}');" title="工作内容填报">
													<i class="ace-icon fa fa-pencil bigger-130"></i>
												</a>
											</c:if>
											<c:if test="${QX.cha == 1 }">
												<a style="cursor:pointer;" class="blue" onclick="view('${var.id}');" title="查看已填报">
													<i class="ace-icon fa fa-search bigger-110 nav-search-icon red"></i>
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
				 if('${page.currentPage}' == '0'){
					 top.jzts();
					 setTimeout("self.location=self.location",100);
				 }else{
					 nextPage('${page.currentPage}');
				 }
				diag.close();
			 };
			 diag.show();
		}
		
		//修改
		function add(Id){
			addOrEdit('<%=basePath%>workcontent/goAdd.do?id='+Id,'工作内容填报');
		}
		//修改
		function view(Id){
			top.jzts();
			var url = "<%=basePath%>workcontent/goView.do?id="+Id;
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title = "工作内容列表";
			 diag.URL = url;
			 diag.Width = 1200;
			 diag.Height = 600;
			 diag.CancelEvent = function(){ //关闭事件
				 if('${page.currentPage}' == '0'){
					 top.jzts();
					 setTimeout("self.location=self.location",100);
				 }else{
					 nextPage('${page.currentPage}');
				 }
				diag.close();
			 };
			 diag.show();
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

