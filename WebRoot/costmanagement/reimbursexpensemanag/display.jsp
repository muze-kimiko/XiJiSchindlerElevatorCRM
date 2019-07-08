<%@ page contentType="text/html;charset=GBK" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<style>
  .show{display:block;}
  .hide{display:none;}

</style>
<logic:present name="reimbursExpenseManagBean">
<html:hidden property="id"/>
<html:hidden name="reimbursExpenseManagBean" property="billno"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
   <tr>
   	<td class="wordtd" width="20%">����ά���ֲ���</td>
   	<td width="30%"><bean:write name="reimbursExpenseManagBean" property="maintDivision"/></td>
   	<td class="wordtd" width="20%">ά��վ��</td>
   	<td width="30%"><bean:write name="reimbursExpenseManagBean" property="maintStation"/></td>
   </tr>
   <tr>
   	<td class="wordtd">�����ˣ�</td>
   	<td><bean:write name="reimbursExpenseManagBean" property="reimbursPeople"/></td>
   	<td class="wordtd">����ʱ�䣺</td>
   	<td><bean:write name="reimbursExpenseManagBean" property="reimbursDate"/></td>
   </tr>
</table>
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">�����ܶԪ����</td>
		<td width="75%"><bean:write name="reimbursExpenseManagBean" property="totalAmount"/></td>
	</tr>
</table>
<br>


<div id="caption_0" style="width: 100%;padding-top: 0;padding-bottom: 2;border-bottom: 0;border-top: 0;border-left: 0;border-right: 0" class="tb">
<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="tb">
	<tr>
		<td class="wordtd" width="20%">��ͬ������Ԫ����</td>
		<td width="75%"><bean:write name="reimbursExpenseManagBean" property="projectMoney"/></td>
	</tr>
</table>
</div>
<table id="prb"  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="10%">��ͬ��</td>
			<td width="10%">��ͬ����</td>
			<td width="15%">ά���ֲ�</td>
			<td width="15%">ά��վ</td>
			<td width="4%">̨��</td>
			<td width="8%">��Ԫ��</td>
			<td width="10%">��������</td>
			<td>��ע</td>
		</tr>
	</thead>
	<tfoot>
      <tr height="15"><td colspan="8">&nbsp;</td></tr>
    </tfoot>
	<tbody>
		<logic:present name="projectList">
          <logic:iterate id="element1" name="projectList" >
          	<tr id="prbT_1" name="prbT_1">
				<td align="center">
				<logic:equal name="element1" property="busType" value="B">
					<a href="<html:rewrite page='/maintContractAction.do'/>?method=toDisplayRecord&typejsp=Yes&id=${element1.billno}" target="_blnk">${element1.maintContractNo}</a>
				</logic:equal>
				<logic:notEqual name="element1" property="busType" value="B">
					<a href="<html:rewrite page='/wgchangeContractAction.do'/>?method=toDisplayRecord&typejsp=Yes&id=${element1.billno}" target="_blnk">${element1.maintContractNo}</a>
				</logic:notEqual>
				</td>
				<td align="center">
					<logic:match name="element1" property="busType" value="B">����</logic:match>
					<logic:match name="element1" property="busType" value="W">ά��</logic:match>
					<logic:match name="element1" property="busType" value="G">����</logic:match>
				</td>
				<td align="center">${element1.comName}</td>
				<td align="center">${element1.storageName}</td>
				<td align="center">${element1.num}</td>
				<td align="center">${element1.money}</td>
				<td align="center">${element1.reimburType}</td>
				<td>${element1.rem}</td>
	         
	        </tr>
          </logic:iterate>
        </logic:present>
	</tbody>
</table>
<br/>
<div id="caption_0" style="width: 100%;padding-top: 0;padding-bottom: 2;border-bottom: 0;border-top: 0;border-left: 0;border-right: 0" class="tb">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">վ�ڱ�����Ԫ����</td>
		<td width="75%"><bean:write name="reimbursExpenseManagBean" property="stationMoney"/></td>
	</tr>
</table>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="10%">��������</td>
			<td width="15%">ά���ֲ�</td>
			<td width="15%">ά��վ</td>
			<td width="10%">��Ԫ��</td>
			<td>��ע</td>
		</tr>
	</thead>
	<tfoot>
      <tr height="15"><td colspan="5">&nbsp;</td></tr>
    </tfoot>
	<tbody>
		<logic:present name="stationList">
          <logic:iterate id="element2" name="stationList" >
          	<tr>
	         <td align="center">${element2.reimburType}</td>
	         <td align="center">${element2.r1}</td>
			 <td align="center">${element2.r2}</td>
			<td align="center">${element2.money}</td>
			<td>${element2.rem}</td>
	        </tr>
          </logic:iterate>
        </logic:present>
	</tbody>
</table>
<br/>

<div id="caption_0" style="width: 100%;padding-top: 0;padding-bottom: 2;border-bottom: 0;border-top: 0;border-left: 0;border-right: 0" class="tb">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">�ֲ�������Ԫ����</td>
		<td width="75%"><bean:write name="reimbursExpenseManagBean" property="divsionMoney"/></td>
	</tr>
</table>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="10%">��������</td>
			<td width="15%">ά���ֲ�</td>
			<td width="10%">��Ԫ��</td>
			<td>��ע</td>
		</tr>
	</thead>
	<tfoot>
      <tr height="15"><td colspan="4">&nbsp;</td></tr>
    </tfoot>
	<tbody>
		<logic:present name="divisionList">
          <logic:iterate id="element3" name="divisionList" >
          	<tr>
	         <td align="center">${element3.reimburType}</td>
	         <td align="center">${element3.r1}</td>
			<td align="center">${element3.money}</td>
			<td>${element3.rem}</td>
	        </tr>
          </logic:iterate>
        </logic:present>
	</tbody>
</table>
<br/>

<div id="caption_0" style="width: 100%;padding-top: 0;padding-bottom: 2;border-bottom: 0;border-top: 0;border-left: 0;border-right: 0" class="tb">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">��ά���ɱ�������Ԫ����</td>
		<td width="75%"><bean:write name="reimbursExpenseManagBean" property="noReimMoney"/></td>
	</tr>
</table>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="10%">��������</td>
			<td width="15%">��������</td>
			<td width="10%">��Ԫ��</td>
			<td>��ע</td>
		</tr>
	</thead>
	<tfoot>
      <tr height="15"><td colspan="4">&nbsp;</td></tr>
    </tfoot>
	<tbody>
		<logic:present name="noReimList">
          <logic:iterate id="element4" name="noReimList" >
          	<tr>
	         <td align="center">${element4.reimburType}</td>
	         <td align="center">${element4.r1}</td>
			<td align="center">${element4.money}</td>
			<td>${element4.rem}</td>
	        </tr>
          </logic:iterate>
        </logic:present>
	</tbody>
</table>
<br/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">¼���ˣ�</td>
		<td width="30%"><bean:write name="reimbursExpenseManagBean" property="operId"/></td>
		<td class="wordtd" width="20%">¼�����ڣ�</td>
		<td width="30%"><bean:write name="reimbursExpenseManagBean" property="operDate"/></td>
	</tr>
</table>
</logic:present>