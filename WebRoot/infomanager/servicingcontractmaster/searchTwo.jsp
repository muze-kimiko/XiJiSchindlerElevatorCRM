<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/DatePicker/WdatePicker.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<input type="hidden" name="isreturn" id="isreturn" />
<input type="hidden" name="method" id="method" />
<table width="100%" border="0">
<tr>
<td>ҵ�����:<select name="busType" id="busType">
		<option value="%">ȫ��</option>
		<option value="W">ά��</option>
		<option value="G">����</option>
	</select>
&nbsp;&nbsp;��ͬ�ţ�
<input type="text" name="maintContractNo" id="maintContractNo" class="default_input" >
&nbsp;&nbsp;����ά���ֲ�:
<html:select property="maintDivision">
<html:options collection="maintDivisionList" property="grcid" labelProperty="grcname"/>
</html:select>
</td>
</tr>
</table>
<br>
<table width="100%" class="tb" id="table_1">
	<tr>
		<td nowrap class="wordtd_header" width="3%"><div align="center"><input type="checkbox" name="selAll" onclick="checkedallbox(this)"></div></td>	
	    <td nowrap class="wordtd_header" width="3%"><div align="center">���</div></td>
		<td nowrap class="wordtd_header" width="5%"><div align="center">ҵ�����</div></td>
		<td nowrap class="wordtd_header" width="9%"><div align="center">��ͬ��</div></td>
	    <td nowrap class="wordtd_header" width="15%"><div align="center">�ͻ�����</div></td>
	    <td nowrap class="wordtd_header" width="10%"><div align="center">Ԥ�Ƶ�������</div></td>
	    <td nowrap class="wordtd_header" width="10%"><div align="center">ȷ�ϵ�������</div></td>
	    <td nowrap class="wordtd_header" width="10%"><div align="center">�����´���</div></td>
	    <td nowrap class="wordtd_header" width="10%"><div align="center">�����´�����</div></td>
	    <td nowrap class="wordtd_header" width="20%"><div align="center">�����´ﱸע</div></td>
	    <td nowrap class="wordtd_header" width="5%"><div align="center">����ά���ֲ�</div></td>
	</tr>
	<logic:empty name="ServicingContractMasterList">
	<tr><td colspan="11" align="center">û��¼��ʾ��</td></tr>
	</logic:empty>
	<logic:present name="ServicingContractMasterList">
	<logic:iterate id="element" name="ServicingContractMasterList" indexId="seid">
	<tr>
		<td align="center">
		<input type="checkbox" name="checkboxids" onclick="sethiddenvalue(this,'isbox',${seid})">
		<input type="hidden" name="isbox" id="isbox" value="N">
		<input type="hidden" name="wgbillno" id="wgbillno" value="<bean:write name="element"  property="wgbillno"/>">
		</td>
		<td align="center">${seid+1}</td>
		<td nowrap>
		<bean:write name="element"  property="bustype"/>
		</td>
		<td nowrap>		
		<a href="<html:rewrite page='/ServicingContractMasterTaskSearchAction.do'/>?method=toDisplayRecord&id=<bean:write name="element"  property="wgbillno"/>" target="_blnk">
		<bean:write name="element"  property="maintcontractno"/></a>
		
		</td>
		<td nowrap><bean:write name="element" property="companyid"/></td>
		<td><input type="text" name="forecastdate" id="forecastdate" size="12" Class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})" readonly="readonly" value="<bean:write name="element" property="forecastdate"/>"/></td>
		<td><input type="text" name="comfirmdate" id="comfirmdate" size="12" Class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})" readonly="readonly" value="<bean:write name="element" property="comfirmdate"/>"/></td>
		<td nowrap><bean:write name="element" property="taskuserid"/></td>
		<td nowrap><bean:write name="element" property="tasksubdate"/></td>
        <td nowrap><bean:write name="element" property="taskrem"/></td>
		<td nowrap><bean:write name="element" property="maintdivision"/></td>
	</tr>
	</logic:iterate>
	</logic:present>
</table>
<script type="text/javascript">
$("#busType").val('${busType}');
$("#maintContractNo").val('${maintContractNo}');

</script>



