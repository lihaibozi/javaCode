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
<link rel="stylesheet" href="static/ace/css/select2.css" />
<link rel="stylesheet" href="static/ace/css/jquery-ui-timepicker-addon.css" />
<link rel="stylesheet" href="static/ace/css/jquery-ui.css"/>

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
						<form action="workschedule/${msg}.do" name="workscheduleForm" id="workscheduleForm"  method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" id="id" value="${pd.id }"/>
							<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:130px;text-align: right;padding-top: 13px;">科室<label style="color:#ff0000">*</label></td>
									<td>
										<select id="depId" name="depId" onchange="getDoctorAndWorkType()" style="width: 98%">
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
									<td style="width:130px;text-align: right;padding-top: 13px;">医生<label style="color:#ff0000">*</label></td>
									<td>
										<select id="doctorID" name="doctorID" style="width: 98%">
													<option value=''>请选择医生</option>
													<c:forEach items="${listDoctor}" var="cate" varStatus="st">
														<option value="${cate.id}"
															<c:if test='${pd.doctorID == cate.id}'> selected='selected'</c:if>>
															${cate.doctorName}</option>
													</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td style="width:130px;text-align: right;padding-top: 13px;">工作类别<label style="color:#ff0000">*</label></td>
									<td>
										<select id="workId" name="workId"  style="width: 98%">
													<option value=''>请选择工作类别</option>
													<c:forEach items="${listWorkType}" var="cate" varStatus="st">
														<option value="${cate.id}"
															<c:if test='${pd.workId == cate.id}'> selected='selected'</c:if>>
															${cate.workName}</option>
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
									<td style="width:110px;text-align: right;padding-top: 13px;">日期<label style="color:#ff0000">*</label></td>
									<td>
										<input name="workTime" id="workTime" value="${pd.workTime}" type="text" class="ui_timepicker" >
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
	<script src="static/html_UI/dist/js/jquery-ui.min.js"></script>
	<script src="static/ace/js/date-time/jquery-ui-timepicker-addon.js"></script>
	<script src="static/ace/js/date-time/jquery-ui-timepicker-zh-CN.js"></script>
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
		
		if(checkNull("doctorID","请选择医生","s2id_doctorID")){
			return ;
		};
		
		if(checkNull("workId","请选择工作类别","s2id_workId")){
			return ;
		};
		if(checkNull("workTime","请选择时间","workTime")){
			return;
		};
		
		if(checkNull("hosId","请选择联盟医院","s2id_hosId")){
			return ;
		};
		
		
		$("#workscheduleForm").submit();
	}
	
	function getDoctorAndWorkType(){
		 var id = $('#depId option:selected').val(); 
		 $("#select2-chosen-4").empty();
		 $("#select2-chosen-3").empty();
		 $("#select2-chosen-2").empty();
		 $("#doctorID").empty();
		 $("#workId").empty();
		 $("#hosId").empty();
		 $.ajax({
				type: "POST",
				url: '<%=basePath%>workschedule/getDoctorAndWorkType.do',
		    	data: {"id":id},
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					if(data!=""||data!=null){
						var listDoctor = data[0].listDoctor;
						var listWorkType = data[0].listWorkType;
						var listHos = data[0].listHos;
						$("#doctorID").append("<option value=''>"+'请选择医生'+"</option>");	
						$("#workId").append("<option value=''>"+'请选择工作类别'+"</option>");
						$("#hosId").append("<option value=''>"+'请选择联盟医院'+"</option>");	
						 $.each(listDoctor, function(index, item){
								 $("#doctorID").append("<option value='"+item.id+"'>"+item.doctorName+"</option>"); 
						 });
						 $.each(listWorkType, function(index, item){
								$("#workId").append("<option value='"+item.id+"'>"+item.workName+"</option>");
						 });
						 $.each(listHos, function(index, item){
								$("#hosId").append("<option value='"+item.id+"'>"+item.name+"</option>");
						 });
					}
				}
			})
	}

	$(function(){
		$('.ui_timepicker').datetimepicker({
			lang: "ch",
            timeFormat: "HH:mm:ss",
            dateFormat: "yy-mm-dd",
            dayNamesMin: ["七", "一", "二", "三", "四", "五", "六"],
            monthNamesShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],
            changeMonth: true,
            changeYear: true
		});
		/* $('.date-picker').datepicker({autoclose: true,todayHighlight: true,format: 'yyyy-mm-dd'}); */
		$("#depId").select2();
		$("#hosId").select2();
		$("#doctorID").select2();
		$("#workId").select2();
		/* $("#repairStaff").select2(); */
	    $("#image").change(function (){
	    	 $('#image-show').remove();
	    });
	  });
</script>
</html>