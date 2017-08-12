<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理分区</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.form.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/serializeJson.js"></script>
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
	var subareaId;
	var editIndex;
	function doAdd(){
		$('#updateSubareaWindow').window("open");
	}
	
	function doEdit(){
		var selections = $('#grid').datagrid('getSelections');
		if(selections.length<1) {
			$.messager.alert('错误','请先选择需要修改的数据','error');
		}else if(selections.length>1) {
			$.messager.alert('错误','只能同时修改一条数据','error');
		}else {
			subareaId=selections[0].id;
			('#updateSubareaWindow').window("open");
			$('#updateSubareaForm').form('load',rowData);
		}
	}
	
	function doSearch(){
		$('#searchWindow').window("open");
	}
	
	function doExport(){
		$('#searchSubareaForm').form('submit',{url:'${pageContext.request.contextPath}/subarea_export'});
	}
	
	function doImport(){
		alert("导入");
	}
	
	//工具栏
	var toolbar = [ {
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-edit',	
		text : '修改',
		iconCls : 'icon-edit',
		handler : doEdit
	},{
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo',
		handler : doImport
	},{
		id : 'button-export',
		text : '导出',
		iconCls : 'icon-undo',
		handler : doExport
	},{
		id : 'button-saveEdit',
		text : '保存编辑',
		iconCls : 'icon-save',
		handler : function() {
			$('#button-saveEdit').hide();
			$('#button-cancelEdit').hide();
			$('#button-search').show();
			$('#button-add').show();
			$('#button-edit').show();
			$('#button-delete').show();
			$('#button-import').show();
			$('#button-export').show();
			$('#grid').datagrid('endEdit',editIndex);
			editIndex=null;
		}
	},{
		id : 'button-cancelEdit',
		text : '取消编辑',
		iconCls : 'icon-undo',
		handler : function() {
			$('#button-saveEdit').hide();
			$('#button-cancelEdit').hide();
			$('#button-search').show();
			$('#button-add').show();
			$('#button-edit').show();
			$('#button-delete').show();
			$('#button-import').show();
			$('#button-export').show();
			$('#grid').datagrid('cancelEdit',editIndex);
			editIndex=null;
		}
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'showid',
		title : '分拣编号',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.id;
		}
	},{
		field : 'province',
		title : '省',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.region.province;
		}
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.region.city;
		}
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.region.district;
		}
	}, {
		field : 'addresskey',
		title : '关键字',
		width : 120,
		align : 'center',
		editor: {
			type:'validatebox',
			options:{
				required:true
			}
		}
	}, {
		field : 'startnum',
		title : '起始号',
		width : 100,
		align : 'center',
		editor: {
			type:'validatebox',
			options:{
				required:true
			}
		}
	}, {
		field : 'endnum',
		title : '终止号',
		width : 100,
		align : 'center',
		editor: {
			type:'validatebox',
			options:{
				required:true
			}
		}
	} , {
		field : 'single',
		title : '单双号',
		width : 100,
		align : 'center',
		editor: {
			type:'combobox',
			options:{
				valueField:'id', 
				textField:'text',
				data: [	{id: '0',text: '单双号'},
		            	{id: '1',text: '单号'},
		           		{id: '2', text: '双号'}]
			}
		},
		formatter: function(value){
        if (value=='0'){
            return '单双号';
        } else if(value=='1') {
            return '单号';
        } else if(value=='2') {
        	return '双号';
        } else {
        	return '数据错误';
        }	
    }  
	} , {
		field : 'position',
		title : '位置',
		width : 200,
		align : 'center',
		editor: {
			type:'validatebox',
			options:{
				required:true
			}
		}
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList: [2,4,8,16],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/subarea_getPage",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow,
			onAfterEdit : function(rowIndex, rowData) {
				$.post('${pageContext.request.contextPath}/subarea_updateSubarea',rowData,function (){
					$('#grid').datagrid('reload');
					$.messager.alert('成功','数据修改成功','info');
				});
			}
		});
		
		// 添加、修改分区
		$('#updateSubareaWindow').window({
	        title: '添加修改分区',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false,
	    });
		
		// 查询分区
		$('#searchWindow').window({
	        title: '查询分区',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		$("#btn").click(function(){
			$('#grid').datagrid('load',$('#searchSubareaForm').serializeJson());
			$('#searchWindow').window('close');
		});
		
		$('#save').click(function() {
			if($('#updateSubareaForm').form('validate')) {
				$('#updateSubareaForm').ajaxSubmit(function() {
					$('#grid').datagrid('reload');
					$('#grid').datagrid('clearSelections');
					$('#updateSubareaForm').form('clear');
					subareaId=null;
					$('#updateSubareaWindow').window('close');
				});
			}
		});
		
		$('#sortId').validatebox({ 
			required: true, 
			validType: ['len[5]','repeatedId']
		}); 
		
		$.extend($.fn.validatebox.defaults.rules, { 
			len: { 
				validator: function(value,param){ 
					if(value.length!=param[0]) {
						return false;
					}
					return true;
				}, 
				message: '格式错误' 
			},
			repeatedId: { 
				validator: function(value){ 
					var flag;
					$.ajax({
						async:false,
						data:{
							Id:value,
							oldId:subareaId
						},
						url:'${pageContext.request.contextPath}/subarea_checkId',
						type:'POST',
						timeout:1000,
						success:function(data) {
							flag=data;
						}
					});
					return flag;
				}, 
				message: '该区域编码已经存在' 
			}
		});
		$('#button-cancelEdit').hide();
		$('#button-saveEdit').hide();
		
	});

	function doDblClickRow(rowIndex,rowData){
		if(editIndex==null) {
			$('#button-search').hide();
			$('#button-add').hide();
			$('#button-edit').hide();
			$('#button-delete').hide();
			$('#button-import').hide();
			$('#button-export').hide();
			$('#button-cancelEdit').show();
			$('#button-saveEdit').show();
			$('#grid').datagrid('beginEdit',rowIndex);
			editIndex = rowIndex;
		}else {
			if(editIndex!=rowIndex) {
				$('#grid').datagrid('cancelEdit',editIndex);
				$('#grid').datagrid('beginEdit',rowIndex);
				editIndex = rowIndex;
			}
		}
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<!-- 添加 修改分区 -->
	<div class="easyui-window" title="分区添加修改" id="updateSubareaWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div style="overflow:auto;padding:5px;" border="false">
			<form action="${pageContext.request.contextPath }/subarea_updateSubarea" id="updateSubareaForm" method="POST">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">分区信息</td>
					</tr>
					<tr>
						<td>分拣编码</td>
						<td><input type="text" name="id" id="sortId"/></td>
					</tr>
					<tr>
						<td>选择区域</td>
						<td>
							<input class="easyui-combobox" name="region.id"  
    							data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath }/region_getRegionList', mode:'remote'" />  
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" name="addresskey" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>起始号</td>
						<td><input type="text" name="startnum" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>终止号</td>
						<td><input type="text" name="endnum" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>单双号</td>
						<td>
							<select class="easyui-combobox" name="single" style="width:150px;">  
							    <option value="0">单双号</option>  
							    <option value="1">单号</option>  
							    <option value="2">双号</option>  
							</select> 
						</td>
					</tr>
					<tr>
						<td>位置信息</td>
						<td><input type="text" name="position" class="easyui-validatebox" required="true" style="width:250px;"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 查询分区 -->
	<div class="easyui-window" title="查询分区窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="searchSubareaForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="region.province"/></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="region.city"/></td>
					</tr>
					<tr>
						<td>区（县）</td>
						<td><input type="text" name="region.district"/></td>
					</tr>
					<tr>
						<td>定区编码</td>
						<td><input type="text" name="decidedZone.id"/></td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" name="addresskey"/></td>
					</tr>
					<tr>
						<td colspan="2"><a id="btn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>