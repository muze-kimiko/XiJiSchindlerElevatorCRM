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
        <html:text property="property(ARF_JnlNo)" styleClass="default_input" />
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
    </tr>
    <tr>
      <td>
        &nbsp;&nbsp;               
        ƾ֤��
        :
      </td>
      <td>
        <html:text property="property(paragraphNo)" styleClass="default_input" />
      </td> 
      <td>  
        &nbsp;&nbsp;     
        ������
        :
      </td>
      <td>
        <html:text property="property(paragraphMoney)" styleClass="default_input" />
      </td>
      <td>
      &nbsp;&nbsp;     
        ��������
        :
      </td>
      <td><html:text property="property(paragraphDate)" size="12" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})" /></td>
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
  <table:table id="guiContractParagraphManage" name="contractParagraphManageList">
    <logic:iterate id="element" name="contractParagraphManageList">
      <table:define id="c_cb">
        <html:radio property="checkBoxList(ids)" styleId="ids" value="${element.jnlNo}" />
        <html:hidden name="element" property="jnlNo" />     
        <html:hidden name="element" property="submitType" />
        <html:hidden name="element" property="auditStatus" />
      </table:define>
      <table:define id="c_ARF_JnlNo">
      	<a href="<html:rewrite page='/contractParagraphManageAction.do'/>?method=toDisplayRecord&id=<bean:write name="element"  property="jnlNo"/>">
      		<bean:write name="element" property="ARF_JnlNo" />
      	</a>
      </table:define>
      <table:define id="c_preDate">
        <bean:write name="element" property="preDate" />
      </table:define>
       <table:define id="c_contractNo">
        <bean:write name="element" property="contractNo" />
      </table:define>
      <table:define id="c_contractType">
        <logic:match name="element" property="contractType" value="B">����</logic:match>
        <logic:match name="element" property="contractType" value="W">ά��</logic:match>
        <logic:match name="element" property="contractType" value="G">����</logic:match>
      </table:define>
      <table:define id="c_paragraphNo">
        <bean:write name="element" property="paragraphNo" />
      </table:define>
      <table:define id="c_paragraphMoney">
        <bean:write name="element" property="paragraphMoney" />
      </table:define>
      <table:define id="c_paragraphDate">
        <bean:write name="element" property="paragraphDate" />
      </table:define>
      <table:define id="c_rem">
        <bean:write name="element" property="rem" />
      </table:define>
      <table:define id="c_maintDivision">
        <bean:write name="element" property="maintDivision" />
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