<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>

<!--
	���Ƶ���������ʾ
	����ҳ��Ҫ�ṩ��һ��</table>��˴�ƥ���һ��<table width="100%">
-->

<table width="100%">
  <tr>
    <td width="100%">
      <table width="100%" cellspacing="0" cellpadding="0" class="top_navigation" >
        <tr>
          <td valign="middle" height="23">
            &nbsp;&nbsp;<bean:message key="navigator.user" /> ��<bean:write name="USER_INFO" scope="session" property="userName"/>
			&nbsp;&nbsp;<bean:message key="navigator.location" /> ��
						<logic:present name="navigator.location" >
							<bean:write name="navigator.location"/>
						</logic:present>
          </td>
        </tr>
      </table>
    </td>
  </tr>