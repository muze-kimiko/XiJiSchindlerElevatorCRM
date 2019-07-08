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

  //AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  AddToolBarItemEx("ReturnTkBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="button.returntask"/>',"110","0","returnTaskMethod()");	
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmyTaskOA" value="Y"> 
    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="button.submit"/>',"65","1","saveMethod()");
  </logic:equal>
  </logic:notPresent>
  
  AddToolBarItemEx("ViewFlow","../../common/images/toolbar/viewdetail.gif","","",'<bean:message key="toolbar.viewflow"/>',"110","1","viewFlow()");
  
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//ȥ���ո�
String.prototype.trim = function(){ return this.replace(/(^\s*)|(\s*$)/g,""); }

//����
/* function returnMethod(){
  window.location = '<html:rewrite page="/maintContractQuoteSearchAction.do"/>?method=toSearchRecord';
} */

//���ش����б�
function returnTaskMethod(){
	window.location ='<html:rewrite page="/myTaskOaSearchAction.do"/>?method=toDoList&jumpto=templetdoorapp';
}

//�ύ
function saveMethod(){
	var flags=getSelectValue();
	if(flags!="" && flags!="1"){
		alert("��Ǹ,��ѡ����'��ͬ��',�����������������д��������!");
		return;
	}
	if('${approve}' == "Y" ){
		document.getElementById("SaveBtn").disabled=true;
		document.maintContractQuoteJbpmForm.submit();
	}else{
		if(checkInput()){
			if(document.getElementsByName("finallyQuote")!=null){
				//var standardQuote=document.getElementsByName("standardQuote");//��׼����
				var finallyQuote=document.getElementsByName("finallyQuote");//���ձ���
				for(var i=0;i<finallyQuote.length;i++){
					//if(standardQuote[i].value==""){
					//	alert("�� "+(i+1)+" �У���׼���۲���Ϊ�գ�������׼���ۣ�");
					//	return;
					//}
					if(finallyQuote[i].value==""){
						alert("�� "+(i+1)+" �У����ձ��۲���Ϊ�գ�");
						return;
					}
				}
				//�����׼�۸� 
				if(jsQuote()){
					document.getElementById("SaveBtn").disabled=true;
					document.maintContractQuoteJbpmForm.submit();
				}
			}else{
				document.getElementById("SaveBtn").disabled=true;
				document.maintContractQuoteJbpmForm.submit();
			}
		}
	}
}

//�鿴����
function viewFlow(){	
	var hiddenReturnStatus=document.getElementById("status");
	if(hiddenReturnStatus.value=="-1"){
		alert("����δ�������޷��鿴��������ͼ��");
	}else{
		var avf=document.getElementById("avf");
		var tokenid=document.getElementById("tokenid");
		var flowname=document.getElementById("flowname");
		if(tokenid!=null && tokenid.value!=""){
			if(avf!=null && tokenid!=null && flowname!=null){
				avf.href='<html:rewrite page="/viewProcessAction.do"/>?method=toViewProcess&tokenid='+tokenid.value+'&flowname='+flowname.value;
				avf.click();
			}else{
				alert("��ѡ��һ��Ҫ�鿴�ļ�¼��");
			}
		}else{
			alert("�ü�¼Ϊ��ʷ���ݣ��޷��鿴��������ͼ��");
		}
	}
}

//ȡselect���ı�ֵ
function getSelectValue(){
	var str="0";
	var obj=document.getElementById('approveresult');

 	var index=obj.selectedIndex; //��ţ�ȡ��ǰѡ��ѡ������	
 	var val = obj.options[index].text;
 	if(val!="" && (val=="ͬ��" || val=="�ر�" || val.indexOf("�ύ")>-1)){
 	    str="1";
 	}else{
 		if(document.getElementById("approverem").value.trim()!="" || document.getElementById("approverem").value.trim().length>0){
 			str="1";
 		}
 	}
 	return str;
	
}

