<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域设置</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.form.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.ocupload-min.js"></script>
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

	var regionId;
	function doAdd(){
		$('#updateRegionWindow').window("open");
	}
	
	function doEdit(){
		var selections = $('#grid').datagrid('getSelections');
		if(selections.length<1) {
			$.messager.alert('错误','请先选择需要修改的数据','error');
		}else if(selections.length>1) {
			$.messager.alert('错误','只能同时修改一条数据','error');
		}else {
			regionId=selections[0].id;
			$('#updateRegionWindow').window("open");
			$('#updateRegionForm').form('load',selections[0]);
		}
	}
	
	
	//工具栏
	var toolbar = [ {
		id : 'button-edit',	
		text : '修改',
		iconCls : 'icon-edit',
		handler : doEdit
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	},{
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo'
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'province',
		title : '省',
		width : 120,
		align : 'center'
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center'
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center'
	}, {
		field : 'postcode',
		title : '邮编',
		width : 120,
		align : 'center'
	}, {
		field : 'shortcode',
		title : '简码',
		width : 120,
		align : 'center'
	}, {
		field : 'citycode',
		title : '城市编码',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : false,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [10,20,30],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/region_getPage.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加、修改区域窗口
		$('#updateRegionWindow').window({
	        title: '添加修改区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false,
	        onBeforeClose:function(){
	        	  $('#grid').datagrid('reload');
	        	  $("#updateRegionForm").form('clear');//  text  
	        	  $("#grid").datagrid('clearSelections');
	        	  regionId=null;
	        }
	    });
		
		$('#searchbox').searchbox({ 
			searcher : function(value,name){
				$('#grid').datagrid('load',{params:name+"="+value});
			}, 
			menu:'#searchMenu', 
			prompt:'请输入查询条件' 
		});
		
		$('#save').click(function() {
			$('#updateRegionForm').ajaxSubmit(function(){
				$('#updateRegionWindow').window('close');
				$.messager.alert('成功','操作成功','info');	
			
			});
		});
		
		$('#regionId').validatebox({ 
			required: true, 
			validType: ['len[5]','repeatedId']
		}); 
		$('#regionPostcode').validatebox({ 
			required: true, 
			validType: ['len[6]','repeatedPostcode']
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
							code:value,
							id:regionId
						},
						url:'${pageContext.request.contextPath}/region_checkId.action',
						type:'POST',
						timeout:1000,
						success:function(data) {
							flag=data;
						}
					});
					return flag;
				}, 
				message: '该区域编码已经存在' 
			},
			
			repeatedPostcode: { 
				validator: function(value){ 
					var flag;
					$.ajax({
						async:false,
						data:{
							code:value,
							id:regionId
						},
						url:'${pageContext.request.contextPath}/region_checkPostcode.action',
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
		
		$('#button-import').upload({
			name: 'upload',
	        action: '${pageContext.request.contextPath}/region_upload.action',
	        enctype: 'multipart/form-data',
	        onSelect: function() {
	        	this.autoSubmit=false;
	        	var pattern = new RegExp(/^.+\.xls[x]{0,1}$/);
	        	if(pattern.test(this.filename())) {
	        		this.submit()
	        	}else {
	        		$.messager.alert('错误','只能上传excel文件','error')
	        	}
	        },
	        onComplete: function(response) {
	        	if(response=='true') {
	        		$.messager.alert('成功','数据上传成功','info')
	        		$('#grid').datagrid('reload');
	        	}else {
	        		$.messager.alert('失败','服务器正在维护，请稍后再试','error')
	        	}
	        },
		})
	});

	function doDblClickRow(rowIndex,rowData){
		regionId=rowData.id;
		$('#updateRegionWindow').window("open");
		$('#updateRegionForm').form('load',rowData);
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">

	<div region="center" border="false">
		<input id="searchbox"></input> 
		<div id="searchMenu" style="width:120px"> 
			<div data-options="name:'postcode'">邮编</div>
			<div data-options="name:'shortcode'">简码</div>
			<div data-options="name:'citycode'">城市编码</div>
		</div>
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="区域添加修改" id="updateRegionWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="updateRegionForm" method="post" action="${pageContext.request.contextPath }/region_updateRegion">
				
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>区域编码</td>
						<td><input type="text" name="id" id="regionId"/></td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="province" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode" id="regionPostcode"/></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode" class="easyui-validatebox" required="true"/></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
</body>
</html>