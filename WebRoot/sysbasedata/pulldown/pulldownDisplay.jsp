<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script type="text/javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<br>
<!-- pdf���������� -->
<style type="text/css">
		*{
			font-family: SongTi_GB2312;
		}
</style>
<style type="text/css">
<%--	����pdf��������ʽ--%>
.divtable table{border-collapse: collapse;border: 1px outset #999999;background-color: #FFFFFF;}
.divtable table td{font-size: 12px;border: 1px outset #999999;}
</style>
<html:errors/>
<logic:present name="pulldownBean">
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"  class=tb>
			<tr>
				<td width="20%"  class="wordtd">
					���룺
				</td>
				<td width="80%" >
				    <bean:write name="pulldownBean" property="pullid"/>
				</td>
			</tr>
			<tr>
				<td class="wordtd">
					���ƣ�
				</td>
				<td>
					<bean:write name="pulldownBean" property="pullname"/>
				</td>
			</tr>
			<tr>
				<td class="wordtd">
					����ţ�
				</td>
				<td>
				    <bean:write name="pulldownBean" property="orderby"/>
				</td>
			</tr>
			<tr>
				<td class="wordtd">
					���ͣ�
				</td>
				<td>
				    <bean:write name="pulldownBean" property="typeflag"/>
				</td>
			</tr>
		  <tr>
		    <td class="wordtd"><bean:message key="loginuser.enabledflag"/>��</td>
		    <td>${pulldownBean.enabledflag=='Y'?'��':'��' }</td>	 
		 </tr>
			<tr>
				<td class="wordtd">
					������
				</td>
				<td>
				    <bean:write name="pulldownBean" property="pullrem"/>
				</td>
			</tr>
		</table>
	</body>
</logic:present>
<script>
	/**
	*ҳ�������ͱ���html�ļ�
	*/
	$(function(){
		var url1 = '<html:rewrite page="/PullDownAction.do"/>';
		 var test=document.getElementsByTagName('html')[0].innerHTML; 
		 var top=$("table.top_navigation").parent().html();	
		 var tabToolBar=$("td.table_outline3").html();
		 	$.ajax({
					url : url1,// Ҫ�����action
				data : {
					method : "createPdfFile",
					test : test,
					top : top,
					tabToolBar : tabToolBar,
				    htmlName : "PullDown.html"
				},// ����Ĳ����ͷ���
				type : "POST",
				dataType : "text",// ��ʲô��������
				async : "false",// �Ƿ��첽����������첽�������������ȴ�����������ؾ�ִ�������������
				cache : "false",// �Ƿ���л���
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				success : function(arr) {

				},
				error: function(){
					
				}
			});
	})
</script>
