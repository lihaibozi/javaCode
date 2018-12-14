<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
					<!-- 检索  -->
					<form action="repairRegister/repairRegister.do" method="post" name="Form" id="Form">
					<div id="zhongxin" style="padding-top: 13px;">
					<table style="margin-top:5px;">
						<tr>
							<td>
								<div class="nav-search">
								<span class="input-icon">
									<input autocomplete="off" class="nav-search-input"  id="nav-search-input" 
									type="text" name="keyword"  value="${pd.keyword}" placeholder="输入单号或科室" />
								</span>
								</div>
							</td>
							<td style="padding-left:2px;">
								<input class="span10 date-picker" name="startTime" id="startTime"  value="${pd.startTime}" 
								type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;"
								 placeholder="开始日期" title="开始日期"/>
							</td>
							<td style="padding-left:2px;">
								<input class="span10 date-picker" name="endTime" id="endTime"  value="${pd.endTime}" 
								type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;"
								 placeholder="结束日期" title="结束日期"/>
							</td>
							 <td style="padding-left:2px;">
							 	<select id="orderPeople" name="orderPeople" style="width: 98%">
											<option value=''>请选择接单人</option>
											<c:forEach items="${listOrders}" var="cate" varStatus="st">
											 <option value="${cate.number}" 
									            <c:if test='${pd.orderPeople == cate.number}'> selected='selected'</c:if>>
									            ${cate.name}
									       	 </option>
										</c:forEach>
								</select>
							 </td>
							<c:if test="${QX.cha == 1 }">
							<td style="vertical-align:top;padding-left:22px;"><a class="btn btn-light btn-xs" onclick="searchs();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
							</c:if>
							<c:if test="${QX.toExcel == 1 }"><td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td></c:if>
							<td  style="padding-left:32px;">
							 <button  type="button" onclick="clean()">清空筛选条件</button>
							 </td>
						</tr>
					</table>
				<table style="width:100%;">
					<tr>
						<td style="vertical-align:top;">
							<c:if test="${QX.add == 1 }">
							<a class="btn btn-sm btn-success" onclick="add();">新增</a>
							</c:if>
							<c:if test="${QX.del == 1 }">
							<a title="批量删除" class="btn btn-sm btn-danger" onclick="deleteAll('确定要删除选中的数据吗?');" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
							</c:if>
						</td>
						<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
					</tr>
				</table>
					<!-- 检索  -->
					<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
						<thead>
							<tr>
								<th class="center" onclick="selectAll()" style="width:35px;">
								<label><input type="checkbox" id="zcheckbox" class="ace" /><span class="lbl"></span></label>
								</th>
								<th class="center" style="width:50px;">序号</th>
								<th class="center">单号</th>
								<th class="center">报修人</th>
								<th class="center">报修内容</th>
								<th class="center">所属科室</th>
								<th class="center">科室电话</th>
								<th class="center">联系电话</th>
								<th class="center">设备资产编号</th>
								<th class="center">设备SN号</th>
								<th class="center">接单人</th>
								<th class="center">协助人员</th>
								<th class="center">维修类别</th>
								<th class="center">处理状态</th>
								<th class="center">登记人</th>
								<th class="center">登记日期</th>
								<th class="center">修改人</th>
								<th class="center">修改时间</th>
								<th class="center">完成时间</th>
								<th class="center">报修图片</th>
								<th class="center">操作</th>
							</tr>
						</thead>
						<tbody>
						<!-- 开始循环 -->	
						<c:choose>
							<c:when test="${not empty repairRegisterList}">
								<c:if test="${QX.cha == 1 }">
								<c:forEach items="${repairRegisterList}" var="var" varStatus="vs">
									<tr>
										<td class='center' style="width: 30px;">
											<label><input type='checkbox' name='ids' class="ace" value="${var.id}" /><span class="lbl"></span></label>
										</td>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td class="center">${var.serialNumber}</td>
										<td class="center">${var.baoxiu}</td>
										<td class="center">${var.content}</td>
										<td class="center">${var.depName}</td>
										<td class="center">${var.depPhone}</td>
										<td class="center">${var.cellPhone}</td>
										<td class="center">${var.assetNumber}</td>
										<td class="center">${var.assetSn}</td>
										<td class="center">${var.jiedan}</td>
										<td class="center">${var.assistants}</td>
										<td class="center">${var.type}</td>
										<td class="center">${var.status}</td>
										<td class="center">${var.registerPeople}</td>
										<td class="center"><fmt:formatDate value="${var.registerDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td class="center">${var.modifyPeople}</td>
										<td class="center"><fmt:formatDate value="${var.modifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td class="center"><fmt:formatDate value="${var.finishDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td class="center">
										<c:if test="${var.image != null && var.image!=''}">
										<%-- <a href="${var.image}" target="_blank"><img src="${var.image}" width="30" /></a> --%>
										<%-- <a style="cursor:pointer " onclick="href='<%=basePath%>/user/downFile.do?filePath=${var.image}'">${var.fileName}</a> --%>
										<a id="loadFile" style="cursor:pointer" onclick="loadFile('${var.fileName}');">${var.fileName}</a>
										<a id="delFile" style="cursor:pointer;margin-left: 10px" class="red" onclick="delFile('${var.id}','${var.fileName}');" title="删除文件">
											<i class="ace-icon fa fa-trash-o bigger-130"></i>
										</a>
										</c:if>
										</td>
										<td class="center" style="width:60px;">
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
										</c:if>
											<c:if test="${QX.edit == 1 }">
											<a  style="cursor:pointer;" class="green" onclick="edit('${var.id}');" title="编辑">
												<i class="ace-icon fa fa-pencil bigger-130"></i>
											</a>
											</c:if>
											&nbsp;
											<c:if test="${QX.del == 1 }">
											<a style="cursor:pointer;" class="red" onclick="del('${var.id}');" title="删除">
												<i class="ace-icon fa fa-trash-o bigger-130"></i>
											</a>
											</c:if>
										</td>
									</tr>
								
								</c:forEach>
								</c:if>
								<c:if test="${QX.cha == 0 }">
									<tr>
										<td colspan="100" class="center">您无权查看</td>
									</tr>
								</c:if>
							</c:when>
							<c:otherwise>
								<tr class="main_info">
									<td colspan="100" class="center" >没有相关数据</td>
								</tr>
							</c:otherwise>
						</c:choose>
						</tbody>
					</table>
				</div>
				</form>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
    <div id="showDiv" style="position: absolute;background:#c8ddf7;width:300px; white-space:normal; word-break:break-all;word-wrap:break-word;"></div>
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<script src="static/js/common/common.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	</body>
	<script type="text/javascript">
		$(top.hangge());
		
		//检索
		function searchs(){
			top.jzts();
			$("#Form").submit();
		}
		//全选 （是/否）
		function selectAll(){
			 var checklist = document.getElementsByName ("ids");
			   if(document.getElementById("zcheckbox").checked){
			   for(var i=0;i<checklist.length;i++){
			      checklist[i].checked = 1;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++){
			     checklist[j].checked = 0;
			  }
			 }
		}
		//新增
		function add(){
			 addOrEdit('<%=basePath%>repairRegister/goAdd.do','维修登记');
		}
		
		//删除
		function del(Id,Image){
			if(confirm("确定要删除?")){ 
				top.jzts();
				var url = "<%=basePath%>repairRegister/delete.do?id="+Id;
				$.get(url,function(data){
					nextPage('${page.currentPage}');
				});
			}
		}
		
		//批量操作
		function deleteAll(msg){
			if(confirm(msg)){ 
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str==''){
						alert("您没有选择任何内容!"); 
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>repairRegister/deleteAll.do',
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage('${page.currentPage}');
									 });
								}
							});
						}
					}
			}
		}
	
		function loadFile(fileName){
			var url = "<%=basePath%>/user/downFile.do?fileName="+fileName;
			url = encodeURI(url,"UTF-8");
			$("#loadFile").attr("href",url);
		}
		
		function delFile(id,fileName){
				top.jzts();
				var url = "<%=basePath%>repairRegister/delFile.do?id="+id+"&fileName="+fileName;
				url = encodeURI(url,"UTF-8");
				$.get(url,function(data){
					nextPage('${page.currentPage}');
				});
			
		}
		
		function addOrEdit(url,title){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title = title;
			 diag.URL = url;
			 diag.Width = 700;
			 diag.Height = 450;
			 diag.CancelEvent = function(){ //关闭事件
				 if('${page.currentPage}' == '0'){
					 top.jzts();
					 setTimeout("self.location=self.location",100);
				 }else{
					 nextPage('${page.currentPage}');
				 }
				diag.close();
			 };
			 diag.show();
		}
		
		//修改
		function edit(Id){
			addOrEdit('<%=basePath%>repairRegister/goEdit.do?id='+Id,'编辑');
		}
		
		function toExcel(){
			var keywords = $("#nav-search-input").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var orderPeople = $("#orderPeople").val();
			window.location.href='<%=basePath%>repairRegister/excel.do?keywords='+keywords+'&startTime='+startTime+'&endTime='+endTime+'&orderPeople='+orderPeople;
		}
	
		function clean(){
			$("#nav-search-input").val("");
			$("#startTime").val("");
			$("#endTime").val("");
			$("#orderPeople").val("");
			
		}
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		})
	</script>
	<style type="text/css">
	li {list-style-type:none;}
	.wrap{
	    list-style:none;
		overflow:hidden;
		white-space:nowrap;
		text-overflow:ellipsis;
		max-width: 200px;
		align: center;
		-o-text-overflow:ellipsis; 
		margin-top:5px; 
	}
	.li2{
	list-style:none;
	margin-top:5px;
	}
		
	</style>
	<ul class="navigationTabs">
           <li><a></a></li>
           <li></li>
       </ul>
</html>

