<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<html:errors />
<br>

<html:form action="/ServeTable.do">
<html:hidden property="property(genReport)" styleId="genReport" />
  <table>
    <tr>
      <td>      
                     ��ˮ��
        :
      </td>
      <td>
        <html:text property="property(billNo)" styleClass="default_input" />
      </td>    
    <td>     &nbsp;&nbsp;   
             ά����ͬ��
        :
      </td>
      <td>
        <html:text property="property(maintContractNo)" styleClass="default_input" />
      </td>              
      <td>
        &nbsp;&nbsp;               
                    ί�к�ͬ��
        :
      </td>
      <td>
        <html:text property="property(entrustContractNo)" styleClass="default_input" />
      </td> 
      <td> 
        &nbsp;&nbsp;      
                   �ҷ���λ
        :
      </td>
      <td>
        <html:text property="property(companyName)" size="35" styleClass="default_input" />
      </td>           
    </tr>
    <tr>
    <td>     
                    ��ͬ����
        :
      </td>
      <td>
        <html:select property="property(contractNatureOf)">
          <html:option value="">ȫ��</html:option>
          <html:options collection="contractNatureOfList" property="id.pullid" labelProperty="pullname"/>
        </html:select>
      </td>
      <td>      
      &nbsp;&nbsp;  
        <bean:message key="maintContract.maintDivision" />
        :
      </td>
      <td>
        <html:select property="property(maintDivision)">
		  <html:options collection="maintDivisionList" property="grcid" labelProperty="grcname"/>
        </html:select>
      </td> 
      <td>
      &nbsp;&nbsp;  
                     ������
        :
      </td>
      <td>
        <html:text property="property(attn)" styleClass="default_input" />
      </td>   
      <td>&nbsp;</td>
      <td>&nbsp;</td> 
    </tr>
  </table>
  <br>
  <table:table id="guiEntrustContractModify" name="entrustContractModifyList">
    <logic:iterate id="element" name="entrustContractModifyList">
      <table:define id="c_cb">
        <bean:define id="billNo" name="element" property="billNo" />
        <html:radio property="checkBoxList(ids)" styleId="ids" value="${billNo}" />
      </table:define>
      <table:define id="c_billNo">
          <bean:write name="element" property="billNo" />
      </table:define>
       <table:define id="c_r1">
       	<logic:present name="element" property="r1">
	        <logic:match name="element" property="r1" value="ZB">�ڱ�</logic:match>
	        <logic:match name="element" property="r1" value="TB">�˱�</logic:match>
	        <logic:match name="element" property="r1" value="END">��ͬ��ֹ</logic:match>
        </logic:present>
      </table:define> 
      <table:define id="c_entrustContractNo">
        <bean:write name="element" property="entrustContractNo" />
      </table:define>
       <table:define id="c_maintContractNo">
        <bean:write name="element" property="maintContractNo" />
      </table:define>
       <table:define id="c_contractNatureOf">
        <logic:match name="element" property="contractNatureOf" value="PY">ƽ��</logic:match>
        <logic:match name="element" property="contractNatureOf" value="WT">ί��</logic:match>
      </table:define> 
      <table:define id="c_companyName">
        <bean:write name="element" property="companyName" />
      </table:define>
      <table:define id="c_contractSdate">
        <bean:write name="element" property="contractSdate" />
      </table:define>
      <table:define id="c_contractEdate">
        <bean:write name="element" property="contractEdate" />
      </table:define>
      <table:define id="c_attn">
        <bean:write name="element" property="attn" />
      </table:define>
      <table:define id="c_maintDivision">
        <bean:write name="element" property="maintDivision" />
      </table:define>
      <table:define id="c_auditStatus">
        <logic:match name="element" property="auditStatus" value="Y">�����</logic:match>
        <logic:match name="element" property="auditStatus" value="N">δ���</logic:match>
      </table:define>
      <table:tr />
    </logic:iterate>
  </table:table>
</html:form>