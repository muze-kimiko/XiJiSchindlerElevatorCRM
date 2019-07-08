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


<br>
<html:form action="/zbReportAction.do?method=toSearchResults">

<input type="hidden" name="maintdivision" id="maintdivision" value="${rmap.maintdivision }"/>
<input type="hidden" name="maintstation" id="maintstation" value="${rmap.maintstation }"/>
<input type="hidden" name="contractterms" id="contractterms" value="${rmap.contractterms }"/>

<html:hidden property="genReport" styleId="genReport" />
	<%int i=1; %>
<table class=tb  width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr class=wordtd_header>
	<td nowrap="nowrap"  style="text-align: center;">���</td>	
	<td nowrap="nowrap"  style="text-align: center;">ά���ֲ�</td>
	<td nowrap="nowrap"  style="text-align: center;">ά��վ</td>
	<td nowrap="nowrap"  style="text-align: center;">ά����ͬ��</td>
	<td nowrap="nowrap"  width="300px" style="text-align: center;">���ۺ�ͬ��</td>
	<td nowrap="nowrap"  width="300px" style="text-align: center;">�׷�����</td>
	<td nowrap="nowrap"  width="300px" style="text-align: center;">ʹ�õ�λ����</td>
	<td nowrap="nowrap"  style="text-align: center;">ֱ��̨��</td>
	<td nowrap="nowrap"  style="text-align: center;">����̨��</td>
	<td nowrap="nowrap"  style="text-align: center;">�ϼ�̨��</td>
	<td nowrap="nowrap"  style="text-align: center;">��ͬ��ʼ����</td>
	<td nowrap="nowrap"  style="text-align: center;">��ͬ��������</td>
	<td nowrap="nowrap"  style="text-align: center;">������ʽ</td>
	<td nowrap="nowrap"  style="text-align: center;">��ͬ�ܶ�</td>
	<td nowrap="nowrap"  width="300px" style="text-align: center;">��ͬ�������������</td>
	<td nowrap="nowrap"  width="300px" style="text-align: center;">��ע</td>
	<td nowrap="nowrap"  style="text-align: center;">���ͬ���</td>
	<td nowrap="nowrap"  style="text-align: center;">��ͬ���ͨ������</td>
	</tr>
	
	<logic:present name="maintenanceReportList" >
	  <logic:iterate id="ele" name="maintenanceReportList">
	  <tr class="inputtd" align="center" height="20">
	  <td><%=i++ %></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="ComName"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="StorageName"/></td>
	  <td nowrap="nowrap"  style="text-align: center;">
	  	<a href="<html:rewrite page='/maintContractAction.do'/>?method=toDisplayRecord&typejsp=Yes&id=<bean:write name="ele"  property="billno"/>" target="_blnk"><bean:write name="ele" property="MaintContractNo"/></a>
	  </td>
	  <td width="300px" style="text-align: center;word-wrap:break-word;"><bean:write name="ele" property="SalesContractNo"/></td>
	  <td width="300px" style="text-align: center;word-wrap:break-word;"><bean:write name="ele" property="CompanyName"/></td>
	  <td width="300px" style="text-align: center;word-wrap:break-word;"><bean:write name="ele" property="MaintAddress"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="tconnum"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="fconnum"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="tfconnum"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="ContractSDate"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="ContractEDate"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="mainmode"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="contracttotal"/></td>
	  <td width="300px" style="text-align: center;word-wrap:break-word;"><bean:write name="ele" property="ContractTerms"/></td>
	  <td width="300px" style="text-align: center;word-wrap:break-word;"><bean:write name="ele" property="rem"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="yearcontracttotal"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="auditDate"/></td>
	  </tr>
	  </logic:iterate>
	</logic:present>
	<logic:notPresent name="maintenanceReportList">
	  <tr class="noItems" align="center" height="20">
	  	<td colspan="17" >
	  		<bean:write name="showinfostr"/>
	  	</td>
	  </tr>
	</logic:notPresent>
</table>
<br/>
<b>
&nbsp;�ڱ���̨��:<bean:write name="totalhmap" property="totalnum"/>
&nbsp;ֱ����̨��:<bean:write name="totalhmap" property="totalnumt"/>
&nbsp;������̨��:<bean:write name="totalhmap" property="totalnumf"/>
&nbsp;�ڱ��ܲ�վ��:<bean:write name="totalhmap" property="totalfloornum"/>
&nbsp;�г���̨��:<bean:write name="totalhmap" property="paidtotalnum"/>
&nbsp;�Ᵽ��̨��:<bean:write name="totalhmap" property="freetotalnum"/>
</b>
<br/>
		
</html:form>

