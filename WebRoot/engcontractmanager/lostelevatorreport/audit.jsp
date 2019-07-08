<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>

<style>
  .show{display:block;}
  .hide{display:none;}
</style>

<br/>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td height="23" colspan="6">&nbsp;<b>�ط���Ϣ</td>
    </tr>
    <tr>
      <td class="wordtd" nowrap="nowrap">�ط���:</td>
      <td width="20%">
        ${lostElevatorReportBean.hfOperid}
      </td>        
      <td class="wordtd" nowrap="nowrap">�ط�ʱ��:</td>
      <td width="20%">
        ${lostElevatorReportBean.hfDate}
      </td>          
      <td class="wordtd" nowrap="nowrap">&nbsp;</td>
      <td width="20%">&nbsp;</td>       
    </tr>
    <tr>
      <td class="wordtd_a" nowrap="nowrap">��һ�λطý��:</td>
      <td colspan="5">${lostElevatorReportBean.hfRemLast}</td> 
    </tr>
    <tr>
      <td class="wordtd_a" nowrap="nowrap">�طý��:</td>
      <td colspan="5">
      	  <span class="hf show">${lostElevatorReportBean.fhRem}</span>
          <span class="hf hide">
            <logic:equal name="lostElevatorReportBean" property="fhRem" value="">
              <html:textarea name="lostElevatorReportBean" property="fhRem" rows="3" cols="100" styleClass="default_textarea"/>
            </logic:equal>
            <logic:notEqual name="lostElevatorReportBean" property="fhRem" value="">
            	${lostElevatorReportBean.fhRem}
            </logic:notEqual>
          </span>
      </td> 
    </tr> 
  </table> 
  <br>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td height="23" colspan="6">&nbsp;<b>�ֲ��������Ϣ</td>
    </tr>

    <tr>
      <td class="wordtd_a" nowrap="nowrap">�Ƿ�ۿ�:</td>
      <td colspan="5">
      	<span class="audit show2">
      		<logic:equal name="lostElevatorReportBean" property="isCharge" value="Y">�ۿ�</logic:equal>
        	<logic:equal name="lostElevatorReportBean" property="isCharge" value="N">���ۿ�</logic:equal>
      	</span>         
          <span class="audit hide2">
            <logic:equal name="lostElevatorReportBean" property="auditStatus2" value="N">
		        <html:select name="lostElevatorReportBean" property="isCharge" styleClass="default_input">
	    			<html:option value="">--��ѡ��--</html:option>
	    			<html:option value="Y">�ۿ�</html:option>
				  	<html:option value="N">���ۿ�</html:option>
	    		</html:select><font color="red">*</font>
            </logic:equal>
            <logic:equal name="lostElevatorReportBean" property="auditStatus2" value="Y">
            	<logic:equal name="lostElevatorReportBean" property="isCharge" value="Y">�ۿ�</logic:equal>
        		<logic:equal name="lostElevatorReportBean" property="isCharge" value="N">���ۿ�</logic:equal>
            </logic:equal>
          </span>
      </td> 
    </tr> 
    
    <tr>
      <td class="wordtd" nowrap="nowrap">�ֲ��������:</td>
      <td width="20%">
        ${lostElevatorReportBean.auditOperid2}
      </td>     
      <td class="wordtd" nowrap="nowrap">�ֲ������״̬:</td>
      <td width="20%">
        <logic:equal name="lostElevatorReportBean" property="auditStatus2" value="Y">�����</logic:equal>
        <logic:equal name="lostElevatorReportBean" property="auditStatus2" value="N">δ���</logic:equal>
        <logic:equal name="lostElevatorReportBean" property="auditStatus2" value="R">����</logic:equal>
      </td>
      <td class="wordtd" nowrap="nowrap">�ֲ������ʱ��:</td>
      <td width="20%">
        ${lostElevatorReportBean.auditDate2}
      </td>
    </tr>
    <tr>
      <td class="wordtd_a" nowrap="nowrap">�ֲ���������:</td>
      <td colspan="5">
      	<span class="audit show2">${lostElevatorReportBean.auditRem2}</span>         
          <span class="audit hide2">
            <logic:equal name="lostElevatorReportBean" property="auditStatus2" value="N">
              <html:textarea name="lostElevatorReportBean" property="auditRem2" rows="3" cols="100" styleClass="default_textarea"/>
            </logic:equal>
            <logic:equal name="lostElevatorReportBean" property="auditStatus2" value="Y">
            	${lostElevatorReportBean.auditRem2}
            </logic:equal>
          </span>
      </td> 
    </tr> 
  </table>
  
   <br>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td height="23" colspan="6">&nbsp;<b>�ܲ��������Ϣ</td>
    </tr>
    
    <tr>
      <td class="wordtd" nowrap="nowrap">�ܲ��������:</td>
      <td width="20%">
        ${lostElevatorReportBean.auditOperid3}
      </td>     
      <td class="wordtd" nowrap="nowrap">�ܲ������״̬:</td>
      <td width="20%">
        <logic:equal name="lostElevatorReportBean" property="auditStatus3" value="Y">�����</logic:equal>
        <logic:equal name="lostElevatorReportBean" property="auditStatus3" value="N">δ���</logic:equal>
        <logic:equal name="lostElevatorReportBean" property="auditStatus3" value="R">����</logic:equal>
      </td>
      <td class="wordtd" nowrap="nowrap">�ܲ������ʱ��:</td>
      <td width="20%">
        ${lostElevatorReportBean.auditDate3}
      </td>
    </tr>
    <tr>
      <td class="wordtd_a" nowrap="nowrap">�ܲ���������:</td>
      <td colspan="5">
      	<span class="audit show3">${lostElevatorReportBean.auditRem3}</span>         
          <span class="audit hide3">
            <logic:equal name="lostElevatorReportBean" property="auditStatus3" value="N">
              <html:textarea name="lostElevatorReportBean" property="auditRem3" rows="3" cols="100" styleClass="default_textarea"/>
            </logic:equal>
            <logic:equal name="lostElevatorReportBean" property="auditStatus3" value="Y">
            	${lostElevatorReportBean.auditRem3}
            </logic:equal>
          </span>
      </td> 
    </tr> 
  </table>
  
 <br>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td height="23" colspan="6">&nbsp;<b>�����Ϣ</td>
    </tr>
    <tr>
      <td class="wordtd" nowrap="nowrap">�����:</td>
      <td width="20%">
        ${lostElevatorReportBean.auditOperid}
      </td>     
      <td class="wordtd" nowrap="nowrap">���״̬:</td>
      <td width="20%">
        <logic:equal name="lostElevatorReportBean" property="auditStatus" value="Y">�����</logic:equal>
        <logic:equal name="lostElevatorReportBean" property="auditStatus" value="N">δ���</logic:equal>
        <logic:equal name="lostElevatorReportBean" property="auditStatus" value="R">����</logic:equal>
      </td>         
      <td class="wordtd" nowrap="nowrap">���ʱ��:</td>
      <td width="20%">
        ${lostElevatorReportBean.auditDate}
      </td>        
    </tr>
    <tr>
      <td class="wordtd_a" nowrap="nowrap">������:</td>
      <td colspan="5">
      	 <span class="audit show">${lostElevatorReportBean.auditRem}</span>         
          <span class="audit hide">
            <logic:equal name="lostElevatorReportBean" property="auditStatus" value="N">
              <html:textarea name="lostElevatorReportBean" property="auditRem" rows="3" cols="100" styleClass="default_textarea"/>
            </logic:equal>
            <logic:equal name="lostElevatorReportBean" property="auditStatus" value="Y">
            	${lostElevatorReportBean.auditRem}
            </logic:equal>
          </span>
      </td> 
    </tr> 
  </table>