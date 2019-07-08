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
		<td>&nbsp;&nbsp;
			��ͬ��
			:
		</td>
		<td>
			<html:text property="property(contractNo)" styleClass="default_input" />
		</td>
		<td>&nbsp;&nbsp;
			��ͬ����
			:
		</td>
		<td>
			<html:select property="property(contractType)">
				<html:option value="">��ѡ��</html:option>
				<html:option value="B">����</html:option>
				<html:option value="W">ά��</html:option>
				<html:option value="G">����</html:option>
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
     <tr>             
      <td>&nbsp;&nbsp;
      	���ۺ�ͬ��
        :
      </td>
      <td>
        <html:text property="property(salesContractNo)" styleClass="default_input" />
      </td>              
      <td>&nbsp;&nbsp;
      	��Ʊ���� 
        :
      </td>
      <td>
        <html:text property="property(invoiceName)" styleClass="default_input" size="40"/>
      </td> 
		<td>
		&nbsp;&nbsp;
			Ӧ�տ�����
			:
		</td>
		<td colspan="3">
		<html:text property="property(sdate1)" styleClass="Wdate" size="13" onfocus="WdatePicker({readOnly:true,isShowClear:true});"/>
		- 
		<html:text property="property(edate1)" styleClass="Wdate" size="13" onfocus="WdatePicker({readOnly:true,isShowClear:true})"/>
	    
		</td>
    </tr>
    
  </table>
  <br>
  <table:table id="guiContractInvoiceManageNext" name="contractInvoiceManageNextList">
    <logic:iterate id="element" name="contractInvoiceManageNextList">
     <table:define id="c_cb">
		<bean:define id="jnlNo" name="element" property="jnlNo" />
			<html:radio property="checkBoxList(ids)" styleId="ids" value="<%=jnlNo.toString()%>" />
			<html:hidden name="element" property="preDate"/>
			<input type="hidden" name="warnRem" value="<bean:write name="element" property="warnRem" />"/>
		</table:define>
		<table:define id="c_jnlNo">
			<bean:write name="element" property="jnlNo" />
		</table:define>
		<table:define id="c_contractNo">
			<bean:write name="element" property="contractNo" />
		</table:define>
		<table:define id="c_contractType">
			<logic:match name="element" property="contractType" value="B">����</logic:match>
			<logic:match name="element" property="contractType" value="W">ά��</logic:match>
			<logic:match name="element" property="contractType" value="G">����</logic:match>
		</table:define>
		<table:define id="c_companyId">
			<a href="<html:rewrite page='/customerAction.do'/>?method=toDisplayRecord&typejsp=Yes&id=<bean:write name="element"  property="companyId"/>" target="_blnk"> 
				<bean:write name="element" property="companyName" />
			</a>
		</table:define>
		<table:define id="c_recName">
			<bean:write name="element" property="recName" />
		</table:define>
		<table:define id="c_preDate">
			<bean:write name="element" property="preDate" />
		</table:define>
		<table:define id="c_preMoney">
			<bean:write name="element" property="preMoney" />
		</table:define>
		<table:define id="c_notPreMoney">
			<bean:write name="element" property="notPreMoney" />
		</table:define>
		<table:define id="c_maintDivision">
			<bean:write name="element" property="maintDivision" />
		</table:define>
		<table:define id="c_warnRem">
			
			<bean:write name="element" property="warnRem" />
		</table:define>
      <table:tr />
    </logic:iterate>
  </table:table>
</html:form>