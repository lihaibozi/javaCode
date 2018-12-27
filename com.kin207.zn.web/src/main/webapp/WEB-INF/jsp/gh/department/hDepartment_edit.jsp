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
						<form action="hDepartment/${msg}.do" name="departmentForm" id="departmentForm"  method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" id="id" value="${pd.id }"/>
							<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:130px;text-align: right;padding-top: 13px;">所属医院<label style="color:#ff0000">*</label></td>
									<td>
										<select id="depHos" name="depHos" onchange="getDeps();" style="width: 98%">
													<option value=''>请选择医院</option>
													<c:forEach items="${listHospital}" var="cate" varStatus="st">
														<option value="${cate.id}"
															<c:if test='${pd.depHos == cate.id}'> selected='selected'</c:if>>
															${cate.name}</option>
													</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td style="width:130px;text-align: right;padding-top: 13px;">上级部门</td>
									<td>
										<select id="depParent" name="depParent" style="width: 98%">
													<option value=''>请选择上级部门</option>
													<c:forEach items="${listDepartment}" var="cate" varStatus="st">
														<option value="${cate.id}"
															<c:if test='${pd.depParent == cate.id}'> selected='selected'</c:if>>
															${cate.depName}</option>
													</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td style="width:130px;text-align: right;padding-top: 13px;">科室名称<label style="color:#ff0000">*</label></td>
									<td><input type="text" name="depName" id="depName" value="${pd.depName }" placeholder="这里输入科室名称" title="科室名称" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:130px;text-align: right;padding-top: 13px;">科室电话</td>
									<td><input type="text" name="depPhone" id="depPhone" value="${pd.depPhone }" placeholder="这里输入科室电话" title="科室电话" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:130px;text-align: right;padding-top: 13px;">科室负责人</td>
									<td><input type="text" name="principal" id="principal" value="${pd.principal }" placeholder="这里输入科室负责人" title="科室负责人" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:130px;text-align: right;padding-top: 13px;">负责人电话</td>
									<td><input type="text" name="cellPhone" id="cellPhone" value="${pd.cellPhone }" placeholder="这里输入负责人联系电话" title="负责人联系电话" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:130px;text-align: right;padding-top: 13px;">是否开展工作科室</td>
									<td>
									<label style="float:left;padding-left: 2px;"><input name="isWork" id="isWork" <c:if test="${pd.isWork == '1' }">checked="checked"</c:if>   type="radio" class="ace" value="1"><span class="lbl">是</span></label>
									<label style="float:left;padding-left: 8px;"><input name="isWork" id="isWork"  <c:if test="${pd.isWork == '0' }">checked="checked"</c:if>   type="radio" class="ace" value="0"><span class="lbl">否</span></label>
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
		if(checkNull("depHos","请选择医院","s2id_depHos")){
			return ;
		};
		if(checkNull("depName","请输入科室名称","depName")){
			return ;
		};
		if($("#id").val()!=""){
			var oldDepName = "${pd.depName}";
			var newDepName = $("#depName").val();
			if(oldDepName!=newDepName){
				if(hasN("depHos","depName")){
					return;
				}	
			}
		}else{
			if(hasN("depHos","depName")){
				return;
			}
		}
		
		$("#departmentForm").submit();
	}
	
	//判断医院是否已有科室
	function hasN(depHos,depName){
		var depHos = $("#depHos").val();
		var depName = $("#depName").val();
		var result = false;
		$.ajax({
			async:false,
			type: "POST",
			url: '<%=basePath%>hDepartment/hasN.do',
	    	data: {depName:depName,depHos:depHos},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#depName").tips({
							side:3,
				            msg:depName+'已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 result = true;
				 }
			}
		});
		return result;
	}
	
	function getDeps(){
		 var id = $('#depHos option:selected').val(); 
		 $("#select2-chosen-2").empty();
		 $("#depParent").empty();
		 $.ajax({
				type: "POST",
				url: '<%=basePath%>hDepartment/getDeps.do',
		    	data: {"id":id},
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					if(data!=""||data!=null){
						$("#depParent").append("<option value=''>"+'请选择上级部门'+"</option>");	
					}
					 $.each(data, function(index, item){
							$("#depParent").append("<option value='"+item.id+"'>"+item.depName+"</option>");
					 });
				}
			})
	}
	$(function(){
		/* $('.date-picker').datepicker({autoclose: true,todayHighlight: true,format: 'yyyy-mm-dd'}); */
		$("#depHos").select2();
		$("#depParent").select2();
		/* $("#repairStaff").select2(); */
	    $("#image").change(function (){
	    	 $('#image-show').remove();
	    });
	  });
</script>
</html>