  <%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<!--
	�ͻ�������ҳ������
-->
<script language="javascript">
//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/search.gif","","",'<bean:message key="toolbar.search"/>',"65","0","searchMethod()"); 
  AddToolBarItemEx("ExcelBtn","../../common/images/toolbar/xls.gif","","",'<bean:message key="toolbar.xls"/>',"90","1","excelMethod()");
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

//��ѯ
function searchMethod(){
	var elevatorType = document.getElementById("elevatorType").value;
	
	
	if(elevatorType=="")
	{
		alert("����ѡ��һ���������ͣ�");
		return;
	}
	
	
	var edate1 = document.getElementById("edate1").value;
	var sdate1 =document.getElementById("sdate1").value;
	
	
	if(sdate1!=""&&edate1!=""){	
       if(sdate1>edate1)
     	{
      	alert("��ʼ���ڱ���С�ڽ������ڣ�");
      	document.getElementById("edate1").value="";
	     return;
	   }
    }/* else
    {
    	alert("����ѡ��ʼ�������ڣ�");
		return;
    } */ 
	
	inspectionReportForm.genReport.value="";
	inspectionReportForm.target = "_blank";
	document.inspectionReportForm.submit();

}

 function addEdate(sdata,edate)
 {
     if(sdata!=null&&sdata!=""){
	 var year=Number(sdata.substr(0, 4)); 
     var edate1=document.getElementById(edate);
     
	 edate1.value=(year+1)+"-"+sdata.substr(5);
     }
 }


//����Excel
function excelMethod(){
	 var elevatorType = document.getElementById("elevatorType").value;
	
	
	if(elevatorType=="")
	{
		alert("����ѡ��һ���������ͣ�");
		return;
	}
	
	
	var edate1 = document.getElementById("edate1").value;
	var sdate1 =document.getElementById("sdate1").value;
	
	
	if(sdate1!=""&&edate1!=""){	
       if(sdate1>edate1)
     	{
      	alert("��ʼ���ڱ���С�ڽ������ڣ�");
      	document.getElementById("edate1").value="";
	     return;
	   }
    }/* else
    {
    	alert("����ѡ��ʼ�������ڣ�");
		return;
    }  */
	inspectionReportForm.genReport.value="Y";
	inspectionReportForm.target = "_blank";
	document.inspectionReportForm.submit();
}


</script>
  <tr>
    <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="22" class="table_outline3" valign="bottom" width="100%">
            <div id="toolbar" align="center">
            <script language="javascript">
              //<!--
                CreateToolBar();
              //-->
            </script>
            </div>
            </td>
        </tr>
      </table>
    </td>
  </tr>
</table>