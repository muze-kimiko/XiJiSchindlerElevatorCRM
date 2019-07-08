<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
  <script language="javascript" src="<html:rewrite forward='pageJS'/>"></script>
  <link href="/XJSCRM/common/css/bb.css" rel="stylesheet" type="text/css">
  <script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>

<br>
<html:form action="/wbdtReportAction.do?method=toSearchResults">

<input type="hidden" name="maintdivision" id="maintdivision" value="${rmap.maintdivision }"/>
<input type="hidden" name="maintstation" id="maintstation" value="${rmap.maintstation }"/>
<input type="hidden" name="maintcontractno" id="maintcontractno" value="${rmap.maintcontractno }"/>
<input type="hidden" name="salescontractno" id="salescontractno" value="${rmap.salescontractno }"/>
<input type="hidden" name="elevatorno" id="elevatorno" value="${rmap.elevatorno }"/>
<input type="hidden" name="sdate1" id="sdate1" value="${rmap.sdate1 }"/>
<input type="hidden" name="edate1" id="edate1" value="${rmap.edate1 }"/>
<html:hidden property="genReport" styleId="genReport" />

	<%int i=1; %>
<table class=tb  width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr class=wordtd_header>
		<td nowrap="nowrap"  style="text-align: center;">���</td>
		<td nowrap="nowrap"  style="text-align: center;">ά���ֲ�</td>
		<td nowrap="nowrap"  style="text-align: center;">ά��վ</td>
		<td nowrap="nowrap"  style="text-align: center;">ά����ͬ��</td>
		<td nowrap="nowrap"  style="text-align: center;">���ۺ�ͬ��</td>
		<td nowrap="nowrap"  style="text-align: center;">���ݱ��</td>
		<td nowrap="nowrap"  style="text-align: center;">��������</td>
		<td nowrap="nowrap"  style="text-align: center;">��վ</td>
		<td nowrap="nowrap"  style="text-align: center;">�����ѽ��</td>
		<td nowrap="nowrap"  style="text-align: center;">������ʽ</td>
		<td nowrap="nowrap"  style="text-align: center;">�����ʽ</td>
		<td nowrap="nowrap"  style="text-align: center;">�˱����ͨ������</td>
		<td nowrap="nowrap"  style="text-align: center;">�Ƿ�ۿ�</td>
		<td nowrap="nowrap"  style="text-align: center;">�ۿ���</td>
	</tr>
	
	<logic:present name="maintenanceWbdtReportList" >
	  <logic:iterate id="ele" name="maintenanceWbdtReportList" indexId="elexh">
	  <tr class="inputtd" align="center" height="20">
	  <td>${elexh+1 }</td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="comname"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="storagename"/></td>
	  <td nowrap="nowrap"  style="text-align: center;">
	  	<a href="<html:rewrite page='/maintContractAction.do'/>?method=toDisplayRecord&typejsp=Yes&id=<bean:write name="ele"  property="billno"/>" target="_blnk"><bean:write name="ele" property="maintcontractno"/></a>
	  </td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="salescontractno"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="elevatorno"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="elevatortypename"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="floorstage"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="bymoney"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="mainmodename"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="bgmainmode"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="auditdate"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="ischargename"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="kkmoney"/></td>

	  </tr>
	  </logic:iterate>
	</logic:present>
	<logic:notPresent name="maintenanceWbdtReportList">
	  <tr class="noItems" align="center" height="20">
	  	<td colspan="14" >
	  		<bean:write name="showinfostr"/>
	  	</td>
	  </tr>
	</logic:notPresent>
</table>
<br/>
<b>
&nbsp;�ϼ�&nbsp;&nbsp;&nbsp;&nbsp;�ۿ���(Ԫ):<bean:write name="totalhmap" property="totalmoney"/>
&nbsp;&nbsp;���ۿ���(Ԫ)��<bean:write name="totalhmap" property="totalnomoney"/>
</b>
<br/>
		
</html:form>

