<%@page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" src="<html:rewrite forward='validator'/>"></script>
<br>
<html:errors/>
<logic:present name="errorstrinfo"><font color="red"><bean:write name="errorstrinfo"/></font></logic:present>
<html:form action="/returningMaintainAction.do?method=toAddRecord">
<html:hidden property="isreturn"/>
<html:hidden property="contracts"/>
<table width="100%" class="tb">

   <tr>
    <td width="20%" class="wordtd">��ͬ��ϵ��:</td>
    <td width="80%"><html:text property="contacts" maxlength="255" styleId="contacts" styleClass="default_input"/><font color="red">*</font></td>
  </tr>
   <tr>
    <td width="20%" class="wordtd">��ͬ��ϵ�˵绰:</td>
    <td width="80%"><html:text property="contactPhone" maxlength="255" styleId="contactPhone" styleClass="default_input"/><font color="red">*</font></td>
  </tr>
   <tr>
    <td width="20%" class="wordtd">�ط�˳��:</td>
   <td width="80%"><html:text property="reOrder" maxlength="255" styleClass="default_input" onblur="onlyNumber(this)" onkeypress="f_check_number2()"/><font color="red">*</font></td>
  </tr>

 <tr>
    <td class="wordtd"><bean:message key="returningmaintain.reMark"/>:</td>
    <td class="inputtd"><html:radio property="reMark" value="Y"/><bean:message key="pageword.yes"/><html:radio property="reMark" value="N"/><bean:message key="pageword.no"/></td>
  </tr>
  <tr>
    <td class="wordtd"><bean:message key="returningmaintain.enabledflag"/>:</td>
    <td class="inputtd"><html:radio property="enabledFlag" value="Y"/><bean:message key="pageword.yes"/><html:radio property="enabledFlag" value="N"/><bean:message key="pageword.no"/></td>
  </tr>
  <tr>
    <td class="wordtd"><bean:message key="returningmaintain.rem"/>:</td>
    <td><html:textarea property="rem" rows="5" cols="50" styleClass="default_textarea"/></td>
  </tr>
</table>
<br/>
<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  &nbsp;<input type="button" value=" + " onclick="addElevators('dynamictable_0')" class="default_input">
  <input type="button" value=" - " onclick="deleteRow('dynamictable_0')" class="default_input">           
  <b>&nbsp;�طÿͻ�ά����ϸ</b>
</div>
<div id="wrap_0" style="overflow: scroll;">
  <table id="dynamictable_0" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <thead>
      <tr id="titleRow_0">
        <td class="wordtd_header" ><input type="checkbox" name="cbAll" onclick="checkTableAll('dynamictable_0',this)"/></td>
        <td class="wordtd_header" nowrap="nowrap">��ͬ����</td>
        <td class="wordtd_header" nowrap="nowrap">��ͬ��</td>
        <td class="wordtd_header" nowrap="nowrap">��ͬ��ʼ����</td>
        <td class="wordtd_header" nowrap="nowrap">��ͬ��������</td>
      </tr>
    </thead>
    <!-- <tfoot>
      <tr height="15"><td colspan="20"></td></tr>
    </tfoot> -->
    <tbody>
      <tr id="sampleRow_0" style="display: none;">
        <td align="center"><input type="checkbox" onclick="cancelCheckAll('dynamictable_0','cbAll')"/></td>
        <!-- <td style="display: none;"><input type="text" name="billno" class="noborder_center"/></td> -->
        <td style="display: none;"><input type="text" name="wtBillno" readonly="readonly" class="noborder_center"/>
        <input type="text" name="wbBillno1" readonly="readonly" class="noborder_center"/>
        <input type="text" name="wbBillno" readonly="readonly" class="noborder_center"/>
        </td>
        <td align="center"><input type="text" name="r4" readonly="readonly" class="noborder_center"/></td>
        <td align="center"><input type="text" name="maintContractNo" onclick="contractdisplay(this);" readonly="readonly" class="link noborder_center"/></td>
        <td align="center"><input type="text" name="contractSdate" readonly="readonly" class="noborder_center"/></td>
        <td align="center"><input type="text" name="contractEdate" readonly="readonly" class="noborder_center"/></td>
      </tr>
     <logic:present name="returningMaintainDetailList">
        <logic:iterate id="e" name="returningMaintainDetailList">
          <tr>
            <td align="center"><input type="checkbox" onclick="cancelCheckAll(this)"/></td>
            <td style="display: none;"><input type="hidden" name="numno" value="${e.numno}"/></td>
            <td style="display: none;"><input type="hidden" name="billno" value="${e.billno}"/></td>
            <td style="display: none;"><input type="hidden" name="wbBillno" value="${e.wbBillno}"/></td>
            <logic:equal name="e" property="r4" value="ί��">
            <td style="display: none;"><input type="hidden" name="wbBillno1" value=""/></td>
            <td style="display: none;"><input type="hidden" name="wtBillno" value="${e.wbBillno}"/></td>
            
            </logic:equal>
            <logic:equal name="e" property="r4" value="ά��">
            <td style="display: none;"><input type="hidden" name="wbBillno1" value="${e.wbBillno}"/></td>
            <td style="display: none;"><input type="hidden" name="wtBillno" value=""/></td>
            
            </logic:equal>
            
            <td align="center">${e.r4}<input type="hidden" name="r4" value="${e.r4}"/></td>
            <td align="center">
	            <a onclick="contractdisplay(this);" class="link">${e.maintContractNo}</a>
            	<input type="hidden" name="maintContractNo" value="${e.maintContractNo}"/>
            </td>
            <td align="center">${e.contractSdate}<input type="hidden" name="contractSdate" value="${e.contractSdate}"/></td>
            <td align="center">${e.contractEdate}<input type="hidden" name="contractEdate" value="${e.contractEdate}"/></td>
          </tr>
        </logic:iterate>
      </logic:present>
    </tbody>    
  </table>
  <div style="width: 100%;padding-top: 0;padding-bottom: 0;border-top: 0" class="tb"></div>
</div>
<br>
<script type="text/javascript"> 
	$("document").ready(function() {
		initPage();  
	})
	
	function initPage(){	
		setDynamicTable("dynamictable_0","sampleRow_0");// ���ö�̬��ɾ�б��
		//setScrollTable("wrap_0","dynamictable_0",10);// ���ù������
		
		// ���ñ����ɾ��ʱ�������¼�
		/* setOnTableChange("dynamictable_0",function(){
			var rownums = document.getElementsByName("rownum"); 
			for(var i=0;i<rownums.length;i++){			
				rownums[i].value = i+1;
			}
		}); */	  
				 
		//$("#contractSdate,#contractEdate").bind("propertychange",function(){setContractPeriod();}); // �����ͬ��Ч��  
		//setTopRowDateInputsPropertychange(dynamictable_0); //��̬����һ�����ڿؼ����propertychange�¼�    
		//selectAddElements($("#isSelectAdd").val()); // ��Ϊѡ���½�ʱִ�еķ���

		//isFree($("#mainMode").val());//�жϱ�����ʽ�Ƿ���ѣ����ʱ�������ڡ��ʼ췢֤���ڡ��ƽ��ͻ����ڡ�ά��ȷ�����ڱ���
	}
</script>
</html:form>
