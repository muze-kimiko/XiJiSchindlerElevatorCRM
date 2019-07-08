<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<html:errors />
<br>
<html:form action="/ServeTable.do">
<html:hidden property="property(genReport)" styleId="genReport" />
  <table>
    <tr>
	    <td>
			��ͬ��ˮ��:
		</td>
		<td>
			<html:text property="property(billno)" styleClass="default_input" />
		</td>
		<td>
		&nbsp;&nbsp;��ͬ��:
		</td>
		<td>
			<html:text property="property(entrustContractNo)" styleClass="default_input" />
		</td>
		 <td>      
       &nbsp;&nbsp;���ۺ�ͬ��
        :
      </td>
      <td>
        <html:text property="property(salesContractNo)" styleClass="default_input" />
      </td>
    	<td> 
        &nbsp;&nbsp;�����ֲ�:
      </td>
      <td>
        <html:select property="property(maintDivision)">
          <html:options collection="maintDivisionList" property="grcid" labelProperty="grcname"/>
        </html:select>
      </td> 
    </tr>
  </table>
  <br>
  <table:table id="guiAccountsPayableManageNext" name="accountsPayableManageNextList">
    <logic:iterate id="element" name="accountsPayableManageNextList">
     <table:define id="c_cb">
		<bean:define id="billno" name="element" property="billno" />
			<html:radio property="checkBoxList(ids)" styleId="ids" value="<%=billno.toString()%>" />
		</table:define>
		<table:define id="c_billno">
			<bean:write name="element" property="billno" />
		</table:define>
		<table:define id="c_entrustContractNo">
			<bean:write name="element" property="entrustContractNo" />
		</table:define>
		<table:define id="c_num">
			<bean:write name="element" property="num" />
		</table:define>
		<table:define id="c_maintDivision">
			<bean:write name="element" property="maintDivision" />
		</table:define>
		<table:define id="c_companyID2">
			<bean:write name="element" property="companyID" />
		</table:define>
      <table:tr />
    </logic:iterate>
  </table:table>
</html:form>