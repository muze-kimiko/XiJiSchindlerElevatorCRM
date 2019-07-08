<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css"
	href="<html:rewrite forward='formCSS'/>">
	<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
	<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
  <link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
  <script language="javascript" src="<html:rewrite forward='pageJS'/>"></script>
  <link href="/XJSCRM/common/css/bb.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
  <script type="text/javascript" src="<html:rewrite page="/common/javascript/highcharts/highcharts.js"/>"></script>

<html:form action="/segmentNotCloseReportAction.do?method=toSearchResults">
<input type="hidden" name="projectName" id="projectName" value="${rmap.projectName }"/>
<input type="hidden" name="insCompanyName" id="insCompanyName" value="${rmap.insCompanyName }"/>
<input type="hidden" name="salesContractNo" id="salesContractNo" value="${rmap.salesContractNo }"/>
<input type="hidden" name="projectManager" id="projectManager" value="${rmap.projectManager }"/>
<input type="hidden" name="elevatorNo" id="elevatorNo" value="${rmap.elevatorNo }"/>
<input type="hidden" name="sdate1" id="sdate1" value="${rmap.sdate1 }"/>
<input type="hidden" name="edate1" id="edate1" value="${rmap.edate1 }"/>
<input type="hidden" name="department" id="department" value="${rmap.department }"/>
<html:hidden property="genReport" styleId="genReport" />

 <bean:write name="showstr"/>
<br/><br/>

	<%int i=1; %>
<table class=tb  width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr class=wordtd_header>
	<td nowrap="nowrap"  style="text-align: center;">���</td>	
	<td nowrap="nowrap"  style="text-align: center;">����ʱ��</td>
	<td nowrap="nowrap"  style="text-align: center;">��������</td>
	<td nowrap="nowrap"  style="text-align: center;">��Ŀ����</td>
	<td nowrap="nowrap"  style="text-align: center;">��ͬ��</td>
	<td nowrap="nowrap"  style="text-align: center;">��װ��λ����</td>
	<td nowrap="nowrap"  style="text-align: center;">�������</td>
	<td nowrap="nowrap"  style="text-align: center;">��Ŀ����</td>
	<td nowrap="nowrap"  style="text-align: center;">���ݱ��</td>
	</tr>
	<logic:present name="maintenanceReportList">
	  <logic:iterate id="ele" name="maintenanceReportList">
	  <tr class="inputtd" align="center" height="20">
	  <td><%=i++ %></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="checkTime"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="department"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="projectName"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="salesContractNo"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="insCompanyName"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="checkNum"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="projectManager"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="elevatorNo"/></td>
	  </tr>
	  </logic:iterate>
	  	</logic:present>
	  	<logic:notPresent name="maintenanceReportList">
	  <tr class="noItems" align="center" height="20"><td colspan="9" >û�м�¼��ʾ��</td></tr>
	  </logic:notPresent>
</table>
		
</html:form>
