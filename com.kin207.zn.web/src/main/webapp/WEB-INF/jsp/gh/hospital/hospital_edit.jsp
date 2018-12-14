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
						<form action="hospital/${msg}.do" name="hospitalForm" id="hospitalForm"  method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" id="id" value="${pd.id}"/>
							<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">医院名称<label style="color:#ff0000">*</label></td>
									<td><input type="text" name="name" id="name" value="${pd.name}" placeholder="这里输入医院名称" title="医院名称" style="width:98%;" onblur="hasN('${pd.name }')" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">医院地址</td>
									<td><input type="text" name="address" id="address" value="${pd.address}" placeholder="这里输入医院地址" title="医院地址" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">医院级别</td>
									<td><input type="text" name="level" id="level" value="${pd.level}" placeholder="这里输入医院级别" title="医院级别" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">医院首拼<label style="color:#ff0000">*</label></td>
									<td><input type="text" name="firstSpell" id="firstSpell" value="${pd.firstSpell}" placeholder="这里输入医院首拼" title="医院首拼" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">医院联系人<label style="color:#ff0000">*</label></td>
									<td><input type="text" name="contactPerson" id="contactPerson" value="${pd.contactPerson}" placeholder="这里输入医院联系人" title="医院联系人" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:120px;text-align: right;padding-top: 13px;">医院联系人电话<label style="color:#ff0000">*</label></td>
									<td><input type="text" name="contactPhone" id="contactPhone" value="${pd.contactPhone}" placeholder="这里输入医院联系人电话" title="医院联系人电话" style="width:98%;" /></td>
								</tr>
								<%-- <tr>
									<td style="width:120px;text-align: right;padding-top: 13px;">备注</td>
									<td><textarea id="A1" name="A1" rows="5" cols="=5" style="resize:none;width:98%">${pd.A1}</textarea></td>
								</tr> --%>

								<tr>
									<td class="center" colspan="6">
										<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
										<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
									</td>
								</tr>
							</table>
							<label style="color:#0000FF">备注:带<label style="color:#ff0000">*</label>为必填项</label>
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
	<script src="static/js/common/common.js"></script>
</body>						
<script type="text/javascript">
	$(top.hangge());

	//判断名称是否存在
	function hasN(name){
		var newname = $("#name").val();
		if(name != newname){
		$.ajax({
			type: "POST",
			url: '<%=basePath%>hospital/hasN.do',
	    	data: {name:newname},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
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
	//保存
	function save(){
		if(checkNull("name","请输入医院名称","name")){
			return ;
		};
		if(checkNull("firstSpell","请输入首拼","firstSpell")){
			return ;
		};
		if(checkNull("contactPerson","请输入医院联系人","contactPerson")){
			return ;
		};
		if(checkNull("contactPhone","请输入医院联系人电话","contactPhone")){
			return ;
		};
		$("#hospitalForm").submit();
	}
	$(function(){
		/* $('.date-picker').datepicker({autoclose: true,todayHighlight: true,format: 'yyyy-mm-dd'}); */
	    $("#image").change(function (){
	    	 $('#image-show').remove();
	    });
	  });
</script>
</html>