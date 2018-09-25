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
						<form action="goods/${msg }.do" name="gsForm" id="gsForm" method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" id="id" value="${pd.id }"/>
							<div id="zhongxin" style="padding-top: 13px;">
							<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">菜品类型:</td>
									<td id="js">
									<select class="chosen-select form-control" name="typeCode" id="typeCode" data-placeholder="请选择菜品类型" style="vertical-align:top;"  title="菜品类型" style="width:98%;" >
									<option value=""></option>
									<c:forEach items="${typeList}" var="type">
										<option value="${type.code }" <c:if test="${type.type == pd.typeCode }">selected</c:if>>${type.name }</option>
									</c:forEach>
									</select>
									</td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">归属(店面):</td>
									<td id="js">
									<select class="chosen-select form-control" name="shopId" id="shopId" data-placeholder="请选择店面" style="vertical-align:top;"  title="店面" style="width:98%;" >
									<option value="0">通用</option>
									<c:forEach items="${shopList}" var="shop">
										<option value="${shop.id }" <c:if test="${shop.id == pd.id }">selected</c:if>>${shop.name }</option>
									</c:forEach>
									</select>
									</td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">菜名:</td>
									<td><input type="text" name="name" id="name" value="${pd.name }" placeholder="这里输入菜名" title="菜名" style="width:98%;" onblur="hasN('${pd.name }')"/></td>
								</tr>
							<tr>
									<td style="width:20%;text-align: right;padding-top: 15px;">菜品图片:</td>
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
									<td style="width:79px;text-align: right;padding-top: 13px;">价格:</td>
									<td><input type="text" name="pric" id="pric"  placeholder="输入价格"  title="价格" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
									<td><input type="text" name="remark" id="remark"value="${pd.remark}" placeholder="这里输入备注" maxlength="64" title="备注" style="width:98%;"/></td>
								</tr>
								<tr>
									<td class="center" colspan="6">
										<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
										<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
									</td>
								</tr>
							</table>
							</div>
							<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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
	<script src="../../../../static/ace/js/ace/ace.js"></script>
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
		if($("#typeCode").val()!=$("#typeCode").val()){
			$("#typeCode").tips({
				side:3,
	            msg:'请选择菜品类型',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#typeCode").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'输入姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		else{
			$("#gsForm").submit();
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
			url: '<%=basePath%>goods/hasN.do',
	    	data: {name:newname},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#name").tips({
							side:3,
				            msg:'菜名'+name+'已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $('#name').val('');
				 }
			}
		});
		}
	}
	//过滤类型
	function fileType(obj){
		var fileType=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	    if(fileType != '.gif' && fileType != '.png' && fileType != '.jpg' && fileType != '.jpeg'){
	    	$("#image").tips({
				side:3,
	            msg:'请上传图片格式的文件',
	            bg:'#AE81FF',
	            time:3
	        });
	    	$("#image").val('');
	    	document.getElementById("image").files[0] = '请选择图片';
	    }
	}
	
	$(function(){
	    $("#image").change(function (){
	    	 $('#image-show').remove();
	    });
	  });
</script>
</html>