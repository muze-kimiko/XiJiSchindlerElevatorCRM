
//ȥ���ո�
 String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g,"");}

 //������Ƿ�Ϊ����
 function f_check_number2(){
  	if((event.keyCode>=48 && event.keyCode<=57)){
  	}else{
		event.keyCode=0;
   	}
 }
 
 //������Ƿ�Ϊ����,���������븺�źͿ���������
 function f_check_number3(){
  	if((event.keyCode>=48 && event.keyCode<=57) || event.keyCode==46 ){
  	}else{
		event.keyCode=0;
   	}
 }
  //������Ƿ�Ϊ����,�����������źͿ������븺��
 function f_check_number4(){
  	if((event.keyCode>=48 && event.keyCode<=57) || event.keyCode==45){
  	}else{
		event.keyCode=0;
   	}
 }
  //������Ƿ�Ϊ����,�������븺�ź͵��
 function f_check_number5(){
  	if((event.keyCode>=48 && event.keyCode<=57) || event.keyCode==45 || event.keyCode==46){
  	}else{
		event.keyCode=0;
   	}
 }

   /** ���������ʾ */
 function xuhaonum(objname){
	var divs=document.getElementsByName(objname);
	for(var i=0;i<divs.length;i++){
		divs[i].innerText=i+1;
	}
 }
  //��ѡ��ȫѡ
  function selAllNode(obj,name){
	var idss=document.getElementsByName(name);
		for(var i=idss.length-1;i>=0;i--){
			idss[i].checked=obj.checked;
		}
	}
  //ɾ��
  function delSelNode22(ptable){
		var pbody=ptable.getElementsByTagName("TBODY")[0];
		var len=pbody.children.length;
		var nodevalue="";
		for(var i=len-1;i>=1;i--){
			//if(pbody.children[i].firstChild.firstChild.checked==true){//ids
				pbody.deleteRow(i);
			//}
		}
   }
  //ɾ�� ί�й��ϷѵĹ���
  function delSelNode23(ptable,num){
		var pbody=ptable.getElementsByTagName("TBODY")[0];
		var len=pbody.children.length;
		var nodevalue="";
		if(num=0){
			for(var i=len-2;i>=1;i--){
				//if(pbody.children[i].firstChild.firstChild.checked==true){//ids
					pbody.deleteRow(i);
				//}
			}
		}else{
			for(var i=len-2;i>=1;i--){
				if(pbody.children[i].firstChild.firstChild.checked==true){//ids
					pbody.deleteRow(i);
				}
			}
		}
		xuhaonum("Wdivorderw");//���
		jisuanxiaoji_w("WW");//С��
   }
  //ɾ�������¼����ܷ���
  function delSelNode222(ptable,name,type,hjtype){
  	
		var pbody=ptable.getElementsByTagName("TBODY")[0];
		var len=pbody.children.length;
		var nodevalue="";
		for(var i=len-1;i>=0;i--){
			if(pbody.children[i].firstChild.firstChild.checked==true){//ids
				pbody.deleteRow(i);
			}
		}
		xuhaonum(name);
		if(type=="W"){
			jisuanxiaoji_w(hjtype);
		}else if(type=="G"){
			jisuanxiaoji_g(hjtype);
		}else if(type=="J"){
			jisuanxiaoji_b(hjtype);
		}
   }

/** ��ʾ���ز� */
function showdivpage(obj){
	var divshoww=document.getElementById("divshoww");
	var divshowg=document.getElementById("divshowg");
	var divshowj=document.getElementById("divshowj");
	var divshowb1=document.getElementById("divshowb1");
	var divshowb2=document.getElementById("divshowb2");
	var divshowpath=document.getElementById("divshowpath");//�ύ·��
	if(obj.value=="J"){//����
		divshoww.style.display="none";
		divshowg.style.display="none";
		divshowj.style.display="inline";
		divshowpath.style.display="none";
		document.getElementById("submitpath").value="W";//�����ύ·��Ĭ��Ϊ�ֲ���
	}else if(obj.value=="G"){//����
		divshoww.style.display="none";
		divshowg.style.display="inline";
		divshowj.style.display="none";
		divshowpath.style.display="inline";
		document.getElementById("submitpath").value="";
	}else if(obj.value=="W"){//ά��
		divshoww.style.display="inline";
		divshowg.style.display="none";
		divshowj.style.display="none";
		divshowpath.style.display="inline";
		document.getElementById("submitpath").value="";
	}else if(obj.value=="1"){//����-��Ȩ��ͬ
		divshowb1.style.display="inline";
		divshowb2.style.display="none";
	}else if(obj.value=="2"){//����-����Э��
		divshowb1.style.display="none";
		divshowb2.style.display="inline";
	}
}
//���������������ĵ�����
function setBApplyContent(obj){
	 if(obj.value=="1"){//����-��Ȩ��ͬ
		document.getElementById("BApplyContent").value=content;
	}else if(obj.value=="2"){//����-����Э��
		document.getElementById("BApplyContent").value=content1;
	}
}

/** ����ʩ����λ���� */
function openNewWindow2(type,typestr){
	var url='query/Search.jsp?path=/XJSCRM/SearchEngEpiCustomerAction.do?method=toSearchRecord_CustName';
	var obj = window.showModalDialog(url,window,'dialogWidth:770px;dialogHeight:500px;dialogLeft:200px;dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes');
   	if(obj){
   		if(type=="W"){//ά��
   			if(typestr=='W'){//��ͬ����
   				document.getElementById("WConstructId").value=obj.id;
   				document.getElementById("WConstructName").value=obj.name;
   			}else if(typestr=='D'){//��װ
   				document.getElementById("wDSCustId").value=obj.id;
   				document.getElementById("wDSCustName").value=obj.name;
   			}else if(typestr=='B'){//����
   				document.getElementById("wBCustId").value=obj.id;
   				document.getElementById("wBCustName").value=obj.name;
   			}else if(typestr=='T'){//����
   				document.getElementById("wTCustId").value=obj.id;
   				document.getElementById("wTCustName").value=obj.name;
   			}else if(typestr=='E'){//����
   				document.getElementById("wECustId").value=obj.id;
   				document.getElementById("wECustName").value=obj.name;
   			}
   		}else if(type=="G"){//����
   			if(typestr=='W'){//��ͬ����
   				document.getElementById("GConstructId").value=obj.id;
   				document.getElementById("GConstructName").value=obj.name;
   			}else if(typestr=='D'){//��װ
   				document.getElementById("gDSCustId").value=obj.id;
   				document.getElementById("gDSCustName").value=obj.name;
   			}else if(typestr=='B'){//����
   				document.getElementById("gBCustId").value=obj.id;
   				document.getElementById("gBCustName").value=obj.name;
   			}else if(typestr=='T'){//����
   				document.getElementById("gTCustId").value=obj.id;
   				document.getElementById("gTCustName").value=obj.name;
   			}else if(typestr=='E'){//����
   				document.getElementById("gECustId").value=obj.id;
   				document.getElementById("gECustName").value=obj.name;
   			}
   		}else if(type=="J"){//����
   			if(typestr=='S'){//��Ȩ��ͬ
   				document.getElementById("BConstructId").value=obj.id;
   				document.getElementById("BConstructName").value=obj.name;
   			}else if(typestr=='L'){//����Э��
   				document.getElementById("BConstructId1").value=obj.id;
   				document.getElementById("BConstructName1").value=obj.name;
   			}
   		}
   	}
}
/** ������ͬ���� */
function openNewWindow(obj){
	if(obj=='B'){//����
		sendServicingContractDetailB();
	}else if(obj=='G'){//����
		openwindowReturnValueCustomer('query/Search.jsp?path=/XJSCRM/SearchEngEpiCustomerAction.do?method=toSearchRecord_GaiZao','G');
	}else if(obj=='W'){//ά��
		openwindowReturnValueCustomer('query/Search.jsp?path=/XJSCRM/SearchEngEpiCustomerAction.do?method=toSearchRecord_WeiXiu','W');
	}
}
function openwindowReturnValueCustomer(url,busstype){
   var obj = window.showModalDialog(url,window,'dialogWidth:770px;dialogHeight:500px;dialogLeft:200px;dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes');
   if(obj){
   		if(busstype=="G"){
   			document.getElementById("GSoureConctractId").value=obj.contractid;
   			document.getElementById("GCustID").value=obj.custid;
   			document.getElementById("GCustName").value=obj.custname;
   			document.getElementById("divgProjectName").innerText=obj.custname;//��װ
   			document.getElementById("divgBProjectName").innerText=obj.custname;//����
   			document.getElementById("divgTProjectName").innerText=obj.custname;//����
   			document.getElementById("divgEProjectName").innerText=obj.custname;//����
   			var billno=obj.billno;//�����ͬ��ˮ��
   			document.getElementById("Gbillno").value=billno;
   			
   			sendServicingContractDetailG(billno);
   		}else if(busstype=="W"){
   			document.getElementById("WSoureConctractId").value=obj.contractid;
   			document.getElementById("WCustID").value=obj.custid;
   			document.getElementById("WCustName").value=obj.custname;
   			document.getElementById("divwProjectName").innerText=obj.custname;//��װ
   			document.getElementById("divwBProjectName").innerText=obj.custname;//����
   			document.getElementById("divwTProjectName").innerText=obj.custname;//����
   			document.getElementById("divwEProjectName").innerText=obj.custname;//����
   			var billno=obj.billno;//ά�޺�ͬ��ˮ��
   			document.getElementById("Wbillno").value=billno;
   			
   			sendServicingContractDetailW(billno);
   		}
   }
}
 /** ajax ȡ��ά�޺�ͬ����ϸ��Ϣ */
var XMLHttpReq2 = false;
  //����XMLHttpRequest����       
