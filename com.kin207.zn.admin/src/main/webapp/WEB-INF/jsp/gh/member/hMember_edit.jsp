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
						<form action="hMember/${msg}.do" name="memberForm" id="memberForm"  method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" id="id" value="${pd.id }"/>
							<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">人员姓名</td>
									<td><input type="text" name="name" id="name" value="${pd.name }" placeholder="这里输入人员姓名" title="人员姓名" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">手机号码</td>
									<td><input type="text" name="telePhone" id="telePhone" value="${pd.telePhone}" placeholder="这里输入手机号码" title="手机号码" style="width:98%;" /></td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">所属科室</td>
									<td>
									<select name="depId" id="depId"  style="width:130px">
										<option value=''>----请选择科室----</option>	
										 <c:forEach items="${listDeparts}" var="cate" varStatus="st">
									        <option value="${cate.id}" 
									            <c:if test='${pd.depId == cate.id}'> selected='selected'</c:if>>
									            ${cate.depName}
									        </option>
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
		var depSelect = $("#depSelect").val();
		var name =  $("#name").val();
		var telePhone =  $("#telePhone").val();
		if(name==null||name==""){
			$("#name").tips({
				side:3,
	            msg:'人员姓名不能为空',
	            bg:'#AE81FF',
	            time:3
	        });
			return;
		}
		var phoneRegx = /^[1][3,4,5,7,8][0-9]{9}$/;
		if(!phoneRegx.test(telePhone)){
			$("#telePhone").tips({
				side:3,
	            msg:'手机号码格式错误',
	            bg:'#AE81FF',
	            time:3
	        });
			return;
		}
		if(depSelect==null||depSelect==""){
			$("#depSelect").tips({
				side:3,
	            msg:'请选择科室',
	            bg:'#AE81FF',
	            time:3
	        });
			return;
		}
		
		$("#memberForm").submit();
	}
	
	$(function(){
		/* var jsonData = ${listDeparts};
		for(var i=0;i<jsonData.length;i++){
			$("#depSelect").append("<option value='"+jsonData[i].id+"'>"+jsonData[i].depName+"</option>")
		} */
		
		/* $('.date-picker').datepicker({autoclose: true,todayHighlight: true,format: 'yyyy-mm-dd'}); */
	    $("#image").change(function (){
	    	 $('#image-show').remove();
	    });
	  });
</script>
</html>