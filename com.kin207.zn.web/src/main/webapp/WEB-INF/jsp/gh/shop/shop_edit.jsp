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
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
						<form action="shop/${msg }.do" name="shopForm" id="shopForm"  method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" id="id" value="${pd.id }"/>
							<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">店名:</td>
									<td><input type="text" name="name" id="name" value="${pd.name }" placeholder="这里输入店名" title="店名" style="width:98%;" onblur="hasN('${pd.name }')"/></td>
								</tr>
								<tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">联系人:</td>
									<td><input type="text" name="phone" id="phone" value="${pd.phone }" placeholder="这里输入联系人" title="联系人" style="width:98%;" onblur="hasN('${pd.name }')"/></td>
								</tr>
								<tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">联系电话:</td>
									<td><input type="text" name="linkMan" id="linkMan" value="${pd.linkMan }" placeholder="这里输入联系电话" title="联系电话" style="width:98%;" onblur="hasN('${pd.name }')"/></td>
								</tr>
								<tr>																
									<td style="width:20%;text-align: right;padding-top: 15px;">店面图片:</td>
									<td>
										<!-- <input type="file" id="tp" name="tp" onchange="fileType(this)"/> -->
										
										<c:if test="${pd != null && pd.image != '' && pd.image != null }">
											<input type="file" name="image" id="image" value="${pd.image}" onchange="previewImage(this);fileType(this)"/>
											<a href="${pd.image}" target="_blank"><img src="${pd.image}" id = "image-show" width="260"/></a>
										</c:if>
										<c:if test="${empty pd.image }">
										<input type="file" id="image" name="image" onchange="previewImage(this)"/>
										</c:if>
									  <div id="preview">
										    <img id="imghead" width=100% height=auto border=0 >
										</div>
										
									</td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">地址:</td>
									<td><input type="text" name="pric" id="pric"  placeholder="输入地址"  title="地址" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">详细地址:</td>
									<td><input type="text" name="detailedAddress" id="detailedAddress"value="${pd.detailedAddress}" placeholder="这里输入详细地址" maxlength="64" title="备注" style="width:98%;"/></td>
								</tr>
								<tr>
									<td class="center" colspan="6">
										<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
										<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
									</td>
								</tr>
							</table>
							</div>
							<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="../../../../static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						</form>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script src="static/js/upload/uploadFile.js"></script>
</body>						
<script type="text/javascript">
	$(top.hangge());
	
	

	//保存
	function save(){
		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'输入名称',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		else{
			$("#shopForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	
	//判断编码是否存在
	function hasN(name){
		var newname = $("#name").val();
		if(name != newname){
		$.ajax({
			type: "POST",
			url: '<%=basePath%>shop/hasN.do',
	    	data: {name:newname},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 alert(data.result);
					 $("#name").tips({
							side:3,
				            msg:'名称'+newname+'已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $('#name').val('');
				 }
			}
		});
		}
	}
	$(function(){
	    $("#image").change(function (){
	    	 $('#image-show').remove();
	    });
	  });
</script>
</html>