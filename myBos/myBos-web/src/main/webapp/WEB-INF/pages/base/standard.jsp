<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>取派标准</title>
		<!-- 导入jquery核心类库 -->
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
			$(function(){
				// 先将body隐藏，再显示，不会出现页面刷新效果
				$("body").css({visibility:"visible"});
				
				// 收派标准信息表格
				$('#grid').datagrid( {
					iconCls : 'icon-forward',
					fit : true,
					border : false,
					rownumbers : true,
					striped : true,
					pageList: [2,4,8],
					pagination : true,
					toolbar : toolbar,
					url : "${pageContext.request.contextPath}/standard_getPage.action",
					idField : 'id',
					columns : columns,
					onDblClickRow : onDblClickRow
				});
				// 添加取派员窗口
				$('#updateStandardWindow').window({
			        title: '取派员操作',
			        width: 500,
			        modal: true,
			        shadow: true,
			        closed: true,
			        height: 350,
			        resizable:false,
			        onBeforeClose:function(){
			        	//   清除form 表单数据 尤其  隐藏id 一定要清除  reset   jquery --->Dom
			        	  $('#grid').datagrid('reload');
			        	  $("#updateStandardForm").form('clear');//  text  
			        	  $("#grid").datagrid('clearSelections');
			        	  $('#deltag').val('1');
			        }
			    });
				
				$('#save').click(function() {
					$('#updateStandardForm').ajaxSubmit(function(){
						$('#updateStandardWindow').window('close');
						$.messager.alert('成功','操作成功','info');	
					
					});
				});
			});	
			
			//工具栏
			var toolbar = [ {
				id : 'button-add',
				text : '增加',
				iconCls : 'icon-add',
				handler : function(){
					$('#updateStandardWindow').window("open");
				}
			}, {
				id : 'button-edit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : function(){
					var selections = $('#grid').datagrid('getSelections');
					if(selections.length<1) {
						$.messager.alert('错误','请先选择需要修改的数据','error');
					}else if(selections.length>1) {
						$.messager.alert('错误','只能同时修改一条数据','error');
					}else {
						
						$('#updateStandardForm').form('load',selections[0]);
						$('#updateStandardWindow').window("open");
					}
				}
			},{
				id : 'button-delete',
				text : '作废',
				iconCls : 'icon-cancel',
				handler : function(){
					var selections = $('#grid').datagrid('getSelections');
					if(selections.length<1) {
						$.messager.alert('错误','请先选择需要删除的数据','error');
					}else {
						var ids = new Array();
						for(var i=0;i<selections.length;i++){
							ids.push(selections[i].id);
						}
						var idStr = ids.join(',');
						$.post('${pageContext.request.contextPath}/standard_batchDelete.action',{ids:idStr},function() {
							$.messager.alert('成功','数据删除成功','info');
							$('#grid').datagrid('reload');
						})
					}
				}
			},{
				id : 'button-restore',
				text : '还原',
				iconCls : 'icon-save',
				handler : function(){
					var selections = $('#grid').datagrid('getSelections');
					if(selections.length<1) {
						$.messager.alert('错误','请先选择需要还原的数据','error');
					}else {
						var ids = new Array();
						for(var i=0;i<selections.length;i++){
							ids.push(selections[i].id);
						}
						var idStr = ids.join(',');
						$.post('${pageContext.request.contextPath}/standard_batchRevert.action',{ids:idStr},function() {
							$.messager.alert('成功','数据还原成功','info');
							$('#grid').datagrid('reload');
						})
					}
				}
			}];
			
			// 定义列
			var columns = [ [ {
				field : 'id',
				checkbox : true
			},{
				field : 'name',
				title : '标准名称',
				width : 120,
				align : 'center'
			}, {
				field : 'minweight',
				title : '最小重量',
				width : 120,
				align : 'center'
			}, {
				field : 'maxweight',
				title : '最大重量',
				width : 120,
				align : 'center'
			}, {
				field : 'minlength',
				title : '最小长度',
				width : 120,
				align : 'center'
			}, {
				field : 'maxlength',
				title : '最大长度',
				width : 120,
				align : 'center'
			}, {
				field : 'operator',
				title : '操作人',
				width : 120,
				align : 'center'
			}, {
				field : 'operationtime',
				title : '操作时间',
				width : 120,
				align : 'center'
			}, {
				field : 'operatorcompany',
				title : '操作单位',
				width : 120,
				align : 'center'
			}, {
				field : 'deltag',
				title : '是否有效',
				width : 120,
				align : 'center',
				formatter: function(value,row,index){
			         if (value=='1'){
			            return '有效';
			         } else {
			            return '作废';
			         }  			
			    }  
			} ] ];
			
			function onDblClickRow(rowIndex,rowData) {
				$('#updateStandardForm').form('load',rowData);
				$('#updateStandardWindow').window("open");
			}
		</script>
	</head>

	<body class="easyui-layout" style="visibility:hidden;">
		<div region="center" border="false">
			<table id="grid"></table>
		</div>
		<!-- 添加取派员窗体  -->
	<div class="easyui-window" title="对收派员进行添加或者修改" id="updateStandardWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="updateStandardForm" method="post" action="${pageContext.request.contextPath }/standard_updateStandard">
				
				<input type='hidden' name='id'>
				<input type='hidden' name='deltag' value='1' id='deltag'>
				
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派标准信息</td>
					</tr>
					<tr>
						<td>收派标准名称</td>
						<td>
						<input type="text" name="name" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td>最小重量</td>
						<td><input type="text" name="minweight" class="easyui-validatebox easyui-numberbox" required="true" data-options="min:1,precision:0,suffix:'KG'"/>
						   </td>
					</tr>
					<tr>
						<td>最大重量</td>
						<td><input type="text" name="maxweight" class="easyui-validatebox easyui-numberbox" required="true" data-options="min:1,precision:0,suffix:'KG'"/></td>
					</tr>
					<tr>
						<td>最小长度</td>
						<td><input type="text" name="minlength" class="easyui-validatebox easyui-numberbox" required="true" data-options="min:1,precision:0,suffix:'CM'"/></td>
					</tr>
					<tr>
						<td>最大长度</td>
						<td><input type="text" name="maxlength" class="easyui-validatebox easyui-numberbox" required="true" data-options="min:1,precision:0,suffix:'CM'"/></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	</body>

</html>