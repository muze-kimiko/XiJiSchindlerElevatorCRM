<%@ page contentType="text/html;charset=GBK"%>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
      
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
      <thead>
        <tr height="12">
          <td colspan="4">            
          
          </td>
        </tr>
        <%-- tr class="wordtd_header">
			<td width="10%">˳��</td>
			<td>�����Ŀ</td>
			<td>��ֵ</td>
			<td width="10%"><bean:message key="qualitycheckmanagement.appendix"/></td>
        </tr--%>
        <tr class="wordtd_header">
			<td width="20%">��������</td>
			<td>������ϸ</td>
			<logic:notPresent name="dispose"><td width="5%">��ֵ</td></logic:notPresent>
			<td width="20%">��ע</td>
			<td width="8%"><bean:message key="qualitycheckmanagement.appendix"/></td>
        </tr>
      </thead>
      <tfoot>
        <tr height="12"><td colspan="4"></td></tr>
      </tfoot>
        
    </table>
    
  
  <script type="text/javascript">  
  
    
  </script>