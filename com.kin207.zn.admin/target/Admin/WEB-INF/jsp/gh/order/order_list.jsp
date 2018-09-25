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

<style type="text/css">
	li {list-style-type:none;}
	.wrap{
	    list-style:none;
		overflow:hidden;
		white-space:nowrap;
		text-overflow:ellipsis;
		max-width: 150px;
		align: center;
		-o-text-overflow:ellipsis; 
		margin-top:5px; 
	}
	.li2{
		list-style:none;
		margin-top:5px;
	}
</style>

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
				<form action="order/list.do" method="post" name="Form" id="Form">
					<table style="margin-top:15px;margin-left:10px;">
						<tr>
							<td>
								<div class="nav-search">
									<span class="input-icon"> 
										<input autocomplete="off" class="nav-search-input"  id="nav-search-input" type="text" name="keywords" value="${pd.keywords}" placeholder="输入订单号、包编码" />
										<i class="ace-icon fa fa-search nav-search-icon" style="top:5px;"></i>
									</span>
								</div>
							</td>
							<td>
								<div class="nav-search">
									<span class="input-icon"> 
										<select class="chosen-select form-control" name="keywords3" id="keywords3" data-placeholder="请选择订单状态" style="vertical-align:top;width: 180px;border: 1px solid #6fb3e0;height: 34px" >
											<option value="" <c:if test="${pd.keywords3 == '' or  pd.keywords3 == null}">selected</c:if>>全部</option>
											<option value="1" <c:if test="${pd.keywords3 == '1' }">selected</c:if>>进行中</option>
											<option value="2" <c:if test="${pd.keywords3 == '2' }">selected</c:if>>结束</option>
										</select>
									</span>
								</div>
								</td>
								<td>
								<div class="nav-search">
									<span class="input-icon"> 
										<select class="chosen-select form-control" name="keywords1" id="keywords1" data-placeholder="请选择订单类型" style="vertical-align:top;width: 180px;border: 1px solid #6fb3e0;height: 34px" >
											<option value="" <c:if test="${pd.keywords1 == '' or  pd.keywords1 == null}">selected</c:if>>全部</option>
											<option value="1" <c:if test="${pd.keywords1 == '1' }">selected</c:if>>在店</option>
											<option value="2" <c:if test="${pd.keywords1 == '2' }">selected</c:if>>外卖</option>
										</select>
									</span>
								</div>								
							</td>
							<td style="vertical-align:top;padding-left:5px;">
								<button type="button" class="btn btn-info btn-sm" onclick="searchOrders();" title="SEARCH">
									<i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>&nbsp;SEARCH
								</button>
					<%-- 			<c:if test="${not empty pd.userId}">
								<button type="button" class="btn btn-success btn-sm" onclick="goBack();" title="返回">
									<i id="nav-return-icon" class="ace-icon fa fa-mail-reply bigger-110 nav-search-icon green"></i>&nbsp;返回
								</button>
								</c:if> --%>
							</td>
						</tr>
					</table>
					<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;margin-left:1px;">
						<thead>
							<tr>
								<th class="center" onclick="selectAll()" style="width:2%;">
								<label><input type="checkbox" id="zcheckbox" class="ace" /><span class="lbl"></span></label>
								</th>
								<th class="center" style="min-width: 3%;">序号</th>
								<th class="center" style="min-width: 10%;">订单号</th>
								<th class="center" style="min-width: 15%;">选择菜单</th>
								<th class="center" style="min-width: 8%;">收件人</th>
								<th class="center" style="min-width: 8%;">联系电话</th>
								<th class="center" style="min-width: 5;">订单价格</th>
								<th class="center" style="min-width: 5%;">订单状态</th>
								<th class="center" style="min-width: 5%;">订单类型</th>
								<th class="center" style="min-width: 8%;">订单时间</th>
								<th class="center" style="min-width: 15%;">地址</th>
								<th class="center" style="min-width: 10%;">备注</th>
								<th class="center" style="min-width: 10%;">操作</th>
							</tr>
						</thead>
						<tbody>
						<!-- 开始循环 -->	
						<c:choose>
							<c:when test="${not empty orderList}">
								<c:if test="${QX.cha == 1 }">
								<c:forEach items="${orderList}" var="var" varStatus="vs">
									<tr>
										<td class='center' style="width: 30px;">
											<label><input type='checkbox' name='ids' class="ace" value="${var.id}" /><span class="lbl"></span></label>
										</td>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td style="min-width: 10%;">${var.orderNo}</td>
										<td style="min-width: 15%;" class="center">${var.goodNames}</td>
										<td style="min-width: 8%;" class="center">${var.recipients}</td>
										<td  style="min-width: 8%;"class="center">${var.phone}</td>
										<td style="min-width: 5%;" class="center">${var.price}</td>
										<td style="min-width: 5%;" class="center">
											<c:if test="${var.status == 1}"><span class="label label-success arrowed">进行中</span></c:if>
											<c:if test="${var.status == 2}"><span class="label label-important arrowed-in">结束</span></c:if>
										</td>
										<td style="min-width: 5%;" class="center">
											<c:if test="${var.type == 1}"><span class="label label-success arrowed">在店</span></c:if>
											<c:if test="${var.type == 2}"><span class="label label-important arrowed-in">外卖</span></c:if>
										</td>
										<td style="min-width: 8%;" class="center">${var.createTime}</td>
										<td style="min-width: 15%;" class="wrap" onmouseover="overShow(event,'${var.address}${var.detailedAddress }')" onmouseout="outHide()">${var.address}${var.detailedAddress }</td>
										<td style="min-width: 10%;" class="wrap" onmouseover="overShow(event,'${var.remark}')" onmouseout="outHide()">${var.remark}</td> 
										<td style="min-width: 10%;" class="center" style="width:70px;">
											<c:if test="${QX.edit != 1}">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
											</c:if>
											<c:if test="${QX.edit == 1 }">
											<c:if test="${var.status == 1}">
												<a style="cursor:pointer;" class="green" onclick="edit('${var.id}','${var.orderNo}');" title="结束订单">
													<i class="ace-icon fa fa-pencil bigger-130"></i>
												</a>&nbsp;
												</c:if>
												<c:if test="${var.type == 2}">
												<a style="cursor:pointer;" class="blue" onclick="orderAddres('${var.id}', '${var.addressId}','${var.userId}' );" title="配送地址">
													<i class="ace-icon fa fa-home bigger-130"></i>
												</a>&nbsp;
											 </c:if>
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
				</form>
				<form action="bkUser/returnList.do" method="post" id="return_form" style="display: none;">
					<input id="return_keywords" type="hidden" name="keywords" />
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
	  <div id="showDiv" style="position: absolute;background:#c8ddf7;width:300px; white-space:normal; word-break:break-all;word-wrap:break-word;"></div>
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
		<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(top.hangge());
		
		function searchOrders(){
			$("#Form").submit();
		}
		
		//修改
		function edit(id,msg){
			bootbox.confirm("您结束订单号是:["+msg+"]", function(result) {
				if(result) {
					top.jzts();
					var url = '<%=path%>/order/edit.do?id='+id;
					$.get(url,function(data){
						nextPage('${page.currentPage}');
					});
				}
			});
		}	
	
		
		//订单地址
		function orderAddres(id, addressId,userId){
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag = true;
			diag.Title = "订单地址修改"; 
			diag.Height = 410;
			diag.Width = 500;
			diag.URL = '<%=path%>/order/goOrderAddress.do?id=' + addressId + '&orderId=' + id+ '&userId=' + userId;
			diag.CancelEvent = function(){ //关闭事件
				if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage('${page.currentPage}');
				}
				diag.close();
			};
			diag.show();
		}
		
		function goBack(){
			$("#return_keywords").val($("#condition").val());
			$("#return_form").submit();
		}
		
		
		function overShow(e,obj) {
			var showDiv = document.getElementById('showDiv');
			showDiv.left = e.pageX;
			showDiv.top = e.pageY;
			$('#showDiv').css("left", e.pageX);
			$('#showDiv').css("top",e.pageY);
			$('#showDiv').css("display",'block');
			showDiv.innerHTML =obj;
		}
			 
		function outHide() {
			var showDiv = document.getElementById('showDiv');
			showDiv.style.display = 'none';
			showDiv.innerHTML = '';
		}
		
	</script>

</body>
</html>