<%@ page contentType="text/html;charset=gb2312" %>
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
    <html:form action="/ProjectBaoYangAction.do?method=toSearchRecord">
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
            ${search.startDate}${search.endDate != '' ? '��' : ''}${search.endDate}���ݱ��������տ��ѯ
          </td>
        </tr>
      </table>
      
      <table id="reportTable" width="98%" border="0" cellpadding="0" class="tb" cellspacing="0" align="center">
        <tr style="height:25px;">
            <td class="wordtd_header" nowrap>��� </td>
            <td class="wordtd_header" nowrap>�տ�����</td>
            <td class="wordtd_header" nowrap>Ӧ�տ�����</td>
            <td class="wordtd_header" nowrap>ά����ͬ��</td>
            <td class="wordtd_header" nowrap>�׷���λ</td>
            <td class="wordtd_header" nowrap>�տ���</td>
            <td class="wordtd_header" nowrap>������־</td>
            <td class="wordtd_header" nowrap>�����ֲ�</td>
        </tr>    
        <logic:present name="reportList">
          <logic:iterate id="element" name="reportList" indexId="i">
            <tr style="height:25px;">
              <td nowrap align="center">${i+1}</td>
              <td nowrap align="center">${element.paragraphDate}</td>
              <td nowrap align="center">${element.preDate}</td>
              <td nowrap align="center">${element.maintContractNo}</td>
              <td nowrap align="center">${element.companyName}</td>
              <td nowrap align="right">${element.paragraphMoney}</td>
              <td nowrap align="center">${element.contractStatus=='XB'?'����':'��ǩ'}</td>
              <td nowrap align="center">${element.maintDivisionName}</td>
            </tr>
          </logic:iterate>
        </logic:present>
        <tr style="height:25px;">
          <td colspan="8">      
            &nbsp;&nbsp;&nbsp;&nbsp;ͳ�ƣ���¼��<b>${rowNums}</b>�����տ����ܼ�Ϊ��<b>${totalPrice}</b>��Ԫ��
          </td>
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