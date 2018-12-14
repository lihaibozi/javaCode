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
						<form action="doctor/${msg}.do" name="doctorForm" id="doctorForm"  method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" id="id" value="${pd.id }"/>
							<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">姓名<label style="color:#ff0000">*</label></td>
									<td><input type="text" name="doctorName" id="doctorName" value="${pd.doctorName }" placeholder="这里输入医生姓名" title="姓名" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">性别<label style="color:#ff0000">*</label></td>
									<td id="selectSex">
									<label style="float:left;padding-left: 2px;"><input name="sex" id="sex" <c:if test="${pd.sex == '1' }">checked="checked"</c:if>   type="radio" class="ace" value="1"><span class="lbl">男</span></label>
									<label style="float:left;padding-left: 8px;"><input name="sex" id="sex"  <c:if test="${pd.sex == '0' }">checked="checked"</c:if>   type="radio" class="ace" value="0"><span class="lbl">女</span></label>
									</td>	
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">所属医院<label style="color:#ff0000">*</label></td>
									<td>
										<select id="hospitalId" name="hospitalId" onchange="getDeps()" style="width: 98%">
													<option value=''>请选择医院</option>
													<c:forEach items="${listHospital}" var="cate" varStatus="st">
														<option value="${cate.id}"
															<c:if test='${pd.hospitalId == cate.id}'> selected='selected'</c:if>>
															${cate.name}</option>
													</c:forEach>
										</select>
									</td>
									<td style="width:79px;text-align: right;padding-top: 13px;">所属科室<label style="color:#ff0000">*</label></td>
									<td>
										<select id="departmentId" name="departmentId" style="width: 98%">
													<option value=''>请选择科室</option>
													<c:forEach items="${listDepartment}" var="cate" varStatus="st">
														<option value="${cate.id}"
															<c:if test='${pd.departmentId == cate.id}'> selected='selected'</c:if>>
															${cate.depName}</option>
													</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td style="width:90px;text-align: right;padding-top: 13px;">手机号<label style="color:#ff0000">*</label></td>
									<td><input type="text" name="cellhone" id="cellhone" value="${pd.cellhone }" placeholder="这里输入医生手机号码" title="手机号码" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">年龄</td>
									<td><input type="text" name="age" id="age" value="${pd.age }" placeholder="这里输入医生年龄" title="年龄" style="width:98%;" /></td>			
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">工号</td>
									<td><input type="text" name="workId" id="workId" value="${pd.workId }" placeholder="这里输入医生工号" title="医生工号" style="width:98%;" /></td>
									<td style="width:90px;text-align: right;padding-top: 13px;">身份证号</td>
									<td><input type="text" name="cardNo" id="cardNo" value="${pd.cardNo }" placeholder="这里输入医生身份证号" title="手机号码" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">职称</td>
									<td><input type="text" name="proTitle" id="proTitle" value="${pd.proTitle }" placeholder="这里输入医生职称" title="职称" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">职务</td>
									<td><input type="text" name="duty" id="duty" value="${pd.duty }" placeholder="这里输入医生职务" title="职务" style="width:98%;" /></td>
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
	<script type="text/javascript" src="static/ace/js/select2.js"></script>
	<script type="text/javascript" src="static/ace/js/pinyin.js"></script>
	<script src="static/js/common/common.js"></script>
</body>						
<script type="text/javascript">
	$(top.hangge());
	
	//保存
	function save(){
		var sex = document.getElementsByName('sex');
		var str = '';
		
		if(checkNull("doctorName","请输入姓名","doctorName")){
			return ;
		};
		
		for(var i=0;i < sex.length;i++)
		{
			  if(sex[i].checked){
			  	if(str=='') str += sex[i].value;
			  	else str += ',' + sex[i].value;
			  }
		}
		if(str==''){
			$("#selectSex").tips({
				side:3,
	            msg:"请选择性别",
	            bg:'#AE81FF',
	            time:2
	        });
			return;
		}
		
		var telePhone = $("#cellhone").val();
		var phoneRegx = /^[1][3,4,5,7,8][0-9]{9}$/;
		if(!phoneRegx.test(telePhone)){
			$("#cellhone").tips({
				side:3,
	            msg:'手机号格式错误',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#cellhone").focus();
			return;
		}; 
		
		if(checkNull("hospitalId","请选择医院","s2id_hospitalId")){
			return ;
		};
		if(checkNull("departmentId","请选择科室","s2id_departmentId")){
			return ;
		};
		
		$("#doctorForm").submit();
	}
	
	function getDeps(){
		 var id = $('#hospitalId option:selected').val(); 
		 $("#select2-chosen-1").empty();
		 $("#departmentId").empty();
		 $.ajax({
				type: "POST",
				url: '<%=basePath%>hDepartment/getDeps.do',
		    	data: {"id":id},
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					if(data!=""||data!=null){
						$("#departmentId").append("<option value=''>"+'请选择科室'+"</option>");	
					}
					 $.each(data, function(index, item){
							$("#departmentId").append("<option value='"+item.id+"'>"+item.depName+"</option>");
					 });
				}
			})
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
		$("#departmentId").select2();
		$("#hospitalId").select2();
	    $("#image").change(function (){
	    	 $('#image-show').remove();
	    });
	  });
</script>
</html>