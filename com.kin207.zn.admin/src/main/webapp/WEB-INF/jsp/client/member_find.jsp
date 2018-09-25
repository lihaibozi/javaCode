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
				<form action="member/findMember.do" method="post" name="Form" id="Form">
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
					
					<c:if test="${QX.cha == 1 }">
					<c:choose>
						<c:when test="${!empty pd && not empty member}">
					    	<table style="margin-top:5px;margin-left:15px;font-size:16px;" class="table-bordered" >
								<tr>
									<td rowspan="3">
										<a href="javascript:;" data-value="${member.username}" data-img="${member.headImg}" data-tel="${member.tel}" data-email="${member.email}" data-balance="${member.balance}" onclick="memberOrder(this);">
											<img id="user-today-lea-photo" style="display:block;width:72px;height:72px;margin:auto;border-radius:50% !important;overflow:hidden;" src="${member.headImg}">
										</a>
									</td>
									<td style="padding-left: 50px;">
										<a href="javascript:;" data-value="${member.username}" data-img="${member.headImg}" data-tel="${member.tel}" data-email="${member.email}" data-balance="${member.balance}" onclick="memberOrder(this);">
											<span id="menber_nickname_info">${member.username}</span>
										</a>
									</td>
								</tr>
								<tr>
									<td style="padding-left: 20px;padding-right: 20px;">
										<span class="user-personal-info"><i class="ace-icon fa fa-mobile-phone bigger-150"></i></span>&nbsp;
						         		<span id="member_phone_info">${member.tel}</span>
									</td>
								</tr>
								<tr>
									<td style="padding-left: 20px;padding-right: 20px;">
										<span class="user-personal-info"><i class="ace-icon fa fa-at bigger-80"></i></span>&nbsp;
						           		<span id="member_email_info">
						           			<a title="发送电子邮件" style="text-decoration:none;cursor:pointer;" onclick="sendEmail('${member.email}');">${member.email}</a>
						           		</span>
									</td>
								</tr>
							</table>
				    	</c:when>
				    	<c:when test="${!empty pd && empty member }">
				    		<span style="margin-top:15px;margin-left:15px;font-size:30px;font-weight: bold;">查询无结果！</span>
				    	</c:when>
			    	</c:choose>
			    	</c:if>
					<c:if test="${QX.cha == 0 }">
						<span style="margin-top:15px;margin-left:15px;font-size:30px;font-weight: bold;">您无权查看</span>
					</c:if>
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
	</script>

</body>
</html>