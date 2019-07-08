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
<html:hidden property="property(hiddatestr)" styleId="hiddatestr"  />
<html:hidden property="property(genReport)" styleId="genReport" />
  <table>
    <tr>
      <td>      
        <bean:message key="maintContract.billNo" />
        :
      </td>
      <td>
        <html:text property="property(billNo)" styleClass="default_input" />
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
        <bean:message key="maintContract.attn" />
        :
      </td>
      <td>
        <html:text property="property(attn)" styleClass="default_input" />
      </td>
      <td>  
        &nbsp;&nbsp;     
        ���ۺ�ͬ��
        :
      </td>
      <td>
        <html:text property="property(salesContractNo)" styleClass="default_input" />
      </td> 
    </tr>   
    <tr>
      <td> 
                    ��ͬ״̬
        :
      </td>
      <td>
        <html:select property="property(contractStatus)">
          <html:option value="">ȫ��</html:option>
		  <html:options collection="contractStatusList" property="id.pullid" labelProperty="pullname"/>
        </html:select>
      </td>    
      <%-- <td>       
                    �ύ��־
        :
      </td>
      <td>
        <html:select property="property(submitType)">
          <html:option value="">ȫ��</html:option>
		  <html:option value="Y">���ύ</html:option>
		  <html:option value="N">δ�ύ</html:option>
        </html:select>
      </td> --%>    
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
      <td> 
        &nbsp;&nbsp;
                    �����´��־
        :
      </td>
      <td>
        <html:select property="property(taskSubFlag)">
          <html:option value="">ȫ��</html:option>
		  <html:option value="Y">���´�</html:option>
		  <html:option value="N">δ�´�</html:option>
        </html:select>
      </td>
      <td>  
        &nbsp;&nbsp;     
        ���ݱ��
        :
      </td>
      <td>
        <html:text property="property(elevatorNo)" styleClass="default_input" />
      </td>
    </tr> 
    <tr>
      <td>           
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
                    �׷���λ
        :
      </td>
      <td>
        <html:text property="property(companyName)" size="30" styleClass="default_input" />
      </td>
      <td> 
        &nbsp;&nbsp;      
                    ʹ�õ�λ����
        :
      </td>
      <td colspan="3">
        <html:text property="property(maintAddress)" size="30" styleClass="default_input" />
      </td> 
    </tr>   
  </table>
  <br>
  <table:table id="guiMaintContractAudit" name="maintContractAuditList">
    <logic:iterate id="element" name="maintContractAuditList">
      <table:define id="c_cb">
        <input type="radio" name="ids" value="${element.billNo}" />
        <html:hidden name="element" property="contractEdate" />
        <html:hidden name="element" property="submitType" />
        <html:hidden name="element" property="auditStatus" /> 
        <html:hidden name="element" property="contractStatus" /> 
        <html:hidden name="element" property="warningStatus"/>     
      </table:define>
      <table:define id="c_billNo">
        <a href="<html:rewrite page='/maintContractAuditAction.do'/>?method=toDisplayRecord&id=<bean:write name="element"  property="billNo"/>">
          <bean:write name="element" property="billNo" />
        </a>
      </table:define>
      <table:define id="c_maintContractNo">
        <bean:write name="element" property="maintContractNo" />
      </table:define>
      <table:define id="c_companyName">
        <bean:write name="element" property="companyId" />
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
      <table:define id="c_contractStatus">
        <bean:write name="element" property="contractStatus" />
      </table:define>
      <table:define id="c_warningStatus">
      <logic:present name="element" property="warningStatus">
        <logic:match name="element" property="warningStatus" value="Y">�ѽ��ݷüƻ�</logic:match>
        <logic:match name="element" property="warningStatus" value="S">�Ѱݷ÷���</logic:match>
       </logic:present>
      </table:define>
      <table:define id="c_submitType">
        <logic:match name="element" property="submitType" value="Y">���ύ</logic:match>
        <logic:match name="element" property="submitType" value="N">δ�ύ</logic:match>
      </table:define>
      <table:define id="c_auditStatus">
        <logic:match name="element" property="auditStatus" value="Y">�����</logic:match>
        <logic:match name="element" property="auditStatus" value="N">δ���</logic:match>
      </table:define>
      <table:define id="c_taskSubFlag">
        <logic:match name="element" property="taskSubFlag" value="Y">���´�</logic:match>
        <logic:match name="element" property="taskSubFlag" value="N">δ�´�</logic:match>
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
</html:form>