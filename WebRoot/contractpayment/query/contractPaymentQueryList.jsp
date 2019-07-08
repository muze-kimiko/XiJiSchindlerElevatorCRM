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
        &nbsp;&nbsp;     
        ��ͬ��
        :
      </td>
      <td>
        <html:text property="property(contractNo)" styleClass="default_input" />
      </td>
      <td>
        &nbsp;&nbsp;               
        ��ͬ����
        :
      </td>
      <td>
        <html:select property="property(contractType)" styleClass="default_input" >
          <html:option value="">ȫ��</html:option>
          <html:option value="B">����</html:option>
		  <html:option value="W">ά��</html:option>
		  <html:option value="G">����</html:option>
        </html:select>
      </td>
   
      <td>
        &nbsp;&nbsp;               
        �׷���λ
        :
      </td>
      <td>
        <html:text property="property(companyId)" styleClass="default_input" size="50" />
      </td> 
     </tr>
    <tr>
      <td>  
        &nbsp;&nbsp;     
        ��ͬ���
        :
      </td>
      <td>
        <html:text property="property(contractTotal)" styleClass="default_input" />
      </td>
    	<td> 
        &nbsp;&nbsp;
        �����ֲ�
        :
      </td>
      <td>
        <html:select property="property(maintDivision)" styleClass="default_input" >
          <html:options collection="maintDivisionList" property="grcid" labelProperty="grcname"/>
        </html:select>
      </td> 
      <td>
        &nbsp;&nbsp;
        ����ά��վ
        :
      </td> 
      <td>
         <html:text property="property(maintStation)"></html:text>
      </td>
    </tr>
  </table>
  <br>
  <table:table id="guiContractPaymentQuery" name="contractPaymentQueryList">
    <logic:iterate id="element" name="contractPaymentQueryList">
      <table:define id="c_cb">
        <html:radio property="checkBoxList(ids)" styleId="ids" value="${element.billno}" />
        <html:hidden name="element" property="billNo" />     
      </table:define>
      <table:define id="c_contractNo">
      	<a href="<html:rewrite page='/contractPaymentQueryAction.do'/>?method=toDisplayRecord&id=<bean:write name="element"  property="billno"/>">
      		<bean:write name="element" property="contractNo" />
      	</a>
      </table:define>
      <table:define id="c_contractType">
        <logic:match name="element" property="contractType" value="B">����</logic:match>
        <logic:match name="element" property="contractType" value="W">ά��</logic:match>
        <logic:match name="element" property="contractType" value="G">����</logic:match>
      </table:define>
      <table:define id="c_companyId">
        <bean:write name="element" property="companyId" />
      </table:define>
      <table:define id="c_contractTotal">
        <bean:write name="element" property="contractTotal" />
      </table:define>
      <table:define id="c_preMoney">
        <bean:write name="element" property="preMoney" />
      </table:define>
      <table:define id="c_invoiceMoney">
        <bean:write name="element" property="invoiceMoney" />
      </table:define>
      <table:define id="c_paragraphMoney">
        <bean:write name="element" property="paragraphMoney" />
      </table:define>
      <table:define id="c_arrearsMoney">
        <bean:write name="element" property="arrearsMoney" />
      </table:define>
      <table:define id="c_maintDivision">
        <bean:write name="element" property="maintDivision" />
      </table:define>
      <table:define id="c_maintStation">
        <bean:write name="element" property="maintStation" />
      </table:define>
      
      <table:tr />
    </logic:iterate>
  </table:table>
  <table>
  	<tr>
  		<td>&nbsp;&nbsp;��ͬӦ�տ��ܶ</td><td><bean:write name="preTotal"/></td>
  		<td>&nbsp;&nbsp;��ͬ��Ʊ�ܶ</td><td><bean:write name="invoiceTotal"/></td>
  		<td>&nbsp;&nbsp;��ͬ�����ܶ</td><td><bean:write name="paragraphTotal"/></td>
  		<td>&nbsp;&nbsp;�ѿ�Ʊδ�����</td><td><bean:write name="arraearTotal"/></td>
  		<td>&nbsp;&nbsp;����Ƿ���</td><td><bean:write name="pre"/></td>
  	</tr>
  </table>
</html:form>