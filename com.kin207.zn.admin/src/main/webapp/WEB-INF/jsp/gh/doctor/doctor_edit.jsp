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
						<form action="doctor/${msg}.do" name="doctorForm" id="doctorForm"  method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" id="id" value="${pd.id }"/>
							<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">昵称</td>
									<td><input type="text" name="doctorNickName" id="doctorNickName" value="${pd.doctorNickName }" placeholder="这里输入昵称" title="昵称" style="width:98%;" onblur="hasN('${pd.doctorNickName }')"/></td>
								</tr>
								<tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">医生姓名:</td>
									<td><input type="text" name="doctorName" id="doctorName" value="${pd.doctorName }" placeholder="这里输入医生姓名" title="医生姓名" style="width:98%;" onblur="hasN('${pd.doctorName }')"/></td>
								</tr>
								<tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">医生微信号:</td>
									<td><input type="text" name="doctorWxId" id="doctorWxId" value="${pd.doctorWxId }" placeholder="这里输入医生微信号" title="医生微信号" style="width:98%;" onblur="hasN('${pd.doctorWxId }')"/></td>
								</tr>
								
								<tr>
									<td style="width:90px;text-align: right;padding-top: 13px;">医生所属医院:</td>
									<td><input type="text" name="dotorHos" id=dotorHos value="${pd.dotorHos }" placeholder="这里输入医生所属医院" title="医生所属医院" style="width:98%;" onblur="hasN('${pd.dotorHos }')"/></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">医生区域:</td>
									<td><input type="text" name="doctorArea" id="doctorArea" value="${pd.doctorArea }" placeholder="这里输入医生区域" title="医生区域" style="width:98%;" onblur="hasN('${pd.doctorArea }')"/></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">是否禁用:</td>
									<td>
									<label style="float:left;padding-left: 32px;"><input name="isInUse" id="isInUse" <c:if test="${pd.isInUse == '1' }">checked="checked"</c:if>   type="radio" class="ace" value="1"><span class="lbl">是</span></label>
									<label style="float:left;padding-left: 5px;"><input name="isInUse" id="isInUse"  <c:if test="${pd.isInUse == '0' }">checked="checked"</c:if>   type="radio" class="ace" value="0"><span class="lbl">否</span></label>
									</td>	
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
		$("#doctorForm").submit();
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