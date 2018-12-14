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
<link rel="stylesheet" href="static/ace/css/select2.css" />

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
						<form action="hosdep/${msg}.do" name="hosdepForm" id="hosdepForm"  method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" id="id" value="${pd.id }"/>
							<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
								
								<tr>
									<td style="width:130px;text-align: right;padding-top: 13px;">科室<label style="color:#ff0000">*</label></td>
									<td>
										<select id="depId" name="depId" style="width: 98%">
													<option value=''>请选择科室</option>
													<c:forEach items="${listDepartment}" var="cate" varStatus="st">
														<option value="${cate.id}"
															<c:if test='${pd.depId == cate.id}'> selected='selected'</c:if>>
															${cate.depName}</option>
													</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td style="width:130px;text-align: right;padding-top: 13px;">联盟医院<label style="color:#ff0000">*</label></td>
									<td>
										<select id="hosId" name="hosId"  style="width: 98%">
													<option value=''>请选择联盟医院</option>
													<c:forEach items="${listHospital}" var="cate" varStatus="st">
														<option value="${cate.id}"
															<c:if test='${pd.hosId == cate.id}'> selected='selected'</c:if>>
															${cate.name}</option>
													</c:forEach>
										</select>
									</td>
								</tr>
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
	<script type="text/javascript" src="static/ace/js/select2.js"></script>
	<script type="text/javascript" src="static/ace/js/pinyin.js"></script>
	<script src="static/js/common/common.js"></script>
	
</body>						
<script type="text/javascript">
	$(top.hangge());
	
	//保存
	function save(){
		
		if(checkNull("depId","请选择科室","s2id_depId")){
			return ;
		};
		
		if(checkNull("hosId","请选择联盟医院","s2id_hosId")){
			return ;
		};
		
		$("#hosdepForm").submit();
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
		/* $('.date-picker').datepicker({autoclose: true,todayHighlight: true,format: 'yyyy-mm-dd'}); */
		$("#depId").select2();
		$("#hosId").select2();
		/* $("#repairStaff").select2(); */
	    $("#image").change(function (){
	    	 $('#image-show').remove();
	    });
	  });
</script>
</html>