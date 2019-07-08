<%@ page contentType="text/html;charset=GBK" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<style>
  .show{display:block;}
  .hide{display:none;}

</style>
<logic:present name="freeFittingCostsManageBean">
<html:hidden property="isreturn"/>
<html:hidden name="freeFittingCostsManageBean" property="billno"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
   <tr>
   	<td class="wordtd" width="20%">����ά���ֲ���</td>
   	<td width="30%"><bean:write name="freeFittingCostsManageBean" property="maintDivision"/></td>
   	<td class="wordtd" width="20%">ά��վ��</td>
   	<td width="30%"><bean:write name="freeFittingCostsManageBean" property="maintStation"/></td>
   </tr>
   <tr>
   	<td class="wordtd">�����ɱ�ʱ�䣺</td>
   	<td colspan="3"><bean:write name="freeFittingCostsManageBean" property="costsDate"/></td>
   </tr>
</table>
<br>
<div id="caption_0" style="width: 100%;padding-top: 0;padding-bottom: 2;border-bottom: 0;border-top: 0;border-left: 0;border-right: 0" class="tb">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">����ܳɱ���Ԫ����</td>
		<td width="75%"><bean:write name="freeFittingCostsManageBean" property="fittingTotal"/></td>
	</tr>
</table>
</div>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="12%">��ͬ��</td>
			<td width="8%">��ͬ����</td>
			<td width="8%">�������</td>
			<td width="8%">�������</td>
			<td width="8%">�ɱ���Ԫ��</td>
			<td>��ע</td>
		</tr>
	</thead>
		
		<logic:present name="projectFittingCostsList">
          <logic:iterate id="element1" name="projectFittingCostsList" >
          	<tr>
				<td >${element1.maintContractNo}</td>
				<td >
					<logic:match name="element1" property="busType" value="B">����</logic:match>
					<logic:match name="element1" property="busType" value="W">ά��</logic:match>
					<logic:match name="element1" property="busType" value="G">����</logic:match>
				</td>
				<td >${element1.fittingName}</td>
				<td >${element1.fittingNum}</td>
				<td >${element1.costs}</td>
				<td>${element1.rem}</td>
	        </tr>
          </logic:iterate>
        </logic:present>
        
	 <tr height="15"><td colspan="6"></td></tr>
</table>
<br/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd">¼���ˣ�</td>
		<td><bean:write name="freeFittingCostsManageBean" property="operId"/></td>
		<td class="wordtd">¼�����ڣ�</td>
		<td><bean:write name="freeFittingCostsManageBean" property="operDate"/></td>
	</tr>
</table>
</logic:present>