function addElevators(tableId){
	var tableObj = document.getElementById(tableId);
	var paramstring = "elevatorNos=";		
	var elevatorNos = document.getElementsByName("elevatorNo");
	for(i=0;i<elevatorNos.length;i++){
		paramstring += i<elevatorNos.length-1 ? "|"+elevatorNos[i].value+"|," : "|"+elevatorNos[i].value+"|"		
	}

	var returnarray = openWindowWithParams("searchElevatorSaleAction","toSearchRecord2",paramstring);

	if(returnarray!=null && returnarray.length>0){					
		addRows(tableId,returnarray.length);
		toSetInputValue(returnarray,"maintContractQuoteJbpmForm");
	}			
}

function checkInput(){  
	  inputTextTrim();// ҳ�������ı������ȥ��ǰ��ո�
	  var boo = false;
	  
	  var companyName=document.getElementById("companyName").value;
	  if(companyName==""){
		  alert("�׷���λ���� ����Ϊ�գ�");
		  boo = false;
	  }else{
		  boo=true;
	  }
	  if(boo == true){
		  var maintStation=document.getElementById("maintStation").value;
		  if(maintStation==""){
			  alert("��ѡ�� ����ά��վ��");
			  boo = false;
		  }else{
			  boo=true;
		  }
	  }
	  if(boo == true){
		  var paymentMethodApply=document.getElementById("paymentMethodApply").value;
		  if(paymentMethodApply==""){
			  alert("��ѡ�� ���ʽ���룡");
			  boo = false;
		  }else{
			  //����
			  if(paymentMethodApply=="99"){
				  var paymentMethodRem=document.getElementById("paymentMethodRem").value;
				  if(paymentMethodRem==""){
					  alert("���ʽ���뱸ע ����Ϊ�գ�");
					  boo = false;
				  }else{
					  boo=true;
				  }
			  }else{
				  boo=true;
			  }
		  }
	   }
	  if(boo == true){
		  var appnum=0;
		  var contractContentApply = document.getElementsByName("contractContentApply"); 
		  for(var c=0;c<contractContentApply.length;c++){
			if(contractContentApply[c].checked==true){
				appnum++;
				break;
			}  
		  }
		  if(appnum==0){
			  //alert("��ѡ�� ��ͬ�������������");
			  //boo = false;
		  }else{
			  var valnum=0;
			  for(var c=0;c<contractContentApply.length;c++){
				if(contractContentApply[c].checked==true && contractContentApply[c].value=="99"){
					valnum++;
					break;
				}  
			  }
			  if(valnum>0){
				  var contractContentRem=document.getElementById("contractContentRem").value;
				  if(contractContentRem==""){
					  alert("��ͬ�������������ע ����Ϊ�գ�");
					  boo = false;
				  }else{
					  boo=true;
				  }
			  }
		  }
	  }
	  
	  if(boo == true){
		  var elevatorNos = document.getElementsByName("elevatorNo"); 
		  if(elevatorNos !=null && elevatorNos.length>0){
		    boo = checkRowInput("dynamictable_0","titleRow_0");
		  }else{
		    alert("����� ������Ϣ ��");
		    boo = false;
		  }
	   }
	  
	  return boo;
	}

function isShowHidden(obj){
	//alert(obj.value);
	var target=document.getElementById("trok");
	if(obj.value=="99"){
		target.style.display="";
	}else{
		target.style.display="none";
	}
}
function isShowHidden2(){
	var appnum=0;
	var contractContentApply = document.getElementsByName("contractContentApply"); 
	for(var c=0;c<contractContentApply.length;c++){
	  if(contractContentApply[c].checked==true && contractContentApply[c].value=="99"){
			appnum++;
			break;
	  }  
	}
	var target=document.getElementById("trok2");
	if(appnum>0){
		target.style.display="";
	}else{
		target.style.display="none";
	}
}


/**===========================�����׼����=======================*/
/** ���ַ�������ȡ���� */
function getNum(text){
	//var value = text.replace(/[^0-9]/ig,""); //��ȡ����
	var value = text.replace(/[^\d.]/g,"");//��ȡ���ֺ�С����
	return value;
}
/** ���㹫ʽ 
 "ֱ�ݵı�׼�۸�/̨ = ����*����*��������ϵ��*����ϵ��*����ϵ��*��ϵ��*��������ϵ��*ά��վϵ��*��ͬ�������������ϵ��*
                                                ֧����ʽϵ��*��ͬ����ϵ��+���ѽ��+������У����+����֬+��"

"���ݵı�׼�۸�/̨ = ����*����*��������ϵ��*��������ϵ��*ά��վϵ��*��ͬ�������������ϵ��*
                                            ֧����ʽϵ��*��ͬ����ϵ��+���ѽ��+������У����+����֬+��"
                                            
                                            ������У����+����֬+�� ��Ҫ�����·�

    ��Χ�ȽϹ�ʽ�����ڵ�����С�� С�ڵ������ģ�>=0 and <=11��
 */

