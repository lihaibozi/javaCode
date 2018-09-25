<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>${pd.SYSNAME}</title>
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="static/ace/css/bootstrap.min.css" />
<link rel="stylesheet" href="static/ace/css/font-awesome.css" />
<!-- page specific plugin styles -->
<!-- text fonts -->
<link rel="stylesheet" href="static/ace/css/ace-fonts.css" />
<!-- ace styles -->
<link rel="stylesheet" href="static/ace/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
<!--[if lte IE 9]>
	<link rel="stylesheet" href="static/ace/css/ace-part2.css" class="ace-main-stylesheet" />
<![endif]-->
<!--[if lte IE 9]>
  <link rel="stylesheet" href="static/ace/css/ace-ie.css" />
<![endif]-->
<!-- inline styles related to this page -->
<!-- ace settings handler -->
<script src="static/ace/js/ace-extra.js"></script>
<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
<!--[if lte IE 8]>
<script src="static/ace/js/html5shiv.js"></script>
<script src="static/ace/js/respond.js"></script>
<![endif]-->
<!--jsp图片插件 -->
<%@ include file="../../system/index/picturePlugin.jsp"%>

</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<form action="ghUser/goUserAddress.do" method="post" name="AForm" id="AForm">
					<div id="zhongxin" style="padding-top: 13px;">
					<table style="margin-top:15px;margin-left:15px;">
						<tr>
							<td>
								<div class="nav-search">
									<span class="input-icon"> 
										<input type="hidden" name="userId" value="${pd.userId}"/>
										<input autocomplete="off" class="nav-search-input" id="nav-search-input" type="text" name="keywords" value="${pd.keywords}" placeholder="输入收件人、手机号" />
										<i class="ace-icon fa fa-search nav-search-icon" style="top:5px;"></i>
									</span>
								</div>
							</td>
							<td style="vertical-align:top;padding-left:5px;">
								<button type="button" class="btn btn-info btn-sm" onclick="searchAdds();" title="SEARCH">
									<i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>&nbsp;SEARCH
								</button>
							</td>
						</tr>
					</table>
					<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;margin-left:15px;">
						<thead>
							<tr>
								<th class="center" onclick="selectAll()" style="width:35px;">
								<label><input type="checkbox" id="zcheckbox" class="ace" /><span class="lbl"></span></label>
								</th>
								<th class="center" style="width:30px;">序号</th>
								<th class="center" >收件人</th>
								<th class="center" >手机号码</th>
								<th class="center" >地址</th>
								<th class="center" >详细地址</th>
								<th class="center" >创建时间</th>
								<!-- <th class="center">操作</th> -->
								
							</tr>
						</thead>
						<tbody>
						<!-- 开始循环 -->	
						<c:choose>
							<c:when test="${not empty addList}">
								<c:forEach items="${addList}" var="var" varStatus="vs">
									<tr>
										<td class='center' style="width: 30px;">
											<label><input type='checkbox' name='ids' class="ace" value="${var.id}" /><span class="lbl"></span></label>
										</td>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td class="center">${var.recipients}</td>
										<td class="center">${var.phone}</td>
										<td class="center">${var.address}</td>
										<td class="center">${var.detailedAddress}</td>
										<td class="center">${var.createTime}</td>
										<%-- <td class="center" style="width:130px;">
											<c:if test="${QX.edit != 1}">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
											</c:if>
											<c:if test="${QX.edit == 1 }">
												<a style="cursor:pointer;" class="green" onclick="edit('${var.id}');" title="编辑">
													<i class="ace-icon fa fa-pencil bigger-130"></i>
												</a>
											</c:if>
										</td>  --%>
									</tr>
								
								</c:forEach>
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
					</div>
				</form>
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
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(top.hangge());
		
		function searchAdds(){
			$("#AForm").submit();
		}
		
		//修改
		<%-- function edit(id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag = true;
			 diag.Title = "用户状态修改"; 
			 diag.Height = 507;
			 diag.Width = 700;
			 diag.URL = '<%=basePath%>bkUser/goAddEdit.do?id='+id;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage('${page.currentPage}');
				}
				diag.close();
			 };
			 diag.show();
		} --%>
		
	</script>

</body>
</html>