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
			    <html:text property="property(processSingleNo)" styleClass="default_input" ></html:text>			
			</td>		
			<td>
				&nbsp;&nbsp;
				<bean:message key="AdvisoryComplaintsManage.ReceivePer"/>
				:
			</td>
			<td>
				<html:text property="property(receivePer)" styleClass="default_input"></html:text>
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
		</tr>
		<tr>
			<td>
				&nbsp;&nbsp;
				ί�д���
				:
			</td>
			<td>
				<html:select property="property(isEntrust)">
		          <html:option value=""><bean:message key="qualitycheckmanagement.all"/></html:option>
			      <html:option value="Y">��</html:option>
			      <html:option value="N">��</html:option>
		        </html:select>
			</td>
			<td>
				&nbsp;&nbsp;
				<bean:message key="AdvisoryComplaintsManage.SubmitType"/>
				:
			</td>
			<td>
				<html:select property="property(submitType)">
		          <html:option value=""><bean:message key="qualitycheckmanagement.all"/></html:option>
			      <html:option value="Y"><bean:message key="qualitycheckmanagement.submitted"/></html:option>
			      <html:option value="N"><bean:message key="qualitycheckmanagement.uncommitted"/></html:option>
			      <html:option value="R">����</html:option>
		        </html:select>
			</td>
			
			<html:hidden property="property(genReport)" styleId="genReport" />
		</tr>
	</table>
	<br>
	<table:table id="guiAdvisoryComplaintsManage" name="advisoryComplaintsManageList">
		<logic:iterate id="element" name="advisoryComplaintsManageList">
			<table:define id="c_cb">
			 <bean:define id="processSingleNo" name="element" property="processSingleNo" />  
				<%--  <html:multibox property="checkBoxList(ids)" styleId="ids">
					<bean:write name="element" property="regionId" />
				</html:multibox>  --%>
				
				  <html:radio property="checkBoxList(ids)" styleId="ids" value="<%=processSingleNo.toString()%>" />
				  <html:hidden property="List(submitType)" styleId="submitType" value="${element.submitType }"/>
			</table:define>
			 <table:define id="c_processSingleNo">
				 <a href="<html:rewrite page='/advisoryComplaintsManageAction.do'/>?method=toDisplayRecord&id=<bean:write name="element"  property="processSingleNo"/>&returnMethod=toSearchRecord"> 
				<bean:write name="element" property="processSingleNo" />
				</a>
			</table:define> 
			
			<table:define id="c_messageSource">
				<bean:write name="element" property="messageSource" />
			</table:define> 
			
			 <table:define id="c_receivePer">
				<bean:write name="element" property="receivePer" />
			</table:define> 
			
			<table:define id="c_receiveDate">
				<bean:write name="element" property="receiveDate" />
			</table:define>
			
			<table:define id="c_feedbackPer">
				<bean:write name="element" property="feedbackPer" />
			</table:define>
			
			<table:define id="c_feedbackTel">
				<bean:write name="element" property="feedbackTel" />
			</table:define>
			<table:define id="c_isEntrust">
			<logic:present name="element" property="isEntrust">
				<logic:match name="element" property="isEntrust" value="Y">
					��
				</logic:match>
				<logic:match name="element" property="isEntrust" value="N">
					��
				</logic:match>
			</logic:present>
			</table:define>
			<table:define id="c_submitType">
				<logic:match name="element" property="submitType" value="Y">
					<bean:message key="qualitycheckmanagement.submitted"/>
				</logic:match>
				<logic:match name="element" property="submitType" value="N">
					<bean:message key="qualitycheckmanagement.uncommitted"/>
				</logic:match>
				<logic:match name="element" property="submitType" value="R">
					����
				</logic:match>
			</table:define>
			<table:define id="c_dispatchType">
			<logic:present name="element" property="dispatchType">
				<logic:match name="element" property="dispatchType" value="Y">
					���ɹ�
				</logic:match>
				<logic:match name="element" property="dispatchType" value="N">
					δ�ɹ�
				</logic:match>
			</logic:present>
			</table:define>
			<table:define id="c_processType">
				<logic:match name="element" property="processType" value="Y">
					�Ѵ���δ�ύ
				</logic:match>
				<logic:match name="element" property="processType" value="N">
					δ����
				</logic:match>
				<logic:match name="element" property="processType" value="S">
					�Ѵ������ύ
				</logic:match>
				<logic:match name="element" property="processType" value="R">
					����
				</logic:match>
			</table:define>
			<table:define id="c_auditType">
				<logic:match name="element" property="auditType" value="Y">
					�ѷ����ܽ�
				</logic:match>
				<logic:match name="element" property="auditType" value="N">
					δ�����ܽ�
				</logic:match>
			</table:define>
			<table:tr />
		</logic:iterate>
	</table:table>
</html:form>