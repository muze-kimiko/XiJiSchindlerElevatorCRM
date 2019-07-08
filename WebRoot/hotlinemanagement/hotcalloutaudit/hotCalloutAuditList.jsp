<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<html:errors />
<br>
<html:form action="/ServeTable.do">
<html:hidden property="property(genReport)" styleId="genReport" />
  <table>
    <tr>
    <td>       
      ���ޱ��:
      </td>
      <td>
        <html:text property="property(calloutMasterNo)" styleClass="default_input" />
      </td> 
      <td>      
         &nbsp;&nbsp;���޵�λ����:
      </td>
      <td>
        <html:text property="property(companyId)"  styleClass="default_input" />
      </td>
        <td>      
       &nbsp;&nbsp;�����:
      </td>
      <td>
        <html:text property="property(operId)"  styleClass="default_input" />
      </td>       
      <td>      
         &nbsp;&nbsp;���޵��ݱ��:
      </td>
      <td>
        <html:text property="property(elevatorNo)"  styleClass="default_input" />
      </td>        
    </tr>
    <tr> 
         
      <%-- 
        <td> 
       	����״̬:
      </td>
      <td>
        <html:select property="property(handleStatus)">
        <html:option value="%">��ѡ��</html:option>
        <html:options collection="PulldownList" property="id.pullid" labelProperty="pullname"/>
        </html:select>
      </td>                
      <td>       
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ύ��־:
      </td>
      <td>
        <html:select property="property(SubmitType)">
        <html:option value="%">��ѡ��</html:option>
        <html:option value="Y">���ύ</html:option>
        <html:option value="N">δ�ύ</html:option>
        </html:select>
      </td>  
      --%>
       <td> 
      	 ά��վ:
      </td>
      <td>
        <html:select property="property(maintStation)">
        <html:option value="%">��ѡ��</html:option>
        <html:options collection="storageidList" property="storageid" labelProperty="storagename"/>
        </html:select>
      </td> 
        <td> 
      	&nbsp;&nbsp; ���޷�ʽ:
      </td>
      <td>
        <html:select property="property(repairMode)">
        <html:option value="%">��ѡ��</html:option>
        <html:options collection="rmList" property="id.pullid" labelProperty="pullname"></html:options>
        </html:select>
      </td>                     
      <td>       
        &nbsp;&nbsp;�������:
      </td>
      <td>
        <html:select property="property(serviceObjects)">
        <html:option value="%">��ѡ��</html:option>
        <html:options collection="soList" property="id.pullid" labelProperty="pullname"></html:options>
        </html:select>
      </td>    
    </tr>
  </table>
  <br>
  <table:table id="guiHotCalloutModify" name="hotCalloutAuditList">
    <logic:iterate id="element" name="hotCalloutAuditList">
      <table:define id="c_cb">
        <html:radio property="checkBoxList(ids)" styleId="ids" value="${element.calloutMasterNo }" />    
      </table:define>
      <table:define id="c_calloutMasterNo">
        <a href="<html:rewrite page="/hotCalloutAuditAction.do"/>?method=toDisplayRecord&typejsp=sh&id=${element.calloutMasterNo }">${element.calloutMasterNo }</a>  
      </table:define>
      <table:define id="c_companyId">
      <logic:equal name="element" property="handleStatus" value="3">
	  <font color="red">${element.companyName }</font>
	  </logic:equal>
	  <logic:notEqual name="element" property="handleStatus" value="3">
	  ${element.companyName }
	  </logic:notEqual>
      </table:define>
      <table:define id="c_elevatorNo">
      <logic:equal name="element" property="handleStatus" value="3">
	  <font color="red">${element.elevatorNo }</font>
	  </logic:equal>
	  <logic:notEqual name="element" property="handleStatus" value="3">
	  ${element.elevatorNo }
	  </logic:notEqual>
      </table:define>
      <table:define id="c_operName">
      <logic:equal name="element" property="handleStatus" value="3">
	  <font color="red">${element.operName }</font>
	  </logic:equal>
	  <logic:notEqual name="element" property="handleStatus" value="3">
	  ${element.operName }
	  </logic:notEqual>    
      </table:define>
      <table:define id="c_operDateLayout">
      <logic:equal name="element" property="handleStatus" value="3">
	  <font color="red">${element.operDate }</font>
	  </logic:equal>
	  <logic:notEqual name="element" property="handleStatus" value="3">
	  ${element.operDate }
	  </logic:notEqual>    
      </table:define>
      <table:define id="c_handleStatus">
      <logic:equal name="element" property="handleStatus" value="3">
	  	<font color="red">${element.handleStatusName }</font>
	  </logic:equal>
	  <logic:notEqual name="element" property="handleStatus" value="3">
	  	${element.handleStatusName }
	  </logic:notEqual> 
      </table:define>
      <table:define id="c_isTrap">
      <logic:equal name="element" property="handleStatus" value="3">
	  <font color="red">${element.isTrap }</font>
	  </logic:equal>
	  <logic:notEqual name="element" property="handleStatus" value="3">
	 ${element.isTrap }
	  </logic:notEqual>    
      </table:define>
      <table:define id="c_completeTime">
      <logic:equal name="element" property="handleStatus" value="3">
	  <font color="red"> ${element.completeTime }</font>
	  </logic:equal>
	  <logic:notEqual name="element" property="handleStatus" value="3">
	  ${element.completeTime }
	  </logic:notEqual>    
      </table:define>
      <table:define id="c_isStop">
      <logic:equal name="element" property="handleStatus" value="3">
	  <font color="red"> ${element.isStop }</font>
	  </logic:equal>
	  <logic:notEqual name="element" property="handleStatus" value="3">
	  ${element.isStop }
	  </logic:notEqual>   
      </table:define>
      <table:define id="c_submitType">
      <logic:equal name="element" property="handleStatus" value="3">
	  <font color="red"> ${element.submitType }</font>
	  </logic:equal>
	  <logic:notEqual name="element" property="handleStatus" value="3">
	 ${element.submitType }
	  </logic:notEqual>         
      </table:define>
       <table:define id="c_repairMode">
       <logic:equal name="element" property="handleStatus" value="3">
	  <font color="red">${element.repairMode }</font>
	  </logic:equal>
	  <logic:notEqual name="element" property="handleStatus" value="3">
	 ${element.repairMode }
	  </logic:notEqual>         
      </table:define>
       <table:define id="c_serviceObjects">
       <logic:equal name="element" property="handleStatus" value="3">
	  <font color="red">${element.serviceObjects } </font>
	  </logic:equal>
	  <logic:notEqual name="element" property="handleStatus" value="3">
	 ${element.serviceObjects } 
	  </logic:notEqual>
        
      </table:define>
       <table:define id="c_maintStation">
        <logic:equal name="element" property="handleStatus" value="3">
	  <font color="red">${element.maintStation } </font>
	  </logic:equal>
	  <logic:notEqual name="element" property="handleStatus" value="3">
	 ${element.maintStation }
	  </logic:notEqual>
         
      </table:define>
      <table:tr />
    </logic:iterate>
  </table:table>
</html:form>
<script type="text/javascript">
function toexamine(obj,value){
	var  shs=document.getElementsByName("sh");
	var  calloutMasterNojxs=document.getElementsByName("calloutMasterNojx");
	for(var i=0;i<shs.length;i++){
		if(shs[i]==obj){
			window.location='<html:rewrite page="/hotphoneAction.do"/>?typejsp='+value+'&id='+calloutMasterNojxs[i].value;
			return;
		}
	}
}
</script>