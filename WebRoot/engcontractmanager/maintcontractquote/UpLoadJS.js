<%@ page contentType="text/html;charset=GBK"%>
<script type="text/javascript">

var fileGlide=0;
  //��Ӹ���
  function AddRow(tableid){
	var tbody=tableid.getElementsByTagName("tbody")[0];
	var tr=document.createElement("tr");
	var td=document.createElement("td");
	var inp=createNode("1","checkbox","nodes",fileGlide,"","");
	td.appendChild(inp); 
	td.style.textAlign="center";
	tr.appendChild(td);
	//use del
	
	td=document.createElement("td");
	inp=createNode("2","file","file_"+fileGlide,"","","");
//	inp=createNode("2","file","file_","","","");
	inp.size="60";
	td.appendChild(inp);
	tr.appendChild(td);
	
	//hidden
	inp=createNode("2","hidden","ids",fileGlide,"","","");
	tr.appendChild(inp);
	tbody.appendChild(tr);
	fileGlide++;
  }
  function createNode(flag, type ,name,value ,clas,checked){
	if(flag=="0"){//text node
		return document.createTextNode(value);
	}else if(flag=="1"){//input node
		return document.createElement("<input type=\'"+type+"\' name=\'"+name+"\' value=\'"+value+"\' class=\'"+clas+"\' "+checked+">");
	}else if(flag=="2"){//element node
		return document.createElement("<input type=\'"+type+"\' name=\'"+name+"\' value=\'"+value+"\' "+checked+" class='default_input' >");
	}
  }
  //��ѡ��ȫѡ
  function selAllNode(obj,name){
	var idss=document.getElementsByName(name);
		for(var i=idss.length-1;i>=0;i--){
			idss[i].checked=obj.checked;
		}
	}
	
	//ɾ������
	function delSelNode2(ptable){
		var pbody=ptable.getElementsByTagName("TBODY")[0];
		var len=pbody.children.length;
		var nodevalue="";
		for(var i=len-1;i>=0;i--){
			if(pbody.children[i].firstChild.firstChild.checked==true){//ids
				pbody.deleteRow(i);
			}
		}
   }
   
	//����ϴ���Ϣ�Ƿ���д����
	function checkUploadFileField(){
		var returnMsg ="";
			var nodesArr = document.getElementsByName("nodes");
			var alertMsgTitle = new Array("����","����");
			if(nodesArr){
				var len = nodesArr.length;
				if(len >0){
					for(var i =0 ; i < len ; i++){
						var nodes_=nodesArr[i].value;
						var file_Arr = document.getElementsByName("file_"+nodes_);
						file_ = file_Arr[0].value;
						if(file_=='' || file_==null){
							returnMsg += alertMsg(i+1,alertMsgTitle[1]);
						}
					}
				}
			}
		if(returnMsg!=""){
			returnMsg="������Ϣ��"+returnMsg;
		}
		return returnMsg;
	}

	function getObjArrByName(fieldName){
		return document.getElementsByName(fieldName);
	}
	function alertMsg(rowno,alertMsgTitle){
		return "\n�� "+rowno+" �� "+alertMsgTitle+" Ϊ��!";
	}
 //--------------------------��Ӹ��� ����---------------------

	var XMLHttpReq = false;
	  //����XMLHttpRequest����       
    function createXMLHttpRequest() {
	  if(window.XMLHttpRequest) { //Mozilla �����
	   XMLHttpReq = new XMLHttpRequest();
	  }else if (window.ActiveXObject) { // IE�����
	   XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
	  }
	 }
	 
	 //����������
	 function sendRequestDelFile(url) {
	  createXMLHttpRequest();
	  XMLHttpReq.open("post", url, true);
	  XMLHttpReq.onreadystatechange = processResponseDelFile;//ָ����Ӧ����
	  XMLHttpReq.send(null);  // ��������
	 }
	 // ��������Ϣ����
	 
	    function processResponseDelFile() {
	    	document.getElementById("filemessagestring").innerHTML="";
	     	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
	         	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ        	
	         	
	            	var res=XMLHttpReq.responseXML.getElementsByTagName("res")[0].firstChild.data;
	            	
	            	//document.getElementById("messagestring").innerHTML=res;
	            	if(res=="Y"){
	            		document.getElementById("filemessagestring").innerHTML="<b><font color='red'>ɾ���ɹ���</font></b>";
	            		tbs.parentElement.parentElement.style.display='none';
	            	}else{
	            		document.getElementById("filemessagestring").innerHTML="<b><font color='red'>ɾ��ʧ�ܣ�</font></b>";
	            	}
	            	
	            	//alert(document.getElementById("messagestring").innerHTML+";123");
	         	} else { //ҳ�治����
	               window.alert("���������ҳ�����쳣��");
	         	}
	       }
	    }

	    //ɾ��
	    var tbs;
	    function deleteFile(value,name,tb){
	   
	 	    tbs=tb;
	 	    if(confirm("ȷ��ɾ����"+name)){
	 			tbs.parentElement.parentElement.style.display='';
	 			var uri = '<html:rewrite page="/maintContractQuoteAction.do"/>?method=toDelFileRecord';
	 			uri +='&filesid='+ value;
	 			sendRequestDelFile(uri);  	
	 			
	 		}
	 	}
	    
		//����
		function downloadFile(id){
			var uri = '<html:rewrite page="/maintContractQuoteAction.do"/>?method=toDownloadFileRecord';
 			uri +='&filesid='+ id;
			window.location = uri;
			//window.open(url);
		}

  </script>
 
 