<%@ page contentType="text/html;charset=GBK" %>

  <br> 
  <table width="100%" border="0" cellpadding="3" cellspacing="0"  class="tb">
    <tr height="23"><td colspan="6">&nbsp;<b>��������</b></td></tr>
    <tr>
      <td class="wordtd" nowrap><div align="center">�����</div></td>
      <td class="wordtd" nowrap><div align="center">��������</div></td> 
      <td class="wordtd" nowrap><div align="center">������</div></td>
      <td class="wordtd" nowrap><div align="center">�������</div></td>
      <td class="wordtd" nowrap><div align="center">��������</div></td>
      <td class="wordtd" nowrap><div align="center">�������</div></td>
    </tr>  
    <logic:present name="processApproveList">
     <logic:iterate name="processApproveList" id="item">
       <tr>
         <td width="10%" nowrap><bean:write name="item" property="taskId"/></td>
         <td width="10%" nowrap><bean:write name="item" property="taskName"/></td>
         <td width="10%" nowrap><bean:write name="item" property="userId"/></td>
         <td><bean:write name="item" property="approveRem"/></td>
         <td width="10%" nowrap><bean:write name="item" property="date1"/>&nbsp;&nbsp;<bean:write name="item" property="time1"/></td>
         <td width="10%" nowrap><bean:write name="item" property="approveResult"/></td>
       </tr>
     </logic:iterate>   
    </logic:present> 
  </table>