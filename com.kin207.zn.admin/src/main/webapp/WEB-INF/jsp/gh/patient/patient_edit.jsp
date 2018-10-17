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
						<form action="patient/${msg}.do" name="doctorForm" id="patientForm"  method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" id="id" value="${pd.id }"/>
							<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">病人姓名</td>
									<td><input type="text" name="patientName" id="patientName" value="${pd.patientName }" placeholder="这里输入姓名" title="姓名" style="width:98%;" onblur="hasN('${pd.patientName }')"/></td>
								</tr>
								<tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">病人性别</td>
									<td>
										<label style="float:left;padding-left: 8px;"><input name="patientSex" id="patientSex" <c:if test="${pd.patientSex == '1' }">checked="checked"</c:if>   type="radio" class="ace" value="1"><span class="lbl">男</span></label>
										<label style="float:left;padding-left: 5px;"><input name="patientSex" id="patientSex"  <c:if test="${pd.patientSex == '2' }">checked="checked"</c:if>   type="radio" class="ace" value="2"><span class="lbl">女</span></label>
									</td>
								</tr>
								<tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">身份证号</td>
									<td><input type="text" name="patientCardNo" id="patientCardNo" value="${pd.patientCardNo }" placeholder="这里输入身份证号" title="身份证号" style="width:98%;" onblur="hasN('${pd.patientCardNo }')"/></td>
								</tr>
								
								<tr>
									<td style="width:90px;text-align: right;padding-top: 13px;">联系电话</td>
									<td><input type="text" name="patientPhoneNumber" id="patientPhoneNumber" value="${pd.patientPhoneNumber }" placeholder="这里输入联系电话" title="联系电话" style="width:98%;" onblur="hasN('${pd.patientPhoneNumber }')"/></td>
								</tr>
								<tr>
									<td style="width:90px;text-align: right;padding-top: 13px;">出生时间</td>
									<td style="padding-left:8px;"><input class="span10 date-picker" name="patientBirth" id="patientBirth"  value="${pd.patientBirth}" type="text" onblur="hasN('${pd.patientBirth}')" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:110px;" placeholder="请选择出生日期" title="出生日期"/></td>			
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
		var patientCardNo = $("#patientCardNo").val();
		var patientPhoneNumber = $("#patientPhoneNumber").val();
		var patientBirth = $("#patientBirth").val();
		var date = new Date();
        var seperator = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator + month + seperator + strDate;
     	var t1 = patientBirth.replace("-","");
     	var t2 = currentdate.replace("-","");
     	if(t1>t2){
     		 $("#patientBirth").tips({
					side:3,
		            msg:'出生日期不能大于当前日期',
		            bg:'#AE81FF',
		            time:3
		        });
			 return;
     	}
		var phoneRegx = /^[1][3,4,5,7,8][0-9]{9}$/;
		var cardRegx = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		if(!(cardRegx.test(patientCardNo))){
			 $("#patientCardNo").tips({
					side:3,
		            msg:'身份证号码格式不正确',
		            bg:'#AE81FF',
		            time:3
		        });
			 return;
		}
		if(!(phoneRegx.test(patientPhoneNumber))){
			 $("#patientPhoneNumber").tips({
					side:3,
		            msg:'手机号码格式不正确',
		            bg:'#AE81FF',
		            time:3
		        });
			 return;
		}
		$("#patientForm").submit();
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
		$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
	    $("#image").change(function (){
	    	 $('#image-show').remove();
	    });
	  });
</script>
</html>