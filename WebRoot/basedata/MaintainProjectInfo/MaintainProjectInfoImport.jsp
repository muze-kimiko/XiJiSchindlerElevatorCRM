<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" src="<html:rewrite forward='pageJS'/>"></script>
<html:errors />
<html:form action="/MaintainProjectInfoAction.do?method=toImportRecord" enctype="multipart/form-data">
<br>
<table>
 <tr>
				<td>
			&nbsp;&nbsp;
			��������
			:
			</td>
			<td><html:select property="elevatorType">
					<html:option value="">
						ȫ��
					</html:option>
					<html:options collection="elevatorTypeList" property="id.pullid" labelProperty="pullname"/>  
				</html:select>
			</td>
			<td> 
				&nbsp;&nbsp;
				��������
				:
			</td>
			<td>
				<html:select property="maintType">
					<html:option value="">
						ȫ��
					</html:option>
					<html:options collection="maintTypeList" property="id.pullid" labelProperty="pullname"/>  
				</html:select>
			</td>
			</tr>
			</table>
</br>
  &nbsp;&nbsp;�ļ���ʽ(Excel)��
  <html:file property="file" styleClass="default_input" size="60" />   
</html:form>

<font color="red">
<logic:present name="reStr">
  <b>&nbsp;&nbsp;������Ϣ��</b><br/>
  &nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="reStr" scope="request" filter="false"/>
</logic:present>
</font>