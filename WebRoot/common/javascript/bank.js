function getTypeid(obj,url){
	
	  var selectacount = document.getElementById("acount");
		//alert("aa");
		//alert(selectacount);
	 if(obj.value==""){	 			
 		selectacount.options.length=0; 
 		selectacount.add(new Option("��ѡ��",""));
 		
 	 }else{
 		getBank(obj,url); ;//������Ǳ�(����)
 		
 	 }

 }

 function getBank(obj,url){

	 var selectacount = document.getElementById("acount");
	//alert("aabb");
	//alert(selectacount.value);
	 var Request = new AjaxRequest();
	url = url+obj.value;
	 Request.url = url;
	 Request.mehod="POST";
	 Request.async = false; //fasle ��ʾͬ����true ��ʾ�첽
	 Request.onComplete = function(req){
	     var req_Obj=req.responseXML;
	     var rowNodes = req_Obj.getElementsByTagName('rows');
	     if(rowNodes!=null){
	 		selectacount.options.length=0; 
	 		selectacount.add(new Option("��ѡ��",""));

		    var rowLen = rowNodes.length;
		    for(var i=0;i<rowLen;i++){
				var colNodes = rowNodes[i].childNodes;
				if(colNodes != null){
					var colLen = colNodes.length;
					for(var j=0;j<colLen;j++){
						var code = colNodes[j].getAttribute("name");
						var acount = colNodes[j].getAttribute("value");
						selectacount.add(new Option(acount,code));
		            }

	             }
	        }
        }
	 }
	 Request.process("getBank");
 }