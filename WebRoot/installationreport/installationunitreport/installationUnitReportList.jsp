<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css"
	href="<html:rewrite forward='formCSS'/>">
	<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
  <script type="text/javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
	<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
	<script language="javascript" src="<html:rewrite page="/common/javascript/DigitalToChinese.js"/>"></script>
  <link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
  <script language="javascript" src="<html:rewrite forward='pageJS'/>"></script>
  <link href="/XJSCRM/common/css/bb.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="<html:rewrite page="/common/javascript/highcharts/highcharts.js"/>"></script>

<html:form action="/installationUnitReportAction.do?method=toSearchResults">
<input type="hidden" name="insCompanyName" id="insCompanyName" value="${rmap.insCompanyName }"/>
<input type="hidden" name="sdate1" id="sdate1" value="${rmap.sdate1 }"/>
<input type="hidden" name="edate1" id="edate1" value="${rmap.edate1 }"/>
<html:hidden property="genReport" styleId="genReport" />
 
 <bean:write name="showstr"/>
<br/><br/>

	<%int i=1; %>
<table class="tb"  width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr class="wordtd_header" id="thead">
	<td nowrap="nowrap"  style="text-align: center;">���</td>	
	<td nowrap="nowrap"  style="text-align: center;">��װ��λ����</td>
	<%int j=1; %>
  	<logic:present name="maxList">
   		<logic:iterate id="e1" name="maxList">
   		<%if(j==1){ %>
   			<td nowrap="nowrap"  style="text-align: center;">����̨��</td>
   			<td nowrap="nowrap"  style="text-align: center;">����ϸ�̨��</td>
   			<td nowrap="nowrap"  style="text-align: center;">����ϸ���</td>
   		<%}else{ %>
   			<td nowrap="nowrap"  style="text-align: center;"><script>document.write(NumberToChinese(<%=j%>)+'��̨��');</script></td>
   			<td nowrap="nowrap"  style="text-align: center;"><script>document.write(NumberToChinese(<%=j%>)+'��ϸ�̨��');</script></td>
   			<td nowrap="nowrap"  style="text-align: center;"><script>document.write(NumberToChinese(<%=j%>)+'��ϸ���');</script></td>
   		<%} %>
   		<%j++; %>
   		</logic:iterate>
   	</logic:present>
	
	<td nowrap="nowrap"  style="text-align: center;">�ۿ��ܶ�</td>
	</tr>
	<logic:present name="maintenanceReportList">
	  <logic:iterate id="ele" name="maintenanceReportList">
	  <tr class="inputtd" align="center" height="20">
	  <td><%=i++ %></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="insCompanyName"/></td>
	  		
		  <logic:present name="ele" property="factoryList">
			  <logic:iterate id="e" name="ele" property="factoryList" indexId="k">
				  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="e" property="factory${k}"/></td>
				  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="e" property="nofactory${k}"/></td>
				  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="e" property="rate${k}"/></td>
				  
			  </logic:iterate>
		  </logic:present>
	 
	
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="deductMoney"/></td>
	  </tr>
	  </logic:iterate>
	  	</logic:present>
	  	<logic:notPresent name="maintenanceReportList">
	  <tr class="noItems" align="center" height="20"><td colspan="${3*length+3}" >û�м�¼��ʾ��</td></tr>
	  </logic:notPresent>
</table>
		
</html:form>


