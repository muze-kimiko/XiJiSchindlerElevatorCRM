<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<link rel="stylesheet" type="text/css"
	href="<html:rewrite forward='formCSS'/>">
<html:errors />
<br>  

<html:form action="/ServeTable.do">
<html:hidden property="property(reimbursPeople)" />
<html:hidden property="property(billnostr)" />
	<table>
		<tr>
			<td>
				��ˮ��
				:
			</td>
			<td>
				<html:text property="property(billno)" styleClass="default_input" />
			</td>
			<td>
			&nbsp;&nbsp;
				��ͬ��
				:
			</td>
			<td>
				<html:text property="property(maintContractNo)" styleClass="default_input" />
			</td>
			
	<td>
	&nbsp;&nbsp;
		��ͬ����
		:
	</td>
	<td>
		<html:select property="property(busType)">
			<html:option value="">--��ѡ��--</html:option>
			<html:option value="B">����</html:option>
			<html:option value="W">ά��</html:option>
			<html:option value="G">����</html:option>
		</html:select>
	</td>
			</tr>
			<tr>
			    <td>
        ά���ֲ�
        :
      </td>
      <td>
        <html:select property="property(comid)">
        <html:options collection="maintDivisionList" property="grcid" labelProperty="grcname"/>
        </html:select>
      </td>
   <td>
&nbsp;&nbsp;
        ά��վ
        :
      </td>
      <td>
        <html:text property="property(storage)" styleClass="default_input" />
      </td>
      
      <td>
&nbsp;&nbsp;
       �Ƿ�����ͬ
        :
      </td>
      <td>
        <html:select property="property(isjoin)">
        <html:option value="%">ȫ��</html:option>
        <html:option value="Y">��</html:option>
        <html:option value="N">��</html:option>
        </html:select>
      </td>
			<html:hidden property="property(genReport)" styleId="genReport" />
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
       	 ��Ŀ����
        :
      </td>
      <td colspan="3">
        <html:text property="property(projectName)" styleClass="default_input" size="50"/>
      </td>  
		</tr>
	</table>
	<br>
	<table:table id="guiProjectReimbursement" name="searchProjectReimbursementList">
		<logic:iterate id="element" name="searchProjectReimbursementList">
			<table:define id="c_cb">
			<bean:define id="billno" name="element" property="billno" />
				<html:checkbox property="checkBoxList(ids)" styleId="ids" value="<%=billno .toString()%>" />
				<html:hidden name="element" property="billno" styleId="billno" />
				<html:hidden name="element" property="maintContractNo" styleId="maintContractNo"/>
				<html:hidden name="element" property="busType" styleId="busType"/> 
				<html:hidden name="element" property="num" styleId="num"/>
				<html:hidden name="element" property="maintDivision" styleId="maintDivision"/>
				<html:hidden name="element" property="comName" styleId="comNameprb"/>
				<html:hidden name="element" property="maintStation" styleId="maintStation"/>
				<html:hidden name="element" property="storageName" styleId="storageNameprb"/>
			</table:define>
			<table:define id="c_billno">
				<bean:write name="element" property="billno" />
			</table:define>
			<table:define id="c_maintContractNo">
				<bean:write name="element" property="maintContractNo" />
			</table:define>
			<table:define id="c_busType">
				<logic:match name="element" property="busType" value="B">����</logic:match>
				<logic:match name="element" property="busType" value="W">ά��</logic:match>
				<logic:match name="element" property="busType" value="G">����</logic:match>
			</table:define>
			<table:define id="c_num">
				<bean:write name="element" property="num" />
			</table:define>
			<table:define id="c_maintDivision">
				<bean:write name="element" property="comName" />
			</table:define>				
			<table:define id="c_maintStation">
				<bean:write name="element" property="storageName" />
			</table:define>			
			<table:define id="c_contractStatus">
				<bean:write name="element" property="contractStatus" />
			</table:define>
			<table:tr />
		</logic:iterate>
	</table:table>
</html:form>