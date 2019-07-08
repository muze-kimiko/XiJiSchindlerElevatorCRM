<%@page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">

<html:errors/>
<logic:present name="errorstrinfo"><font color="red"><bean:write name="errorstrinfo"/></font></logic:present>

<html:form action="/returningMaintainAction.do?method=toUpdateRecord">
<html:hidden property="isreturn"/>
<html:hidden name="returningMaintainBean" property="billno"/>
<html:hidden property="id" value="${returningMaintainBean.billno}"/>
<html:hidden property="contracts"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td class="wordtd">��ͬ��ϵ��:</td>
    <td>
      <html:text name="returningMaintainBean" property="contacts" maxlength="255" styleId="contacts" styleClass="default_input"/><font color="red">*</font>
    </td>     
  </tr>
  <tr>
    <td class="wordtd">��ͬ��ϵ�˵绰:</td>
    <td>
      <html:text name="returningMaintainBean" property="contactPhone" maxlength="255" styleId="contactPhone" styleClass="default_input"/><font color="red">*</font>
    </td>     
  </tr>
  <td class="wordtd"><bean:message key="returningmaintain.reOrder"/>:</td>
    <td><html:text name="returningMaintainBean" property="reOrder" styleClass="default_input"  onblur="onlyNumber(this)" onkeypress="f_check_number2()" /><font color="red">*</font></td>
  </tr> 
 
  <tr>
    <td class="wordtd"><bean:message key="returningmaintain.reMark"/>:</td>
    <td>
	  <html:radio name="returningMaintainBean" property="reMark" value="Y"/><bean:message key="pageword.yes"/>
      <html:radio name="returningMaintainBean" property="reMark" value="N"/><bean:message key="pageword.no"/>
	</td>
  </tr>
 
  <tr>
    <td class="wordtd"><bean:message key="returningmaintain.enabledflag"/>:</td>
    <td>
	  <html:radio name="returningMaintainBean" property="enabledFlag" value="Y"/><bean:message key="pageword.yes"/>
      <html:radio name="returningMaintainBean" property="enabledFlag" value="N"/><bean:message key="pageword.no"/>
	</td>
  </tr>
  <tr>
    <td class="wordtd"><bean:message key="returningmaintain.rem"/>:</td>
    <td><html:textarea name="returningMaintainBean" property="rem" rows="5" cols="50" styleClass="default_textarea"/></td>
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
    <tfoot>
      <tr height="15"><td colspan="20"></td></tr>
    </tfoot>
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
        <!-- <td align="center"><input type="text" name="maintContractNo" onclick="simpleOpenWindow('returningMaintainDetailAction',this.value);" readonly="readonly" class="link noborder_center"/></td> -->
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
	            <a onclick="contractdisplay(this);" class="link" >${e.maintContractNo}</a>
            	<input type="hidden" name="maintContractNo" value="${e.maintContractNo}"/>
            </td>
            <td align="center">${e.contractSdate}<input type="hidden" name="contractSdate" value="${e.contractSdate}"/></td>
            <td align="center">${e.contractEdate}<input type="hidden" name="contractEdate" value="${e.contractEdate}"/></td>
          </tr>
        </logic:iterate>
      </logic:present>
    </tbody>    
  </table>
</div>
<br>
<script type="text/javascript"> 
	$("document").ready(function() {
		initPage();  
	})
	
	function initPage(){	
		setDynamicTable("dynamictable_0","sampleRow_0");// ���ö�̬��ɾ�б��
		
	}
</script>
</html:form>