/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.37
 * Generated at: 2017-08-15 02:56:28 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.pages.qupai;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class noticebill_005fadd_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<title>添加业务受理单</title>\r\n");
      out.write("<!-- 导入jquery核心类库 -->\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/js/jquery-1.8.3.js\"></script>\r\n");
      out.write("<!-- 导入easyui类库 -->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/js/easyui/themes/default/easyui.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/js/easyui/themes/icon.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/js/easyui/ext/portal.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/css/default.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/css/city-picker.css\">\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/js/easyui/jquery.easyui.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/js/easyui/ext/jquery.portal.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/js/easyui/ext/jquery.cookie.js\"></script>\r\n");
      out.write("<script\r\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/js/easyui/locale/easyui-lang-zh_CN.js\"\r\n");
      out.write("\ttype=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/js/city-picker/city-picker.data.js\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/js/city-picker/city-picker.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"http://api.map.baidu.com/api?v=2.0&ak=yCKuMnScUqbCSGt2esx2uBgAz60s3ClH\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t$(function(){\r\n");
      out.write("\t\t$(\"body\").css({visibility:\"visible\"});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t// 对save按钮条件 点击事件\r\n");
      out.write("\t\t$('#save').click(function(){\r\n");
      out.write("\t\t\t// 对form 进行校验\r\n");
      out.write("\t\t\tif($('#noticebillForm').form('validate')){\r\n");
      out.write("\t\t\t\t$('#noticebillForm').submit();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t$('#reset').click(function() {\r\n");
      out.write("\t\t\t$('#citypick').citypicker('reset');\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t$('#telephone').blur(function() {\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif(telephone.value.match(/^1[34578]\\d{9}$/)) {\r\n");
      out.write("\t\t\t\t$.post('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/customer_getCustomerByTel.action',{\"telephone\":telephone.value}, function (data) {\r\n");
      out.write("\t\t\t\t\t$('#customerName').val(data.name);\r\n");
      out.write("\t\t\t\t\t$('#customerId').val(data.id);\r\n");
      out.write("\t\t\t\t} )\r\n");
      out.write("\t\t\t\t$('#telPrompt').text(\"\");\r\n");
      out.write("\t\t\t}else {\r\n");
      out.write("\t\t\t\t$('#telPrompt').text(\"格式不正确\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t// 百度地图API功能\r\n");
      out.write("\tfunction G(id) {\r\n");
      out.write("\t\treturn document.getElementById(id);\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tvar ac = new BMap.Autocomplete(    //建立一个自动完成的对象\r\n");
      out.write("\t\t{\"input\" : \"locationDetail\"\r\n");
      out.write("\t});\r\n");
      out.write("\r\n");
      out.write("\tac.addEventListener(\"onhighlight\", function(e) {  //鼠标放在下拉列表上的事件\r\n");
      out.write("\t\tvar str = \"\";\r\n");
      out.write("\t\tvar _value = e.fromitem.value;\r\n");
      out.write("\t\tvar value = \"\";\r\n");
      out.write("\t\tif (e.fromitem.index > -1) {\r\n");
      out.write("\t\t\tvalue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;\r\n");
      out.write("\t\t}    \r\n");
      out.write("\t\tstr = \"FromItem<br />index = \" + e.fromitem.index + \"<br />value = \" + value;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvalue = \"\";\r\n");
      out.write("\t\tif (e.toitem.index > -1) {\r\n");
      out.write("\t\t\t_value = e.toitem.value;\r\n");
      out.write("\t\t\tvalue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;\r\n");
      out.write("\t\t}    \r\n");
      out.write("\t\tstr += \"<br />ToItem<br />index = \" + e.toitem.index + \"<br />value = \" + value;\r\n");
      out.write("\t\tG(\"searchResultPanel\").innerHTML = str;\r\n");
      out.write("\t});\r\n");
      out.write("\r\n");
      out.write("\tvar myValue;\r\n");
      out.write("\tac.addEventListener(\"onconfirm\", function(e) {    //鼠标点击下拉列表后的事件\r\n");
      out.write("\t\tvar _value = e.item.value;\r\n");
      out.write("\t\tmyValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;\r\n");
      out.write("\t\tG(\"searchResultPanel\").innerHTML =\"onconfirm<br />index = \" + e.item.index + \"<br />myValue = \" + myValue;\r\n");
      out.write("\t\t$('#citypick').citypicker('reset');\r\n");
      out.write("\t\t$('#citypick').citypicker('destroy');\r\n");
      out.write("\t\t$('#citypick').val(_value.province + \"/\" +  _value.city + \"/\" +  _value.district);\r\n");
      out.write("\t\t$.getJSON('http://api.map.baidu.com/cloudgc/v1?callback=?&ak=yCKuMnScUqbCSGt2esx2uBgAz60s3ClH&address='+myValue,function(data) {\r\n");
      out.write("\t\t\tvar address=data.result[0].address_components\r\n");
      out.write("\t\t\t$('#citypick').citypicker('reset');\r\n");
      out.write("\t\t\t$('#citypick').citypicker('destroy');\r\n");
      out.write("\t\t\t$('#citypick').citypicker({\r\n");
      out.write("\t\t\t\tprovince:address.province,\r\n");
      out.write("\t\t\t\tcity:address.city,\r\n");
      out.write("\t\t\t\tdistrict:address.district\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t})\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body class=\"easyui-layout\" style=\"visibility:hidden;\">\r\n");
      out.write("\t<div region=\"north\" style=\"height:31px;overflow:hidden;\" split=\"false\"\r\n");
      out.write("\t\tborder=\"false\">\r\n");
      out.write("\t\t<div class=\"datagrid-toolbar\">\r\n");
      out.write("\t\t\t<a id=\"save\" icon=\"icon-save\" href=\"#\" class=\"easyui-linkbutton\"\r\n");
      out.write("\t\t\t\tplain=\"true\">新单</a>\r\n");
      out.write("\t\t\t<a id=\"edit\" icon=\"icon-edit\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/page_qupai_noticebill.action\" class=\"easyui-linkbutton\"\r\n");
      out.write("\t\t\t\tplain=\"true\">工单操作</a>\t\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div region=\"center\" style=\"overflow:auto;padding:5px;\" border=\"false\">\r\n");
      out.write("\t\t<form id=\"noticebillForm\" action=\"\" method=\"post\">\r\n");
      out.write("\t\t\t<table class=\"table-edit\" width=\"95%\" align=\"center\">\r\n");
      out.write("\t\t\t\t<tr class=\"title\">\r\n");
      out.write("\t\t\t\t\t<td colspan=\"4\">客户信息</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>来电号码:</td>\r\n");
      out.write("\t\t\t\t\t<td><input type=\"text\" class=\"easyui-validatebox\" name=\"telephone\" id=\"telephone\"\r\n");
      out.write("\t\t\t\t\t\trequired=\"true\" /><span id=\"telPrompt\" style=\"color:red;\"></span></td>\r\n");
      out.write("\t\t\t\t\t<td>客户编号:</td>\r\n");
      out.write("\t\t\t\t\t<td><input type=\"text\" class=\"easyui-validatebox\"  name=\"customerId\"\r\n");
      out.write("\t\t\t\t\t\trequired=\"true\" id=\"customerId\"/></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>客户姓名:</td>\r\n");
      out.write("\t\t\t\t\t<td><input type=\"text\" class=\"easyui-validatebox\" name=\"customerName\"\r\n");
      out.write("\t\t\t\t\t\trequired=\"true\" id=\"customerName\"/></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr class=\"title\">\r\n");
      out.write("\t\t\t\t\t<td colspan=\"4\">货物信息</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>品名:</td>\r\n");
      out.write("\t\t\t\t\t<td><input type=\"text\" class=\"easyui-validatebox\" name=\"product\"\r\n");
      out.write("\t\t\t\t\t\trequired=\"true\" /></td>\r\n");
      out.write("\t\t\t\t\t<td>件数:</td>\r\n");
      out.write("\t\t\t\t\t<td><input type=\"text\" class=\"easyui-numberbox\" name=\"num\"\r\n");
      out.write("\t\t\t\t\t\trequired=\"true\" /></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>重量:</td>\r\n");
      out.write("\t\t\t\t\t<td><input type=\"text\" class=\"easyui-numberbox\" name=\"weight\"\r\n");
      out.write("\t\t\t\t\t\trequired=\"true\" /></td>\r\n");
      out.write("\t\t\t\t\t<td>体积:</td>\r\n");
      out.write("\t\t\t\t\t<td><input type=\"text\" class=\"easyui-validatebox\" name=\"volume\"\r\n");
      out.write("\t\t\t\t\t\trequired=\"true\" /></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>省市区</td>\r\n");
      out.write("\t\t\t\t\t<td colspan=\"3\">\r\n");
      out.write("\t\t\t\t\t<div style=\"position: relative;\">\r\n");
      out.write("\t\t\t\t\t   <input type=\"text\" id=\"citypick\" name=\"ssq\" size=\"40\" data-toggle=\"city-picker\"  placeholder=\"请选择省/市/区\"/>\r\n");
      out.write("\t\t\t\t\t   <button class=\"btn btn-warning\" id=\"reset\" type=\"button\">重置</button>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>详细地址</td>\r\n");
      out.write("\t\t\t\t\t<td colspan=\"3\">\r\n");
      out.write("\t\t\t\t\t   <input type=\"text\"  name=\"pickaddress\" size=\"80\" id=\"locationDetail\"/>\r\n");
      out.write("\t\t\t\t\t   <div id=\"searchResultPanel\" style=\"border:1px solid #C0C0C0;width:150px;height:auto; display:none;\"></div>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>到达城市:</td>\r\n");
      out.write("\t\t\t\t\t<td><input type=\"text\" class=\"easyui-validatebox\" name=\"arrivecity\"\r\n");
      out.write("\t\t\t\t\t\trequired=\"true\" /></td>\r\n");
      out.write("\t\t\t\t\t<td>预约取件时间:</td>\r\n");
      out.write("\t\t\t\t\t<td><input type=\"text\" class=\"easyui-datebox\" name=\"pickdate\"\r\n");
      out.write("\t\t\t\t\t\tdata-options=\"required:true, editable:false\" /></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>备注:</td>\r\n");
      out.write("\t\t\t\t\t<td colspan=\"3\"><textarea rows=\"5\" cols=\"80\" type=\"text\" class=\"easyui-validatebox\" name=\"remark\"\r\n");
      out.write("\t\t\t\t\t\trequired=\"true\" ></textarea></td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
