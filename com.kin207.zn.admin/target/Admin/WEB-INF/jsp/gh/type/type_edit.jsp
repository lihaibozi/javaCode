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
						<form action="type/${msg }.do" name="typeForm" id="typeForm" method="post">
							<input type="hidden" name="id" id="id" value="${pd.id }"/>
							<div id="zhongxin" style="padding-top: 13px;">
							<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">编码:</td>
									<td><input type="text"  name="code" id="code" value="${pd.code }" placeholder="这里输入编码" title="编码" style="width:98%;" onblur="hasC('${pd.code }')"/>
									<span class="user-personal-info">四位数编码如：1001 代表第一种类型1002代表第二种类型不能重复</span>
									</td>
								</tr>
								<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">分类名称:</td>
									<td><input type="text" name="name" id="name"  placeholder="输入分类名称" value="${pd.name }"  title="分类名称" style="width:98%;" onblur="hasN('${pd.name }')"/>
									<span class="user-personal-info">分类名称比如：三及第 或 者五及第 不能重复</span>
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
		if($("#code").val()==""){
			$("#code").tips({
				side:3,
	            msg:'请选择菜品类型编号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#code").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'输入名称',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		else{
			$("#typeForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	
	//判断编码是否存在
	function hasN(name){
		var newname = $("#name").val();
		if(name != newname){
		$.ajax({
			type: "POST",
			url: '<%=basePath%>type/hasN.do',
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
	//判断编码是否存在
	function hasC(code){
		var newcode = $("#code").val();
		if(code != newcode){
		$.ajax({
			type: "POST",
			url: '<%=basePath%>type/hasC.do',
	    	data: {code:newcode},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#code").tips({
							side:3,
				            msg:'编码'+newcode+'已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $('#code').val('');
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