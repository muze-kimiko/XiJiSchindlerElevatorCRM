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
        Ӧ�տ���ˮ��
        :
      </td>
      <td>
        <html:text property="property(jnlNo)" styleClass="default_input" />
      </td>                 
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
        �ύ��־
        :
      </td>
      <td>
        <html:select property="property(submitType)" styleClass="default_input" >
          <html:option value="">ȫ��</html:option>
		  <html:option value="Y">���ύ</html:option>
		  <html:option value="N">δ�ύ</html:option>
		  <html:option value="R">����</html:option>
        </html:select>
      </td>
       <td>  
        &nbsp;&nbsp;     
        �׷���λ
        :
      </td>
      <td>
        <html:text property="property(companyId)" styleClass="default_input" size="30" />
      </td> 
    </tr>
    <tr>
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
        �Ƿ�Ʊ
        :
      </td>
      <td>
        <html:select property="property(isInvoice)" styleClass="default_input" >
          <html:option value="">ȫ��</html:option>
		  <html:option value="Y">�ѿ�Ʊ</html:option>
		  <html:option value="N">δ��Ʊ</html:option>
        </html:select>
      </td> 
      <td> 
        &nbsp;&nbsp;
        �Ƿ�����
        :
      </td>
      <td>
        <html:select property="property(isParagraph)" styleClass="default_input" >
          <html:option value="">ȫ��</html:option>
		  <html:option value="Y">������</html:option>
		  <html:option value="N">δ����</html:option>
        </html:select>
      </td> 
      <td> 
        &nbsp;&nbsp;
        ��Ʊ����
        :
      </td>
      <td>
        <html:text property="property(invoiceName)" styleClass="default_input" size="30" />
      </td>
   </tr>
   <tr> 
      <td> 
        &nbsp;&nbsp;
        ���״̬
        :
      </td>
      <td>
        <html:select property="property(auditStatus)" styleClass="default_input" >
          <html:option value="">ȫ��</html:option>
		  <html:option value="Y">�����</html:option>
		  <html:option value="N">δ���</html:option>
        </html:select>
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
        <html:select property="property(maintStation)" styleClass="default_input" >
          <html:options collection="stationList" property="storageid" labelProperty="storagename"/>
        </html:select>
      </td> 
    </tr>
  </table>
  <br>
  <table:table id="guiProContractARFeeMaster" name="proContractArfeeMasterList">
    <logic:iterate id="element" name="proContractArfeeMasterList">
      <table:define id="c_cb">
        <html:radio property="checkBoxList(ids)" styleId="ids" value="${element.jnlNo}" />
        <html:hidden name="element" property="jnlNo" />     
        <html:hidden name="element" property="submitType" />
      </table:define>
      <table:define id="c_jnlNo">
        <a href="<html:rewrite page='/proContractArfeeMasterAction.do'/>?method=toDisplayRecord&id=<bean:write name="element"  property="jnlNo"/>">
          <bean:write name="element" property="jnlNo" />
	      <html:hidden name="element" property="preDate" />
	      <html:hidden name="element" property="notParagraph"/>
        </a>
      </table:define>
      <table:define id="c_ARF_JnlNo">
      	<bean:write name="element" property="ARF_JnlNo" />
      </table:define>
      <table:define id="c_contractType">
      	<logic:match name="element" property="contractType" value="B">����</logic:match>
        <logic:match name="element" property="contractType" value="W">ά��</logic:match>
        <logic:match name="element" property="contractType" value="G">����</logic:match>
      </table:define>
      <table:define id="c_contractNo">
        <bean:write name="element" property="contractNo" />
      </table:define>
      <table:define id="c_companyId">
        <bean:write name="element" property="companyId" />
      </table:define>
      <table:define id="c_preDate">
        <bean:write name="element" property="preDate" />
      </table:define>
      <table:define id="c_preMoney">
        <bean:write name="element" property="preMoney" />
      </table:define>
      <table:define id="c_isInvoice">
      <logic:present name="element" property="isInvoice">
      	<logic:match name="element" property="isInvoice" value="Y">�ѿ�Ʊ</logic:match>
        <logic:match name="element" property="isInvoice" value="N">δ��Ʊ</logic:match>
       </logic:present>
      </table:define>
      <table:define id="c_notInvoice">
        <bean:write name="element" property="notInvoice" />
      </table:define>
      <table:define id="c_isParagraph">
      	<logic:present name="element" property="isParagraph">
      	<logic:match name="element" property="isParagraph" value="Y">������</logic:match>
        <logic:match name="element" property="isParagraph" value="N">δ����</logic:match>
       </logic:present>
      </table:define>
      <table:define id="c_notParagraph">
      	<bean:write name="element" property="notParagraph" />
      </table:define>
      <table:define id="c_maintDivision">
      	<bean:write name="element" property="maintDivision" />
      </table:define>
      <table:define id="c_maintStation">
      	<bean:write name="element" property="maintStation" />
      </table:define>
      <table:define id="c_submitType">
        <logic:match name="element" property="submitType" value="Y">���ύ</logic:match>
        <logic:match name="element" property="submitType" value="N">δ�ύ</logic:match>
        <logic:match name="element" property="submitType" value="R">����</logic:match>
      </table:define>
      <table:define id="c_auditStatus">
        <logic:match name="element" property="auditStatus" value="Y">�����</logic:match>
        <logic:match name="element" property="auditStatus" value="N">δ���</logic:match>
      </table:define>
      <table:define id="c_warnRem">
        <bean:write name="element" property="warnRem" />
      </table:define>
      <table:tr />
    </logic:iterate>
  </table:table>
</html:form>