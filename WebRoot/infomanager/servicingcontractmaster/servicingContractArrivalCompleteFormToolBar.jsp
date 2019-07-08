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
<logic:equal name="typejsp" value="display">
AddToolBarItemEx("SaveSubmitBtn","../../common/images/toolbar/close.gif","","",'�ر�',"60","0","window.close()");
</logic:equal>
<logic:equal name="typejsp" value="search">
AddToolBarItemEx("SaveSubmitBtn","../../common/images/toolbar/fresh_record.gif","","",'ˢ���б�',"80","0","searchone('search')");
</logic:equal>
<logic:equal name="typejsp" value="arrival">
AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","searchone('search')");
AddToolBarItemEx("searchBtn","../../common/images/toolbar/search.gif","","",'<bean:message key="toolbar.search"/>',"65","1","searchtwo('arrival')");
AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod('arrival','N')");
AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="toolbar.returnsave"/>',"80","1","saveMethod('arrival','Y')");
</logic:equal>
<logic:equal name="typejsp" value="complete">
AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","searchone('search')");
AddToolBarItemEx("searchBtn","../../common/images/toolbar/search.gif","","",'<bean:message key="toolbar.search"/>',"65","1","searchtwo('complete')");
AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod('complete','N')");
AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="toolbar.returnsave"/>',"80","1","saveMethod('complete','Y')");
</logic:equal>

  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//ˢ���б�
function searchone(value){
  window.location = '<html:rewrite page="/ServicingContractMasterTaskSearchAction.do"/>?typejsp='+value;
}
//��ѯ
function searchtwo(value){
  document.wgchangeContractForm.typejsp.value=value;
  document.wgchangeContractForm.submit();
}
//����
function saveMethod(value,value2){
	if(value=="arrival"){
	var errorstr=savecheckinfo("");
	}else{
	var errorstr=savecheckinfo2("");	
	}
	if(errorstr==""){
	document.wgchangeContractForm.isreturn.value=value2;
	document.wgchangeContractForm.method.value=value;
	document.wgchangeContractForm.submit();
	}else{
	alert(errorstr);	
	}
} 

function checkedallbox(obj){
	var selected=obj.checked;
	var checkboxids=document.getElementsByName("checkboxids");
	for(var i=0;i<checkboxids.length;i++){
		checkboxids[i].checked=selected;
	}
}
function savecheckinfo(error){
	var checkboxids=document.getElementsByName("checkboxids");//��Ҫ����ĸ�ѡ��
	var forecastdates=document.getElementsByName("forecastdate");//Ԥ�Ƶ�������
	var comfirmdates=document.getElementsByName("comfirmdate");//ȷ�ϵ�������
	var boxnum=0;
	for(var i=0;i<checkboxids.length;i++){
		if(checkboxids[i].checked){
			var forecastdate=forecastdates[i].value;
			var comfirmdate=comfirmdates[i].value;
			if(forecastdate.trim()==""){
				error+=" ��ѡ��Ԥ�Ƶ������ڣ�\n";						
			}
			boxnum++;
			if(error!=""){
				error="���"+(i+1)+"��\n"+error;
				break;
			}
		}
	}
	if(boxnum==0){
		error="����ѡ��һ����¼���б��棡";
	}
	return error;
}

function savecheckinfo2(error){
	var checkboxids=document.getElementsByName("checkboxids");//��Ҫ����ĸ�ѡ��
	var itemUserId=document.getElementsByName("itemUserId");//��Ŀ������
	var appWorkDate=document.getElementsByName("appWorkDate");//�ɹ�����
	var enterArenaDate=document.getElementsByName("enterArenaDate");//��������
	var epibolyFlag=document.getElementsByName("epibolyFlag");//�����־
	var finishFlag=document.getElementsByName("finishFlag");//�깤��־
	var finishDate=document.getElementsByName("finishDate");//�깤����
	var boxnum=0;
	for(var i=0;i<checkboxids.length;i++){
		if(checkboxids[i].checked){
			var finishflagstr=finishFlag[i].value;
			var ItemUserIdstr=itemUserId[i].value;
			var AppWorkDatestr=appWorkDate[i].value;
			var EnterArenaDatestr=enterArenaDate[i].value;
			var EpibolyFlagstr=epibolyFlag[i].value;
			var FinishDatestr=finishDate[i].value;
			if(finishflagstr.trim()==""){
				error+=" ��ѡ���깤��־��\n";
			}else if(finishflagstr.trim()=="Y"){
				if(ItemUserIdstr.trim()==""){
					error+=" ��Ŀ�����˲���Ϊ�գ�\n";
				}
				if(AppWorkDatestr.trim()==""){
					error+=" �ɹ����ڲ���Ϊ�գ�\n";
				}
				if(EnterArenaDatestr.trim()==""){
					error+=" �������ڲ���Ϊ�գ�\n";
				}
				if(EpibolyFlagstr.trim()==""){
					error+=" ��ѡ�������־��\n";
				}
				if(FinishDatestr.trim()==""){
					error+=" �깤���ڲ���Ϊ�գ�\n";
				}
			}
			boxnum++;
			if(error!=""){
				error="���"+(i+1)+"��\n"+error;
				break;
			}
		}
	}
	if(boxnum==0){
		error="����ѡ��һ����¼���б��棡";
	}
	return error;
}

/** ��ֵ */
function sethiddenvalue(obj,namestr,num){
	var nameval=document.getElementsByName(namestr)[num];
	if(obj.checked){
		nameval.value="Y";
	}else{
		nameval.value="N";
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