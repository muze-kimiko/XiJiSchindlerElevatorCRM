function getTypeid(obj,url){
	
	 var selectqjid = document.getElementById("qjId");
	
	 if(obj.value==""){	 			
 		selectqjid.options.length=0; 
 		selectqjid.add(new Option("��ѡ��",""));
 		
 	 }else{
 		getObliquity(obj,url);//������Ǳ�(����)
 		
 	 }

 }
  function getTypeider(obj,url){
 		getObliquity(obj,url); //������Ǳ�(����)
 }
 function getTypeid2(obj,url2){
	
	 var selectfreeid = document.getElementById("freeId");
	 if(obj.value==""){	 			
 		selectfreeid.options.length=0; 
 		selectfreeid.add(new Option("��ѡ��",""));
 	 }else{
 		getFreeItem(obj,url2);
 	 }

 }
 function getFreeItem(obj,url2){
	 var selectfreeid = document.getElementById("freeId");

	 var Request = new AjaxRequest();
	 //var url = '<html:rewrite page="/searchContractElevator2Action.do"/>?method=toAjaxFreeItem&typeid='+obj.value;
	url2 = url2+obj.value;
	 Request.url = url2;
	 Request.mehod="POST";
	 Request.async = false; //fasle ��ʾͬ����true ��ʾ�첽
	 Request.onComplete = function(req){
	     var req_Obj=req.responseXML;
	     var rowNodes = req_Obj.getElementsByTagName('rows');
	     if(rowNodes!=null){
	     	selectfreeid.options.length=0; 
	 		selectfreeid.add(new Option("��ѡ��",""));	

		    var rowLen = rowNodes.length;
		    for(var i=0;i<rowLen;i++){
				var colNodes = rowNodes[i].childNodes;
				if(colNodes != null){
					var colLen = colNodes.length;
					for(var j=0;j<colLen;j++){
						var freeid = colNodes[j].getAttribute("name");
						var freename = colNodes[j].getAttribute("value");
						selectfreeid.add(new Option(freename,freeid));
		            }
	             }
	        }
        }
	 }
	 Request.process("getFreeItem");
 }
function getObliquity(obj,url){
	// var typeid = document.getElementById("typeId").value;
	  var selectqjid = document.getElementById("qjId");
	// alert(obj);
		 var Request = new AjaxRequest();
		 // var url = '<html:rewrite page="/ElevatorFpriceAction.do"/>?method=toAjaxObliquity&typeid='+obj.value;
		 //var url = '<html:rewrite page="/LiftFparmAction.do?method=AjaxLiftf&typeid="/>'+typeid;
		 url = url+obj.value;

		 Request.url = url;
		 Request.mehod="POST";		
		 Request.async = false; //fasle ��ʾͬ����true ��ʾ�첽
		 Request.onComplete = function(req){
	     var req_Obj=req.responseXML;

	     var rowNodes = req_Obj.getElementsByTagName('rows');
	     if(rowNodes!=null){

	 		selectqjid.options.length=0; 
	 		selectqjid.add(new Option("��ѡ��",""));

		    var rowLen = rowNodes.length;

		    for(var i=0;i<rowLen;i++){
				var colNodes = rowNodes[i].childNodes;

				if(colNodes != null){
					var colLen = colNodes.length;
					for(var j=0;j<colLen;j++){
						var qjid = colNodes[j].getAttribute("name");
						var obliquity = colNodes[j].getAttribute("value");
						selectqjid.add(new Option(obliquity,qjid));
		            }
					
	             }
	        }
        }
	 }
	 Request.process("getObliquity");
 }
