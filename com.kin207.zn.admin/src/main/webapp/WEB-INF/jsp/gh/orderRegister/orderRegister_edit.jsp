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
<link rel="stylesheet" href="static/ace/css/jquery-ui-timepicker-addon.css" />
<link rel="stylesheet" href="static/ace/css/bootstrap-multiselect.css" />
<link rel="stylesheet" href="static/ace/css/jquery-ui.css"/>
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
						<form action="orderRegister/${msg}.do" name="orderRegisterForm" id="orderRegisterForm"  method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" id="id" value="${pd.id }"/>
							<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">维修状态</td>
									<td>
									<select id="status" name="status" style="width:40%">
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
									<td style="width:79px;text-align: right;padding-top: 13px;">维修内容</td>
									<td>
										<input type="text" name="repairContent" id="repairContent" value="${pd.repairContent }" placeholder="这里输入维修内容" title="维修内容" style="width:98%;" />
									</td>
								</tr>
								<tr>
									<td style="width:110px;text-align: right;padding-top: 13px;">维修过程描述</td>
									<td>
									<input type="text" name="repairProcess" id="repairProcess" value="${pd.repairProcess }" placeholder="这里输入维修过程描述" title="维修过程描述" style="width:98%;" />
									</textarea>
									</td>
								</tr>
								<tr>
									<td style="width:110px;text-align: right;padding-top: 13px;">是否送修</td>
									<td>
									<label style="float:left;padding-left: 2px;"><input name="isToRepair" id="isToRepair" <c:if test="${pd.isToRepair == '0' }">checked="checked"</c:if> type="radio" class="ace" value="0"><span class="lbl">未送修</span></label>
									<label style="float:left;padding-left: 8px;"><input name="isToRepair" id="isToRepair"  <c:if test="${pd.isToRepair == '1' }">checked="checked"</c:if>  type="radio" class="ace" value="1"><span class="lbl">已送修</span></label>
									</td>
								</tr>
								<tr>
									<td style="width:110px;text-align: right;padding-top: 13px;">是否更换配件</td>
									<td>
									<label style="float:left;padding-left: 2px;"><input name="isReplace" id="isReplace" <c:if test="${pd.isReplace == '0' }">checked="checked"</c:if>   type="radio" class="ace" value="0"><span class="lbl">未更换</span></label>
									<label style="float:left;padding-left: 8px;"><input name="isReplace" id="isReplace"  <c:if test="${pd.isReplace == '1' }">checked="checked"</c:if>   type="radio" class="ace" value="1"><span class="lbl">已更换</span></label>
									</td>
								</tr>
								<tr>
									<td style="width:110px;text-align: right;padding-top: 13px;">是否启用备用机</td>
									<td>
									<label style="float:left;padding-left: 2px;"><input name="isStartBack" id="isStartBack" <c:if test="${pd.isStartBack == '0' }">checked="checked"</c:if>   type="radio" class="ace" value="0"><span class="lbl">未启用</span></label>
									<label style="float:left;padding-left: 8px;"><input name="isStartBack" id="isStartBack"  <c:if test="${pd.isStartBack == '1' }">checked="checked"</c:if>   type="radio" class="ace" value="1"><span class="lbl">已启用</span></label>
									</td>
								</tr>
								
								<tr>
									<td style="width:110px;text-align: right;padding-top: 13px;">到达时间</td>
									<td>
										<input name="arriveDate" id="arriveDate" value="${pd.arriveDate}" type="text" class="ui_timepicker" >
									</td>
								</tr>
								
								<tr>
									<td style="width:110px;text-align: right;padding-top: 13px;">完成时间</td>
									<td>
									<input name="finishDate" id="finishDate" value="${pd.finishDate}" type="text"  class="ui_timepicker">
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
	
	<script type="text/javascript" src="static/ace/js/bootstrap-multiselect.js"></script>
	<!-- 日期框 -->
	<script src="static/html_UI/dist/js/jquery-ui.min.js"></script>
	<script src="static/ace/js/date-time/jquery-ui-timepicker-addon.js"></script>
	<script src="static/ace/js/date-time/jquery-ui-timepicker-zh-CN.js"></script>
	<script src="static/ace/js/date-time/jedate.js"></script>
	
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script src="static/js/upload/uploadFile.js"></script>
	<script src="static/js/common/common.js"></script>
	<script type="text/javascript" src="static/ace/js/select2.js"></script>
	<script type="text/javascript" src="static/ace/js/pinyin.js"></script>
</body>						
<script type="text/javascript">
	$(top.hangge());
	//保存
	function save(){
		
		if(checkNull("status","请选择维修状态","s2id_status")){
			return ;
		};
		if(checkNull("repairContent","请输入维修内容","repairContent")){
			return;
		};
		if(checkNull("repairProcess","请输入维修过程描述","repairProcess")){
			return;
		};
		if(checkNull("arriveDate","请选择达时间","arriveDate")){
			return;
		};
		
		$("#orderRegisterForm").submit();
	}
	
	function getMemberAndPhone(){
		 var id = $('#department option:selected') .val(); 
		 $("#depPhone").val("");
		 $("#repairStaff").empty();
		 $.ajax({
				type: "POST",
				url: '<%=basePath%>orderRegister/getMemberAndPhone.do',
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
		$('.ui_timepicker').datetimepicker({
			lang: "ch",
            timeFormat: "HH:mm:ss",
            dateFormat: "yy-mm-dd",
            dayNamesMin: ["七", "一", "二", "三", "四", "五", "六"],
            monthNamesShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],
            changeMonth: true,
            changeYear: true
		});
		$("#status").select2();
		/* $('.date-picker').datepicker({autoclose: true,todayHighlight: true,format: 'yyyy-mm-dd'}); */
	    $("#image").change(function (){
	    	 $('#image-show').remove();
	    });
	  });
</script>
</html>