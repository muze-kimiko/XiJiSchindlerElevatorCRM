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
<html:form action="/companyPaymentReportAction.do?method=toSearchResults">
<input type="hidden" name="maintdivision" id="maintdivision" value="${rmap.maintdivision }"/>
<input type="hidden" name="contractno" id="contractno" value="${rmap.contractno }"/>
<input type="hidden" name="companyname" id="companyname" value="${rmap.companyname }"/>
<input type="hidden" name="paragraphdate1" id="paragraphdate1" value="${rmap.paragraphdate1 }"/>
<input type="hidden" name="paragraphdate2" id="paragraphdate2" value="${rmap.paragraphdate2 }"/>
<html:hidden property="genReport" styleId="genReport" />

	<%int i=1; %>
<table class=tb  width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr class=wordtd_header>
	<td nowrap="nowrap"  style="text-align: center;" rowspan="2">���</td>	
	<td nowrap="nowrap"  style="text-align: center;" rowspan="2">��������</td>
	<td nowrap="nowrap"  style="text-align: center;" rowspan="2">ί�к�ͬ��</td>
	<td nowrap="nowrap"  style="text-align: center;" rowspan="2">ί�е�λ����</td>
	<td nowrap="nowrap"  style="text-align: center;" rowspan="2">ί��̨��</td>
	<td nowrap="nowrap"  style="text-align: center;" rowspan="2">ƾ֤��</td>
	<td nowrap="nowrap"  style="text-align: center;" rowspan="2">������</td>
	<td nowrap="nowrap"  style="text-align: center;" rowspan="2">�ۿ���</td>
	<td nowrap="nowrap"  style="text-align: center;" rowspan="2">��������</td>
	<td nowrap="nowrap"  style="text-align: center;" rowspan="2">���������������</td>
	<td nowrap="nowrap"  style="text-align: center;" colspan="2">���лطý�����</td>	
	<td nowrap="nowrap"  style="text-align: center;" colspan="2">���߹�����</td>
	<td nowrap="nowrap"  style="text-align: center;" rowspan="2">�ɼ��˻��������</td>
	<td nowrap="nowrap"  style="text-align: center;" rowspan="2">�ֲ����������</td>
	<td nowrap="nowrap"  style="text-align: center;" rowspan="2">�ܲ���������</td>
	<td nowrap="nowrap"  style="text-align: center;" rowspan="2">ά�����ʼ���</td>
	</tr>
	<tr class=wordtd_header>
	<td nowrap="nowrap"  style="text-align: center;">������������ĸ����������</td>
	<td nowrap="nowrap"  style="text-align: center;">��Ǽ�����������ĸ����������</td>
	<td nowrap="nowrap"  style="text-align: center;">�������������Ͷ�ߴ���</td>
	<td nowrap="nowrap"  style="text-align: center;">��Ǽ������������Ͷ�ߴ���</td>
	</tr>
	
	<logic:present name="companyPaymentReportList" >
	  <logic:iterate id="ele" name="companyPaymentReportList">
	  <tr class="inputtd" align="center" height="20">
	  <td><%=i++ %></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="MaintDivision"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="EntrustContractNo"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="CompanyName"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="elenum"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="ParagraphNo"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="ParagraphMoney"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="DebitMoney"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="ParagraphDate"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="BydAuditEvaluate"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="HfAuditNum"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="HfAuditNum2"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="RxAuditNum"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="RxAuditNum2"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="JjthAuditEvaluate"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="FbzAuditEvaluate"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="ZbzAuditRem"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="CustLevel"/></td>
	  </tr>
	  </logic:iterate>
	</logic:present>
	<logic:notPresent name="companyPaymentReportList">
	  <tr class="noItems" align="center" height="20">
	  	<td colspan="10" >
	  		<bean:write name="showinfostr"/>
	  	</td>
	  </tr>
	</logic:notPresent>
</table>
<br/>
		
</html:form>
