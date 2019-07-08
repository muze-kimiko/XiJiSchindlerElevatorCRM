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
<html:form action="/bytlReportAction.do?method=toSearchResults">

<input type="hidden" name="maintdivision" id="maintdivision" value="${rmap.maintdivision }"/>
<input type="hidden" name="maintstation" id="maintstation" value="${rmap.maintstation }"/>
<input type="hidden" name="maintcontractno" id="maintcontractno" value="${rmap.maintcontractno }"/>
<input type="hidden" name="elevatorno" id="elevatorno" value="${rmap.elevatorno }"/>
<input type="hidden" name="salescontractno" id="salescontractno" value="${rmap.salescontractno }"/>
<input type="hidden" name="maintpersonnel" id="maintpersonnel" value="${rmap.maintpersonnel }"/>
<%-- 
<input type="hidden" name="sdate1" id="sdate1" value="${rmap.sdate1 }"/>
<input type="hidden" name="edate1" id="edate1" value="${rmap.edate1 }"/>
--%>
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
		<td nowrap="nowrap"  style="text-align: center;">��վϵ��</td>
		<td nowrap="nowrap"  style="text-align: center;">��������</td>
		<td nowrap="nowrap"  style="text-align: center;">����ϵ��</td>
		<td nowrap="nowrap"  style="text-align: center;">����</td>
		<td nowrap="nowrap"  style="text-align: center;">����ϵ��</td>
		
		<logic:present name="ElevatorNatureList" >
		<logic:iterate id="enl" name="ElevatorNatureList">
		<%-- background-color: yellow; --%>
			<td nowrap="nowrap" style="text-align: center;">${enl.pullname}</td>
		</logic:iterate>
		</logic:present>
		
		<td nowrap="nowrap"  style="text-align: center;">̨��</td>
		<td nowrap="nowrap"  style="text-align: center;">̨��ϵ��</td>
		<td nowrap="nowrap"  style="text-align: center;">̨����(Ԫ)</td>
		<td nowrap="nowrap"  style="text-align: center;">ά�������֤��</td>
		<td nowrap="nowrap"  style="text-align: center;">ά����</td>
		<td nowrap="nowrap"  style="text-align: center;">�����Ƿ����</td>
		<td nowrap="nowrap"  style="text-align: center;">�Ƿ�̨����</td>
		<%-- td nowrap="nowrap"  style="text-align: center;">�´�����</td--%>
	</tr>
	
	<logic:present name="maintenanceBytlReportList" >
	  <logic:iterate id="ele" name="maintenanceBytlReportList" indexId="elexh">
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
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="floorstagexs"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="seriesidname"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="seriesidxs"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="weight"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="weightxs"/></td>
		
	  <logic:present name="ElevatorNatureList" >
		<logic:iterate id="enl2" name="ElevatorNatureList">
			<logic:equal name="enl2" property="id.pullid" value="${ele.elevatornature }">
				<td nowrap="nowrap"  style="text-align: center;">${ele.elevatornaturexs }</td>
			</logic:equal>
			<logic:notEqual name="enl2" property="id.pullid" value="${ele.elevatornature }">
				<td nowrap="nowrap"  style="text-align: center;">&nbsp;</td>
			</logic:notEqual>
		</logic:iterate>
		</logic:present>
		
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="tlnum"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="tlxs"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="nummoney"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="idcardno"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="maintpersonnelname"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="transfercomplete"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="r4"/></td>
	  <%--td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="tasksubdate"/></td--%>
	  </tr>
	  </logic:iterate>
	</logic:present>
	<logic:notPresent name="maintenanceBytlReportList">
	  <tr class="noItems" align="center" height="20">
	  	<td colspan="${colnum }" >
	  		<bean:write name="showinfostr"/>
	  	</td>
	  </tr>
	</logic:notPresent>
</table>
<br/>
<b>
&nbsp;̨�����ϼ�(Ԫ):<bean:write name="totalhmap" property="totalmoney"/>
</b>
<br/>
		
</html:form>

