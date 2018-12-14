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
						<form action="workcontent/${msg}.do" name="workcontentForm" id="workcontentForm"  method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" id="id" value="${pd.id }"/>
							<input type="hidden" name="type" id="type" value="${pd.type }"/>
							<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr id="tr1">
									<td style="width:110px;text-align: right;padding-top: 13px;">患者姓名<label style="color:#ff0000">*</label>
									</td>
									<td>
									<input type="text" name="patientName" id="patientName" value="${pd.patientName}" placeholder="输入患者姓名" title="患者姓名" style="width:98%;" />
									</td>
									<td style="width:110px;text-align: right;padding-top: 13px;">患者性别<label style="color:#ff0000">*</label>
									</td>
									<td id="selectSex">
									<label  style="float:left;padding-left: 2px;"><input name="patientSex" id="patientSex" <c:if test="${pd.patientSex == '1' }">checked="checked"</c:if> type="radio" class="ace" value="1"><span class="lbl">男</span></label>
									<label style="float:left;padding-left: 8px;"><input name="patientSex" id="patientSex"  <c:if test="${pd.patientSex == '0' }">checked="checked"</c:if> type="radio" class="ace" value="0"><span class="lbl">女</span></label>
									</td>
									<td style="width:110px;text-align: right;padding-top: 13px;">患者年龄<label style="color:#ff0000">*</label></td>
									<td><input onkeyup="value=value.replace(/[^\d]/g,'')" value="${pd.patientAge}" type="text" name="patientAge" id="patientAge" maxlength="3" placeholder="输入患者年龄" title="患者年龄" style="width:98%;" />
									</td>
								</tr>
								<tr id="tr2">
									<td style="width:110px;text-align: right;padding-top: 13px;">患者身份证号
									</td>
									<td>
									<input type="text" name="patientCardNo" id="patientCardNo" value="${pd.patientCardNo}" placeholder="输入患者身份证号" title="身份证号" style="width:98%;" />
									</td>
									<td style="width:110px;text-align: right;padding-top: 13px;">患者联系方式</td>
									<td>
										<input type="text" name="patientCellPhone" id="patientCellPhone" value="${pd.patientCellPhone}" placeholder="输入联系方式" title="患者联系方式" style="width:98%;" />
									</td>
									<td style="width:110px;text-align: right;padding-top: 13px;">手术级别</td>
									<td>
										<input type="text" name="operationLevel" id="operationLevel" value="${pd.operationLevel}" placeholder="输入手术级别" title="手术级别" style="width:98%;" />
									</td>
								</tr>
								<tr id="tr3">
									<td style="width:110px;text-align: right;padding-top: 13px;">诊断/手术日期<label style="color:#ff0000">*</label></td>
									<td>
										<input placeholder="选择日期" name="medicalDate" id="medicalDate"  value="${pd.medicalDate}" type="text" class="ui_timepicker" >
									</td>
									<td style="width:110px;text-align: right;padding-top: 13px;">诊断/手术结果<label style="color:#ff0000">*</label></td>
									<td colspan="6">
									<textarea rows="5" cols="5" name="medicalResult" id="medicalResult"  style="resize:none;width:99%" title="诊断/手术结果">${pd.medicalResult}</textarea>
									<%-- <input type="text" name="medicalResult" id="medicalResult" value="${pd.medicalResult }" placeholder="输入诊断/手术结果" title="诊断/手术结果" style="width:99.5%;" /> --%>
									</td>
								</tr>
								<tr id="tr4">
									<td style="width:110px;text-align: right;padding-top: 15px;">上传文件</td>
										<td colspan="6">
											<c:if test="${pd != null && pd.courseware != '' && pd.courseware != null }">
														<input type="file" name="courseware1" id="courseware1" />
														<%-- <a href="${pd.image}" target="_blank"><img src="${pd.image}" id = "image-show" width="79px"/></a> --%>
														<a id="loadFile" style="cursor:pointer" onclick="loadFile('${pd.fileName}');">${pd.fileName}</a>
														<a id="delFile" style="cursor:pointer;margin-left: 10px" class="red" onclick="delFile('${pd.id}','${pd.fileName}');" title="删除文件">
															<i class="ace-icon fa fa-trash-o bigger-130"></i>
														</a>
											</c:if>
											<c:if test="${empty pd.courseware }">
													<input type="file" id="courseware1" name="courseware1" />
											</c:if>
										  <div id="preview">
											    <img id="imghead" width=100% height=auto border=0 >
											</div>
											
										</td>
								</tr>
								<tr id="tr5">
									<td  style="width:150px;text-align: right;padding-top: 13px;">讨论/讲座/查房日期<label style="color:#ff0000">*</label></td>
									<td >
										<input placeholder="选择日期" name="conferenceTime" id="conferenceTime" value="${pd.conferenceTime}"  type="text" class="ui_timepicker" >
									</td>
									<td  style="width:110px;text-align: right;padding-top: 13px;">参与人数
									</td>
									<td >
									<input onkeyup="value=value.replace(/[^\d]/g,'')" value="${joinNumber}" maxlength="5" type="text" name="joinNumber" id="joinNumber"  placeholder="输入参与人数" title="参与人数" style="width:50%;" />
									</td>
								</tr>
								<tr id="tr6">
								<td  style="width:110px;text-align: right;padding-top: 13px;">讨论/讲座/查房内容<label style="color:#ff0000">*</label></td>
								<td colspan="7">
									<textarea rows="5" cols="5" name="content" id="content" style="resize:none;width:99%" title="讨论/讲座/查房内容">${pd.content} </textarea>
						<%-- 			<input type="text" name="content" id="content" value="${pd.content }" placeholder="输入讨论/讲座内容" title="讨论/讲座内容" style="width:99.5%;" /> --%>
								</td>
								</tr>
								<tr id="tr7">
								<td  style="width:110px;text-align: right;padding-top: 13px;">讨论/讲座评价</td>
								<td colspan="6">
									<input type="text" name="apprise" id="apprise" value="${pd.apprise}" placeholder="输入讨论/讲座评价" title="讨论/讲座/查房评价" style="width:99.5%;" />
								</td>
								</tr>
								<tr id="tr8">
									<td style="width:110px;text-align: right;padding-top: 15px;">上传文件</td>
										<td colspan="6">
											<c:if test="${pd != null && pd.courseware != '' && pd.courseware != null }">
														<input type="file" name="courseware2" id="courseware2" />
														<%-- <a href="${pd.image}" target="_blank"><img src="${pd.image}" id = "image-show" width="79px"/></a> --%>
														<a id="loadFile" style="cursor:pointer" onclick="loadFile('${pd.fileName}');">${pd.fileName}</a>
														<a id="delFile" style="cursor:pointer;margin-left: 10px" class="red" onclick="delFile('${pd.id}','${pd.fileName}');" title="删除文件">
															<i class="ace-icon fa fa-trash-o bigger-130"></i>
														</a>
											</c:if>
											<c:if test="${empty pd.courseware }">
													<input type="file" id="courseware2" name="courseware2" />
											</c:if>
										  <div id="preview">
											    <img id="imghead" width=100% height=auto border=0 >
											</div>
											
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
	$('input:radio[name="contentType"]').click(function(){
	    contentType = $(this).val();//获取选中的radio的值
	    if(contentType==3){
	    	$("#tr1").show();
			$("#tr2").show();
			$("#tr3").show();
			$("#tr4").show();
			$("#tr5").hide();
			$("#tr6").hide();
			$("#tr7").hide();
			$("#tr8").hide();
	    }else if(contentType==4){
	    	$("#tr1").hide();
			$("#tr2").hide();
			$("#tr3").hide();
	    	$("#tr4").hide();
			$("#tr5").show();
			$("#tr6").show();
			$("#tr7").show();
			$("#tr8").show();
	    }
	});
	
	function loadFile(fileName){
		var url = "<%=basePath%>/user/downFile.do?fileName="+fileName;
		$("#loadFile").attr("href",url);
	}
	
	
	function delFile(id,fileName){
		top.jzts();
		var url = "<%=basePath%>workcontent/delFile.do?id="+id+"&fileName="+fileName;
		$.get(url,function(data){
			location.reload();
		});
	}
	
	//保存
	function save(){
		var patientSex = document.getElementsByName('patientSex');
		var contentType = document.getElementsByName('contentType');
		var ctype = ${pd.type};
		if(ctype==""){
			alert("请选择一项工作内容填报");
			return;
		}else if(ctype==3){
			if(checkNull("patientName","请输入患者姓名","patientName")){
				return ;
			};
			var sexType = "";
			for(var i=0;i < patientSex.length;i++)
			{
				  if(patientSex[i].checked){
					 sexType = patientSex[i].value;
				  }
			}
			if(sexType==""){
				$("#selectSex").tips({
					side:3,
		            msg:"请选择性别",
		            bg:'#AE81FF',
		            time:2
		        });
				return;
			}
			if(checkNull("patientAge","请输入患者年龄","patientAge")){
				return ;
			};
			if(checkNull("medicalDate","请选择诊断/手术日期","medicalDate")){
				return ;
			};
			if($("#medicalResult").val()==""){
				$("#medicalResult").tips({
					side:3,
		            msg:"请填写诊断/手术结果",
		            bg:'#AE81FF',
		            time:2
		        });
				return;
			}
			/* if(checkNull("medicalResult","请填写诊断/手术结果","medicalResult")){
				return ;
			}; */
		}else if(ctype==4){
			if(checkNull("conferenceTime","请选择讨论/讲座/查房日期","conferenceTime")){
				return ;
			};
			/* if(checkNull("joinNumber","请输入参与人数","joinNumber")){
				return ;
			}; */
			if($("#content").val()==""){
				$("#content").tips({
					side:3,
		            msg:"请输入讨论/讲座/查房内容",
		            bg:'#AE81FF',
		            time:2
		        });
				return ;
			}
			/* if(checkNull("content","请输入讨论/讲座内容","content")){
				return ;
			}; */
		}
		 $("#workcontentForm").submit(); 
	}
	
	$(function(){
		var type = ${pd.type};
		if(type==3){
			$("#tr5").hide();
			$("#tr6").hide();
			$("#tr7").hide();
			$("#tr8").hide();
			
		}else if(type==4){
			$("#tr1").hide();
			$("#tr2").hide();
			$("#tr3").hide();
			$("#tr4").hide();
		}
		
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