<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.form.min.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function doAdd(){
		$('#updateStaffWindow').window("open");
	}
	
	function doView(){
		$('#searchStaffWindow').window('open');
	}
	
	function doDelete(){
		var selections = $('#grid').datagrid('getSelections');
		if(selections.length<1) {
			$.messager.alert('错误','请先选择需要删除的数据','error');
		}else {
			var ids = new Array();
			for(var i=0;i<selections.length;i++){
				ids.push(selections[i].id);
			}
			var idStr = ids.join(',');
			$.post('${pageContext.request.contextPath}/staff_batchDelete.action',{ids:idStr},function() {
				$.messager.alert('成功','数据删除成功','info');
				$('#grid').datagrid('reload');
			})
		}
		
	}
	
	function doRestore(){
		var selections = $('#grid').datagrid('getSelections');
		if(selections.length<1) {
			$.messager.alert('错误','请先选择需要还原的数据','error');
		}else {
			var ids = new Array();
			for(var i=0;i<selections.length;i++){
				ids.push(selections[i].id);
			}
			var idStr = ids.join(',');
			$.post('${pageContext.request.contextPath}/staff_batchRevert.action',{ids:idStr},function() {
				$.messager.alert('成功','数据还原成功','info');
				$('#grid').datagrid('reload');
			})
		}
	}
	//工具栏
	var toolbar = [ {
		id : 'button-view',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '作废',
		iconCls : 'icon-cancel',
		handler : doDelete
	},{
		id : 'button-save',
		text : '还原',
		iconCls : 'icon-save',
		handler : doRestore
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'name',
		title : '姓名',
		width : 120,
		align : 'center'
	}, {
		field : 'telephone',
		title : '手机号',
		width : 120,
		align : 'center'
	}, {
		field : 'haspda',
		title : '是否有PDA',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="1"){
				return "有";
			}else{
				return "无";
			}
		}
	}, {
		field : 'deltag',
		title : '是否作废',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="1"){
				return "正常使用"
			}else{
				return "已作废";
			}
		}
	}, {
		field : 'standard',
		title : '取派标准',
		width : 120,
		align : 'center'
	}, {
		field : 'station',
		title : '所谓单位',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 取派员信息表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [2,4,8],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/staff_getPage.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加取派员窗口
		$('#updateStaffWindow').window({
	        title: '添加取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 350,
	        resizable:false,
	        onBeforeClose:function(){
	        	  $('#grid').datagrid('reload');
	        	  $("#updateStandardForm").form('clear');//  text  
	        	  $("#grid").datagrid('clearSelections');
	        	  $('#deltag').val('1');
	        }
	    });
		
		$('#save').click(function() {
			$('#updateStaffForm').ajaxSubmit(function(){
				$('#updateStaffWindow').window('close');
				$.messager.alert('成功','操作成功','info');	
			
			});
		}) 
		
		// 查询取派员窗口
		$('#searchStaffWindow').window({
	        title: '添加取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 350,
	        resizable:false,
	    });
		
		$('#clearForm').click(function(){
			$('#searchStaffForm').form('clear');
		})
		
		$('#search').click(function(){
			$('#grid').datagrid('load',{params:$('#searchStaffForm').serialize()});
			$('#searchStaffWindow').window('close');
		})	
		
		$('#staffTel').validatebox({ 
			required: true, 
			validType: ['telephone','repeatedTel'] 
		}); 
		$('#staffTel2').validatebox({ 
			required: true, 
			validType: ['telephone'] 
		}); 
		
		$.extend($.fn.validatebox.defaults.rules, { 
			telephone: { 
				validator: function(value, param){ 
					var pattern = new RegExp(/^1[34578]\d{9}$/);
					if(pattern.test(value)) {
						return true;
					}
					return false;
				}, 
				message: '手机号格式错误' 
			},
			
			repeatedTel: { 
				validator: function(value, param){ 
					var flag;
					$.ajax({
						async:false,
						data:{
							telephone:value,
							id:$('#staffId').val()
						},
						url:'${pageContext.request.contextPath}/staff_checkTel.action',
						type:'POST',
						timeout:1000,
						success:function(data) {
							flag=data;
						}
					});
					return flag;
				}, 
				message: '该手机号已经存在' 
			} 

		}); 
		
		$('.deliverStandard').combobox({ 
			url:'${pageContext.request.contextPath}/standard_standardList.action', 
			valueField:'name', 
			textField:'name' 
		});





		
	});

	function doDblClickRow(rowIndex,rowData) {
		$('#updateStaffForm').form('load',rowData);
		$('#updateStaffWindow').window("open");
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="对收派员进行添加或者修改" id="updateStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="updateStaffForm" method="post" action="${pageContext.request.contextPath }/staff_updateStaff">
				
				<input type='hidden' name='id' id = "staffId">
				<input type='hidden' name='deltag' value='1' id='deltag'>
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone" id="staffTel"/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="deliverStandard"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	
	<div class="easyui-window" title="对收派员进行查询" id="searchStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="search" icon="icon-search" href="#" class="easyui-linkbutton" plain="true" >查询</a>
				<a id="clearForm" icon="icon-undo" href="#" class="easyui-linkbutton" plain="true" >清空</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="searchStaffForm" method="post" action="${pageContext.request.contextPath }/staff_getPage">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员查询信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone" id="staffTel2"/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="deliverStandard"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
</body>
</html>	