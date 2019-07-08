<%@page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<br>
<html:errors/>
<logic:present name="lostElevatorMaintainBean">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td class="wordtd"><%-- <bean:message key="returningmaintain.contacts"/> --%>��ͬ��ϵ��:</td>
    <td class="inputtd"><bean:write name="lostElevatorMaintainBean" scope="request" property="contacts"/></td>
  </tr>
  <tr>
    <td class="wordtd">��ͬ��ϵ�˵绰:</td>
    <td class="inputtd"><bean:write name="lostElevatorMaintainBean" scope="request" property="contactPhone"/></td>
  </tr>
  <tr>
    <td width="20%" class="wordtd">��λ����:</td>
    <td width="80%" class="inputtd"><bean:write name="lostElevatorMaintainBean" scope="request" property="companyName"/></td>
  </tr>
  
  <tr>
    <td class="wordtd">�ط�˳��:</td>
    <td class="inputtd"><bean:write name="lostElevatorMaintainBean" scope="request" property="reOrder"/></td>
  </tr>
  
  <tr>
    <td class="wordtd">�طñ��:</td>
    <td class="inputtd">
	<logic:match name="lostElevatorMaintainBean" property="reMark" value="Y">
	<bean:message key="pageword.yes"/>
	</logic:match>
	<logic:match name="lostElevatorMaintainBean" property="reMark" value="N">
	<bean:message key="pageword.no"/>
	</logic:match>
	</td>
  </tr>
  
  <tr>
    <td class="wordtd">���ñ�־:</td>
    <td class="inputtd">
	<logic:match name="lostElevatorMaintainBean" property="enabledFlag" value="Y">
	<bean:message key="pageword.yes"/>
	</logic:match>
	<logic:match name="lostElevatorMaintainBean" property="enabledFlag" value="N">
	<bean:message key="pageword.no"/>
	</logic:match>
	</td>
  </tr>
  <tr>
    <td class="wordtd">��ע:</td>
    <td class="inputtd"><bean:write name="lostElevatorMaintainBean" scope="request" property="rem"/></td>
  </tr>
</table>
<br/>
<%-- <div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  &nbsp;           
  <b>&nbsp;���ݿͻ���ͬ��ϸ</b>
</div>
<div id="wrap_0" style="overflow: scroll;">
  <table id="dynamictable_0" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <thead>
      <tr id="titleRow_0">
        <td class="wordtd_header" ><input type="checkbox" name="cbAll" onclick="checkTableAll('dynamictable_0',this)"/></td>
        <td class="wordtd_header" nowrap="nowrap">ά����ͬ��</td>
        <td class="wordtd_header" nowrap="nowrap">��������</td>
        <td class="wordtd_header" nowrap="nowrap">ԭ�����</td>
      </tr>
    </thead>
    <tfoot>
      <tr height="15"><td colspan="20"></td></tr>
    </tfoot>
    <tbody>
   
      <logic:present name="lostElevatorMaintainDetailList">
        <logic:iterate id="e" name="lostElevatorMaintainDetailList">
          <tr>
            <td align="center"><input type="checkbox" onclick="cancelCheckAll(this)"/></td>
     
            <td align="center">
           <a onclick="simpleOpenWindow('lostElevatorMaintainDetailAction','${e.maintContractNo}');" class="link">${e.maintContractNo}</a>
            </td>
            <td align="center">${e.lostElevatorDate}</td>
            <td align="center">${e.causeAnalysis}</td>
          </tr>
        </logic:iterate>
      </logic:present>
    </tbody>    
  </table>
</div> --%>
</logic:present>