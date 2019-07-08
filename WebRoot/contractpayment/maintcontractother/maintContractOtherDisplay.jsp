<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">

<br>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <title>XJSCRM</title>
</head>
<body>
  <html:errors/>
  <html:form action="/maintContractOtherAction.do?method=toUpdateRecord">
<logic:present name="masterhmap">

	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
		<tr>
			<td class="wordtd" width="15%">ά���ֲ���</td>
			<td><bean:write name="masterhmap" property="maintdivision"/></td>
			<td class="wordtd" width="15%">ά��վ��</td>
			<td><bean:write name="masterhmap" property="maintstation"/></td>
			<td class="wordtd" width="15%">��Ʊ���ƣ�</td>
			<td><bean:write name="masterhmap" property="invoicename"/></td>
		</tr>
		<tr>
			<td class="wordtd">ά����ͬ�ţ�</td>
			<td><bean:write name="masterhmap" property="maintcontractno"/></td>
			<td class="wordtd">ҵ����ܶ</td>
			<td><bean:write name="masterhmap" property="otherfee"/></td>
			<td class="wordtd">δ֧����� ��</td>
			<td><bean:write name="masterhmap" property="nototherfee"/></td>
		</tr>
	</table>
	<br/>
	<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
	  <b>&nbsp;ҵ���֧����ϸ</b>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
		<thead>
			<tr class="wordtd_header">
				<td width="15%">֧������</td>
				<td width="15%">֧�����</td>
				<td width="15%">¼����</td>
				<td width="15%">¼������</td>
				<td width="40%">��ע</td>
			</tr>
		</thead>
		<tfoot>
			<tr height="15"><td colspan="5"></td></tr>
		</tfoot>
		<tbody>
			<logic:present name="mcolist">
	          <logic:iterate id="pro" name="mcolist" >
	          	<tr>
					<td align="center"><bean:write name="pro" property="paydate"/></td>
					<td align="center"><bean:write name="pro" property="paymoney"/></td>
					<td align="center"><bean:write name="pro" property="operid"/></td>
					<td align="center"><bean:write name="pro" property="operdate"/></td>
					<td><bean:write name="pro" property="rem"/></td>
				</tr>
	          </logic:iterate>
	        </logic:present>
		</tbody>
	</table>
	<br/>

</logic:present>
  </html:form>
</body>