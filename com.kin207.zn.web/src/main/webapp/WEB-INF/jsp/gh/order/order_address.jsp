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
								<form action="order/${msg }.do" name="addForm" id="addForm" method="post">
									<input type="hidden" name="orderId" id="order_id" value="${pd.orderId }"/><!-- 订单ID -->
									<input type="hidden" name="id" id="address_id" value="${pd.id }"/><!-- 订单地址ID -->
									<input type="hidden" name="userId" id="userId" value="${pd.userId }"/><!-- 用户ID -->
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">收件人:</td>
											<td><input type="text" name="recipients" id="recipients" value="${pd.recipients }" maxlength="20" placeholder="这里输入收件人" title="收件人" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">联系电话:</td>
											<td><input type="text" name="phone" id="phone" value="${pd.phone }" maxlength="20" placeholder="这里输入联系电话" title="联系电话" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">地址:</td>
											<td><input type="text" name="address" id="address" value="${pd.address }" maxlength="100" placeholder="这里输入地址" title="地址" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">详细地址:</td>
											<td><input type="text" name="detailedAddress" id="detailedAddress" value="${pd.detailedAddress }" maxlength="100" placeholder="这里输入详细地址" title="详细地址" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="10">
												<!-- <a class="btn btn-mini btn-primary" onclick="save();">保存</a> -->
												<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
											</td>
										</tr>
									</table>
									</div>
									<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){});
	
	//保存
	function save(){
		if($("#recipients").val()==""){
			$("#recipients").tips({
				side:3,
	            msg:'输入收件人',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#recipients").focus();
			return false;
		}else{
			$("#recipients").val(jQuery.trim($('#recipients').val()));
		}
		if($("#phone").val()==""){
			$("#phone").tips({
				side:3,
	            msg:'输入联系电话',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#phone").focus();
			return false;
		}else{
			$("#phone").val(jQuery.trim($('#phone').val()));
		}
		if($("#address").val()==""){
			$("#address").tips({
				side:3,
	            msg:'输入地址',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#address").focus();
			return false;
		}else{
			$("#address").val(jQuery.trim($('#address').val()));
		}
		if($("#detailedAddress").val()==""){
			$("#detailedAddress").tips({
				side:3,
	            msg:'输入详细地址',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#detailedAddress").focus();
			return false;
		}else{
			$("#detailedAddress").val(jQuery.trim($('#detailedAddress').val()));
		}
		$("#addForm").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
	/* $(function() {
		//下拉框
		if(!ace.vars['touch']) {
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			$(window)
			.off('resize.chosen')
			.on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			}).trigger('resize.chosen');
			$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
				if(event_name != 'sidebar_collapsed') return;
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			});
			$('#chosen-multiple-style .btn').on('click', function(e){
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
				 else $('#form-field-select-4').removeClass('tag-input-style');
			});
		}
	}); */
</script>
</html>