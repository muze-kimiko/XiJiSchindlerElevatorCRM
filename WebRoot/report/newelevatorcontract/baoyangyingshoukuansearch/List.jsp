<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<%-- <script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script> --%>

<html>
  <head>
    <title></title>
  </head>
  <body>
    <html:form action="/ProjectBaoYangYingShouKuanSearchAction.do?method=toSearchRecord">
      <html:hidden property="property(genReport)" styleId="genReport" value="Y"/>
      <html:hidden property="property(startDate)" styleId="startDate" value="${search.startDate}"/>
      <html:hidden property="property(endDate)" styleId="endDate" value="${search.endDate}"/>
      <html:hidden property="property(maintContractNo)" styleId="maintContractNo" value="${search.maintContractNo}"/>
      <html:hidden property="property(contractStatus)" styleId="contractStatus" value="${search.contractStatus}"/>
      <html:hidden property="property(companyName)" styleId="companyName" value="${search.companyName}"/>
      <html:hidden property="property(maintDivision)" styleId="maintDivision" value="${search.maintDivision}"/>

      <table width="98%"  border="0" cellpadding="0" cellspacing="0" align="center">
        <tr>
          <td align="center" height="50" style="font-size: 15px; color: blue;">      
            ${search.startDate}${search.endDate!=''?'��':''}${search.endDate}���ݱ����տ��ѯ
          </td>
        </tr>
      </table>
      
      <table id="reportTable" width="98%" border="0" cellpadding="0" class="tb" cellspacing="0" align="center">
        <tr style="height:25px;">
           <td class="th" nowrap>���</td>
		   <td class="th" nowrap>��ͬ��</td>
		   <td class="th" nowrap>�׷���λ</td>
		   <td class="th" nowrap>Ӧ������</td>
		   <td class="th" nowrap>Ӧ�ս��</td>
		   <td class="th" nowrap>�ѿ�Ʊ���</td>
		   <td class="th" nowrap>δ��Ʊ���</td>   
		   <td class="th" nowrap>�ѿ�Ʊ���տ���</td>   
		   <td class="th" nowrap>�ѿ�Ʊ����Ƿ����</td>   
		   <td class="th" nowrap>δ��Ʊ����Ƿ����</td>     
		   <td class="th" nowrap>�ѿ�Ʊ��Ƿ����</td>
		   <td class="th" nowrap>����ά���ֲ�</td> 
        </tr>    
        <logic:present name="reportList">
          <logic:iterate id="element" name="reportList" indexId="a" >
            <tr style="height:25px;">
            <td nowrap align="center">${a+1}</td>  		
	  		<td nowrap align="center"><bean:write name="element" property="contractid"/></td>  		
	  		<td nowrap align="center"><bean:write name="element" property="custname"/></td>
	  		<td nowrap align="center"><bean:write name="element" property="predate"/></td>		 		
	  		<td nowrap align="center"><bean:write name="element" property="premoney"/></td>
	  		<td nowrap align="center"><bean:write name="element" property="nowfee"/></td>
	  		<td nowrap align="center"><bean:write name="element" property="nonowfee"/></td>
	  		<td nowrap align="center"><bean:write name="element" property="billatm"/></td>
	  		<td nowrap align="center"><bean:write name="element" property="billnoatm"/></td> 
	  		<td nowrap align="center"><bean:write name="element" property="nobillatm"/></td>
	  		<td nowrap align="center"><bean:write name="element" property="nobillnoatm"/></td>
	  		<td nowrap align="center"><bean:write name="element" property="grcname"/></td>
            </tr>
          </logic:iterate>
        </logic:present>
        <tr style="height:25px;">
          <td colspan="12">      
         <div height="100">${sj }</div>
        </tr>
      </table>  
      <br> 
      <div align="center">
        <input type="button" name="toExcelRecord" value="����Excel" style="padding-top: 4px;" onclick="this.form.submit();"/>&nbsp;
        <input type="button" name="close" value="&nbsp;�ر�&nbsp;" style="padding-top: 4px;" onclick="javascript:window.close()";>
      </div>
      
      <br>

    </html:form>
  </body>
</html>