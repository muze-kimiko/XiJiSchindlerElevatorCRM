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
&nbsp;&nbsp;�깤��׼:<select name="FinishFlag22" id="FinishFlag22">
		<option value="%">ȫ��</option>
		<option value="Y">��</option>
		<option value="N">��</option>
	</select>
&nbsp;&nbsp;Ԥ�Ƶ�������:��<input type="text" name="ForecastDatestatr" id="ForecastDatestatr" size="12" Class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})" >
��<input type="text" name="ForecastDateend" id="ForecastDateend" size="12" Class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})" >
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
		<td nowrap class="wordtd_header" width="3%"><div align="center">ҵ�����</div></td>
		<td nowrap class="wordtd_header" width="7%"><div align="center">��ͬ��</div></td>
	    <td nowrap class="wordtd_header" width="10%"><div align="center">�ͻ�����</div></td>
	    <td nowrap class="wordtd_header" width="8%"><div align="center">Ԥ�Ƶ�������</div></td>
	    <td nowrap class="wordtd_header" width="8%"><div align="center">ȷ�ϵ�������</div></td>	    
	    <td nowrap class="wordtd_header" width="3%"><div align="center">��Ŀ������</div></td>
		<td nowrap class="wordtd_header" width="8%"><div align="center">�ɹ�����</div></td>
	    <td nowrap class="wordtd_header" width="8%"><div align="center">��������</div></td>
	    <td nowrap class="wordtd_header" width="3%"><div align="center">�����־</div></td>
	    <td nowrap class="wordtd_header" width="6%"><div align="center">�깤��־</div></td>
	    <td nowrap class="wordtd_header" width="8%"><div align="center">�깤����</div></td>
	    <td nowrap class="wordtd_header" width="8%"><div align="center">�깤��ע</div></td>	    
	    <td nowrap class="wordtd_header" width="5%"><div align="center">�����´���</div></td>
	    <td nowrap class="wordtd_header" width="5%"><div align="center">�����´�����</div></td>
	    <td nowrap class="wordtd_header" width="13%"><div align="center">�����´ﱸע</div></td>
	    <td nowrap class="wordtd_header" width="5%"><div align="center">����ά���ֲ�</div></td>
	</tr>
	<logic:empty name="ServicingContractMasterList">
	<tr><td colspan="18" align="center">û��¼��ʾ��</td></tr>
	</logic:empty>
	<logic:present name="ServicingContractMasterList">
	<logic:iterate id="element" name="ServicingContractMasterList" indexId="seid">
	<tr>
		<td align="center">
		<logic:notEqual name="element" property="finishflag" value="Y">
		<input type="checkbox" name="checkboxids" onclick="sethiddenvalue(this,'isbox',${seid})">
		</logic:notEqual>
		<input type="hidden" name="isbox" id="isbox" value="N" >
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
		<td><bean:write name="element" property="forecastdate"/></td>
		<td><bean:write name="element" property="comfirmdate"/></td>
		<logic:equal name="element" property="finishflag" value="N">
		<td><input type="text" size="8" name="itemUserId" id="itemUserId" value="${element.itemuserid }" ></td>
		<td><input type="text" name="appWorkDate" id="appWorkDate" size="12" Class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})" value="${element.appworkdate }" ></td>
		<td><input type="text" name="enterArenaDate" id="enterArenaDate" size="12" Class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})" value="${element.enterarenadate }" ></td>
		<td><select name="epibolyFlag" >
		<option value="N" ${element.epibolyflag =='N'?'selected':''}>��</option>
		<option value="Y" ${element.epibolyflag =='Y'?'selected':''}>��</option>		
		</select></td>
		<td><select name="finishFlag">
		<option value="Y" ${element.finishflag =='Y'?'selected':''}>��</option>
		<option value="N" ${element.finishflag =='N'?'selected':''}>��</option>
		</select></td>
		<td><input type="text" name="finishDate" id="finishDate" size="12" Class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})" value="${element.finishdate }" ></td>
		<td><input type="text" name="finishRem" id="finishRem" value="${element.finishrem }" ></td>
		</logic:equal>
		<logic:equal name="element" property="finishflag" value="Y">
		<td>${element.itemuserid }</td>
		<td>${element.appworkdate }</td>
		<td>${element.enterarenadate }</td>
		<td ><script>document.write('${element.epibolyflag }'=='Y'?'��':'��')</script></td>
		<td><script>document.write('${element.finishflag }'=='Y'?'��':'��')</script></td>
		<td>${element.finishdate }</td>
		<td>${element.finishrem }</td>
		</logic:equal>		
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
$("#FinishFlag22").val('${finishFlag}');
$("#ForecastDatestatr").val('${ForecastDatestatr}');
$("#ForecastDateend").val('${ForecastDateend}');
</script>



