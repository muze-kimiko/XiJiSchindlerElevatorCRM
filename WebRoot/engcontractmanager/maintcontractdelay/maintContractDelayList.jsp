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
                    ��ˮ��
        :
      </td>
      <td>
        <html:text property="property(jnlno)" styleClass="default_input" />
      </td>                 
      <td>
        &nbsp;&nbsp;               
        <bean:message key="maintContract.maintContractNo" />
        :
      </td>
      <td>
        <html:text property="property(maintContractNo)" styleClass="default_input" />
      </td> 
      <td> 
        &nbsp;&nbsp;   
                    �׷���λ
        :
      </td>
      <td>
        <html:text property="property(companyName)" size="35" styleClass="default_input" />
      </td>            
    </tr>
    <tr>
      <td>       
                    �ύ��־
        :
      </td>
      <td>
        <html:select property="property(submitType)">
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
        <html:select property="property(auditStatus)">
          <html:option value="">ȫ��</html:option>
		  <html:option value="Y">�����</html:option>
		  <html:option value="N">δ���</html:option>
        </html:select>
      </td>
      <%-- <td> 
        &nbsp;&nbsp;
                   ����״̬
        :
      </td>
      <td>
        <html:select property="property(status)">
          <html:option value="">ȫ��</html:option>
		  <html:options collection="processStatusList" property="typeid" labelProperty="typename"/>
        </html:select>
      </td> --%>
      <td> 
        &nbsp;&nbsp;
                    ����ά���ֲ�
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
  <table:table id="guiMaintContractDelay" name="maintContractDelayList">
    <logic:iterate id="element" name="maintContractDelayList">
      <table:define id="c_cb">
        <html:radio property="checkBoxList(ids)" styleId="ids" value="${element.jnlno}" />  
        <html:hidden name="element" property="contractEdate" />
        <html:hidden name="element" property="submitType" />
      </table:define>
      <table:define id="c_jnlno">
        <a href="<html:rewrite page='/maintContractDelayAction.do'/>?method=toDisplayRecord&id=${element.jnlno}">
          <bean:write name="element" property="jnlno" />
        </a>
      </table:define>
      <table:define id="c_maintContractNo">
        <bean:write name="element" property="maintContractNo" />
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
      <table:define id="c_submitType">
        <logic:match name="element" property="submitType" value="Y">���ύ</logic:match>
        <logic:match name="element" property="submitType" value="N">δ�ύ</logic:match>
        <logic:match name="element" property="submitType" value="R">����</logic:match>
      </table:define>
      <table:define id="c_auditStatus">
        <logic:match name="element" property="auditStatus" value="Y">�����</logic:match>
        <logic:match name="element" property="auditStatus" value="N">δ���</logic:match>
      </table:define>
      <%-- <table:define id="c_statusName">
        <bean:write name="element" property="statusName" />
      </table:define>      
      <table:define id="c_processName">
        <bean:write name="element" property="processName" />
      </table:define> --%>
      <table:define id="c_maintStationName">
        <bean:write name="element" property="maintStationName" />
      </table:define>
      <table:define id="c_maintDivisionName">
        <bean:write name="element" property="maintDivisionName" />
      </table:define>
      <table:tr />
    </logic:iterate>
  </table:table>
</html:form>