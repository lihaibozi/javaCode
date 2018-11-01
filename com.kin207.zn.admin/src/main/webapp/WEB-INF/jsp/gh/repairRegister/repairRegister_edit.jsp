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
<link rel="stylesheet" href="static/ace/css/bootstrap-multiselect.css" />

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
						<form action="repairRegister/${msg}.do" name="departmentForm" id="departmentForm"  method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" id="id" value="${pd.id }"/>
							<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">所属科室</td>
									<td>
									<select id="department" name="department" onchange="getMemberAndPhone()" style="width:98%">
										<option value=''>请选择科室</option>	
										 <c:forEach items="${listDeparts}" var="cate" varStatus="st">
									        <option value="${cate.id}" 
									            <c:if test='${pd.department == cate.id}'> selected='selected'</c:if>>
									            ${cate.depName}
									        </option>
								    	 </c:forEach>
									</select>
									<td style="width:79px;text-align: right;padding-top: 13px;">科室电话</td>
									<td><input disabled="disabled" type="text" name="depPhone" id="depPhone" value="${pd.depPhone}"  title="科室电话" style="width:98%;" /></td>
									</td>
									
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">报修人员</td>
									<td>
									<select id="repairStaff" name="repairStaff"  style="width:98%">
										<option value=''>请选择报修人</option>	
										 <c:forEach items="${members}" var="cate" varStatus="st">
									        <option value="${cate.id}" 
									            <c:if test='${pd.repairStaff == cate.id}'> selected='selected'</c:if>>
									            ${cate.name}
									        </option>
								    	 </c:forEach>
									</select>
									</td>
									<td style="width:79px;text-align: right;padding-top: 13px;">联系电话</td>
									<td><input type="text" name="cellPhone" id="cellPhone" value="${pd.cellPhone }" placeholder="这里输入联系电话" title="联系电话" style="width:98%;"/></td>
									</td>
								</tr>
								<tr>
									<td style="width:110px;text-align: right;padding-top: 13px;">设备资产编号</td>
									<td><input type="text" name="assetNumber" id="assetNumber" value="${pd.assetNumber }" placeholder="这里输入资产编号" title="资产编号" style="width:98%;" /></td>
									</td>
									<td style="width:130px;text-align: right;padding-top: 13px;">设备资产SN编号</td>
									<td><input type="text" name="assetSn" id="assetSn" value="${pd.assetSn }" placeholder="这里输入资产SN编号" title="SN编号" style="width:98%;" /></td>
									</td>
								</tr>
								<tr>
									<td style="width:110px;text-align: right;padding-top: 13px;">报修内容</td>
									<td colspan="6"><input type="text" name="content" id="content" value="${pd.content }" placeholder="这里输入报修内容" title="报修内容" style="width:98%;" /></td>
									</td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">接单人</td>
									<td>
									<select id="orderPeople" name="orderPeople" id="orderPeople"  style="width:98%">
										<option value=''>请选择接单人</option>	
										 <c:forEach items="${listOrders}" var="cate" varStatus="st">
									        <option value="${cate.id}" 
									            <c:if test='${pd.orderPeople == cate.id}'> selected='selected'</c:if>>
									            ${cate.name}
									        </option>
								    	 </c:forEach>
									</select>
									</td>
									<td  style="width:79px;text-align: right;padding-top: 13px;">协助人员</td>
									<td id="idAss">
									<select  id="assistants" name="assistants"  multiple="multiple">
										<c:forEach items="${listOrders}" var="cate" >
										 <option value="${cate.id}"
											   <c:forEach items="${assistants}" var="ass" >
											            <c:if test='${ass.id == cate.id}'> selected='selected'</c:if>
										        </c:forEach>>
										        ${cate.name}
									       </option>
								    	 </c:forEach>
									</select>
									</td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">维修类别</td>
									<td>
									<select name="type" id="type"  style="width:98%">
										<option value=''>请选择接维修类别</option>	
										 <c:forEach items="${listTypes}" var="cate" varStatus="st">
									        <option value="${cate.id}" 
									            <c:if test='${pd.typeId == cate.id}'> selected='selected'</c:if>>
									            ${cate.type}
									        </option>
								    	 </c:forEach>
									</select>
									</td>
									<td style="width:79px;text-align: right;padding-top: 13px;">维修状态</td>
									<td>
									<select id="status" name="status"  style="width:98%">
										<option value=''>请选择维修状态</option>	
										 <c:forEach items="${listStatus}" var="cate" varStatus="st">
									        <option value="${cate.id}" 
									            <c:if test='${pd.statusId == cate.id}'> selected='selected'</c:if>>
									            ${cate.status}
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
	
	<script src="static/html_UI/dist/js/jquery.min.js"></script>
	<script type="text/javascript" src="static/ace/js/bootstrap-multiselect.js"></script>
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
		
		if(!checkNull("department","科室不能为空")){
			return ;
		};
		if(!checkNull("repairStaff","报修人不能为空")){
			return;
		};
		if(!checkNull("content","报修内容不能为空")){
			return;
		};
		if(!checkNull("orderPeople","接单人不能为空")){
			return;
		};
		if(!checkNull("type","维修类别不能为空")){
			return;
		};
		if(!checkNull("status","维修状态不能为空")){
			return;
		};
		
		var orderPeople = $("#orderPeople").val();
		var assistants = $("#assistants").val();
		if(assistants!=null){
			if(assistants.indexOf(orderPeople)!=-1){
				/* alert("协助人员不能包含接单人"); */
				$(".btn-group").tips({
				side:3,
	            msg:"协助人员不能包含接单人",
	            bg:'#AE81FF',
	            time:2
	        });
				return;
			}
		}
		
		$("#departmentForm").submit();
	}
	
	function checkNull(name,title){
		var value = $("#"+name).val();
		if(value==""){
			$("#"+name).tips({
				side:3,
	            msg:title,
	            bg:'#AE81FF',
	            time:2
	        });
			return false;
		}else{
			return true;
		}
	}
	
	function getMemberAndPhone(){
		 var id = $('#department option:selected') .val(); 
		 $("#depPhone").val("");
		 $("#repairStaff").empty();
		 $.ajax({
				type: "POST",
				url: '<%=basePath%>repairRegister/getMemberAndPhone.do',
		    	data: {"id":id},
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					if(data!=""||data!=null){
						$("#repairStaff").append("<option value=''>"+'请选择报修人'+"</option>");	
					}
					 $.each(data, function(index, item){
							$("#depPhone").val(item.depPhone);
							$("#repairStaff").append("<option value='"+item.id+"'>"+item.name+"</option>");
					 });
				}
			});
	}

	$(function(){
		$('#assistants').multiselect({
			nonSelectedText:'请选择协助人员',
			enableFiltering : true,
			includeSelectAllOption:true
		});
		/* $('.date-picker').datepicker({autoclose: true,todayHighlight: true,format: 'yyyy-mm-dd'}); */
	    $("#image").change(function (){
	    	 $('#image-show').remove();
	    });
	  });
</script>
</html>