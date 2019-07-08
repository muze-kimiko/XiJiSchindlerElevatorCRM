<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<script language="javascript" src="<html:rewrite forward='calendarJS'/>"></script>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script type="text/javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<html:errors />
<br>
<html:form action="/ServeTable.do" styleId="serveForm">
	<font color="red">ע��:����û�кͿ�����Э��������£������޸Ĵ˴������ݡ�</font>
	<table>
		<tr>
			<td align="right">����:</td>
			<td>
				<html:text property="property(pullid)" styleClass="default_input" />
			</td>
			<td align="right">
				&nbsp;&nbsp;����:
			</td>
			<td>
				<html:text property="property(pullname)" styleClass="default_input" />
			</td>
			<td align="right">
				&nbsp;&nbsp;����:
			</td>
			<td>
				<html:text property="property(typeflag)" styleClass="default_input"  size="50"/>
			</td>
			</tr>
			<tr>
			<td align="right">
				&nbsp;&nbsp;����:
			</td>
			<td colspan="3">
				<html:text property="property(pullrem)" styleClass="default_input"  size="51"/>
			</td>
			<td align="right">
				&nbsp;&nbsp;���ñ�־:
			</td>
			<td>
				<html:select property="property(enabledflag)">
					<html:option value="">ȫ��</html:option>
					<html:option value="N">��</html:option>
					<html:option value="Y">��</html:option>
				</html:select>
			</td>
			<html:hidden property="property(genReport)" styleId="genReport" />
			
		</tr>
	</table>
	<br>
	<table:table id="guiPullDown" name="pulldList">
		<logic:iterate id="element" name="pulldList">
			<table:define id="c_cb">
				<%--html:multibox property="checkBoxList(ids)" styleId="ids">
					<bean:write name="element" property="id.pullid" />
				</html:multibox--%>
				
	        	<bean:define id="pullid" name="element" property="id.pullid" />
				<html:radio property="checkBoxList(ids)" styleId="ids" value="<%=pullid.toString()%>" />
			
				<input name="typeflag" id="typeflag" type="hidden" value="<bean:write name="element" property="id.typeflag"/>" />
				<input name="pullid" id="pullid" type="hidden" value="<bean:write name="element" property="id.pullid"/>" />
			</table:define>
			<table:define id="c_pullid">
				<bean:write name="element" property="id.pullid" />
			</table:define>
			<table:define id="c_pullname">
				<bean:write name="element" property="pullname" />
			</table:define>
			<table:define id="c_orderby">
				<bean:write name="element" property="orderby" />
			</table:define>
			<table:define id="c_typeflag">
				<bean:write name="element" property="id.typeflag" />
			</table:define>
			<table:define id="c_enabledflag">
				<logic:equal name="element" property="enabledflag" value="Y">
				��
				</logic:equal>
				<logic:equal name="element" property="enabledflag" value="N">
				��
				</logic:equal>
			</table:define>
			<table:define id="c_pullrem">
				<bean:write name="element" property="pullrem" filter="false"/>
			</table:define>
			<table:tr />
		</logic:iterate>
	</table:table>
</html:form>