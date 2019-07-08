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
        ��Ʊ��ˮ��
        :
      </td>
      <td>
        <html:text property="property(jnlNo)" styleClass="default_input" />
      </td>                 
      <td>
      &nbsp;&nbsp;      
        Ӧ�տ���ˮ��
        :
      </td>
      <td>
        <html:text property="property(ARF_JnlNo)" styleClass="default_input" />
      </td>                 
      <td>  
        &nbsp;&nbsp;     
        ��Ʊ��
        :
      </td>
      <td>
        <html:text property="property(invoiceNo)" styleClass="default_input" />
      </td>           
      <td>
        &nbsp;&nbsp;               
        ��Ʊ����
        :
      </td>
      <td>
        <html:select property="property(invoiceType)" styleClass="default_input" >
          <html:option value="">ȫ��</html:option>
          <html:options collection="invoiceTypeList" property="inTypeId" labelProperty="inTypeName"/>
        </html:select>
      </td> 
    </tr>
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
        �Ƿ��˲�Ʊ
        :
      </td>
      <td>
        <html:select property="property(istbp)" styleClass="default_input" >
          <html:option value="">ȫ��</html:option>
		  <html:option value="TP">��Ʊ</html:option>
		  <html:option value="BP">��Ʊ</html:option>
        </html:select>
      </td> 
    </tr>
    <tr>
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
    </tr>
  </table>
  <br>
  <table:table id="guiContractInvoiceManage" name="contractInvoiceManageList">
    <logic:iterate id="element" name="contractInvoiceManageList">
      <table:define id="c_cb">
        <html:radio property="checkBoxList(ids)" styleId="ids" value="${element.jnlNo}" />
        <html:hidden name="element" property="jnlNo" />     
        <html:hidden name="element" property="submitType" />
        <html:hidden name="element" property="invoiceNo" />
        <html:hidden name="element" property="invoiceMoney" />
        <html:hidden name="element" property="auditStatus" />
        <html:hidden name="element" property="istbp" />
      </table:define>
     <%--  <table:define id="c_jnlNo">
        <bean:write name="element" property="jnlNo" />
      </table:define> --%>
    <table:define id="c_jnlNo">
     <a href="<html:rewrite page='/contractInvoiceManageAction.do'/>?method=toDisplayRecord&id=<bean:write name="element"  property="jnlNo"/>">
        <bean:write name="element" property="jnlNo" />
        </a>
      </table:define> 
      <table:define id="c_ARF_JnlNo">
      		<bean:write name="element" property="ARF_JnlNo" />
    <%--   	 <a href="<html:rewrite page='/contractInvoiceManageAction.do'/>?method=toDisplayRecord&id=<bean:write name="element"  property="jnlNo"/>">
      	</a> --%>
      </table:define>
      <table:define id="c_contractNo">
        <bean:write name="element" property="contractNo" />
      </table:define>
      <table:define id="c_contractType">
        <logic:match name="element" property="contractType" value="B">����</logic:match>
        <logic:match name="element" property="contractType" value="W">ά��</logic:match>
        <logic:match name="element" property="contractType" value="G">����</logic:match>
      </table:define>
      <table:define id="c_invoiceNo">
        <bean:write name="element" property="invoiceNo" />
      </table:define>
      <table:define id="c_invoiceDate">
        <bean:write name="element" property="invoiceDate" />
      </table:define>
      <table:define id="c_invoiceType">
        <bean:write name="element" property="invoiceType" />
      </table:define>
      <table:define id="c_invoiceMoney">
        <bean:write name="element" property="invoiceMoney" />
      </table:define>
      <table:define id="c_rem">
        <bean:write name="element" property="rem" />
      </table:define>
      <table:define id="c_maintDivision">
        <bean:write name="element" property="maintDivision" />
      </table:define>
      <table:define id="c_istbp">
      <logic:present name="element" property="istbp">
        <logic:match name="element" property="istbp" value="TP">��Ʊ</logic:match>
        <logic:match name="element" property="istbp" value="BP">��Ʊ</logic:match>
        <logic:match name="element" property="istbp" value="CX">����</logic:match>
       </logic:present>
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
      <table:tr />
    </logic:iterate>
  </table:table>
</html:form>