function jsQuote(){
	
	<logic:present name="returnhmap">
	if(checkInput()){

		var sqtotal=0;
		var fqtotal=0;
		
		//��ȡά��վϵ��
		var mscf='';
		var maintStation=document.getElementById("maintStation").value;
	 	<logic:iterate id="ele" name="returnhmap" property="mscfList">
			if(maintStation=="${ele.maintstation}"){
				mscf="${ele.coefficient}";
			}
		</logic:iterate>
		if(mscf==''){
			alert("ά��վϵ�������ڣ�");
			return false;
		}
		
		//���ʽ����
		var pmcf='';
		var paymentMethodApply=document.getElementById("paymentMethodApply").value;
	 	<logic:iterate id="ele" name="returnhmap" property="pmcfList">
			if(paymentMethodApply=="${ele.paymentmethod}"){
				pmcf="${ele.coefficient}";
			}
		</logic:iterate>
		if(pmcf==''){
			alert("���ʽϵ�������ڣ�");
			return false;
		}
		
		
		//��ͬ�������������
		var cacf="";//���ϵ��
		var smoney=0;//������
		var smoneyname="";//��������
		var smoney7=0;//��������У��ѷ�����
		var smoneyname7="";//��������У��ѷ�������
		var smoney8=0;//���ƽ��
		var smoneyname8="";//���Ʒ�������
		var smoney9=0;//������֬������
		var smoneyname9="";//������֬��������
		var jyf='';//�Ƿ��������
		var contractContentApply = document.getElementsByName("contractContentApply"); 
		var contractContentApplyName = document.getElementsByName("contractContentApplyName"); 
		  for(var c=0;c<contractContentApply.length;c++){
			if(contractContentApply[c].checked==true){
				//���
				<logic:iterate id="ele" name="returnhmap" property="cacfList">
					if(contractContentApply[c].value=="${ele.contentapplyid}"){
						cacf="${ele.coefficient}";
					}
				</logic:iterate>
				
				//�Ƿ�ѡ�˰�������
				if(contractContentApply[c].value=="6"){
					jyf='6';
				}else{
					//������
					<logic:iterate id="ele2" name="returnhmap" property="mssList">
						if(contractContentApply[c].value=="${ele2.serviceid}"){
							if(contractContentApply[c].value=="7"){
								//��������У���,��Ҫ��������
								smoney7=parseFloat("${ele2.servicemoney}");
								smoneyname7=contractContentApplyName[c].value+"(${ele2.servicemoney})";
							}else if(contractContentApply[c].value=="8"){
								//����,��Ҫ��������
								smoney8=parseFloat("${ele2.servicemoney}");
								smoneyname8=contractContentApplyName[c].value+"(${ele2.servicemoney})";
							}else if(contractContentApply[c].value=="9"){
								//������֬,��Ҫ��������
								smoney9=parseFloat("${ele2.servicemoney}");
								smoneyname9=contractContentApplyName[c].value+"(${ele2.servicemoney})";
							}else{
								smoney+=parseFloat("${ele2.servicemoney}");
								smoneyname+="+"+contractContentApplyName[c].value+"(${ele2.servicemoney})";
							}
						}
					</logic:iterate>
				}
			}  
		  }
		  if(cacf==''){
			  //�������=0.93
			  cacf='0.93';
			  //alert("��ͬ�������������ϵ�������ڣ�");
			  //return;
		  }
	
		  /** ������ϸ��Ϣ */
		  var standardQuote=document.getElementsByName("standardQuote");//��׼���ۣ�Ԫ��
		  var finallyQuote=document.getElementsByName("finallyQuote");//���ձ��ۣ�Ԫ��
		  
		  var quotedis=document.getElementsByName("standardQuoteDis");//��׼���ۼ�������
		  
		  var contractPeriods=document.getElementsByName("contractPeriod");//��ͬ��Ч�ڣ��£�
		  var elevatorAges=document.getElementsByName("elevatorAge");//��������
		  var elevatorTypes=document.getElementsByName("elevatorType");//��������
		  var floors=document.getElementsByName("floor");//��
		var weights=document.getElementsByName("weight");//����
		var speeds=document.getElementsByName("speed");//�ٶ�
		var jymoney=document.getElementsByName("jymoney");//����
		
		//̨��ϵ��
		var tlcf='';
		<logic:iterate id="ele" name="returnhmap" property="tlcfList">
			if(parseFloat(elevatorTypes.length)>=parseFloat("${ele.stl}") 
				&& parseFloat(elevatorTypes.length)<=parseFloat("${ele.etl}")){
				tlcf="${ele.coefficient}";
			}
		</logic:iterate>
		if(tlcf==''){
			alert("̨��ϵ�������ڣ�");
			return false;
		}
		
		for(var i=0;i<elevatorTypes.length;i++){
			//��������ϵ��
			var etcf="";
			var basicprice="";
			<logic:iterate id="ele" name="returnhmap" property="etcfList">
				var et="${ele.elevatortype}";
				if(et=="T"){
					et="ֱ��";
				}else if(et=="F"){
					et="����";
				}
				if(elevatorTypes[i].value==et){
					etcf="${ele.coefficient}";
					basicprice="${ele.basicprice}";
				}
			</logic:iterate>
			if(etcf==''){
				alert("��������ϵ�������ڣ�");
				return false;
			}
			if(basicprice==''){
				alert("�������ͻ����۸񲻴��ڣ�");
				return false;
			}
			//��������ϵ��
			var eafc='';
		 	<logic:iterate id="ele" name="returnhmap" property="eafcList">
				if(parseFloat(elevatorAges[i].value)>=parseFloat("${ele.selevatorage}") 
						&& parseFloat(elevatorAges[i].value)<=parseFloat("${ele.eelevatorage}")){
					eafc="${ele.coefficient}";
				}
			</logic:iterate>
			if(eafc==''){
				alert("��������ϵ�������ڣ�");
				return false;
			}
			//��ͬ��Ч��ϵ�� 
			var cpfc='';
		 	<logic:iterate id="ele" name="returnhmap" property="cpfcList">
				if(parseFloat(contractPeriods[i].value)>=parseFloat("${ele.scontractperiod}") 
						&& parseFloat(contractPeriods[i].value)<=parseFloat("${ele.econtractperiod}")){
					cpfc="${ele.coefficient}";
				}
			</logic:iterate>
			if(cpfc==''){
				alert("��ͬ��Ч��ϵ�������ڣ�");
				return false;
			}

			if(jyf!=""){
				if(jymoney[i].value==""){
					alert("�� "+(i+1)+" �У����Ѳ���Ϊ�գ�");
					return false;
				}else if(isNaN(jymoney[i].value)){
					alert("�� "+(i+1)+" �У����ѱ���Ϊ���֣�");
					return false;
				}
			}
			
			//ֱ�ݲ��У��ţ����أ��ٶȵļ���
			if(elevatorTypes[i].value=="ֱ��"){
				//��ϵ��
				var dfc='';
			 	<logic:iterate id="ele" name="returnhmap" property="dfcList">
					if(parseFloat(floors[i].value)>=parseFloat("${ele.sdoor}") 
							&& parseFloat(floors[i].value)<=parseFloat("${ele.edoor}")){
						dfc="${ele.coefficient}";
					}
				</logic:iterate>
				if(dfc==''){
					alert("��ϵ�������ڣ�");
					return false;
				}
				//����ϵ��
				var weight=getNum(weights[i].value);
				if(weight==""){
					alert("�� "+(i+1)+" �У���ȡ���ص�����ʧ�ܣ�");
					return false;
				}
				var wfc='';
			 	<logic:iterate id="ele" name="returnhmap" property="wfcList">
					if(parseFloat(weight)>=parseFloat("${ele.sweight}") 
							&& parseFloat(weight)<=parseFloat("${ele.eweight}")){
						wfc="${ele.coefficient}";
					}
				</logic:iterate>
				if(wfc==''){
					alert("����ϵ�������ڣ�");
					return false;
				}
				//�ٶ�ϵ��
				var speed=getNum(speeds[i].value);
				if(speed==""){
					alert("�� "+(i+1)+" �У���ȡ�ٶȵ�����ʧ�ܣ�");
					return false;
				}
				var sfc='';
			 	<logic:iterate id="ele" name="returnhmap" property="sfcList">
					if(parseFloat(speed)>=parseFloat("${ele.sspeed}") 
							&& parseFloat(speed)<=parseFloat("${ele.espeed}")){
						sfc="${ele.coefficient}";
					}
				</logic:iterate>
				if(sfc==''){
					alert("�ٶ�ϵ�������ڣ�");
					return false;
				}
				
				//ֱ�ݼ���
				//"ֱ�ݵı�׼�۸�/̨ = ����*����*��������ϵ��*����ϵ��*����ϵ��*��ϵ��*��������ϵ��*ά��վϵ��*��ͬ�������������ϵ��*
				//֧����ʽϵ��*��ͬ����ϵ��+���ѽ��+������У����+����֬+��"
	 			var squ=parseFloat(contractPeriods[i].value)*parseFloat(basicprice)*parseFloat(etcf)
	 					*parseFloat(wfc)*parseFloat(sfc)*parseFloat(dfc)*parseFloat(eafc)*parseFloat(mscf)
	 					*parseFloat(cacf)*parseFloat(pmcf)*parseFloat(cpfc)*parseFloat(tlcf)
	 					+parseFloat(smoney);
				var squdis="ֱ�ݵı�׼�۸�=��ͬ��Ч��("+contractPeriods[i].value+")*�����۸�("+basicprice+")*��������ϵ��("+etcf
						+")*����ϵ��("+wfc+")*����ϵ��("+sfc+")*��ϵ��("+dfc+")*��������ϵ��("+eafc+")*ά��վϵ��("+mscf
						+")*��ͬ�������������ϵ��("+cacf+")*֧����ʽϵ��("+pmcf+")*��ͬ����ϵ��("+cpfc+")*̨��ϵ��("+tlcf+")"
						+smoneyname;
				//��������У���,��Ҫ��������
				if(smoneyname7!=""){
					squ+=(parseFloat(smoney7)*parseFloat(contractPeriods[i].value));
					squdis+="+("+smoneyname7+"*"+contractPeriods[i].value+")";
				}
				//����,��Ҫ��������
				if(smoneyname8!=""){
					squ+=(parseFloat(smoney8)*parseFloat(contractPeriods[i].value));
					squdis+="+("+smoneyname8+"*"+contractPeriods[i].value+")";
				}
				//������֬,��Ҫ��������
				if(smoneyname9!=""){
					squ+=(parseFloat(smoney9)*parseFloat(contractPeriods[i].value));
					squdis+="+("+smoneyname9+"*"+contractPeriods[i].value+")";
				}
				//����
				if(jyf!=""){
					squ+=parseFloat(jymoney[i].value);
					squdis+="+����("+jymoney[i].value+")";
				}
				
	
				standardQuote[i].value=parseFloat(squ).toFixed(2);//��׼���ۣ�Ԫ��
				quotedis[i].value=squdis;//��׼���ۼ�������
				
				sqtotal+=parseFloat(standardQuote[i].value);
				/**
				if(finallyQuote[i].value==""){
					finallyQuote[i].value=parseFloat(squ).toFixed(2);//���ձ���
					fqtotal+=parseFloat(finallyQuote[i].value);
				}else{
					fqtotal+=parseFloat(finallyQuote[i].value);
				}
				*/
			}else{
				//���ݼ���
				//"���ݵı�׼�۸�/̨ = ����*����*��������ϵ��*��������ϵ��*ά��վϵ��*��ͬ�������������ϵ��*
				//֧����ʽϵ��*��ͬ����ϵ��+���ѽ��+������У����+����֬+��"
				var squ=parseFloat(contractPeriods[i].value)*parseFloat(basicprice)*parseFloat(etcf)
	 					*parseFloat(eafc)*parseFloat(mscf)
	 					*parseFloat(cacf)*parseFloat(pmcf)*parseFloat(cpfc)*parseFloat(tlcf)
	 					+parseFloat(smoney);
				var squdis="���ݵı�׼�۸�=��ͬ��Ч��("+contractPeriods[i].value+")*�����۸�("+basicprice+")*��������ϵ��("+etcf
						+")*��������ϵ��("+eafc+")*ά��վϵ��("+mscf
						+")*��ͬ�������������ϵ��("+cacf+")*֧����ʽϵ��("+pmcf+")*��ͬ����ϵ��("+cpfc+")*̨��ϵ��("+tlcf+")"
						+smoneyname;
				//��������У���,��Ҫ��������
				if(smoneyname7!=""){
					squ+=(parseFloat(smoney7)*parseFloat(contractPeriods[i].value));
					squdis+="+("+smoneyname7+"*"+contractPeriods[i].value+")";
				}
				//����,��Ҫ��������
				if(smoneyname8!=""){
					squ+=(parseFloat(smoney8)*parseFloat(contractPeriods[i].value));
					squdis+="+("+smoneyname8+"*"+contractPeriods[i].value+")";
				}
				//������֬������,��Ҫ��������
				if(smoneyname9!=""){
					squ+=(parseFloat(smoney9)*parseFloat(contractPeriods[i].value));
					squdis+="+("+smoneyname9+"*"+contractPeriods[i].value+")";
				}
				//����
				if(jyf!=""){
					squ+=parseFloat(jymoney[i].value);
					squdis+="+����("+jymoney[i].value+")";
				}
	
				standardQuote[i].value=parseFloat(squ).toFixed(2);//��׼���ۣ�Ԫ��
				quotedis[i].value=squdis;//��׼���ۼ�������
				
				sqtotal+=parseFloat(standardQuote[i].value);
				/**
				if(finallyQuote[i].value==""){
					finallyQuote[i].value=parseFloat(squ).toFixed(2);//���ձ���
					fqtotal+=parseFloat(finallyQuote[i].value);
				}else{
					fqtotal+=parseFloat(finallyQuote[i].value);
				}
				*/
			}
			
			
		}
	
		//ҵ���
		var bcosts=document.getElementById("businessCosts").value;
		if(bcosts==""){
			bcosts="0";
		}
		//��׼���ۺϼ� ��׼��ͬ�ܶ�= ��̨��׼�۸��ܺ�+ҵ���
		document.getElementById("standardQuoteTotal").value=(parseFloat(sqtotal)+parseFloat(bcosts)).toFixed(2);
		//���ձ��ۺϼ�
		//document.getElementById("finallyQuoteTotal").value=parseFloat(fqtotal).toFixed(2);
		
		jsr8();//�����ۿ���
	}
	</logic:present>
	
	return true;
}

