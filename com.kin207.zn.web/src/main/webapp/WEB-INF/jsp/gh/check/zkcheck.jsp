<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
								<th class="center" >医生名称</th>
								<th class="center" >科室</th>
								<th class="center" >联盟医院</th>
								<th class="center" >工作类别</th>
								<th class="center" >查房/讲座/讨论内容</th>
								<th class="center" >查房/诊断/手术结果</th>
								<th class="center" >与会人数</th>
								<th class="center" >讲座/讨论/查房时间</th>
								<th class="center" >课件</th>
								<th class="center" >填报人</th>
								<th class="center" >填报日期</th>
								<th class="center" >审核意见</th>
								<th class="center" >审核</th>
							</tr>
						</thead>
						<tbody>
						<!-- 开始循环 -->	
						<c:choose>
							<c:when test="${not empty checkList}">
								<c:if test="${QX.cha == 1 }">
								<c:forEach items="${checkList}" var="var" varStatus="vs">
									<tr>
										<td class='center' style="width: 30px;">
											<label><input type='checkbox' name='ids' class="ace" value="${var.id}" /><span class="lbl"></span></label>
										</td>
										
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td class="center">${var.doctorName}</td>
										<td class="center">${var.depName}</td>
										<td class="center">${var.hosName}</td>
										<td class="center">${var.workName}</td>
										<td class="center">${var.content}</td>
										<td class="center">${var.medicalResult}</td>
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
										<td class="center"><label style="color:#ff0000">${var.zkOpinion}</label></td>
										<td>
											<c:if test="${QX.check == 1 }">
											<a style="cursor:pointer;" class="green" onclick="edit('${var.id}');" title="审核">
												<i class="ace-icon fa fa-pencil bigger-130"></i>
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
		var type = ${type};
		
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
		
		//修改
		function edit(Id){
			var opinion = prompt("输入您的审核意见", "");
			$.ajax({
				type: "POST",
				url: '<%=basePath%>check/addOpinion.do?type=3',
		    	data: {id:Id,opinion:opinion},
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					window.location.href = '<%=basePath%>check/zkcheck.do';
				}
			});
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

