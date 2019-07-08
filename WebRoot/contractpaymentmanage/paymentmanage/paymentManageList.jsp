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
        ��Ʊ��ˮ��
        :
      </td>
      <td>
        <html:text property="property(jnlNo)" styleClass="default_input" />
      </td>                 
      <td>  
        &nbsp;&nbsp;     
        ί�к�ͬ��
        :
      </td>
      <td>
        <html:text property="property(contractNo)" styleClass="default_input" />
      </td>
       <td>  
        &nbsp;&nbsp;     
        ί�е�λ
        :
      </td>
      <td>
        <html:text property="property(companyName)" styleClass="default_input" size="50" />
      </td> 
    </tr>
    <tr>
     <td>      
        ���ۺ�ͬ��
        :
      </td>
      <td>
        <html:text property="property(salesContractNo)" styleClass="default_input" />
      </td>
      <td>
      &nbsp;&nbsp; 
        �ύ��־
        :
      </td>
      <td>
        <html:select property="property(submitType)">
          <html:option value="">��ѡ��</html:option>
		  <html:option value="Y">���ύ</html:option>
		  <html:option value="N">δ�ύ</html:option>
        </html:select>
        </td>
      <td> 
        &nbsp;&nbsp;
        ����״̬
        :
      </td>
      <td>
        <html:select property="property(status)">
          <html:option value="">ȫ��</html:option>
		  <html:options collection="processStatusList" property="typeid" labelProperty="typename"/>
        </html:select>
      </td>
      </tr>
    <tr>
      <td>
        �����ֲ�
        :
      </td>
      <td>
        <html:select property="property(maintDivision)">
          <html:options collection="maintDivisionList" property="grcid" labelProperty="grcname"/>
        </html:select>
      </td> 
    </tr>
  </table>
  <br>
  <table:table id="guiPaymentManage" name="paymentManageList">
    <logic:iterate id="element" name="paymentManageList">
      <table:define id="c_cb">
        <html:radio property="checkBoxList(ids)" styleId="ids" value="${element.jnlNo}" />
        <html:hidden name="element" property="jnlNo" />     
        <html:hidden name="element" property="submitType" />
        <html:hidden property="tcjnlNo" value="${element.r1}"/>
      </table:define>
      <table:define id="c_jnlNo">
      	<a href="<html:rewrite page='/paymentManageAction.do'/>?method=toDisplayRecord&tcjnlNo='${element.r1}'&id=<bean:write name="element"  property="jnlNo"/>">
	    	<bean:write name="element" property="jnlNo" />
	    </a>
	  </table:define>								
      <table:define id="c_tcjnlNo">
        
          <bean:write name="element" property="r1" />
        
      </table:define>
	   <table:define id="c_entrustContractNo">
	    <bean:write name="element" property="entrustContractNo" />
	  </table:define>	 
	  <table:define id="c_companyId">
	    <bean:write name="element" property="companyId" />
	  </table:define>
	  <table:define id="c_paragraphNo">
	  <bean:write name="element" property="paragraphNo" />
	  </table:define>
	  <table:define id="c_paragraphMoney">
	  <bean:write name="element" property="paragraphMoney" />
	  </table:define>
	  <table:define id="c_debitMoney">
	  <bean:write name="element" property="debitMoney" />
	  </table:define>
	  <table:define id="c_paragraphDate">
	  <bean:write name="element" property="paragraphDate" />
	  </table:define> 	
      <table:define id="c_maintDivision">
      	<bean:write name="element" property="maintDivision" />
      </table:define>     
      <table:define id="c_rem">
      	<bean:write name="element" property="rem" />
      </table:define>   
      <table:define id="c_submitType">
        <logic:match name="element" property="submitType" value="Y">���ύ</logic:match>
        <logic:match name="element" property="submitType" value="N">δ�ύ</logic:match>
        <logic:match name="element" property="submitType" value="R">����</logic:match>
      </table:define>
      <table:define id="c_status">
      	<bean:write name="element" property="r2" />
      	<html:hidden name="element" property="r2" />
      </table:define>
      <table:define id="c_processName">
      	<bean:write name="element" property="processName" />
      	
      </table:define>
      <table:tr />
    </logic:iterate>
  </table:table>
</html:form>