<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<html:errors/>
<html:form action="/FaultProcessAction.do?method=toUpdateRecord">
<table width="100%" class="tb">
<html:hidden property="isreturn"/>
<html:hidden property="appNo" value="${list2.appNo }"/>
<tr>
<td class="wordtd"  >������:</td>
<td >${list2.repairName }</td>
<td class="wordtd" >��Ŀ��ַ:</td>
<td width="30%">${list2.projectAddress }</td>
</tr> 

<tr>
<td class="wordtd">�����˵绰:</td><td >${list2.repairPhone }</td>
<td class="wordtd">��Ŀ����:</td><td>${list2.projectName }</td>
</tr>

<tr>
<td  class="wordtd">��������:</td><td >${list2.repairDesc }</td>
<td  class="wordtd">�Ƿ�����:</td><td >
<logic:equal name="list2" property="isTiring" value="Y">
��
</logic:equal>
<logic:equal name="list2" property="isTiring" value="N">
��
</logic:equal>
</td>
</tr>

<tr>
<td  class="wordtd">¼����:</td><td >${list2.operId }</td>
<td  class="wordtd">¼������:</td><td >${list2.operDate }</td>
</tr>

<tr>
<td  class="wordtd">�Ƿ���:</td><td >
<logic:equal name="list2" property="isProcess" value="Y">
��
</logic:equal>
<logic:notEqual name="list2" property="isProcess" value="Y">
��
</logic:notEqual>
</td>
<td class="wordtd"></td><td ></td>
</tr>

<tr>
<td  class="wordtd">������:</td><td >${list2.dealId }</td>
<td  class="wordtd">��������:</td><td >${list2.dealDate }</td>
</tr>

<tr>
<td  class="wordtd">��ע:</td><td colspan="3"><html:textarea rows="5" cols="50" property="rem" value="${list2.rem }"/></td>
</tr>
</table>
</html:form>