function createXMLHttpRequest2() {
  if(window.XMLHttpRequest) { //Mozilla �����
   XMLHttpReq2 = new XMLHttpRequest();
  }else if (window.ActiveXObject) { // IE�����
   XMLHttpReq2 = new ActiveXObject("Microsoft.XMLHTTP");
  }
 }

 /** ���ʩ����λ */
 function checkConstructId(){
	var issubmit=true;
	var BussType=document.getElementById("BussType").value;
	if(BussType=="W"){
		var constructId=document.getElementById("WConstructId").value;//ʩ����λ
		if(constructId.trim()==""){
	 		issubmit=confirm("ά��ί�к�ͬ����,ʩ����λ��δ��д,�Ƿ�������棡\n");
	 	}
	}else if(BussType=="G"){
		var constructId=document.getElementById("GConstructId").value;//ʩ����λ
		if(constructId.trim()==""){
	 		issubmit=confirm("����ί�к�ͬ����,ʩ����λ��δ��д,�Ƿ�������棡\n");
	 	}
	}
	return issubmit;
}
/** ����ʱ����Ƿ���д���� */
 function checksaveinfo(error){
 	var BussType=document.getElementById("BussType").value;
 	var ApplyDate=document.getElementById("ApplyDate").value;//��������
	if(ApplyDate.trim()==""){
		error="�������ڲ���Ϊ��!\n";
	}else{
	 	if(BussType=="W"){
		 	var soureConctractId=document.getElementById("WSoureConctractId").value;//��ͬ��
		 	//var constructId=document.getElementById("WConstructId").value;//ʩ����λ
		 	if(soureConctractId.trim()==""){
		 		error+="��ͬ�Ų���Ϊ�գ�\n";
		 	//}else if(constructId.trim()==""){
		 	//	error+="ά��ί�к�ͬ����,ʩ����λ����Ϊ�գ�\n";
		 	}else{
		 		error+=wcheckweihuinfo2("","WW","");//ί�й��ϷѵĹ���
		 		if(error==""){
		 			error+=wcheckweihuinfo2("","D","W");//��װ
		 		}
		 		if(error==""){
		 			error+=wcheckweihuinfo2("","B","W");//����
		 		}
		 		if(error==""){
		 			error+=wcheckweihuinfo2("","T","W");//����
		 		}
		 		if(error==""){
		 			error+=wcheckweihuinfo2("","E","W");//����
		 		}
		 	}
	 	}else if(BussType=="G"){
	 		//var applyContent=document.getElementById("GApplyContent").value;//��������
	 		//if(applyContent.trim()==""){
	 		//	error+="�������Ĳ���Ϊ�գ�\n";
	 		//}
	 		var soureConctractId=document.getElementById("GSoureConctractId").value;//��ͬ��
		 	//var constructId=document.getElementById("GConstructId").value;//ʩ����λ
		 	if(soureConctractId.trim()==""){
		 		error+="��ͬ�Ų���Ϊ�գ�\n";
		 	//}else if(constructId.trim()==""){
		 	//	error+="����ί�к�ͬ����,ʩ����λ����Ϊ�գ�\n";
		 	}else{
		 		error+=gcheckweihuinfo2("","D","G");//��װ
		 		if(error==""){
		 			error+=gcheckweihuinfo2("","B","G");//����
		 		}
		 		if(error==""){
		 			error+=gcheckweihuinfo2("","T","G");//����
		 		}
		 		if(error==""){
		 			error+=gcheckweihuinfo2("","E","G");//����
		 		}
		 	}
	 	}else if(BussType=="J"){
	 		var appType=document.getElementById("AppType").value;
	 		var applyContent=document.getElementById("BApplyContent").value;//��������
	 		//var applyContent1=document.getElementById("BApplyContent1").value;//��������
	 		var saftyDesc=document.getElementById("BSaftyDesc").value;//��ȫ����Э����
	 		var divisorDesc=document.getElementById("BDivisorDesc").value;//����¸��Լ
	 		
	 		if(applyContent.trim()==""){
	 			error+="�������Ĳ���Ϊ�գ�\n";
	 		}
	 		if(saftyDesc.trim()==""){
	 			error+="��ȫ����Э���鲻��Ϊ�գ�\n";
	 		}
	 		if(divisorDesc.trim()==""){
	 			error+="����¸��Լ����Ϊ�գ�\n";
	 		}
	 		var apptype=document.getElementById("AppType").value;
			if(apptype=="1"){//��Ȩ��ͬ
				var constructId=document.getElementById("BConstructId").value;//�����λ
				if(constructId==""){
					error+="�����λ����Ϊ�գ�\n";
				}else{
					error+=bcheckweihuinfo("","S","B");
				}
			}else if(apptype=="2"){//����Э��
				var constructId=document.getElementById("BConstructId1").value;//�����λ
				if(constructId==""){
					error+="�����λ����Ϊ�գ�\n";
				}else{
					error+=bcheckweihuinfo("","L","B");
					if(error==""){
						error+=bcheckweihuinfo("","W","B");
					}
				}
			}
	 		
	 	}
	}
	return error;
 }
 
 /**=================== ά�ޱ��� start ===========================*/
 //����������
 function sendServicingContractDetailW(billno) {
 	var url = '/XJSCRM/SearchEngEpiCustomerAction.do?method=toServicingContractDetailW';
		url +='&wbillno='+billno;
  	createXMLHttpRequest2();
 	XMLHttpReq2.open("post", url, false);
  	XMLHttpReq2.onreadystatechange = processServicingContractDetailW;//ָ����Ӧ����
  	XMLHttpReq2.send(null);  // ��������
 }
 // ��������Ϣ����
 function processServicingContractDetailW() {
     	if (XMLHttpReq2.readyState == 4) { // �ж϶���״̬
         	if (XMLHttpReq2.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ        	
         		var xmlDOM=XMLHttpReq2.responseXML;

            	var elevatorid=xmlDOM.getElementsByTagName("elevatorid");//[0].firstChild.data;
            	var floor=xmlDOM.getElementsByTagName("floor");
            	var stage=xmlDOM.getElementsByTagName("stage");
            	var door=xmlDOM.getElementsByTagName("door");
            	var high=xmlDOM.getElementsByTagName("high");
            	var num=xmlDOM.getElementsByTagName("num");
            	var angle=xmlDOM.getElementsByTagName("angle");
            	var amt=xmlDOM.getElementsByTagName("amt");
            	var detailrowid=xmlDOM.getElementsByTagName("detailrowid");
            	var quoteelevatorid=xmlDOM.getElementsByTagName("quoteelevatorid");
            	//��ɾ���Ѿ����ڵĵ����ͺ���Ϣ
            	delSelNode22(adjtb_wd);
            	var amtsum=0;
            	for(var i=0;i<elevatorid.length;i++){
            		var eleid=elevatorid[i].firstChild.data;
            		var floorstr=floor[i].firstChild.data;
            		var stagestr=stage[i].firstChild.data;
            		var doorstr=door[i].firstChild.data;
            		var highstr=high[i].firstChild.data;
            		var numstr=num[i].firstChild.data;
            		var eanglestr=angle[i].firstChild.data;
            		var amtstr=amt[i].firstChild.data;
            		var rowidstr=detailrowid[i].firstChild.data;
            		var quotestr=quoteelevatorid[i].firstChild.data;
            		
            		AddRow_WD(eleid,floorstr,stagestr,doorstr,highstr,numstr,eanglestr,rowidstr,quotestr);
            		
            		amtsum=parseFloat(amtsum)+parseFloat(amtstr);
            	}
            	//��ͬ�ܼ�
				amtsum=parseFloat(amtsum).toFixed(2);
				var contractsum=document.getElementById("contractsum");
				contractsum.value=amtsum; //���صĺ�ͬ�ܼ�	
				//document.getElementById("div_contractsum").innerText=amtsum; //��ͬ�ܼ�
				
				//��Ŀί�м۸񹹳�
				projectPriceConstitute("0");
				
            	var engcontent=xmlDOM.getElementsByTagName("engcontent");
            	var qty=xmlDOM.getElementsByTagName("qty");
            	var personprice=xmlDOM.getElementsByTagName("personprice");
            	var stuffprice=xmlDOM.getElementsByTagName("stuffprice");
            	var conrowid=xmlDOM.getElementsByTagName("conrowid");
            	var prodinfo=xmlDOM.getElementsByTagName("prodinfo");
            	//ɾ���Ѿ����ڵ�ί�й��ϷѵĹ���
            	delSelNode23(adjtb_ww,0);
            	for(var j=0;j<engcontent.length;j++){
            		var engstrt=engcontent[j].firstChild.data;
            		var qtystr=qty[j].firstChild.data;
            		var perstre=personprice[j].firstChild.data;
            		var stustr=stuffprice[j].firstChild.data;
            		var conrowidstr=conrowid[j].firstChild.text;
            		var prodinfostr="";
            		if(prodinfo[j].firstChild){
            			prodinfostr=prodinfo[j].firstChild.text;
            		}

            		AddRow_WD2(engstrt,qtystr,perstre,stustr,conrowidstr,prodinfostr);
            	}
            	
         	} else { //ҳ�治����
               window.alert("���������ҳ�����쳣��");
         	}
       }
    }
   /** ��Ӻ�ͬ������ϸ��Ϣ */
  function AddRow_WD(elevatorid,floor,stage,door,high,numkk,angle,rowidstr,quotestr){
		var adjtb_w=document.getElementById("adjtb_wd");
		var num=adjtb_w.rows.length;

		adjtb_w.insertRow(num);

		var ince0=adjtb_w.rows(num).insertCell(0);
		ince0.innerHTML="";
		
		//�����ͺ�
		var name0="welevatorid";
		var name00="wdetailrowid";
		var name000="wquoteelevatorid";
		var ince1=adjtb_w.rows(num).insertCell(1);
		ince1.innerHTML=elevatorid+"<input type=\'hidden\' name=\'"+name0+"\' value=\'"+elevatorid+"\' /><input type=\'hidden\' name=\'"+name00+"\' value=\'"+rowidstr+"\' /><input type=\'hidden\' name=\'"+name000+"\' value=\'"+quotestr+"\' />";
		//��վ��
		var name1="wfloor";
		var name2="wstage";
		var name3="wdoor";
		var ince2=adjtb_w.rows(num).insertCell(2);
		ince2.innerHTML=floor+"/"+stage+"/"+door+"<input type=\'hidden\' name=\'"+name1+"\' value=\'"+floor+"\' /><input type=\'hidden\' name=\'"+name2+"\' value=\'"+stage+"\' /><input type=\'hidden\' name=\'"+name3+"\' value=\'"+door+"\' />";
		//�����߶�
		var name4="whigh";
		var ince3=adjtb_w.rows(num).insertCell(3);
		ince3.align="right";
		ince3.innerHTML=high+"&nbsp;<input type=\'hidden\' name=\'"+name4+"\' value=\'"+high+"\' />";
		//��б�Ƕ�
		var name5="wangle";
		var ince4=adjtb_w.rows(num).insertCell(4);
		ince4.align="right";
		ince4.innerHTML=angle+"&nbsp;<input type=\'hidden\' name=\'"+name5+"\' value=\'"+angle+"\' />";
		//����
		var name6="wnum";
		var ince5=adjtb_w.rows(num).insertCell(5);
		ince5.align="right";
		ince5.innerHTML=numkk+"&nbsp;<input type=\'hidden\' name=\'"+name6+"\' value=\'"+numkk+"\' />";
 }
 
 /** ί�й��ϷѵĹ��� */
   function AddRow_WD2(engstrt,qtystr,perstre,stustr,conrowidstr,prodinfostr){
		var adjtb_w=document.getElementById("adjtb_ww");
		var num=adjtb_w.rows.length-1;

		adjtb_w.insertRow(num);
		
		//��ѡ��
		var name0="Wnodesw";
		var ince0=adjtb_w.rows(num).insertCell(0);
		ince0.align="center";
		ince0.innerHTML="<input type=\'checkbox\' name=\'"+name0+"\' >";
		//���
		var name1="Wdivorderw";
		var ince1=adjtb_w.rows(num).insertCell(1);
		ince1.align="center";
		ince1.innerHTML="<div id=\'"+name1+"\'></div>";
		//��Ŀ
		var ince2=adjtb_w.rows(num).insertCell(2);
		ince2.innerHTML="<input type=\'text\' name=\'wengcontent\' value=\'"+engstrt+"\' class=\'default_input\' size=\'50\' /><input type=\'hidden\' name=\'wconrowid\' value=\'"+conrowidstr+"\' />";
		//����
		var ince3=adjtb_w.rows(num).insertCell(3);
		ince3.align="right";
		ince3.innerHTML="<input type=\'text\' name=\'wqty\' value=\'"+qtystr+"\' class=\'default_input\' onkeypress=\'f_check_number2();\'/>";
		//����
		var ince4=adjtb_w.rows(num).insertCell(4);
		ince4.align="right";
		ince4.innerHTML="<input type=\'text\' name=\'wpersonprice\' value=\'"+perstre+"\' class=\'default_input\' onkeypress=\'f_check_number3();\' onkeyup=\"jisuanxiaoji_w('WW')\" />";
		//���Ϸ�
		var ince5=adjtb_w.rows(num).insertCell(5);
		ince5.align="right";
		ince5.innerHTML="<input type=\'text\' name=\'wstuffprice\' value=\'"+stustr+"\' class=\'default_input\' onkeypress=\'f_check_number3();\' onkeyup=\"jisuanxiaoji_w('WW')\" /><input type=\'hidden\' name=\'wprodinfo\' value=\'"+prodinfostr+"\' />";
 		
		xuhaonum(name1);
		jisuanxiaoji_w("WW");//С��
 }
 function AddRow_WW(tableid){
 	var errorstr=wcheckweihuinfo2("","WW","");//ί�й��ϷѵĹ���
 	if(errorstr==""){
 		AddRow_WD2("","","","","","");
 	}else{
 		alert(errorstr);
 	}
 }
  /** ��װ */ 
  function AddRow_W(tableid){
  	var errorstr=wcheckweihuinfo2("","D","");
  	if(errorstr==""){
		var adjtb_w=document.getElementById("adjtb_w");
		var num=adjtb_w.rows.length;

		adjtb_w.insertRow(num);		
		//��ѡ��
		var name0="Wnodes";
		var ince0=adjtb_w.rows(num).insertCell(0);
		ince0.align="center";
		ince0.innerHTML="<input type=\'checkbox\' name=\'"+name0+"\' >";
		//���
		var name1="Wdivorder";
		var ince1=adjtb_w.rows(num).insertCell(1);
		ince1.align="center";
		ince1.innerHTML="<div id=\'"+name1+"\'></div>";
		//��
		var name2="wDfloor";
		var ince2=adjtb_w.rows(num).insertCell(2);
		ince2.innerHTML="<input type=\'text\' name=\'"+name2+"\' class=\'default_input\' size=\'4\' onkeyup=\"setStageValue(this,'wDstage')\" onkeypress=\'f_check_number2();\' >";
		//վ
		var name3="wDstage";
		var ince3=adjtb_w.rows(num).insertCell(3);
		ince3.innerHTML="<input type=\'text\' name=\'"+name3+"\' class=\'default_input\' size=\'4\' onkeypress=\'f_check_number2();\'>";
		//�������أ�kg��
		var name4="wDZzl";
		var ince4=adjtb_w.rows(num).insertCell(4);
		ince4.innerHTML="<input type=\'text\' name=\'"+name4+"\' onkeypress=\'f_check_number3();\' size=\'10\' class=\'default_input\'>";
		//��װ�ص�
		var name5="wDSuspendAddress";
		var ince5=adjtb_w.rows(num).insertCell(5);
		ince5.innerHTML="<input type=\'text\' name=\'"+name5+"\' class=\'default_input\'>";
		//��װ��Ŀ
		var name6="wDSuspendItem";
		var ince6=adjtb_w.rows(num).insertCell(6);
		ince6.innerHTML="<input type=\'text\' name=\'"+name6+"\' class=\'default_input\' >";
		//��װ��ͷ
		var name7="wDSuspendBox";
		var ince7=adjtb_w.rows(num).insertCell(7);
		ince7.innerHTML="<input type=\'text\' name=\'"+name7+"\' class=\'default_input\' >";
		//���㹫ʽ��ԭ��װ�����ۣ�
		var name8="wDCountExp";
		var ince8=adjtb_w.rows(num).insertCell(8);
		//ince8.innerHTML="<input type=\'text\' name=\'wCountExp\' class=\'default_input\' >";
		ince8.innerHTML="<textarea name=\'"+name8+"\' rows=\'1\' cols=\'30\'>";
		//��װ̨��
		var name9="wDSuspendNum";
		var ince9=adjtb_w.rows(num).insertCell(9);
		ince9.align="right";
		ince9.innerHTML="<input type=\'text\' name=\'"+name9+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number2();\' onkeyup=\"jisuanxiaoji_w('D')\" size=\'6\'>";
		//��װ����
		var name10="wDSuspendPrice";
		var ince10=adjtb_w.rows(num).insertCell(10);
		ince10.align="right";
		ince10.innerHTML="<input type=\'text\' name=\'"+name10+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number3();\' onkeyup=\"jisuanxiaoji_w('D')\" size=\'10\'>";

		xuhaonum(name1);
	}else{
		alert(errorstr);
	}
  }
  /** ���� */ 
  function AddRow_W1(tableid){
	  var errorstr=wcheckweihuinfo2("","B","");
	  if(errorstr==""){
  		var adjtb_w=document.getElementById("adjtb_w1");
		var num=adjtb_w.rows.length;

		adjtb_w.insertRow(num);		
		//��ѡ��
		var name0="Wnodes1";
		var ince0=adjtb_w.rows(num).insertCell(0);
		ince0.align="center";
		ince0.innerHTML="<input type=\'checkbox\' name=\'"+name0+"\' >";
		//���
		var name1="Wdivorder1";
		var ince1=adjtb_w.rows(num).insertCell(1);
		ince1.align="center";
		ince1.innerHTML="<div id=\'"+name1+"\'>";
		//��
		var name2="wBfloor";
		var ince2=adjtb_w.rows(num).insertCell(2);
		ince2.innerHTML="<input type=\'text\' name=\'"+name2+"\' class=\'default_input\' size=\'4\' onkeyup=\"setStageValue(this,'wBstage')\" onkeypress=\'f_check_number2();\' >";
		//վ
		var name3="wBstage";
		var ince3=adjtb_w.rows(num).insertCell(3);
		ince3.innerHTML="<input type=\'text\' name=\'"+name3+"\' class=\'default_input\' size=\'4\' onkeypress=\'f_check_number2();\'>";
		//�������أ�kg��
		var name4="wBZzl";
		var ince4=adjtb_w.rows(num).insertCell(4);
		ince4.innerHTML="<input type=\'text\' name=\'"+name4+"\' onkeypress=\'f_check_number3();\' class=\'default_input\' size=\'10\'>";
		//����ص�
		var name5="wBBuildAddress";
		var ince5=adjtb_w.rows(num).insertCell(5);
		ince5.innerHTML="<input type=\'text\' name=\'"+name5+"\' class=\'default_input\' size=\'40\'>";
		//������Ŀ
		var name6="wBBuildItem";
		var ince6=adjtb_w.rows(num).insertCell(6);
		ince6.innerHTML="<input type=\'text\' name=\'"+name6+"\' class=\'default_input\' size=\'40\' >";
		//�����ܸߣ��ף�
		var name7="wBJdH";
		var ince7=adjtb_w.rows(num).insertCell(7);
		ince7.innerHTML="<input type=\'text\' name=\'"+name7+"\' class=\'default_input\' size=\'10\' onkeypress=\'f_check_number3();\'>"
		//����̨��
		var name8="wBBuildNum";
		var ince8=adjtb_w.rows(num).insertCell(8);
		ince8.align="right";
		ince8.innerHTML="<input type=\'text\' name=\'"+name8+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number2();\' onkeyup=\"jisuanxiaoji_w('B')\" size=\'6\'>";
		//�������
		var name9="wBBuildPrice";
		var ince9=adjtb_w.rows(num).insertCell(9);
		ince9.align="right";
		ince9.innerHTML="<input type=\'text\' name=\'"+name9+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number3();\' onkeyup=\"jisuanxiaoji_w('B')\" size=\'10\'>";

		xuhaonum(name1);
	}else{
		alert(errorstr);
	}
  }
  /** ���� */
  function AddRow_W2(tableid){
	  var errorstr=wcheckweihuinfo2("","T","");
	  if(errorstr==""){
  		var adjtb_w=document.getElementById("adjtb_w2");
		var num=adjtb_w.rows.length;

		adjtb_w.insertRow(num);		
		//��ѡ��
		var name0="Wnodes2";
		var ince0=adjtb_w.rows(num).insertCell(0);
		ince0.align="center";
		ince0.innerHTML="<input type=\'checkbox\' name=\'"+name0+"\' >";
		//���
		var name1="Wdivorder2";
		var ince1=adjtb_w.rows(num).insertCell(1);
		ince1.align="center";
		ince1.innerHTML="<div id=\'"+name1+"\'>";
		//����ص�
		var name2="wTransAddress";
		var ince2=adjtb_w.rows(num).insertCell(2);
		ince2.innerHTML="<input type=\'text\' name=\'"+name2+"\' class=\'default_input\' size=\'40\' >";
		//������Ŀ
		var name3="wTransItem";
		var ince3=adjtb_w.rows(num).insertCell(3);
		ince3.innerHTML="<input type=\'text\' name=\'"+name3+"\' class=\'default_input\' size=\'40\'>";
		//��������
		var name4="wTSendType";
		var ince4=adjtb_w.rows(num).insertCell(4);
		ince4.innerHTML="<input type=\'text\' name=\'"+name4+"\' class=\'default_input\' size=\'40\' >";
		//����̨��
		var name5="wTransNum";
		var ince5=adjtb_w.rows(num).insertCell(5);
		ince5.align="right";
		ince5.innerHTML="<input type=\'text\' name=\'"+name5+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number2();\' onkeyup=\"jisuanxiaoji_w('T')\" size=\'6\'>";
		//�������
		var name6="wTransPrice";
		var ince6=adjtb_w.rows(num).insertCell(6);
		ince6.align="right";
		ince6.innerHTML="<input type=\'text\' name=\'"+name6+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number3();\' onkeyup=\"jisuanxiaoji_w('T')\" size=\'10\'>";

		xuhaonum(name1);
	}else{
		alert(errorstr);
	}
  }
   /** ���� */ 
  function AddRow_W3(tableid){
	  var errorstr=wcheckweihuinfo2("","E","");
	  if(errorstr==""){
  		var adjtb_w=document.getElementById("adjtb_w3");
		var num=adjtb_w.rows.length;

		adjtb_w.insertRow(num);		
		//��ѡ��
		var name0="Wnodes1e";
		var ince0=adjtb_w.rows(num).insertCell(0);
		ince0.align="center";
		ince0.innerHTML="<input type=\'checkbox\' name=\'"+name0+"\' >";
		//���
		var name1="Wdivorder1e";
		var ince1=adjtb_w.rows(num).insertCell(1);
		ince1.align="center";
		ince1.innerHTML="<div id=\'"+name1+"\'>";
		//��
		var name2="wEfloor";
		var ince2=adjtb_w.rows(num).insertCell(2);
		ince2.innerHTML="<input type=\'text\' name=\'"+name2+"\' class=\'default_input\' size=\'4\' onkeyup=\"setStageValue(this,'wEstage')\" onkeypress=\'f_check_number2();\' >";
		//վ
		var name3="wEstage";
		var ince3=adjtb_w.rows(num).insertCell(3);
		ince3.innerHTML="<input type=\'text\' name=\'"+name3+"\' class=\'default_input\' size=\'4\' onkeypress=\'f_check_number2();\'>";
		//�������أ�kg��
		var name4="wEZzl";
		var ince4=adjtb_w.rows(num).insertCell(4);
		ince4.innerHTML="<input type=\'text\' name=\'"+name4+"\' onkeypress=\'f_check_number3();\' class=\'default_input\' size=\'10\'>";
		//�����ص�
		var name5="wEBuildAddress";
		var ince5=adjtb_w.rows(num).insertCell(5);
		ince5.innerHTML="<input type=\'text\' name=\'"+name5+"\' class=\'default_input\' size=\'40\'>";
		//������Ŀ
		var name6="wEBuildItem";
		var ince6=adjtb_w.rows(num).insertCell(6);
		ince6.innerHTML="<input type=\'text\' name=\'"+name6+"\' class=\'default_input\' size=\'40\' >";
		//�����ܸߣ��ף�
		var name7="wEJdH";
		var ince7=adjtb_w.rows(num).insertCell(7);
		ince7.innerHTML="<input type=\'text\' name=\'"+name7+"\' class=\'default_input\' size=\'10\' onkeypress=\'f_check_number3();\'>"
		//����̨��
		var name8="wEBuildNum";
		var ince8=adjtb_w.rows(num).insertCell(8);
		ince8.align="right";
		ince8.innerHTML="<input type=\'text\' name=\'"+name8+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number2();\' onkeyup=\"jisuanxiaoji_w('E')\" size=\'6\'>";
		//��������
		var name9="wEBuildPrice";
		var ince9=adjtb_w.rows(num).insertCell(9);
		ince9.align="right";
		ince9.innerHTML="<input type=\'text\' name=\'"+name9+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number3();\' onkeyup=\"jisuanxiaoji_w('E')\" size=\'10\'>";

		xuhaonum(name1);
	}else{
		alert(errorstr);
	}
  }
  
   /** �����ϸ��Ϣ,�����ʩ�������ڣ��ͱ�����д��ϸ��Ϣ */
 function wcheckweihuinfo2(error,typestr,showtype){
 	if(typestr=="WW"){
 		var Wdivorderw=document.getElementsByName("Wdivorderw");//���
		var wengcontent=document.getElementsByName("wengcontent");//��Ŀ
		var wqty=document.getElementsByName("wqty"); //����
		var wpersonprice=document.getElementsByName("wpersonprice");//����
		var wstuffprice=document.getElementsByName("wstuffprice");//���Ϸ�
		
		for(var i=0;i<Wdivorderw.length;i++){
			var xuhao=Wdivorderw[i].innerText;
			if(wengcontent[i].value.trim()==""){
	 			error+="  ���"+xuhao+" ��Ŀ������Ϊ�գ�\n";
	 		}
			if(wqty[i].value.trim()==""){
	 			error+="  ���"+xuhao+" ����������Ϊ�գ�\n";
	 		}else if(isNaN(wqty[i].value.trim())){
	 			error+="  ���"+xuhao+" ��������Ϊ���֣�\n";
	 		}
	 		if(wpersonprice[i].value.trim()==""){
	 			error+="  ���"+xuhao+" ���ò�����Ϊ�գ�\n";
	 		}else if(isNaN(wpersonprice[i].value.trim())){
	 			error+="  ���"+xuhao+" ���ñ���Ϊ���֣�\n";
	 		}
	 		if(wstuffprice[i].value.trim()==""){
	 			error+="  ���"+xuhao+" ���ϷѲ�����Ϊ�գ�\n";
	 		}else if(isNaN(wstuffprice[i].value.trim())){
	 			error+="  ���"+xuhao+" ���Ϸѱ���Ϊ���֣�\n";
	 		}
		}
		if(error!=""){
			error="ί�й��ϷѵĹ���:\n"+error;
		}
 	}else if(typestr=='D'){//��װ
		var wSCustId=document.getElementById("wDSCustId").value.trim();
		if(wSCustId!=""){
 			if(document.getElementsByName("Wdivorder")==null || document.getElementsByName("Wdivorder").length==0){
 				if(showtype=="W"){
 					error="��װ�������룬�������ϸ��Ϣ��";
 				}
 			}else{
 				var divorder=document.getElementsByName("Wdivorder");
 				
 				var wfloor=document.getElementsByName("wDfloor");//��
			 	var wstage=document.getElementsByName("wDstage");//վ
			 	var wZzl=document.getElementsByName("wDZzl");//�������أ�kg��
			 	var wSuspendAddress=document.getElementsByName("wDSuspendAddress");//��װ�ص�
			 	var wSuspendItem=document.getElementsByName("wDSuspendItem");//��װ��Ŀ
			 	var wSuspendBox=document.getElementsByName("wDSuspendBox");//��װ��ͷ
			 	var wCountExp=document.getElementsByName("wDCountExp");//���㹫ʽ��ԭ��װ�����ۣ�
			 	var wSuspendNum=document.getElementsByName("wDSuspendNum");//��װ̨��
			 	var wSuspendPrice=document.getElementsByName("wDSuspendPrice");//��װ����
 				
 				for(var i=0;i<divorder.length;i++){
 					var xuhao=divorder[i].innerText;
 					if(wfloor[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" �㲻����Ϊ�գ�\n";
			 		}else if(isNaN(wfloor[i].value.trim())){
			 			error+="��װ�������룬���"+xuhao+" �����Ϊ���֣�\n";
			 		}
			 		if(wstage[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" վ������Ϊ�գ�\n";
			 		}else if(isNaN(wstage[i].value)){
			 			error+="��װ�������룬���"+xuhao+" վ����Ϊ���֣�\n";
			 		}
			 		if(wZzl[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" �������ز�����Ϊ�գ�\n";
			 		}else if(isNaN(wZzl[i].value.trim())){
			 			error+="��װ�������룬���"+xuhao+" �������ر���Ϊ���֣�\n";
			 		}
			 		if(wSuspendAddress[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" ��װ�ص㲻����Ϊ�գ�\n";
			 		}
			 		if(wSuspendItem[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" ��װ��Ŀ������Ϊ�գ�\n";
			 		}
			 		if(wSuspendBox[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" ��װ��ͷ������Ϊ�գ�\n";
			 		}
			 		if(wCountExp[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" ���㹫ʽ������Ϊ�գ�\n";
			 		}
			 		if(wSuspendNum[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" ��װ̨��������Ϊ�գ�\n";
			 		}else if(isNaN(wSuspendNum[i].value.trim())){
			 			error+="��װ�������룬���"+xuhao+" ��װ̨������Ϊ���֣�\n";
			 		}else if(parseFloat(wSuspendNum[i].value.trim())<=0){
			 			error+="��װ�������룬���"+xuhao+" ��װ̨����������㣡\n";
			 		}
			 		if(wSuspendPrice[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" ��װ���ò�����Ϊ�գ�\n";
			 		}else if(isNaN(wSuspendPrice[i].value.trim())){
			 			error+="��װ�������룬���"+xuhao+" ��װ���ñ���Ϊ���֣�\n";
			 		}else if(parseFloat(wSuspendPrice[i].value.trim())<=0){
			 			error+="��װ�������룬���"+xuhao+" ��װ���ñ�������㣡\n";
			 		}
 				}
 			}
		}else{
			if(showtype==""){
				error+="����ѡ��ʩ������";
			}
		}
 	}else if(typestr=='B'){//����
 		var wBCustId=document.getElementById("wBCustId").value.trim();
		if(wBCustId!=""){			
 			if(document.getElementsByName("Wdivorder1")==null || document.getElementsByName("Wdivorder1").length==0){
 				if(showtype=="W"){
 					error="���﷢�����룬�������ϸ��Ϣ��";
 				}
 			}else{
 				var divorder=document.getElementsByName("Wdivorder1");
 				
 				var wBfloor=document.getElementsByName("wBfloor");//��
			 	var wBstage=document.getElementsByName("wBstage");//վ
			 	var wBZzl=document.getElementsByName("wBZzl");//�������أ�kg��
			 	var wBBuildAddress=document.getElementsByName("wBBuildAddress");//����ص�
			 	var wBBuildItem=document.getElementsByName("wBBuildItem");//������Ŀ
			 	var wBJdH=document.getElementsByName("wBJdH");//�����ܸ�
			 	var wBBuildNum=document.getElementsByName("wBBuildNum");//����̨��
			 	var wBBuildPrice=document.getElementsByName("wBBuildPrice");//�������
 				
 				for(var i=0;i<divorder.length;i++){
 					var xuhao=divorder[i].innerText;
 					if(wBfloor[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" �㲻����Ϊ�գ�\n";
			 		}else if(isNaN(wBfloor[i].value.trim())){
			 			error+="���﷢�����룬���"+xuhao+" �����Ϊ���֣�\n";
			 		}
			 		if(wBstage[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" վ������Ϊ�գ�\n";
			 		}else if(isNaN(wBstage[i].value)){
			 			error+="���﷢�����룬���"+xuhao+" վ����Ϊ���֣�\n";
			 		}
			 		if(wBZzl[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" �������ز�����Ϊ�գ�\n";
			 		}else if(isNaN(wBZzl[i].value.trim())){
			 			error+="���﷢�����룬���"+xuhao+" �������ر���Ϊ���֣�\n";
			 		}
			 		if(wBBuildAddress[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" ����ص㲻����Ϊ�գ�\n";
			 		}
			 		if(wBBuildItem[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" ������Ŀ������Ϊ�գ�\n";
			 		}
			 		if(wBJdH[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" �����ܸ߲�����Ϊ�գ�\n";
			 		}else if(isNaN(wBJdH[i].value.trim())){
			 			error+="���﷢�����룬���"+xuhao+" �����ܸ߱���Ϊ���֣�\n";
			 		}
			 		if(wBBuildNum[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" ����̨��������Ϊ�գ�\n";
			 		}else if(isNaN(wBBuildNum[i].value.trim())){
			 			error+="���﷢�����룬���"+xuhao+" ����̨������Ϊ���֣�\n";
			 		}else if(parseFloat(wBBuildNum[i].value.trim())<=0){
			 			error+="���﷢�����룬���"+xuhao+" ����̨����������㣡\n";
			 		}
			 		if(wBBuildPrice[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" ������ò�����Ϊ�գ�\n";
			 		}else if(isNaN(wBBuildPrice[i].value.trim())){
			 			error+="���﷢�����룬���"+xuhao+" ������ñ���Ϊ���֣�\n";
			 		}else if(parseFloat(wBBuildPrice[i].value.trim())<=0){
			 			error+="���﷢�����룬���"+xuhao+" ������ñ�������㣡\n";
			 		}
 				}
 			}
		}else{
			if(showtype==""){
				error+="����ѡ��ʩ������";
			}
		}
 	}else if(typestr=='T'){//����
 		 var wTCustId=document.getElementById("wTCustId").value.trim();
		if(wTCustId!=""){		
 			if(document.getElementsByName("Wdivorder2")==null || document.getElementsByName("Wdivorder2").length==0){
 				if(showtype=="W"){
 					error="���䷢�����룬�������ϸ��Ϣ��";
 				}
 			}else{
 				var divorder=document.getElementsByName("Wdivorder2");
 				
 				var wTransAddress=document.getElementsByName("wTransAddress");//����ص�
			 	var wTransItem=document.getElementsByName("wTransItem");//������Ŀ
			 	var wTSendType=document.getElementsByName("wTSendType");//��������
			 	var wTransNum=document.getElementsByName("wTransNum");//����̨��
			 	var wTransPrice=document.getElementsByName("wTransPrice");//�������
 				
 				for(var i=0;i<divorder.length;i++){
 					var xuhao=divorder[i].innerText;
			 		if(wTransAddress[i].value.trim()==""){
			 			error+="���䷢�����룬���"+xuhao+" ����ص㲻����Ϊ�գ�\n";
			 		}
			 		if(wTransItem[i].value.trim()==""){
			 			error+="���䷢�����룬���"+xuhao+" ������Ŀ������Ϊ�գ�\n";
			 		}
			 		if(wTSendType[i].value.trim()==""){
			 			error+="���䷢�����룬���"+xuhao+" �������Ͳ�����Ϊ�գ�\n";
			 		}
			 		if(wTransNum[i].value.trim()==""){
			 			error+="���䷢�����룬���"+xuhao+" ����̨��������Ϊ�գ�\n";
			 		}else if(isNaN(wTransNum[i].value.trim())){
			 			error+="���䷢�����룬���"+xuhao+" ����̨������Ϊ���֣�\n";
			 		}else if(parseFloat(wTransNum[i].value.trim())<=0){
			 			error+="���䷢�����룬���"+xuhao+" ����̨����������㣡\n";
			 		}
			 		if(wTransPrice[i].value.trim()==""){
			 			error+="���䷢�����룬���"+xuhao+" ������ò�����Ϊ�գ�\n";
			 		}else if(isNaN(wTransPrice[i].value.trim())){
			 			error+="���䷢�����룬���"+xuhao+" ������ñ���Ϊ���֣�\n";
			 		}else if(parseFloat(wTransPrice[i].value.trim())<=0){
			 			error+="���䷢�����룬���"+xuhao+" ������ñ�������㣡\n";
			 		}
 				}
 			}
		}else{
			if(showtype==""){
				error+="����ѡ��ʩ������";
			}
		}
 	}else if(typestr=='E'){//����
 		var wECustId=document.getElementById("wECustId").value.trim();
		if(wECustId!=""){
 			if(document.getElementsByName("Wdivorder1e")==null || document.getElementsByName("Wdivorder1e").length==0){
 				if(showtype=="W"){
 					error="�����������룬�������ϸ��Ϣ��";
 				}
 			}else{
 				var divorder=document.getElementsByName("Wdivorder1e");
 				
 				var wEfloor=document.getElementsByName("wEfloor");//��
			 	var wEstage=document.getElementsByName("wEstage");//վ
			 	var wEZzl=document.getElementsByName("wEZzl");//�������أ�kg��
			 	var wEBuildAddress=document.getElementsByName("wEBuildAddress");//�����ص�
			 	var wEBuildItem=document.getElementsByName("wEBuildItem");//������Ŀ
			 	var wEJdH=document.getElementsByName("wEJdH");//�����ܸ�
			 	var wEBuildNum=document.getElementsByName("wEBuildNum");//����̨��
			 	var wEBuildPrice=document.getElementsByName("wEBuildPrice");//��������
 				
 				for(var i=0;i<divorder.length;i++){
 					var xuhao=divorder[i].innerText;
 					if(wEfloor[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" �㲻����Ϊ�գ�\n";
			 		}else if(isNaN(wEfloor[i].value.trim())){
			 			error+="�����������룬���"+xuhao+" �����Ϊ���֣�\n";
			 		}
			 		if(wEstage[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" վ������Ϊ�գ�\n";
			 		}else if(isNaN(wEstage[i].value)){
			 			error+="�����������룬���"+xuhao+" վ����Ϊ���֣�\n";
			 		}
			 		if(wEZzl[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" �������ز�����Ϊ�գ�\n";
			 		}else if(isNaN(wEZzl[i].value.trim())){
			 			error+="�����������룬���"+xuhao+" �������ر���Ϊ���֣�\n";
			 		}
			 		if(wEBuildAddress[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" �����ص㲻����Ϊ�գ�\n";
			 		}
			 		if(wEBuildItem[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" ������Ŀ������Ϊ�գ�\n";
			 		}
			 		if(wEJdH[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" �����ܸ߲�����Ϊ�գ�\n";
			 		}else if(isNaN(wEJdH[i].value.trim())){
			 			error+="�����������룬���"+xuhao+" �����ܸ߱���Ϊ���֣�\n";
			 		}
			 		if(wEBuildNum[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" ����̨��������Ϊ�գ�\n";
			 		}else if(isNaN(wEBuildNum[i].value.trim())){
			 			error+="�����������룬���"+xuhao+" ����̨������Ϊ���֣�\n";
			 		}else if(parseFloat(wEBuildNum[i].value.trim())<=0){
			 			error+="�����������룬���"+xuhao+" ����̨����������㣡\n";
			 		}
			 		if(wEBuildPrice[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" �������ò�����Ϊ�գ�\n";
			 		}else if(isNaN(wEBuildPrice[i].value.trim())){
			 			error+="�����������룬���"+xuhao+" �������ñ���Ϊ���֣�\n";
			 		}else if(parseFloat(wEBuildPrice[i].value.trim())<=0){
			 			error+="�����������룬���"+xuhao+" �������ñ�������㣡\n";
			 		}
 				}
 			}
		}else{
			if(showtype==""){
				error+="����ѡ��ʩ������";
			}
		}
 	}

 	return error;
 }
  /** ��̨�� �ܷ��� parseFloat();parseInt();isNaN();����Ƿ����ַ� */
 function jisuanxiaoji_w(typestr){
 	
 	var prosum=0;
 	var costsum=0;
 	
 	if(typestr=='WW'){ 		
 		var personprice=document.getElementsByName("wpersonprice"); //����
 		var stuffprice=document.getElementsByName("wstuffprice");//���Ϸ�
		for(var i=0;i<personprice.length;i++){
			var perstre=personprice[i].value;
			if(perstre==""){
				perstre="0";
			}
			var stustr=stuffprice[i].value;
			if(stustr==""){
				stustr="0";
			}
			prosum=parseFloat(prosum)+parseFloat(perstre)+parseFloat(stustr);
		}
 		//ί�з��� ����2λС��
    	document.getElementById("divwxiaoji").innerText=Math.round(parseFloat(prosum)*100)/100;
 	}else if(typestr=='D'){
 		var name0="divwProSum";
 		var name1="divwProCostSum";
 		var name2="wDSuspendNum";
 		var name3="wDSuspendPrice";
 		var divwProSum=document.getElementById(name0);//��װ��̨�� D
 		var divwProCostSum=document.getElementById(name1);//��װ�ܷ���
 		var wDAllCost=document.getElementById("wDAllCost");
 		if(document.getElementsByName(name2)==null || document.getElementsByName(name2).length==0){
 			
 		}else{
 			var wSuspendNum=document.getElementsByName(name2);
 			var wSuspendPrice=document.getElementsByName(name3);
 			for(var i=0;i<wSuspendNum.length;i++){
 				var num=wSuspendNum[i].value;
		 		if(num==""){
		 			num="0";
		 		}
		 		var price=wSuspendPrice[i].value;
		 		if(price==""){
		 			price="0";
		 		}
		 		prosum=parseFloat(prosum)+parseFloat(num);
		 		costsum=parseFloat(costsum)+parseFloat(price);
 			}
 			
 		}
 		divwProSum.innerHTML=prosum;
 		divwProCostSum.innerHTML=parseFloat(costsum).toFixed(2);
 		wDAllCost.value=parseFloat(costsum).toFixed(2);
 	}else if(typestr=='B'){
 		var name0="divwBProSum";
 		var name1="divwBProCostSum";
 		var name2="wBBuildNum";
 		var name3="wBBuildPrice";
 		var divwBProSum=document.getElementById(name0);//������̨�� B
 		var divwBProCostSum=document.getElementById(name1);//�����ܷ���
 		var wBAllCost=document.getElementById("wBAllCost");
 		if(document.getElementsByName(name2)==null || document.getElementsByName(name2).length==0){
 			
 		}else{
 			var wBBuildNum=document.getElementsByName(name2);
 			var wBBuildPrice=document.getElementsByName(name3);
 			for(var i=0;i<wBBuildNum.length;i++){
 				var num=wBBuildNum[i].value;
		 		if(num==""){
		 			num="0";
		 		}
		 		var price=wBBuildPrice[i].value;
		 		if(price==""){
		 			price="0";
		 		}
		 		prosum=parseFloat(prosum)+parseFloat(num);
		 		costsum=parseFloat(costsum)+parseFloat(price);
 			}
 			
 		}
 		divwBProSum.innerHTML=prosum;
 		divwBProCostSum.innerHTML=parseFloat(costsum).toFixed(2);
 		wBAllCost.value=parseFloat(costsum).toFixed(2);
 	}else if(typestr=='T'){
 		var name0="divwTProSum";
 		var name1="divwTProCostSum";
 		var name2="wTransNum";
 		var name3="wTransPrice";
 		var divwTProSum=document.getElementById(name0);//������̨�� T
 		var divwTProCostSum=document.getElementById(name1);//�����ܷ��� 
 		var wTAllCost=document.getElementById("wTAllCost");
 		if(document.getElementsByName(name2)==null || document.getElementsByName(name2).length==0){
 			
 		}else{
 			var wTransNum=document.getElementsByName(name2);
 			var wTransPrice=document.getElementsByName(name3);
 			for(var i=0;i<wTransNum.length;i++){
 				var num=wTransNum[i].value;
		 		if(num==""){
		 			num="0";
		 		}
		 		var price=wTransPrice[i].value;
		 		if(price==""){
		 			price="0";
		 		}
		 		prosum=parseFloat(prosum)+parseFloat(num);
		 		costsum=parseFloat(costsum)+parseFloat(price);
 			}
 		}
 		divwTProSum.innerHTML=prosum;
 		divwTProCostSum.innerHTML=parseFloat(costsum).toFixed(2);
 		wTAllCost.value=parseFloat(costsum).toFixed(2);
 		
 	}else if(typestr=='E'){//����
 		var name0="divwEProSum";
 		var name1="divwEProCostSum";
 		var name2="wEBuildNum";
 		var name3="wEBuildPrice";
 		var divwBProSum=document.getElementById(name0);//������̨�� B
 		var divwBProCostSum=document.getElementById(name1);//�����ܷ���
 		var wEAllCost=document.getElementById("wEAllCost");
 		if(document.getElementsByName(name2)==null || document.getElementsByName(name2).length==0){
 			
 		}else{
 			var wEBuildNum=document.getElementsByName(name2);
 			var wEBuildPrice=document.getElementsByName(name3);
 			for(var i=0;i<wEBuildNum.length;i++){
 				var num=wEBuildNum[i].value;
		 		if(num==""){
		 			num="0";
		 		}
		 		var price=wEBuildPrice[i].value;
		 		if(price==""){
		 			price="0";
		 		}
		 		prosum=parseFloat(prosum)+parseFloat(num);
		 		costsum=parseFloat(costsum)+parseFloat(price);
 			}
 			
 		}
 		divwEProSum.innerHTML=prosum;
 		divwEProCostSum.innerHTML=parseFloat(costsum).toFixed(2);
 		wEAllCost.value=parseFloat(costsum).toFixed(2);
 	}
 }
 
 /** ��Ŀί�м۸񹹳� */
 function projectPriceConstitute(num){ 
 	
 	/** ����˰�� */
	var yyshui=0.33;//33%�ɱ���
 	var cjshui=0.04;//˰��Լ4%

 	var contractsum=document.getElementById("contractsum"); //��ͬ�ܼ�
	var costamount=document.getElementById("costamount"); //�ɱ���
	var materialcost=document.getElementById("materialcost");//Ԥ�Ʋ��ϳɱ�
	var taxes=document.getElementById("taxes"); //˰��
	var costproject=document.getElementById("costproject"); //�ɱ�С��
	var workfee=document.getElementById("workfee"); //�ⶨί�й���
	var freight=document.getElementById("freight"); //�˷�
	var entrustproject=document.getElementById("entrustproject");//ί��С��
	var maori=document.getElementById("maori"); //ë��
	var grossprofitmargin=document.getElementById("grossprofitmargin");//ë����
	var costrate=document.getElementById("costrate"); //�ɱ���
	
	var div_contractsum=document.getElementById("div_contractsum"); //��ͬ�ܼ�
	var div_costamount=document.getElementById("div_costamount"); //�ɱ���
	var div_taxes=document.getElementById("div_taxes"); //˰��
	var div_costproject=document.getElementById("div_costproject"); //�ɱ�С��
	//var div_entrustproject=document.getElementById("div_entrustproject");//ί��С��
	var div_maori=document.getElementById("div_maori"); //ë��
	var div_grossprofitmargin=document.getElementById("div_grossprofitmargin");//ë����
	var div_costrate=document.getElementById("div_costrate"); //�ɱ���
	
	var div_WAllCost=document.getElementById("divWAllCost");//ί�з���
	var WAllCost=document.getElementById("WAllCost");//ί�з���
	
	if(num=="0" || num=="1"){
		//�ɱ��� (��ͬ�ܼ�*0.33)
		var coststr=(parseFloat(contractsum.value)*yyshui).toFixed(2);
		costamount.value=coststr;
		div_costamount.innerText=coststr;  
		//˰�� (��ͬ�ܼ�*0.04)
		var taxestr=(parseFloat(contractsum.value)*cjshui).toFixed(2);
		taxes.value=taxestr;
		div_taxes.innerText=taxestr;
		//�ɱ�С�� (Ԥ�Ʋ��ϳɱ�+˰��)
		var mavalue=materialcost.value;
		if(mavalue.trim()==""){
			mavalue="0";
		}
		var costamstr=(parseFloat(mavalue)+parseFloat(taxestr)).toFixed(2);
		costproject.value=costamstr;
		div_costproject.innerText=costamstr;
		//ί��С�� (�ⶨί�й���+�˷�)
		var workfeestr=workfee.value;
		var freightstr=freight.value;
		if(workfeestr.trim()==""){
			workfeestr="0";
		}
		if(freightstr.trim()==""){
			freightstr="0";
		}
		var entrstr=(parseFloat(workfeestr)+parseFloat(freightstr)).toFixed(2);
		entrustproject.value=entrstr;
		//div_entrustproject.innerText=entrstr;
		div_WAllCost.innerText=entrstr;//ί�з���
		WAllCost.value=entrstr;//ί�з���
		
		//ë�� (��ͬ�ܼ�-�ɱ�С��-ί��С��)
		var maostr=(parseFloat(contractsum.value)-costamstr-entrstr).toFixed(2);
		maori.value=maostr;
		div_maori.innerText=maostr;
		//ë���� (ë��/��ͬ�ܼ�)
		var rosstr=(maostr/parseFloat(contractsum.value)*100).toFixed(2);
		grossprofitmargin.value=rosstr;
		div_grossprofitmargin.innerText=rosstr+"%";
		//�ɱ��� (ί��С��+�ɱ�С��)/��ͬ�ܼ�
		var cosvalue=((parseFloat(entrstr)+parseFloat(costamstr))/parseFloat(contractsum.value)*100).toFixed(2);
		costrate.value=cosvalue;
		div_costrate.innerText=cosvalue+"%";
		
	}else if(num=="3"){ //Ԥ�Ʋ��ϳɱ�����
		//�ɱ�С�� (Ԥ�Ʋ��ϳɱ�+˰��)
		var mavalue=materialcost.value;
		if(mavalue.trim()==""){
			mavalue="0";
		}
		var costamstr=(parseFloat(mavalue)+parseFloat(taxes.value)).toFixed(2);
		costproject.value=costamstr;
		div_costproject.innerText=costamstr;
		//ë�� (��ͬ�ܼ�-�ɱ�С��-ί��С��)
		var maostr=(parseFloat(contractsum.value)-costamstr-parseFloat(entrustproject.value)).toFixed(2);
		maori.value=maostr;
		div_maori.innerText=maostr;
		//ë���� (ë��/��ͬ�ܼ�)
		var rosstr=(maostr/parseFloat(contractsum.value)*100).toFixed(2);
		grossprofitmargin.value=rosstr;
		div_grossprofitmargin.innerText=rosstr+"%";
		//�ɱ��� (ί��С��+�ɱ�С��)/��ͬ�ܼ�
		var cosvalue=((parseFloat(entrustproject.value)+parseFloat(costamstr))/parseFloat(contractsum.value)*100).toFixed(2);
		costrate.value=cosvalue;
		div_costrate.innerText=cosvalue+"%";
		
 	}else if(num=="6" || num=="7"){//�ⶨί�й��� or �˷� ����
		//ί��С�� (�ⶨί�й���+�˷�)
		var workfeestr=workfee.value;
		var freightstr=freight.value;
		if(workfeestr.trim()==""){
			workfeestr="0";
		}
		if(freightstr.trim()==""){
			freightstr="0";
		}
		var entrstr=(parseFloat(workfeestr)+parseFloat(freightstr)).toFixed(2);
		entrustproject.value=entrstr;
		//div_entrustproject.innerText=entrstr;		
		div_WAllCost.innerText=entrstr;//ί�з���
		WAllCost.value=entrstr;//ί�з���
		
		//ë�� (��ͬ�ܼ�-�ɱ�С��-ί��С��)
		var maostr=(parseFloat(contractsum.value)-parseFloat(costproject.value)-entrstr).toFixed(2);
		maori.value=maostr;
		div_maori.innerText=maostr;
		//ë���� (ë��/��ͬ�ܼ�)
		var rosstr=(maostr/parseFloat(contractsum.value)*100).toFixed(2);
		grossprofitmargin.value=rosstr;
		div_grossprofitmargin.innerText=rosstr+"%";
		//�ɱ��� (ί��С��+�ɱ�С��)/��ͬ�ܼ�
		var cosvalue=((parseFloat(entrstr)+parseFloat(costproject.value))/parseFloat(contractsum.value)*100).toFixed(2);
		costrate.value=cosvalue;
		div_costrate.innerText=cosvalue+"%";

 	}else if(num=="8"){//ί��С������
 		var entrpstr = entrustproject.value;
 		div_WAllCost.innerText=entrpstr;//ί�з���
		WAllCost.value=entrpstr;//ί�з���
		
		//ë�� (��ͬ�ܼ�-�ɱ�С��-ί��С��)
		var maostr=(parseFloat(contractsum.value)-parseFloat(costproject.value)-parseFloat(entrpstr)).toFixed(2);
		maori.value=maostr;
		div_maori.innerText=maostr;
		//ë���� (ë��/��ͬ�ܼ�)
		var rosstr=(maostr/parseFloat(contractsum.value)*100).toFixed(2);
		grossprofitmargin.value=rosstr;
		div_grossprofitmargin.innerText=rosstr+"%";
		//�ɱ��� (ί��С��+�ɱ�С��)/��ͬ�ܼ�
		var cosvalue=((parseFloat(entrpstr)+parseFloat(costproject.value))/parseFloat(contractsum.value)*100).toFixed(2);
		costrate.value=cosvalue;
		div_costrate.innerText=cosvalue+"%";
 	}
 }
/**=================== ά�ޱ��� end ===========================*/
 
/**=================== ���챨�� start ===========================*/
 //����������
 function sendServicingContractDetailG(billno) {
 	var url = '/XJSCRM/SearchEngEpiCustomerAction.do?method=toServicingContractDetailG';
		url +='&gbillno='+billno;
  	createXMLHttpRequest2();
 	XMLHttpReq2.open("post", url, false);
  	XMLHttpReq2.onreadystatechange = processServicingContractDetailG;//ָ����Ӧ����
  	XMLHttpReq2.send(null);  // ��������
 }
 // ��������Ϣ����
 function processServicingContractDetailG() {
     	if (XMLHttpReq2.readyState == 4) { // �ж϶���״̬
         	if (XMLHttpReq2.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ        	
         		var xmlDOM=XMLHttpReq2.responseXML;

            	var elevatorid=xmlDOM.getElementsByTagName("elevatorid");
            	var floor=xmlDOM.getElementsByTagName("floor");
            	var stage=xmlDOM.getElementsByTagName("stage");
            	var door=xmlDOM.getElementsByTagName("door");
            	var high=xmlDOM.getElementsByTagName("high");
            	var num=xmlDOM.getElementsByTagName("num");
            	var angle=xmlDOM.getElementsByTagName("angle");
            	var amt=xmlDOM.getElementsByTagName("amt");
            	var detailrowid=xmlDOM.getElementsByTagName("detailrowid");
            	var quoteelevatorid=xmlDOM.getElementsByTagName("quoteelevatorid");
            	//��ɾ���Ѿ����ڵĵ����ͺ���Ϣ
            	delSelNode22(adjtb_gd);
            	var amtsum=0;
            	for(var i=0;i<elevatorid.length;i++){
            		var eleid=elevatorid[i].firstChild.data;
            		var floorstr=floor[i].firstChild.data;
            		var stagestr=stage[i].firstChild.data;
            		var doorstr=door[i].firstChild.data;
            		var highstr=high[i].firstChild.data;
            		var numstr=num[i].firstChild.data;
            		var eanglestr=angle[i].firstChild.data;
            		var amtstr=amt[i].firstChild.data;
            		var rowidstr=detailrowid[i].firstChild.data;
            		var quotestr=quoteelevatorid[i].firstChild.data;
            		
            		AddRow_GD(eleid,floorstr,stagestr,doorstr,highstr,numstr,eanglestr,rowidstr,quotestr);
            	}
         	} else { //ҳ�治����
               window.alert("���������ҳ�����쳣��");
         	}
       }
    }
  /** ��Ӻ�ͬ������ϸ��Ϣ */
  function AddRow_GD(elevatorid,floor,stage,door,high,numkk,angle,rowidstr,quotestr){
		var adjtb_w=document.getElementById("adjtb_gd");
		var num=adjtb_w.rows.length;

		adjtb_w.insertRow(num);

		//�����ͺ�
		var name0="gelevatorid";
		var name00="gdetailrowid";
		var name000="gquoteelevatorid";
		var ince1=adjtb_w.rows(num).insertCell(0);
		ince1.innerHTML=elevatorid+"<input type=\'hidden\' name=\'"+name0+"\' value=\'"+elevatorid+"\' /><input type=\'hidden\' name=\'"+name00+"\' value=\'"+rowidstr+"\' /><input type=\'hidden\' name=\'"+name000+"\' value=\'"+quotestr+"\' />";
		//��վ��
		var name1="gfloor";
		var name2="gstage";
		var name3="gdoor";
		var ince2=adjtb_w.rows(num).insertCell(1);
		ince2.innerHTML=floor+"/"+stage+"/"+door+"<input type=\'hidden\' name=\'"+name1+"\' value=\'"+floor+"\' /><input type=\'hidden\' name=\'"+name2+"\' value=\'"+stage+"\' /><input type=\'hidden\' name=\'"+name3+"\' value=\'"+door+"\' />";
		//�����߶�
		var name4="ghigh";
		var ince3=adjtb_w.rows(num).insertCell(2);
		ince3.align="right";
		ince3.innerHTML=high+"&nbsp;<input type=\'hidden\' name=\'"+name4+"\' value=\'"+high+"\' />";
		//��б�Ƕ�
		var name5="gangle";
		var ince4=adjtb_w.rows(num).insertCell(3);
		ince4.align="right";
		ince4.innerHTML=angle+"&nbsp;<input type=\'hidden\' name=\'"+name5+"\' value=\'"+angle+"\' />";
		//����
		var name6="gnum";
		var ince5=adjtb_w.rows(num).insertCell(4);
		ince5.align="right";
		ince5.innerHTML=numkk+"&nbsp;<input type=\'hidden\' name=\'"+name6+"\' value=\'"+numkk+"\' />";
 }
 /** ��װ */ 
  function AddRow_G(tableid){
  	var errorstr=gcheckweihuinfo2("","D","");
  	if(errorstr==""){
		var adjtb_w=document.getElementById("adjtb_g");
		var num=adjtb_w.rows.length;

		adjtb_w.insertRow(num);		
		//��ѡ��
		var name0="Gnodes";
		var ince0=adjtb_w.rows(num).insertCell(0);
		ince0.align="center";
		ince0.innerHTML="<input type=\'checkbox\' name=\'"+name0+"\' >";
		//���
		var name1="Gdivorder";
		var ince1=adjtb_w.rows(num).insertCell(1);
		ince1.align="center";
		ince1.innerHTML="<div id=\'"+name1+"\'>";
		//��
		var name2="gDfloor";
		var ince2=adjtb_w.rows(num).insertCell(2);
		ince2.innerHTML="<input type=\'text\' name=\'"+name2+"\' class=\'default_input\' size=\'4\'  onkeyup=\"setStageValue(this,'gDstage')\" onkeypress=\'f_check_number2();\' >";
		//վ
		var name3="gDstage";
		var ince3=adjtb_w.rows(num).insertCell(3);
		ince3.innerHTML="<input type=\'text\' name=\'"+name3+"\' class=\'default_input\' size=\'4\' onkeypress=\'f_check_number2();\'>";
		//�������أ�kg��
		var name4="gDZzl";
		var ince4=adjtb_w.rows(num).insertCell(4);
		ince4.innerHTML="<input type=\'text\' name=\'"+name4+"\' onkeypress=\'f_check_number3();\' size=\'10\' class=\'default_input\'>";
		//��װ�ص�
		var name5="gDSuspendAddress";
		var ince5=adjtb_w.rows(num).insertCell(5);
		ince5.innerHTML="<input type=\'text\' name=\'"+name5+"\' class=\'default_input\'>";
		//��װ��Ŀ
		var name6="gDSuspendItem";
		var ince6=adjtb_w.rows(num).insertCell(6);
		ince6.innerHTML="<input type=\'text\' name=\'"+name6+"\' class=\'default_input\' >";
		//��װ��ͷ
		var name7="gDSuspendBox";
		var ince7=adjtb_w.rows(num).insertCell(7);
		ince7.innerHTML="<input type=\'text\' name=\'"+name7+"\' class=\'default_input\' >";
		//���㹫ʽ��ԭ��װ�����ۣ�
		var name8="gDCountExp";
		var ince8=adjtb_w.rows(num).insertCell(8);
		//ince8.innerHTML="<input type=\'text\' name=\'wCountExp\' class=\'default_input\' >";
		ince8.innerHTML="<textarea name=\'"+name8+"\' rows=\'1\' cols=\'30\'>";
		//��װ̨��
		var name9="gDSuspendNum";
		var ince9=adjtb_w.rows(num).insertCell(9);
		ince9.align="right";
		ince9.innerHTML="<input type=\'text\' name=\'"+name9+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number2();\' onkeyup=\"jisuanxiaoji_g('D')\" size=\'6\'>";
		//��װ����
		var name10="gDSuspendPrice";
		var ince10=adjtb_w.rows(num).insertCell(10);
		ince10.align="right";
		ince10.innerHTML="<input type=\'text\' name=\'"+name10+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number3();\' onkeyup=\"jisuanxiaoji_g('D')\" size=\'10\'>";

		xuhaonum(name1);
	}else{
		alert(errorstr);
	}
  }
  /** ���� */ 
  function AddRow_G1(tableid){
	  var errorstr=gcheckweihuinfo2("","B","");
	  if(errorstr==""){
  		var adjtb_w=document.getElementById("adjtb_g1");
		var num=adjtb_w.rows.length;

		adjtb_w.insertRow(num);		
		//��ѡ��
		var name0="Gnodes1";
		var ince0=adjtb_w.rows(num).insertCell(0);
		ince0.align="center";
		ince0.innerHTML="<input type=\'checkbox\' name=\'"+name0+"\' >";
		//���
		var name1="Gdivorder1";
		var ince1=adjtb_w.rows(num).insertCell(1);
		ince1.align="center";
		ince1.innerHTML="<div id=\'"+name1+"\'>";
		//��
		var name2="gBfloor";
		var ince2=adjtb_w.rows(num).insertCell(2);
		ince2.innerHTML="<input type=\'text\' name=\'"+name2+"\' class=\'default_input\' size=\'4\'  onkeyup=\"setStageValue(this,'gBstage')\" onkeypress=\'f_check_number2();\' >";
		//վ
		var name3="gBstage";
		var ince3=adjtb_w.rows(num).insertCell(3);
		ince3.innerHTML="<input type=\'text\' name=\'"+name3+"\' class=\'default_input\' size=\'4\' onkeypress=\'f_check_number2();\'>";
		//�������أ�kg��
		var name4="gBZzl";
		var ince4=adjtb_w.rows(num).insertCell(4);
		ince4.innerHTML="<input type=\'text\' name=\'"+name4+"\' onkeypress=\'f_check_number3();\' class=\'default_input\' size=\'10\'>";
		//����ص�
		var name5="gBBuildAddress";
		var ince5=adjtb_w.rows(num).insertCell(5);
		ince5.innerHTML="<input type=\'text\' name=\'"+name5+"\' class=\'default_input\' size=\'40\'>";
		//������Ŀ
		var name6="gBBuildItem";
		var ince6=adjtb_w.rows(num).insertCell(6);
		ince6.innerHTML="<input type=\'text\' name=\'"+name6+"\' class=\'default_input\' size=\'40\' >";
		//�����ܸߣ��ף�
		var name7="gBJdH";
		var ince7=adjtb_w.rows(num).insertCell(7);
		ince7.innerHTML="<input type=\'text\' name=\'"+name7+"\' class=\'default_input\' size=\'10\' onkeypress=\'f_check_number3();\'>"
		//����̨��
		var name8="gBBuildNum";
		var ince8=adjtb_w.rows(num).insertCell(8);
		ince8.align="right";
		ince8.innerHTML="<input type=\'text\' name=\'"+name8+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number2();\' onkeyup=\"jisuanxiaoji_g('B')\" size=\'6\'>";
		//�������
		var name9="gBBuildPrice";
		var ince9=adjtb_w.rows(num).insertCell(9);
		ince9.align="right";
		ince9.innerHTML="<input type=\'text\' name=\'"+name9+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number3();\' onkeyup=\"jisuanxiaoji_g('B')\" size=\'10\'>";

		xuhaonum(name1);
	}else{
		alert(errorstr);
	}
  }
  /** ���� */
  function AddRow_G2(tableid){
	  var errorstr=gcheckweihuinfo2("","T","");
	  if(errorstr==""){
  		var adjtb_w=document.getElementById("adjtb_g2");
		var num=adjtb_w.rows.length;

		adjtb_w.insertRow(num);		
		//��ѡ��
		var name0="Gnodes2";
		var ince0=adjtb_w.rows(num).insertCell(0);
		ince0.align="center";
		ince0.innerHTML="<input type=\'checkbox\' name=\'"+name0+"\' >";
		//���
		var name1="Gdivorder2";
		var ince1=adjtb_w.rows(num).insertCell(1);
		ince1.align="center";
		ince1.innerHTML="<div id=\'"+name1+"\'>";
		//����ص�
		var name2="gTransAddress";
		var ince2=adjtb_w.rows(num).insertCell(2);
		ince2.innerHTML="<input type=\'text\' name=\'"+name2+"\' class=\'default_input\' size=\'40\' >";
		//������Ŀ
		var name3="gTransItem";
		var ince3=adjtb_w.rows(num).insertCell(3);
		ince3.innerHTML="<input type=\'text\' name=\'"+name3+"\' class=\'default_input\' size=\'40\'>";
		//��������
		var name4="gTSendType";
		var ince4=adjtb_w.rows(num).insertCell(4);
		ince4.innerHTML="<input type=\'text\' name=\'"+name4+"\' class=\'default_input\' size=\'40\' >";
		//����̨��
		var name5="gTransNum";
		var ince5=adjtb_w.rows(num).insertCell(5);
		ince5.align="right";
		ince5.innerHTML="<input type=\'text\' name=\'"+name5+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number2();\' onkeyup=\"jisuanxiaoji_g('T')\" size=\'6\'>";
		//�������
		var name6="gTransPrice";
		var ince6=adjtb_w.rows(num).insertCell(6);
		ince6.align="right";
		ince6.innerHTML="<input type=\'text\' name=\'"+name6+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number3();\' onkeyup=\"jisuanxiaoji_g('T')\" size=\'10\'>";

		xuhaonum(name1);
	}else{
		alert(errorstr);
	}
  }
    /** ���� */ 
  function AddRow_G3(tableid){
	  var errorstr=gcheckweihuinfo2("","E","");
	  if(errorstr==""){
  		var adjtb_w=document.getElementById("adjtb_g3");
		var num=adjtb_w.rows.length;

		adjtb_w.insertRow(num);		
		//��ѡ��
		var name0="Gnodes1e";
		var ince0=adjtb_w.rows(num).insertCell(0);
		ince0.align="center";
		ince0.innerHTML="<input type=\'checkbox\' name=\'"+name0+"\' >";
		//���
		var name1="Gdivorder1e";
		var ince1=adjtb_w.rows(num).insertCell(1);
		ince1.align="center";
		ince1.innerHTML="<div id=\'"+name1+"\'>";
		//��
		var name2="gEfloor";
		var ince2=adjtb_w.rows(num).insertCell(2);
		ince2.innerHTML="<input type=\'text\' name=\'"+name2+"\' class=\'default_input\' size=\'4\'  onkeyup=\"setStageValue(this,'gEstage')\" onkeypress=\'f_check_number2();\' >";
		//վ
		var name3="gEstage";
		var ince3=adjtb_w.rows(num).insertCell(3);
		ince3.innerHTML="<input type=\'text\' name=\'"+name3+"\' class=\'default_input\' size=\'4\' onkeypress=\'f_check_number2();\'>";
		//�������أ�kg��
		var name4="gEZzl";
		var ince4=adjtb_w.rows(num).insertCell(4);
		ince4.innerHTML="<input type=\'text\' name=\'"+name4+"\' onkeypress=\'f_check_number3();\' class=\'default_input\' size=\'10\'>";
		//�����ص�
		var name5="gEBuildAddress";
		var ince5=adjtb_w.rows(num).insertCell(5);
		ince5.innerHTML="<input type=\'text\' name=\'"+name5+"\' class=\'default_input\' size=\'40\'>";
		//������Ŀ
		var name6="gEBuildItem";
		var ince6=adjtb_w.rows(num).insertCell(6);
		ince6.innerHTML="<input type=\'text\' name=\'"+name6+"\' class=\'default_input\' size=\'40\' >";
		//�����ܸߣ��ף�
		var name7="gEJdH";
		var ince7=adjtb_w.rows(num).insertCell(7);
		ince7.innerHTML="<input type=\'text\' name=\'"+name7+"\' class=\'default_input\' size=\'10\' onkeypress=\'f_check_number3();\'>"
		//����̨��
		var name8="gEBuildNum";
		var ince8=adjtb_w.rows(num).insertCell(8);
		ince8.align="right";
		ince8.innerHTML="<input type=\'text\' name=\'"+name8+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number2();\' onkeyup=\"jisuanxiaoji_g('E')\" size=\'6\'>";
		//��������
		var name9="gEBuildPrice";
		var ince9=adjtb_w.rows(num).insertCell(9);
		ince9.align="right";
		ince9.innerHTML="<input type=\'text\' name=\'"+name9+"\' class=\'default_input\' value=\'0\' onkeypress=\'f_check_number3();\' onkeyup=\"jisuanxiaoji_g('E')\" size=\'10\'>";

		xuhaonum(name1);
	}else{
		alert(errorstr);
	}
  }
   /** ��̨�� �ܷ��� parseFloat();parseInt();isNaN();����Ƿ����ַ� */
 function jisuanxiaoji_g(typestr){
 	
 	var prosum=0;
 	var costsum=0;
 	
 	if(typestr=='D'){
 		var name0="divgProSum";
 		var name1="divgProCostSum";
 		var name2="gDSuspendNum";
 		var name3="gDSuspendPrice";
 		var divwProSum=document.getElementById(name0);//��װ��̨�� D
 		var divwProCostSum=document.getElementById(name1);//��װ�ܷ���
 		var gDAllCost=document.getElementById("gDAllCost");
 		if(document.getElementsByName(name2)==null || document.getElementsByName(name2).length==0){
 			
 		}else{
 			var wSuspendNum=document.getElementsByName(name2);
 			var wSuspendPrice=document.getElementsByName(name3);
 			for(var i=0;i<wSuspendNum.length;i++){
 				var num=wSuspendNum[i].value;
		 		if(num==""){
		 			num="0";
		 		}
		 		var price=wSuspendPrice[i].value;
		 		if(price==""){
		 			price="0";
		 		}
		 		prosum=parseFloat(prosum)+parseFloat(num);
		 		costsum=parseFloat(costsum)+parseFloat(price);
 			}
 			
 		}
 		divwProSum.innerHTML=prosum;
 		divwProCostSum.innerHTML=parseFloat(costsum).toFixed(2);
 		gDAllCost.value=parseFloat(costsum).toFixed(2);
 	}else if(typestr=='B'){
 		var name0="divgBProSum";
 		var name1="divgBProCostSum";
 		var name2="gBBuildNum";
 		var name3="gBBuildPrice";
 		var divwBProSum=document.getElementById(name0);//������̨�� B
 		var divwBProCostSum=document.getElementById(name1);//�����ܷ���
 		var gBAllCost=document.getElementById("gBAllCost");
 		if(document.getElementsByName(name2)==null || document.getElementsByName(name2).length==0){
 			
 		}else{
 			var wBBuildNum=document.getElementsByName(name2);
 			var wBBuildPrice=document.getElementsByName(name3);
 			for(var i=0;i<wBBuildNum.length;i++){
 				var num=wBBuildNum[i].value;
		 		if(num==""){
		 			num="0";
		 		}
		 		var price=wBBuildPrice[i].value;
		 		if(price==""){
		 			price="0";
		 		}
		 		prosum=parseFloat(prosum)+parseFloat(num);
		 		costsum=parseFloat(costsum)+parseFloat(price);
 			}
 			
 		}
 		divwBProSum.innerHTML=prosum;
 		divwBProCostSum.innerHTML=parseFloat(costsum).toFixed(2);
 		gBAllCost.value=parseFloat(costsum).toFixed(2);
 	}else if(typestr=='T'){
 		var name0="divgTProSum";
 		var name1="divgTProCostSum";
 		var name2="gTransNum";
 		var name3="gTransPrice";
 		var divwTProSum=document.getElementById(name0);//������̨�� T
 		var divwTProCostSum=document.getElementById(name1);//�����ܷ��� 
 		var gTAllCost=document.getElementById("gTAllCost");
 		if(document.getElementsByName(name2)==null || document.getElementsByName(name2).length==0){
 			
 		}else{
 			var wTransNum=document.getElementsByName(name2);
 			var wTransPrice=document.getElementsByName(name3);
 			for(var i=0;i<wTransNum.length;i++){
 				var num=wTransNum[i].value;
		 		if(num==""){
		 			num="0";
		 		}
		 		var price=wTransPrice[i].value;
		 		if(price==""){
		 			price="0";
		 		}
		 		prosum=parseFloat(prosum)+parseFloat(num);
		 		costsum=parseFloat(costsum)+parseFloat(price);
 			}
 		}
 		divwTProSum.innerHTML=prosum;
 		divwTProCostSum.innerHTML=parseFloat(costsum).toFixed(2);
 		gTAllCost.value=parseFloat(costsum).toFixed(2);
 	}else if(typestr=='E'){//����
 		var name0="divgEProSum";
 		var name1="divgEProCostSum";
 		var name2="gEBuildNum";
 		var name3="gEBuildPrice";
 		var divgEProSum=document.getElementById(name0);//������̨�� B
 		var divgEProCostSum=document.getElementById(name1);//�����ܷ���
 		var gEAllCost=document.getElementById("gEAllCost");
 		if(document.getElementsByName(name2)==null || document.getElementsByName(name2).length==0){
 			
 		}else{
 			var gEBuildNum=document.getElementsByName(name2);
 			var gEBuildPrice=document.getElementsByName(name3);
 			for(var i=0;i<gEBuildNum.length;i++){
 				var num=gEBuildNum[i].value;
		 		if(num==""){
		 			num="0";
		 		}
		 		var price=gEBuildPrice[i].value;
		 		if(price==""){
		 			price="0";
		 		}
		 		prosum=parseFloat(prosum)+parseFloat(num);
		 		costsum=parseFloat(costsum)+parseFloat(price);
 			}
 			
 		}
 		divgEProSum.innerHTML=prosum;
 		divgEProCostSum.innerHTML=parseFloat(costsum).toFixed(2);
 		gEAllCost.value=parseFloat(costsum).toFixed(2);
 	}
 }
 
   /** �����ϸ��Ϣ,�����ʩ�������ڣ��ͱ�����д��ϸ��Ϣ */
 function gcheckweihuinfo2(error,typestr,showtype){
 	if(typestr=='D'){//��װ
		var custId=document.getElementById("gDSCustId").value.trim();
		if(custId!=""){
 			if(document.getElementsByName("Gdivorder")==null || document.getElementsByName("Gdivorder").length==0){
 				if(showtype=="G"){
 					error="��װ�������룬�������ϸ��Ϣ��";
 				}
 			}else{
 				var divorder=document.getElementsByName("Gdivorder");
 				
 				var floor=document.getElementsByName("gDfloor");//��
			 	var stage=document.getElementsByName("gDstage");//վ
			 	var Zzl=document.getElementsByName("gDZzl");//�������أ�kg��
			 	var SuspendAddress=document.getElementsByName("gDSuspendAddress");//��װ�ص�
			 	var SuspendItem=document.getElementsByName("gDSuspendItem");//��װ��Ŀ
			 	var SuspendBox=document.getElementsByName("gDSuspendBox");//��װ��ͷ
			 	var CountExp=document.getElementsByName("gDCountExp");//���㹫ʽ��ԭ��װ�����ۣ�
			 	var SuspendNum=document.getElementsByName("gDSuspendNum");//��װ̨��
			 	var SuspendPrice=document.getElementsByName("gDSuspendPrice");//��װ����
 				
 				for(var i=0;i<divorder.length;i++){
 					var xuhao=divorder[i].innerText;
 					if(floor[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" �㲻����Ϊ�գ�\n";
			 		}else if(isNaN(floor[i].value.trim())){
			 			error+="��װ�������룬���"+xuhao+" �����Ϊ���֣�\n";
			 		}
			 		if(stage[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" վ������Ϊ�գ�\n";
			 		}else if(isNaN(stage[i].value)){
			 			error+="��װ�������룬���"+xuhao+" վ����Ϊ���֣�\n";
			 		}
			 		if(Zzl[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" �������ز�����Ϊ�գ�\n";
			 		}else if(isNaN(Zzl[i].value.trim())){
			 			error+="��װ�������룬���"+xuhao+" �������ر���Ϊ���֣�\n";
			 		}
			 		if(SuspendAddress[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" ��װ�ص㲻����Ϊ�գ�\n";
			 		}
			 		if(SuspendItem[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" ��װ��Ŀ������Ϊ�գ�\n";
			 		}
			 		if(SuspendBox[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" ��װ��ͷ������Ϊ�գ�\n";
			 		}
			 		if(CountExp[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" ���㹫ʽ������Ϊ�գ�\n";
			 		}
			 		if(SuspendNum[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" ��װ̨��������Ϊ�գ�\n";
			 		}else if(isNaN(SuspendNum[i].value.trim())){
			 			error+="��װ�������룬���"+xuhao+" ��װ̨������Ϊ���֣�\n";
			 		}else if(parseFloat(SuspendNum[i].value.trim())<=0){
			 			error+="��װ�������룬���"+xuhao+" ��װ̨����������㣡\n";
			 		}
			 		if(SuspendPrice[i].value.trim()==""){
			 			error+="��װ�������룬���"+xuhao+" ��װ���ò�����Ϊ�գ�\n";
			 		}else if(isNaN(SuspendPrice[i].value.trim())){
			 			error+="��װ�������룬���"+xuhao+" ��װ���ñ���Ϊ���֣�\n";
			 		}else if(parseFloat(SuspendPrice[i].value.trim())<=0){
			 			error+="��װ�������룬���"+xuhao+" ��װ���ñ�������㣡\n";
			 		}
 				}
 			}
		}else{
			if(showtype==""){
				error+="����ѡ��ʩ������";
			}
		}
 	}else if(typestr=='B'){//����
 		var custId=document.getElementById("gBCustId").value.trim();
		if(custId!=""){			
 			if(document.getElementsByName("Gdivorder1")==null || document.getElementsByName("Gdivorder1").length==0){
 				if(showtype=="G"){
 					error="���﷢�����룬�������ϸ��Ϣ��";
 				}
 			}else{
 				var divorder=document.getElementsByName("Gdivorder1");
 				
 				var floor=document.getElementsByName("gBfloor");//��
			 	var stage=document.getElementsByName("gBstage");//վ
			 	var Zzl=document.getElementsByName("gBZzl");//�������أ�kg��
			 	var BuildAddress=document.getElementsByName("gBBuildAddress");//����ص�
			 	var BuildItem=document.getElementsByName("gBBuildItem");//������Ŀ
			 	var JdH=document.getElementsByName("gBJdH");//�����ܸ�
			 	var BuildNum=document.getElementsByName("gBBuildNum");//����̨��
			 	var BuildPrice=document.getElementsByName("gBBuildPrice");//�������
 				
 				for(var i=0;i<divorder.length;i++){
 					var xuhao=divorder[i].innerText;
 					if(floor[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" �㲻����Ϊ�գ�\n";
			 		}else if(isNaN(floor[i].value.trim())){
			 			error+="���﷢�����룬���"+xuhao+" �����Ϊ���֣�\n";
			 		}
			 		if(stage[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" վ������Ϊ�գ�\n";
			 		}else if(isNaN(stage[i].value)){
			 			error+="���﷢�����룬���"+xuhao+" վ����Ϊ���֣�\n";
			 		}
			 		if(Zzl[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" �������ز�����Ϊ�գ�\n";
			 		}else if(isNaN(Zzl[i].value.trim())){
			 			error+="���﷢�����룬���"+xuhao+" �������ر���Ϊ���֣�\n";
			 		}
			 		if(BuildAddress[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" ����ص㲻����Ϊ�գ�\n";
			 		}
			 		if(BuildItem[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" ������Ŀ������Ϊ�գ�\n";
			 		}
			 		if(JdH[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" �����ܸ߲�����Ϊ�գ�\n";
			 		}else if(isNaN(JdH[i].value.trim())){
			 			error+="���﷢�����룬���"+xuhao+" �����ܸ߱���Ϊ���֣�\n";
			 		}
			 		if(BuildNum[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" ����̨��������Ϊ�գ�\n";
			 		}else if(isNaN(BuildNum[i].value.trim())){
			 			error+="���﷢�����룬���"+xuhao+" ����̨������Ϊ���֣�\n";
			 		}else if(parseFloat(BuildNum[i].value.trim())<=0){
			 			error+="���﷢�����룬���"+xuhao+" ����̨����������㣡\n";
			 		}
			 		if(BuildPrice[i].value.trim()==""){
			 			error+="���﷢�����룬���"+xuhao+" ������ò�����Ϊ�գ�\n";
			 		}else if(isNaN(BuildPrice[i].value.trim())){
			 			error+="���﷢�����룬���"+xuhao+" ������ñ���Ϊ���֣�\n";
			 		}else if(parseFloat(BuildPrice[i].value.trim())<=0){
			 			error+="���﷢�����룬���"+xuhao+" ������ñ�������㣡\n";
			 		}
 				}
 			}
		}else{
			if(showtype==""){
				error+="����ѡ��ʩ������";
			}
		}
 	}else if(typestr=='T'){//����
 		 var custId=document.getElementById("gTCustId").value.trim();
		if(custId!=""){		
 			if(document.getElementsByName("Gdivorder2")==null || document.getElementsByName("Gdivorder2").length==0){
 				if(showtype=="G"){
 					error="���䷢�����룬�������ϸ��Ϣ��";
 				}
 			}else{
 				var divorder=document.getElementsByName("Gdivorder2");
 				
 				var TransAddress=document.getElementsByName("gTransAddress");//����ص�
			 	var TransItem=document.getElementsByName("gTransItem");//������Ŀ
			 	var TSendType=document.getElementsByName("gTSendType");//��������
			 	var TransNum=document.getElementsByName("gTransNum");//����̨��
			 	var TransPrice=document.getElementsByName("gTransPrice");//�������
 				
 				for(var i=0;i<divorder.length;i++){
 					var xuhao=divorder[i].innerText;
			 		if(TransAddress[i].value.trim()==""){
			 			error+="���䷢�����룬���"+xuhao+" ����ص㲻����Ϊ�գ�\n";
			 		}
			 		if(TransItem[i].value.trim()==""){
			 			error+="���䷢�����룬���"+xuhao+" ������Ŀ������Ϊ�գ�\n";
			 		}
			 		if(TSendType[i].value.trim()==""){
			 			error+="���䷢�����룬���"+xuhao+" �������Ͳ�����Ϊ�գ�\n";
			 		}
			 		if(TransNum[i].value.trim()==""){
			 			error+="���䷢�����룬���"+xuhao+" ����̨��������Ϊ�գ�\n";
			 		}else if(isNaN(TransNum[i].value.trim())){
			 			error+="���䷢�����룬���"+xuhao+" ����̨������Ϊ���֣�\n";
			 		}else if(parseFloat(TransNum[i].value.trim())<=0){
			 			error+="���䷢�����룬���"+xuhao+" ����̨����������㣡\n";
			 		}
			 		if(TransPrice[i].value.trim()==""){
			 			error+="���䷢�����룬���"+xuhao+" ������ò�����Ϊ�գ�\n";
			 		}else if(isNaN(TransPrice[i].value.trim())){
			 			error+="���䷢�����룬���"+xuhao+" ������ñ���Ϊ���֣�\n";
			 		}else if(parseFloat(TransPrice[i].value.trim())<=0){
			 			error+="���䷢�����룬���"+xuhao+" ������ñ�������㣡\n";
			 		}
 				}
 			}
		}else{
			if(showtype==""){
				error+="����ѡ��ʩ������";
			}
		}
 	}else if(typestr=='E'){//����
 		var custId=document.getElementById("gECustId").value.trim();
		if(custId!=""){			
 			if(document.getElementsByName("Gdivorder1e")==null || document.getElementsByName("Gdivorder1e").length==0){
 				if(showtype=="G"){
 					error="�����������룬�������ϸ��Ϣ��";
 				}
 			}else{
 				var divorder=document.getElementsByName("Gdivorder1e");
 				
 				var floor=document.getElementsByName("gEfloor");//��
			 	var stage=document.getElementsByName("gEstage");//վ
			 	var Zzl=document.getElementsByName("gEZzl");//�������أ�kg��
			 	var BuildAddress=document.getElementsByName("gEBuildAddress");//�����ص�
			 	var BuildItem=document.getElementsByName("gEBuildItem");//������Ŀ
			 	var JdH=document.getElementsByName("gEJdH");//�����ܸ�
			 	var BuildNum=document.getElementsByName("gEBuildNum");//����̨��
			 	var BuildPrice=document.getElementsByName("gEBuildPrice");//��������
 				
 				for(var i=0;i<divorder.length;i++){
 					var xuhao=divorder[i].innerText;
 					if(floor[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" �㲻����Ϊ�գ�\n";
			 		}else if(isNaN(floor[i].value.trim())){
			 			error+="�����������룬���"+xuhao+" �����Ϊ���֣�\n";
			 		}
			 		if(stage[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" վ������Ϊ�գ�\n";
			 		}else if(isNaN(stage[i].value)){
			 			error+="�����������룬���"+xuhao+" վ����Ϊ���֣�\n";
			 		}
			 		if(Zzl[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" �������ز�����Ϊ�գ�\n";
			 		}else if(isNaN(Zzl[i].value.trim())){
			 			error+="�����������룬���"+xuhao+" �������ر���Ϊ���֣�\n";
			 		}
			 		if(BuildAddress[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" �����ص㲻����Ϊ�գ�\n";
			 		}
			 		if(BuildItem[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" ������Ŀ������Ϊ�գ�\n";
			 		}
			 		if(JdH[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" �����ܸ߲�����Ϊ�գ�\n";
			 		}else if(isNaN(JdH[i].value.trim())){
			 			error+="�����������룬���"+xuhao+" �����ܸ߱���Ϊ���֣�\n";
			 		}
			 		if(BuildNum[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" ����̨��������Ϊ�գ�\n";
			 		}else if(isNaN(BuildNum[i].value.trim())){
			 			error+="�����������룬���"+xuhao+" ����̨������Ϊ���֣�\n";
			 		}else if(parseFloat(BuildNum[i].value.trim())<=0){
			 			error+="�����������룬���"+xuhao+" ����̨����������㣡\n";
			 		}
			 		if(BuildPrice[i].value.trim()==""){
			 			error+="�����������룬���"+xuhao+" �������ò�����Ϊ�գ�\n";
			 		}else if(isNaN(BuildPrice[i].value.trim())){
			 			error+="�����������룬���"+xuhao+" �������ñ���Ϊ���֣�\n";
			 		}else if(parseFloat(BuildPrice[i].value.trim())<=0){
			 			error+="�����������룬���"+xuhao+" �������ñ�������㣡\n";
			 		}
 				}
 			}
		}else{
			if(showtype==""){
				error+="����ѡ��ʩ������";
			}
		}
 	}

 	return error;
 }
/**=================== ���챨�� end ===========================*/
 
 /**=================== �������� start ===========================*/
 /**   */
 function sendServicingContractDetailB(){
	var url='query/Search.jsp?path=/XJSCRM/SearchEngEpiCustomerAction.do?method=toSearchRecord_BaoYangDetail';
	var arr = window.showModalDialog(url,window,'dialogWidth:770px;dialogHeight:500px;dialogLeft:200px;dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes');

   	if(arr!=null){
		for(var i=0;i<arr.length;i++){
			var obj=arr[i];
			var apptype=document.getElementById("AppType").value;
			if(apptype=="1"){
				AddRow_B(obj);
			}else if(apptype=="2"){
				AddRow_B0(obj);
			}
		}
	}
}
  /** �����ͺ���Ϣ */ 
  function AddRow_B(obj){

		var adjtb_w=document.getElementById("adjtb_b");
		var num=adjtb_w.rows.length;

		adjtb_w.insertRow(num);		
		//��ѡ��
		var name0="Bnodes";
		var ince0=adjtb_w.rows(num).insertCell(0);
		ince0.align="center";
		ince0.innerHTML="<input type=\'checkbox\' name=\'"+name0+"\' >";
		//���
		var name1="Bdivorder";
		var ince1=adjtb_w.rows(num).insertCell(1);
		ince1.align="center";
		ince1.innerHTML="<div id=\'"+name1+"\'>";
		//�ͻ�����
		var name2="bSourceCustId";
		var ince2=adjtb_w.rows(num).insertCell(2);
		ince2.innerHTML=obj.custname+"<input type=\'hidden\' name=\'"+name2+"\' value=\'"+obj.custid+"\' />";
		//��ͬ��
		var name3="bSoureConctractId";
		var name3_0="bBillNo";
		var name3_1="bDetailRowId";
		var ince3=adjtb_w.rows(num).insertCell(3);
		ince3.innerHTML=obj.contractid+"<input type=\'hidden\' name=\'"+name3+"\' value=\'"+obj.contractid+"\' />"+
						"<input type=\'hidden\' name=\'"+name3_0+"\' value=\'"+obj.billno+"\' />"+
						"<input type=\'hidden\' name=\'"+name3_1+"\' value=\'"+obj.detailrowid+"\' />";
		//�����
		var name4="bServiceNo";
		var ince4=adjtb_w.rows(num).insertCell(4);
		ince4.innerHTML=obj.serviceno+"<input type=\'hidden\' name=\'"+name4+"\' value=\'"+obj.serviceno+"\' />";
		//�����ͺ�
		var name5="bElevatorId";
		var ince5=adjtb_w.rows(num).insertCell(5);
		ince5.innerHTML=obj.elevatorid+"<input type=\'hidden\' name=\'"+name5+"\' value=\'"+obj.elevatorid+"\' />";
		//��վ��
		var name6="bfloor";
		var name6_0="bstage";
		var name6_1="bdoor";
		var ince6=adjtb_w.rows(num).insertCell(6);
		ince6.innerHTML=obj.floor+"/"+obj.stage+"/"+obj.door+
						"<input type=\'hidden\' name=\'"+name6+"\' value=\'"+obj.floor+"\' />"+
						"<input type=\'hidden\' name=\'"+name6_0+"\' value=\'"+obj.stage+"\' />"+
						"<input type=\'hidden\' name=\'"+name6_1+"\' value=\'"+obj.door+"\' />";
		//�����߶�
		var name7="bHigh";
		var ince7=adjtb_w.rows(num).insertCell(7);
		ince7.align="right";
		ince7.innerHTML=obj.high+"<input type=\'hidden\' name=\'"+name7+"\' value=\'"+obj.high+"\' />";
		//��б�Ƕ�
		var name8="bAngle";
		var ince8=adjtb_w.rows(num).insertCell(8);
		ince8.align="right";
		ince8.innerHTML=obj.angle+"<input type=\'hidden\' name=\'"+name8+"\' value=\'"+obj.angle+"\' />";
		//������
		var name9="bZzl";
		var ince9=adjtb_w.rows(num).insertCell(9);
		ince9.align="right";
		ince9.innerHTML=obj.zzl+"<input type=\'hidden\' name=\'"+name9+"\' value=\'"+obj.zzl+"\' />";
		//�ٶ�
		var name10="bSpeed";
		var ince10=adjtb_w.rows(num).insertCell(10);
		ince10.innerHTML=obj.speed+"<input type=\'hidden\' name=\'"+name10+"\' value=\'"+obj.speed+"\' />";
		//����
		var name11="bNum";
		var ince11=adjtb_w.rows(num).insertCell(11);
		ince11.align="right";
		ince11.innerHTML=obj.num+"<input type=\'hidden\' name=\'"+name11+"\' value=\'"+obj.num+"\' />";
		//������ַ
		var name12="baddress";
		var ince12=adjtb_w.rows(num).insertCell(12);
		ince12.innerHTML=obj.mugaddress+"<input type=\'hidden\' name=\'"+name12+"\' value=\'"+obj.mugaddress+"\' />";
		//�۸�
		var name13="bamt";
		var name13_0="bamthid";
		var price=obj.price;
		if(price==null || price.trim()==""){
			price="0";
		}
		price=(parseFloat(price)*0.7).toFixed(2);//��ʾĬ�ϵ���70%
		var ince13=adjtb_w.rows(num).insertCell(13);
		ince13.align="right";
		ince13.innerHTML="<input type=\'text\' name=\'"+name13+"\' value=\'"+price+"\' onkeypress=\'f_check_number3();\' onkeyup=\"jisuanxiaoji_b('S')\" size=\'10\' class=\'default_input\'/>"+
						"<input type=\'hidden\' name=\'"+name13_0+"\' value=\'"+obj.price+"\' />";
		//�����·�
		var name14="bMugMonth";
		var ince14=adjtb_w.rows(num).insertCell(14);
		ince14.align="right";
		ince14.innerHTML="<input type=\'text\' name=\'"+name14+"\' value=\'"+obj.r18+"\' onkeypress=\'f_check_number2();\' onkeyup=\"jisuanxiaoji_b('S')\" size=\'6\' class=\'default_input\'/>";
		//������ʼ����
		var name15="bmugstartdate";
		var ince15=adjtb_w.rows(num).insertCell(15);
		ince15.innerHTML="<input type=\'text\' name=\'"+name15+"\' value=\'"+obj.mugstartdate+"\' class=\'default_input\' onclick=\'setday(this)\' readonly=\'readonly\' size=\'12\' />";
		//������������
		var name16="bmugenddate";
		var ince16=adjtb_w.rows(num).insertCell(16);
		ince16.innerHTML="<input type=\'text\' name=\'"+name16+"\' value=\'"+obj.mugenddate+"\' class=\'default_input\' onclick=\'setday(this)\' readonly=\'readonly\'  size=\'12\' />";

		xuhaonum(name1);
		jisuanxiaoji_b("S");
  }  

    /** ������Ա���ú���ҵʱ��� */ 
  function AddRow_B0(obj){
//  	var errorstr=bcheckweihuinfo("","L","");
//  	if(errorstr==""){
		var adjtb_w=document.getElementById("adjtb_b1");
		var num=adjtb_w.rows.length;

		adjtb_w.insertRow(num);		
		//��ѡ��
		var name0="Bnodes1";
		var ince0=adjtb_w.rows(num).insertCell(0);
		ince0.align="center";
		ince0.innerHTML="<input type=\'checkbox\' name=\'"+name0+"\' >";
		//���
		var name1="Bdivorder1";
		var ince1=adjtb_w.rows(num).insertCell(1);
		ince1.align="center";
		ince1.innerHTML="<div id=\'"+name1+"\'>";
		//�ͻ�����
		var name2="bSourceCustId1";
		var ince2=adjtb_w.rows(num).insertCell(2);
		ince2.innerHTML=obj.custname+"<input type=\'hidden\' name=\'"+name2+"\' value=\'"+obj.custid+"\' />";
		//��ͬ��
		var name3="bSoureConctractId1";
		var name3_0="bBillNo1";
		var name3_1="bDetailRowId1";
		var ince3=adjtb_w.rows(num).insertCell(3);
		ince3.innerHTML=obj.contractid+"<input type=\'hidden\' name=\'"+name3+"\' value=\'"+obj.contractid+"\' />"+
						"<input type=\'hidden\' name=\'"+name3_0+"\' value=\'"+obj.billno+"\' />"+
						"<input type=\'hidden\' name=\'"+name3_1+"\' value=\'"+obj.detailrowid+"\' />";
		//�����ͺ�
		var name4="bElevatorId1";
		var ince4=adjtb_w.rows(num).insertCell(4);
		ince4.innerHTML=obj.elevatorid+"<input type=\'hidden\' name=\'"+name4+"\' value=\'"+obj.elevatorid+"\' />";
		//����
		var name5="bNum1";
		var ince5=adjtb_w.rows(num).insertCell(5);
		ince5.align="right";
		ince5.innerHTML="<input type=\'text\' name=\'"+name5+"\' value=\'"+obj.num+"\' class=\'default_input\' onkeypress=\'f_check_number2();\' size=\'6\' />";
		//����ʼ����	
		var name6="bmugstartdate1";
		var ince6=adjtb_w.rows(num).insertCell(6);
		ince6.innerHTML="<input type=\'text\' name=\'"+name6+"\' class=\'default_input\' onclick=\'setday(this)\' onpropertychange=\"setDateReduplicate(this)\" readonly=\'readonly\'  size=\'12\'/>";
		//�����������
		var name7="bmugenddate1";
		var ince7=adjtb_w.rows(num).insertCell(7);
		ince7.innerHTML="<input type=\'text\' name=\'"+name7+"\' class=\'default_input\' onclick=\'setday(this)\' onpropertychange=\"setDateReduplicate(this)\" readonly=\'readonly\'  size=\'12\'/>";
		//�����·�
		var name8="bMugMonth1";
		var ince8=adjtb_w.rows(num).insertCell(8);
		ince8.align="right";
		ince8.innerHTML="<input type=\'text\' name=\'"+name8+"\' class=\'default_input\' onkeypress=\'f_check_number2();\' onkeyup=\"setDateReduplicate(this);jisuanxiaoji_b('L');\" size=\'6\' />";
		//���ѵ���/��/�� 
		var name9="bamt1";
		var ince9=adjtb_w.rows(num).insertCell(9);
		ince9.align="right";
		ince9.innerHTML="<input type=\'text\' name=\'"+name9+"\' class=\'default_input\' onkeypress=\'f_check_number3();\' onkeyup=\"jisuanxiaoji_b('L')\" size=\'10\' />";
		//�����ܼ�/��		
		var name10="div_amtsum";
		var ince10=adjtb_w.rows(num).insertCell(10);
		ince10.align="right";
		ince10.innerHTML="<input type=\'text\' name=\'"+name10+"\' style=\'border:0;text-align:right;\' readonly=\'readonly\'>";

		xuhaonum(name1);
//	}else{
//		alert(errorstr);
//	}
  }
   /** ������Ա�嵥 */ 
   function AddRow_B1(tableid){
  	var errorstr=bcheckweihuinfo("","W","");
  	if(errorstr==""){
		var adjtb_w=document.getElementById("adjtb_b2");
		var num=adjtb_w.rows.length;

		adjtb_w.insertRow(num);		
		//��ѡ��
		var name0="Bnodes2";
		var ince0=adjtb_w.rows(num).insertCell(0);
		ince0.align="center";
		ince0.innerHTML="<input type=\'checkbox\' name=\'"+name0+"\' >";
		//���
		var name1="Bdivorder2";
		var ince1=adjtb_w.rows(num).insertCell(1);
		ince1.align="center";
		ince1.innerHTML="<div id=\'"+name1+"\'>";
		//����
		var name2="bPersonName";
		var ince2=adjtb_w.rows(num).insertCell(2);
		ince2.innerHTML="<input type=\'text\' name=\'"+name2+"\' class=\'default_input\' />";
		//�Ա�
		var name3="bPersonGender";
		var ince3=adjtb_w.rows(num).insertCell(3);
		ince3.innerHTML="<input type=\'text\' name=\'"+name3+"\' class=\'default_input\' size=\'6\' />";
		//����
		var name4="bPersonAge";
		var ince4=adjtb_w.rows(num).insertCell(4);
		ince4.innerHTML="<input type=\'text\' name=\'"+name4+"\' onkeypress=\'f_check_number2();\' size=\'4\' class=\'default_input\' />";
		//ְ��
		var name5="bPersonDuty";
		var ince5=adjtb_w.rows(num).insertCell(5);
		ince5.innerHTML="<input type=\'text\' name=\'"+name5+"\' class=\'default_input\'>";
		//ʡ����֤����
		var name6="bPersonOperNo";
		var ince6=adjtb_w.rows(num).insertCell(6);
		ince6.innerHTML="<input type=\'text\' name=\'"+name6+"\' class=\'default_input\' >";
		//���֤����
		var name7="bPersonCardNo";
		var ince7=adjtb_w.rows(num).insertCell(7);
		ince7.innerHTML="<input type=\'text\' name=\'"+name7+"\' class=\'default_input\' >";
	
		xuhaonum(name1);
	}else{
		alert(errorstr);
	}
  }
   /** �����ܷ��� parseFloat();parseInt();isNaN();����Ƿ����ַ� */
 function jisuanxiaoji_b(typestr){
 	
 	if(typestr=='L'){
 		var prosum=0;
 		var costsum=0;
 	
 		var name="BAllCost1";
 		var name_0="div_ballcost2";
 		var ballcost=document.getElementById(name);//�����ܷ���
 		var div_ballcost2=document.getElementById(name_0);
 		
 		var name1="bMugMonth1";
 		var name2="bamt1";
		var bMugMonth1=document.getElementsByName(name1);//�����·�
		var bamt1=document.getElementsByName(name2);//���ѵ���/��/�� 
		for(var i=0;i<bMugMonth1.length;i++){
			var num=bMugMonth1[i].value;
	 		if(num==""){
	 			num="0";
	 		}
	 		var price=bamt1[i].value;
	 		if(price==""){
	 			price="0";
	 		}
	 		prosum=(parseFloat(num)*parseFloat(price)).toFixed(2);
		 	costsum=parseFloat(costsum)+parseFloat(prosum);
		 	//alert("for: div_amtsum_"+(i+1));
		 	document.getElementsByName("div_amtsum")[i].value=prosum;//�����ܼ�/��;
		}
		div_ballcost2.innerText=costsum;
 		ballcost.value=costsum;
 	}else if(typestr=='S'){
 		var prosum=0;
 		var costsum=0;
 	
 		var name="BAllCost";
 		var name_0="div_ballcost";
 		var ballcost=document.getElementById(name);//�����ܷ���
 		var div_ballcost=document.getElementById(name_0);

 		var name2="bamt";
 		var name3="bMugMonth";

		var bamt=document.getElementsByName(name2);//�۸�
		var bMugMonth=document.getElementsByName(name3);//�����·�
		for(var i=0;i<bMugMonth.length;i++){
			var num=bMugMonth[i].value;
	 		if(num==""){
	 			num="0";
	 		}
	 		var price=bamt[i].value;
	 		if(price==""){
	 			price="0";
	 		}
	 		prosum=(parseFloat(num)*parseFloat(price)).toFixed(2);
		 	costsum=parseFloat(costsum)+parseFloat(prosum);
		}
		div_ballcost.innerText=costsum;
 		ballcost.value=parseFloat(costsum);
 	}
 }
 /** ���������Ա���ú���ҵʱ���	��������Ա�嵥 */
 function bcheckweihuinfo(error,typestr,showtype){
 	if(typestr=='S'){//��Ȩ��ͬ
 		if(document.getElementsByName("Bdivorder")==null || document.getElementsByName("Bdivorder").length==0){
			if(showtype=="B"){
				error="����ӵ����ͺ���Ϣ��";
			}
 		}else{
 			var divorder=document.getElementsByName("Bdivorder");
			var bmugstartdate=document.getElementsByName("bmugstartdate");//������ʼ����
		 	var bmugenddate=document.getElementsByName("bmugenddate");//������������
		 	var bMugMonth=document.getElementsByName("bMugMonth");//�����·�
		 	var bamt=document.getElementsByName("bamt");//�۸�
		 	
		 	for(var i=0;i<divorder.length;i++){
				var xuhao=divorder[i].innerText;
				
		 		if(bamt[i].value.trim()==""){
		 			error+="  ���"+xuhao+" �۸񲻿���Ϊ�գ�\n";
		 		}else if(isNaN(bamt[i].value.trim())){
		 			error+="  ���"+xuhao+" �۸����Ϊ���֣�\n";
		 		}
		 		if(bMugMonth[i].value.trim()==""){
		 			error+="  ���"+xuhao+" �����·ݲ�����Ϊ�գ�\n";
		 		}else if(isNaN(bMugMonth[i].value.trim())){
		 			error+="  ���"+xuhao+" �����·ݱ���Ϊ���֣�\n";
		 		}
				if(bmugstartdate[i].value.trim()==""){
		 			error+="  ���"+xuhao+" ������ʼ���ڲ�����Ϊ�գ�\n";
		 		}
		 		if(bmugenddate[i].value.trim()==""){
		 			error+="  ���"+xuhao+" �����������ڲ�����Ϊ�գ�\n";
		 		}
			}
			if(error!=""){
				error="�����ͺ���Ϣ\n"+error;
			}
 		}
 	}else if(typestr=='L'){//������Ա���ú���ҵʱ���	
		if(document.getElementsByName("Bdivorder1")==null || document.getElementsByName("Bdivorder1").length==0){
			if(showtype=="B"){
				error="�����������Ա���ú���ҵʱ���";
			}
 		}else{
			var divorder=document.getElementsByName("Bdivorder1");
			var bmugstartdate1=document.getElementsByName("bmugstartdate1");//����ʼ����
		 	var bmugenddate1=document.getElementsByName("bmugenddate1");//�����������
		 	var bMugMonth1=document.getElementsByName("bMugMonth1");//�����·�
		 	var bamt1=document.getElementsByName("bamt1");//���ѵ���/��/��
		 	var bNum1=document.getElementsByName("bNum1");//����
			
			for(var i=0;i<divorder.length;i++){
				var xuhao=divorder[i].innerText;
				if(bNum1[i].value.trim()==""){
		 			error+="  ���"+xuhao+" ̨��������Ϊ�գ�\n";
		 		}else if(isNaN(bNum1[i].value.trim())){
		 			error+="  ���"+xuhao+" ̨������Ϊ���֣�\n";
		 		}
				if(bmugstartdate1[i].value.trim()==""){
		 			error+="  ���"+xuhao+" ����ʼ���ڲ�����Ϊ�գ�\n";
		 		}
		 		if(bmugenddate1[i].value.trim()==""){
		 			error+="  ���"+xuhao+" ����������ڲ�����Ϊ�գ�\n";
		 		}
		 		if(bMugMonth1[i].value.trim()==""){
		 			error+="  ���"+xuhao+" �����·ݲ�����Ϊ�գ�\n";
		 		}else if(isNaN(bMugMonth1[i].value.trim())){
		 			error+="  ���"+xuhao+" �����·ݱ���Ϊ���֣�\n";
		 		}
		 		if(bamt1[i].value.trim()==""){
		 			error+="  ���"+xuhao+" ���ѵ���/��/�²�����Ϊ�գ�\n";
		 		}else if(isNaN(bamt1[i].value.trim())){
		 			error+="  ���"+xuhao+" ���ѵ���/��/�±���Ϊ���֣�\n";
		 		}
			}
			if(error!=""){
				error="������Ա���ú���ҵʱ���\n"+error;
			}
 		}
 	}else if(typestr=='W'){//������Ա�嵥
		if(document.getElementsByName("Bdivorder2")==null || document.getElementsByName("Bdivorder2").length==0){
			if(showtype=="B"){
				error="�����������Ա�嵥��";
			}
 		}else{
			var divorder=document.getElementsByName("Bdivorder2");
			var bPersonName=document.getElementsByName("bPersonName");//����
		 	var bPersonGender=document.getElementsByName("bPersonGender");//�Ա�
		 	var bPersonAge=document.getElementsByName("bPersonAge");//����
		 	var bPersonDuty=document.getElementsByName("bPersonDuty");//ְ��
		 	var bPersonOperNo=document.getElementsByName("bPersonOperNo");//ʡ����֤����
		 	var bPersonCardNo=document.getElementsByName("bPersonCardNo");//���֤����
			
			for(var i=0;i<divorder.length;i++){
				var xuhao=divorder[i].innerText;
				if(bPersonName[i].value.trim()==""){
		 			error+="  ���"+xuhao+" ����������Ϊ�գ�\n";
		 		}
		 		if(bPersonGender[i].value.trim()==""){
		 			error+="  ���"+xuhao+" �Ա𲻿���Ϊ�գ�\n";
		 		}
		 		if(bPersonAge[i].value.trim()==""){
		 			error+="  ���"+xuhao+" ���䲻����Ϊ�գ�\n";
		 		}else if(isNaN(bPersonAge[i].value.trim())){
		 			error+="  ���"+xuhao+" �������Ϊ���֣�\n";
		 		}else if(parseFloat(bPersonAge[i].value.trim())<=0){
		 			error+="  ���"+xuhao+" �����������㣡\n";
		 		}
		 		if(bPersonDuty[i].value.trim()==""){
		 			error+="  ���"+xuhao+" ְ�񲻿���Ϊ�գ�\n";
		 		}
		 		if(bPersonOperNo[i].value.trim()==""){
		 			error+="  ���"+xuhao+" ʡ����֤���벻����Ϊ�գ�\n";
		 		}
		 		if(bPersonCardNo[i].value.trim()==""){
		 			error+="  ���"+xuhao+" ���֤���벻����Ϊ�գ�\n";
		 		}
			}
			if(error!=""){
				error="������Ա�嵥\n"+error;
			}
 		}
 	}
 	return error;
 }
/**=================== �������� end ===========================*/

 /** ������ֵ�Զ���վ��ֵ */
function setStageValue(obj,name){
	var objarr=document.getElementsByName(obj.name);
	var onum=0;
	for(var i=0;i<objarr.length;i++){
		if(objarr[i]==obj){
			onum=i;
			break;
		}
	}
	document.getElementsByName(name)[onum].value=obj.value;
}
/** ����ʼ����,�����������,�����·�  */
function setDateReduplicate(obj){
	var datearr=document.getElementsByName(obj.name);
	var dnum=0;
	for(var j=0;j<datearr.length;j++){
		if(datearr[j]==obj){
			dnum=j;
			break;
		}
	}
	var datevalue=datearr[dnum].value;
	for(var i=0;i<datearr.length;i++){
		if(i>dnum){
			datearr[i].value=datevalue;
		}else{
			if(datearr[i].value.trim()==""){
				datearr[i].value=datevalue;
			}
		}
	}
}


  
  