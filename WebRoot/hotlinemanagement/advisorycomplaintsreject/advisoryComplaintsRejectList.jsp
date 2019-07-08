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
	<table>
		<tr>
		    <td>
				&nbsp;&nbsp;
				 <bean:message key="AdvisoryComplaintsManage.ProcessSingleNo"/>
				:
			</td>
			<td>
			    <html:text property="property(processSingleNo)" styleClass="default_input" />			
			</td>		
			<td>
				&nbsp;&nbsp;
				���⴦����
				:
			</td>
			<td>
				<html:text property="property(processPer)" styleClass="default_input"/>
			</td>
			<td>
				&nbsp;&nbsp;
				 <bean:message key="AdvisoryComplaintsManage.MessageSource"/>
				:
			</td>
			<td>
				<%-- <html:text property="property(messageSource)" styleClass="default_input" size="50" ></html:text> --%>
				<html:select property="property(messageSource)">
					<html:option value=""><bean:message key="qualitycheckmanagement.all"/></html:option>
					<html:options collection="MessageSourceList" property="id.pullid" labelProperty="pullname"/>
				</html:select>
			</td>
			<td>
				&nbsp;&nbsp;
				�Ƿ�ر�
				:
			</td>
			<td>
				<html:select property="property(isClose)" styleClass="default_input">
		          <html:option value=""><bean:message key="qualitycheckmanagement.all"/></html:option>
			      <html:option value="Y">�ѹر�</html:option>
			      <html:option value="N">δ�ر�</html:option>
		        </html:select>
			</td>
			<html:hidden property="property(genReport)" styleId="genReport" />
		</tr>
	</table>
	<br>
	<table:table id="guiAdvisoryComplaintsAudit" name="advisoryComplaintsRejectList">
		<logic:iterate id="element" name="advisoryComplaintsRejectList">
			<table:define id="c_cb">
			 <bean:define id="processSingleNo" name="element" property="processSingleNo" />  
				<%--  <html:multibox property="checkBoxList(ids)" styleId="ids">
					<bean:write name="element" property="regionId" />
				</html:multibox>  --%>
				
				  <html:radio property="checkBoxList(ids)" styleId="ids" value="<%=processSingleNo.toString()%>" />
				  <html:hidden property="List(isClose)" styleId="isClose" value="${element.isClose }"/>
			</table:define>
			 <table:define id="c_processSingleNo">
				 <a href="<html:rewrite page='/advisoryComplaintsManageAction.do'/>?method=toRejectDisplay&id=<bean:write name="element"  property="processSingleNo"/>"> 
				<bean:write name="element" property="processSingleNo" />
				</a>
			</table:define> 
			
			<table:define id="c_messageSource">
				<bean:write name="element" property="messageSource" />
			</table:define> 
			
			 <table:define id="c_receivePer">
				<bean:write name="element" property="processPer" />
			</table:define> 
			
			<table:define id="c_receiveDate">
				<bean:write name="element" property="processDate" />
			</table:define>
			
			<table:define id="c_feedbackPer">
				<bean:write name="element" property="feedbackPer" />
			</table:define>
			
			<table:define id="c_feedbackTel">
				<bean:write name="element" property="feedbackTel" />
			</table:define>
			<table:define id="c_auditType">
				<logic:match name="element" property="auditType" value="Y">
					�ѷ����ܽ�
				</logic:match>
				<logic:match name="element" property="auditType" value="N">
					δ�����ܽ�
				</logic:match>
			</table:define>
			<table:define id="c_isClose">
				<logic:match name="element" property="isClose" value="Y">
					<bean:message key="AdvisoryComplaintsManage.Close"/>
				</logic:match>
				<logic:match name="element" property="isClose" value="N">
					<bean:message key="AdvisoryComplaintsManage.noClose"/>
				</logic:match>
			</table:define>
			<table:tr />
		</logic:iterate>
	</table:table>
</html:form>