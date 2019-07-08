
<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" src="<html:rewrite forward='pageJS'/>"></script>
<link href="/XJSCRM/common/css/bb.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script type="text/javascript" src="<html:rewrite page="/common/javascript/highcharts/highcharts.js"/>"></script>
	<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>

<br>
<html:form action="/reimbursExpenseReportAction.do?method=toSearchResults">
<html:hidden property="genReport" styleId="genReport" />
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
    	<td width="20%" class="wordtd">����ά���ֲ�:</td>
    	<td width="80%" class="inputtd">
    		<html:select property="maintdivision" styleId="maintdivision" onchange="Evenmore(this,'maintstation')">
		    	<html:options collection="maintDivisionList" property="grcid" labelProperty="grcname"/>
    		</html:select>
    	</td>
    </tr>
     <tr >
    	<td width="20%" class="wordtd">����ά��վ:</td>
    	<td width="80%" class="inputtd">
    		<html:select property="maintstation" styleId="maintstation">
    			<%-- html:option value="">ȫ��</html:option--%>
		    	<html:options collection="mainStationList" property="storageid" labelProperty="storagename"/>
    		</html:select>
    	</td>
    </tr>
    <tr>
    	<td width="20%" class="wordtd">������:</td>
    	<td width="80%" class="inputtd">
    		<html:text property="reimburspeople" styleClass="default_input"></html:text>
    	</td>
    </tr>
    <tr>
	    <td width="20%" class="wordtd">����ʱ��:</td>
	    <td width="80%" class="inputtd">
		    <html:text property="sdate1" styleId="sdate1" styleClass="Wdate" size="13" onfocus="WdatePicker({readOnly:true,isShowClear:true})" />
			- 
			<html:text property="edate1" styleId="edate1" styleClass="Wdate" size="13" onfocus="WdatePicker({readOnly:true,isShowClear:true})" />
	    </td>
    </tr>
    <tr>
    	<td width="20%" class="wordtd">ά����ͬ��:</td>
    	<td width="80%" class="inputtd">
    		<html:text property="maintcontractno" styleClass="default_input"/>
    	</td>
    </tr>
     <tr >
    	<td width="20%" class="wordtd">������Χ:</td>
    	<td width="80%" class="inputtd">
    		<html:select property="reimburrange" styleId="reimburrange" onchange="EveReimburType(this)">
    			<html:option value="%">ȫ��</html:option>
    			<html:option value="1">��ͬ����</html:option>
    			<html:option value="2">վ�ڱ���</html:option>
    			<html:option value="3">�ֲ�����</html:option>
    			<html:option value="4">��ά���ɱ�</html:option>
    		</html:select>
    	</td>
    </tr>
    
     <tr >
    	<td width="20%" class="wordtd">��������:</td>
    	<td width="80%" class="inputtd">
    		<html:select property="reimburtype" styleId="reimburtype">
    			<html:option value="%">ȫ��</html:option>
    			<%--
    			<html:options collection="mrTypeList" property="id.pullid" labelProperty="pullname"/>
    			<html:options collection="prTypeList" property="id.pullid" labelProperty="pullname"/>
    			--%>
    		</html:select>
    	</td>
    </tr>
    
    </table>
</html:form>


