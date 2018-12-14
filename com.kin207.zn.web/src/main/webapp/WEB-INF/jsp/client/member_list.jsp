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
<%@ include file="../system/index/picturePlugin.jsp"%>

</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<form action="member/listMembers.do" method="post" name="Form" id="Form">
					<table style="margin-top:15px;margin-left:15px;">
						<tr>
							<td>
								<div class="nav-search">
									<span class="input-icon"> 
										<input autocomplete="off" class="nav-search-input"  id="nav-search-input" type="text" name="keywords"  value="${pd.keywords}" placeholder="输入客户名、邮箱、电话" />
										<i class="ace-icon fa fa-search nav-search-icon" style="top:5px;"></i>
									</span>
								</div>
							</td>
							<td style="vertical-align:top;padding-left:5px;">
								<button type="button" class="btn btn-info btn-sm" onclick="searchMembers();" title="SEARCH">
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
								<th class="center" style="width:50px;">序号</th>
								<th class="center" >会员</th>
								<th class="center" >昵称</th>
								<th class="center" >电话</th>
								<th class="center" ><i class="ace-icon fa fa-envelope-o"></i>邮箱</th>
								<th class="center">操作</th>
							</tr>
						</thead>
						<tbody>
						<!-- 开始循环 -->	
						<c:choose>
							<c:when test="${not empty memberList}">
								<c:if test="${QX.cha == 1 }">
								<c:forEach items="${memberList}" var="var" varStatus="vs">
									<tr>
										<td class='center' style="width: 30px;">
											<label><input type='checkbox' name='ids' class="ace" value="${var.PICTURES_ID}" /><span class="lbl"></span></label>
										</td>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td>
											<a href="javascript:;" data-value="${var.username}" data-img="${var.headImg}" data-tel="${var.tel}" data-email="${var.email}" data-balance="${var.balance}" onclick="memberOrder(this);">
												<span class="emp-photo"><img src="${var.headImg}"></span>&nbsp;&nbsp;${var.username}
											</a>
										</td>
										<td class="center">${var.nickname}</td>
										<td class="center">${var.tel}</td>
										<td class="center">
											<a title="发送电子邮件" style="text-decoration:none;cursor:pointer;" onclick="sendEmail('${var.email}');">${var.email}&nbsp;<i class="ace-icon fa fa-envelope-o"></i></a>
										</td>
										<td class="center" style="width:130px;">
										<c:if test="${QX.edit != 1}">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
										</c:if>
											<c:if test="${QX.edit == 1 }">
											<a style="cursor:pointer;" class="green" onclick="edit('${var.id}');" title="编辑">
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
							<td style="vertical-align:top;">
								<a title="批量发送电子邮件" class="btn btn-mini btn-primary" onclick="makeAll('确定要给选中的用户发送邮件吗?');"><i class="ace-icon fa fa-envelope bigger-120"></i></a>
								<!-- <a title="批量发送短信" class="btn btn-mini btn-warning" onclick="makeAll('确定要给选中的用户发送短信吗?');"><i class="ace-icon fa fa-envelope-o bigger-120"></i></a>
								<a title="批量删除" class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a> -->
								
								<%-- <c:if test="${QX.add == 1 }">
								<a class="btn btn-mini btn-success" onclick="add();">新增</a>
								</c:if>
								<c:if test="${QX.email == 1 }"><a title="批量发送电子邮件" class="btn btn-mini btn-primary" onclick="makeAll('确定要给选中的用户发送邮件吗?');"><i class="ace-icon fa fa-envelope bigger-120"></i></a></c:if>
								<c:if test="${QX.sms == 1 }"><a title="批量发送短信" class="btn btn-mini btn-warning" onclick="makeAll('确定要给选中的用户发送短信吗?');"><i class="ace-icon fa fa-envelope-o bigger-120"></i></a></c:if>
								<c:if test="${QX.del == 1 }">
								<a title="批量删除" class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
								</c:if> --%>
							</td>
							<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
						</tr>
					</table>
					</div>
				</form>
				<form action="getOrder/userOrderList.do" method="post" name="order_form" id="order_form" style="display: none;">
					<input id="user-order-search-input" type="hidden" name="username" />
					<input id="user-order-search-tel" type="hidden" name="tel" />
					<input id="user-order-search-email" type="hidden" name="email" />
					<input id="user-order-search-headImg" type="hidden" name="headImg" />
					<input id="user-order-search-balance" type="hidden" name="balance" />
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
	<%@ include file="../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(top.hangge());
		$(document).ready(function () {
			$("#youtuer_order_type").html(Form.make("select", YTT_ORDER_TYPE, {id: "status", name : "task.status", label : "", placeholder : "请选择订单类型"}));
		});
		
		function searchMembers(){
			$("#Form").submit();
		}
		
		function memberOrder(obj){
			var username = $(obj).attr("data-value");
			if(username){
				$("#user-order-search-input").val(username);
				$("#user-order-search-tel").val($(obj).attr("data-tel"));
				$("#user-order-search-email").val($(obj).attr("data-email"));
				$("#user-order-search-headImg").val($(obj).attr("data-img"));
				$("#user-order-search-balance").val($(obj).attr("data-balance"));
				$("#order_form").submit();
			}
		}
		
		//去发送电子邮件页面
		function sendEmail(EMAIL){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="发送电子邮件";
			 diag.URL = '<%=basePath%>head/goSendEmail.do?EMAIL='+EMAIL;
			 diag.Width = 660;
			 diag.Height = 486;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//修改
		function edit(id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="资料";
			 diag.URL = '<%=basePath%>member/goEditU.do?id='+id;
			 diag.Width = 469;
			 diag.Height = 402;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
	</script>

</body>
</html>