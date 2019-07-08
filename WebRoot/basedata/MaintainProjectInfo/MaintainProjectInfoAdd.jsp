<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<br>
<html:errors/>
<html:form action="/MaintainProjectInfoAction.do?method=toAddRecord">
<html:hidden property="isreturn"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">

<tr>
    <td class="wordtd">��������:</td>
    <td>
	  <html:select property="elevatorType">
	    <html:option value="">��ѡ��</html:option>
	  <html:options collection="elevatorTypeList" property="id.pullid" labelProperty="pullname"/>  
	  </html:select>
	  <font color="red">*</font>
	</td>
  </tr>
  <tr>
   <td class="wordtd"> ��������:</td>
    <td>
	<html:select property="maintType">
	  <html:option value="">��ѡ��</html:option>
	  <html:options collection="maintTypeList" property="id.pullid" labelProperty="pullname"/>  
	  </html:select>
<font color="red">*</font>
	</td> 
  </tr>
  <tr>
    <td class="wordtd">ά����Ŀ:</td>
    <td><html:text size="50" property="maintItem" styleClass="default_input"/><font color="red">*</font></td>
  </tr>
  <tr>
    <td class="wordtd">ά������Ҫ��:</td>
    <td><html:textarea property="maintContents" rows="5" cols="50" styleClass="default_textarea"/><font color="red">*</font></td>
  </tr>
 
   <tr>
    <td class="wordtd">�����:</td>
    <td nowrap="nowrap"><input type="text" id="orderby"  name="orderby" onpropertychange="checkthisvalue(this);"  Class="default_input"/><font color="red">*��ֻ�������֣�</font></td>
  </tr>
  <tr>
    <td class="wordtd">���ñ�־:</td>
    <td>
	  <html:radio property="enabledFlag" value="Y"/><bean:message key="pageword.yes"/>
      <html:radio property="enabledFlag" value="N"/><bean:message key="pageword.no"/>
	</td>
  </tr>
  <tr>
    <td class="wordtd">��ע:</td>
    <td><html:textarea property="rem" rows="5" cols="50" styleClass="default_textarea"/></td>
  </tr>
</table>
</html:form>
<html:javascript formName="MaintainProjectInfoForm"/>