//�����ۿ��ʡ� �ۿ���=���պ�ͬ�ܶ�/��׼��ͬ�ܶ�*100%��
function jsr8(){
	sumValuesByName('finallyQuote','finallyQuoteTotal');
	//���ձ��ۺϼ�
	var finallyQuoteTotal=document.getElementById("finallyQuoteTotal").value;
	//��׼���ۺϼ�
	var standardQuoteTotal=document.getElementById("standardQuoteTotal").value;
	if(standardQuoteTotal!="" && standardQuoteTotal!=0 && finallyQuoteTotal!=""){
		document.getElementById("discountRate").value=(parseFloat(finallyQuoteTotal)/parseFloat(standardQuoteTotal)*100).toFixed(2);
	}
}

function jssdqtotal(val){
	if(val==""){
		val="0";
	}
	var total=0;
	var standardQuote=document.getElementsByName("standardQuote");//��׼���ۣ�Ԫ��
	for(var i=0;i<standardQuote.length;i++){
		if(standardQuote[i].value!=""){
			total+=parseFloat(standardQuote[i].value);
		}
	}
	document.getElementById("standardQuoteTotal").value=(parseFloat(total)+parseFloat(val)).toFixed(2);
	
	jsr8();//�����ۿ���
}
/**===========================�����׼����=======================*/

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