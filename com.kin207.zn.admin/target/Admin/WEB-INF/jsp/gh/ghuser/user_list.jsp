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
				<form action="ghUser/list.do" method="post" name="Form" id="Form">
					<table style="margin-top:15px;margin-left:15px;">
						<tr>
							<td>
								<div class="nav-search">
									<span class="input-icon"> 
										<input autocomplete="off" class="nav-search-input" id="nav-search-input" type="text" name="keywords" value="${pd.keywords}" placeholder="输入微信昵称、宝宝昵称、手机号" />
										<i class="ace-icon fa fa-search nav-search-icon" style="top:5px;"></i>
									</span>
								</div>
							</td>
							<td style="vertical-align:top;padding-left:5px;">
								<button type="button" class="btn btn-info btn-sm" onclick="searchUsers();" title="SEARCH">
									<i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>&nbsp;SEARCH
								</button>
							</td>
						</tr>
					</table>
					<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;margin-left:15px;">
						<thead>
							<tr>
								<th class="center" onclick="selectAll()" style="width:10%;">
								<label><input type="checkbox" id="zcheckbox" class="ace" /><span class="lbl"></span></label>
								</th>
								<th class="center" style="width:10%;">序号</th>
								<th class="center" style="width: 20%;">微信昵称</th>
								<th class="center" style="width: 10%;">手机号码</th>
								<th class="center" style="width: 10%;">性别</th>
								<th class="center" style="width: 20%;">最后登录时间</th>
								<th class="center" style="width: 20%;">操作</th>
							</tr>
						</thead>
						<tbody>
						<!-- 开始循环 -->	
						<c:choose>
							<c:when test="${not empty userList}">
								<c:if test="${QX.cha == 1 }">
								<c:forEach items="${userList}" var="var" varStatus="vs">
									<tr>
										<td class='center' style="width: 10%;">
											<label><input type='checkbox' name='ids' class="ace" value="${var.id}" /><span class="lbl"></span></label>
										</td>
										<td class='center' style="width: 10%;">${vs.index+1}</td>
										<td  style="width: 20%;">
											<a href="javascript:;" data-id="${var.id}">
												<span class="emp-photo"><img src="${var.photo}"/></span>&nbsp;&nbsp;${var.nickName}
											</a>
										</td>
										<td class="center" style="width: 10%;">${var.phone}</td>
										<td style="width: 10%;" class="center">
											<c:if test="${var.gender == 'M'}">男</c:if>
											<c:if test="${var.gender == 'F'}">女</c:if>
										</td>
										<td style="width: 20%;" class="center">${var.lastLoginTime}</td>
										<td style="width: 20%;" class="center" >
											<c:if test="${QX.edit != 1}">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
											</c:if>
											<%-- <a style="cursor:pointer;" class="blue" onclick="order('${var.id}');" title="用户订单">
												<i class="ace-icon fa fa-reorder bigger-130"></i>
											</a>&nbsp; --%>
											<a style="cursor:pointer;" class="blue" onclick="address('${var.id}');" title="用户地址">
												<i class="ace-icon fa fa-home bigger-130"></i>
											</a>&nbsp;
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
				</form>
				<form action="order/list.do" method="post" name="order_form" id="order_form" style="display: none;">
					<input type="hidden" id="user-order-search-id" name="userId" />
					<input type="hidden" id="go_order_keywords" name="condition"/>
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
		
		function searchUsers(){
			$("#Form").submit();
		}
		
		
		//用户地址列表
        function address(id) {
            top.jzts();
            var diag = new top.Dialog();
            diag.Title = "用户地址列表";  
            diag.URL = '<%=path%>/ghUser/goUserAddress.do?userId=' + id;
            diag.Drag = true;
            diag.Width = 1200;
            diag.Height = 700;
            diag.CancelEvent = function(){ //关闭事件
                if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                    nextPage('${page.currentPage}');
                }
                diag.close();
            };
            diag.show();
        }
		
      	//用户订单列表 
        function order(id){
        	$("#user-order-search-id").val(id);
        	$("#go_order_keywords").val($("#nav-search-input").val());
        	$("#order_form").submit();
        }
		
	</script>

</body>
</html>