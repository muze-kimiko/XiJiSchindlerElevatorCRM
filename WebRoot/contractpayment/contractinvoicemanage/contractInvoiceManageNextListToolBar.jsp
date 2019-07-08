<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/search.gif","","",'<bean:message key="toolbar.search"/>',"65","1","searchMethod()");
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="ncontractinvoicemanage" value="Y"> 
    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/add.gif","","",'��һ��',"65","1","addMethod()");
    AddToolBarItemEx("WarnBtn","../../common/images/toolbar/edit.gif","","",'��д���ѱ�ע',"100","1","warnMethod()");
  </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

//��ע����
	function warnMethod(){
	  if(serveTableForm.ids)
	  {
	    var l = document.serveTableForm.ids.length;
	    if(l)
	    {
	      for(i=0;i<l;i++)
	      {
	        if(document.serveTableForm.ids[i].checked == true)
	        {
	          var jnlNo = document.serveTableForm.ids[i].value;
	          //var warnRem=document.getElementsByName("warnRem")[i].value;
	          //var warnRem2=prompt("��д���ѱ�ע��",warnRem);
	          //window.location = '<html:rewrite page="/contractInvoiceManageAction.do"/>?jnlNo='+jnlNo+'&warnRem='+warnRem2+'&method=toPrepareWarnRecord';
	          
	          var url='query/Search.jsp?path=<html:rewrite page="/contractInvoiceManageAction.do" />?warnjnlNo='+jnlNo;
	          var obj = window.showModalDialog(url,window,'dialogWidth:700px;dialogHeight:300px;center:yes;scroll:no;status:yes;');
 			  if(obj){
 				 searchMethod();
 			  }
	          return;       
	        }
	      }
	      if(l >0)
	      {
	        alert("��ѡ��һ����¼��");
	      }
	    }else if(document.serveTableForm.ids.checked == true)
	    {  
	      var jnlNo = document.serveTableForm.ids.value;
          //var warnRem=document.getElementsByName("warnRem")[0].value;
          //var warnRem2=prompt("��д���ѱ�ע��",warnRem);
          //window.location = '<html:rewrite page="/contractInvoiceManageAction.do"/>?jnlNo='+jnlNo+'&warnRem='+warnRem2+'&method=toWarnRecord';
          
          var url='query/Search.jsp?path=<html:rewrite page="/contractInvoiceManageAction.do" />?warnjnlNo='+jnlNo;
          var obj = window.showModalDialog(url,window,'dialogWidth:700px;dialogHeight:300px;center:yes;scroll:no;status:yes;');
		  if(obj){
			 searchMethod();
		  }
          return; 
	    }
	    else
	    {
	      alert("��ѡ��һ����¼��");
	    }
	  }

	}

//����
function returnMethod(){
  window.location = '<html:rewrite page="/contractInvoiceManageSearchAction.do"/>?method=toSearchRecord';
}

//��ѯ
function searchMethod(){
  serveTableForm.target = "_self";
  document.serveTableForm.submit();
}

//����
function addMethod(){
  if(serveTableForm.ids)
  {
    var l = document.serveTableForm.ids.length;
    if(l)
    {
      for(i=0;i<l;i++)
      {
        if(document.serveTableForm.ids[i].checked == true)
        {
          var billno = document.serveTableForm.ids[i].value;
          
          window.location = '<html:rewrite page="/contractInvoiceManageAction.do"/>?billno='+billno+'&method=toPrepareAddRecord';
          return;       
        }
      }
      if(l >0)
      {
        alert("��ѡ��һ����¼��");
      }
    }else if(document.serveTableForm.ids.checked == true)
    {  
      var billno = document.serveTableForm.ids.value;
        
      window.location = '<html:rewrite page="/contractInvoiceManageAction.do"/>?billno='+billno+'&method=toPrepareAddRecord';
      return; 
    }
    else
    {
      alert("��ѡ��һ����¼��");
    }
  }

}
</script>

  <tr>
    <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="22" class="table_outline3" valign="bottom" width="100%">
            <div id="toolbar" align="center">
            <script language="javascript">
              CreateToolBar();
            </script>
            </div>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>