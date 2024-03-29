
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
<html:form action="/preliminaryReportAction.do?method=toSearchResults">

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
     <tr>
	    <td width="20%" class="wordtd">审核时间:</td>
	    <td width="80%" class="inputtd">
	    <html:text property="sdate1" styleId="sdate1" styleClass="Wdate" size="16" onfocus="WdatePicker({readOnly:true,isShowClear:true});"/>
		- 
		<html:text property="edate1" styleId="edate1" styleClass="Wdate" size="16" onfocus="WdatePicker({readOnly:true,isShowClear:true})"/>
	    <html:hidden property="genReport" styleId="genReport" />
	    </td>
    </tr>
    <tr>
    	<td width="20%" class="wordtd">厂检员:</td>
    	<td width="80%" class="inputtd">
    		<html:text property="staffName" styleClass="default_input"/>
    	</td>
    </tr>
    <tr>
    	<td width="20%" class="wordtd">审批结果:</td>
    	<td width="80%" class="inputtd">
    		<html:select property="approveResult">
    			<html:option value="">请选择</html:option>
    			<html:option value="驳回">驳回</html:option>
    			<html:option value="不通过">不通过</html:option>
    		</html:select>
    	</td>
    </tr>
    </table>
</html:form>