/**
 * ����ָ���Ĳ�ѯҳ��Ĵ��ڣ������ݷ��ص����������ҳ���Ӧid�������ֵ 
 * 
 * @param actionName ��ѯҳ���actionName ��searchCustomerAction ��ͷ���Сд
 * @param formName ��ҳ��ı����� ��<action attribute="serveTableForm" �е�serveTableForm ����Ϊ��ʱ�����Զ���ȡҳ���б������ơ�
 */
function openWindowAndReturnValue(actionName,formName){	 
  var obj = openWindow(actionName);
  if(formName == null || formName == ""){
	  var forms = document.getElementsByTagName("form");
	  if(forms !=null && forms.length == 1){
		  formName = document.getElementsByTagName("form")[0].name; //��formNameΪ��ʱ���Զ���ȡҳ�������
	  }	  
  }
  if(formName != null || !formName == ""){
	  toSetInputValue(obj,formName);  
  }
}

/**
 * ����ָ���Ĳ�ѯҳ��Ĵ��ڣ������ݷ��ص����������ҳ���Ӧid�������ֵ 
 * 
 * @param actionName ��ѯҳ���actionName ��searchCustomerAction ��ͷ���Сд
 * @param flag ���������id="name2", flag�ʹ���2
 */
function openWindowAndReturnValue2(actionName,flag){	 
  var returnvalue = openWindow(actionName);
  toSetInputValue2(returnvalue,flag); 
}


/**
 * ��ȡ�����Ĳ�ѯҳ�汻ѡ�е������а����������������id��ֵ�������ά���鲢���ء�
 * ���ظ�openWindowAndReturnValue��������ҳ���Ӧid�������ֵ
 * 
 * ����ֵ ��ά���� returnarray
 */
function selectReturnMethod(){
  if(serveTableForm.ids){      
    var len = document.serveTableForm.ids.length;
    len = len==null?1:len
    
    var returnarray = new Array();
    var flag = false;
    for(a=0,b=0;a<len;a++){
      var checkBox = len>1?document.serveTableForm.ids[a]:document.serveTableForm.ids

      if(checkBox.checked == true){
        returnarray[b] = new Array();   
         
        parentObj = checkBox.parentNode;
        inputs = parentObj.getElementsByTagName("input");
          
        for(i=0;i<inputs.length;i++){
          if(inputs[i].type == "hidden" && inputs[i].id !=null 
        		  && inputs[i].id !="" && inputs[i].value != null){   
            returnarray[b].push(inputs[i].id+"="+inputs[i].value);         
          }
        }

        b++;
        flag = true;
      }
    }  
    if(!flag){
      alert("��ѡ����һ����¼��");
    }else{
      parent.window.returnuu(returnarray);
    }
    
  }
}

//����ָ���Ĳ�ѯҳ��Ĵ���
function openWindow(actionName){
	var projectName = window.location.pathname.replace(window.location.pathname.replace(/\s*\/+.*\/+/g,''),'');
	var url="query/Search.jsp?path="+projectName+actionName+".do";  
	return window.showModalDialog(url,window,'dialogWidth:970px;dialogHeight:500px;dialogLeft:200px;dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes');
}

//����ָ���Ĳ�ѯҳ��Ĵ��ڣ�������
function openWindowWithParams(actionName,method,paramstring){
	var projectName = window.location.pathname.replace(window.location.pathname.replace(/\s*\/+.*\/+/g,''),'');
	var url="query/Search.jsp?path="+encodeURIComponent(projectName+actionName+".do?method="+method+"&"+paramstring);
	return window.showModalDialog(url,window,'dialogWidth:970px;dialogHeight:500px;dialogLeft:200px;dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes');
}

function simpleOpenWindow(actionName,id,params){
	var params = params;
	if(params == null || params == ""){
		params = "isOpen=Y";
	}
	var projectName = window.location.pathname.replace(window.location.pathname.replace(/\s*\/+.*\/+/g,''),'');
	var url=projectName+actionName+".do?method=toDisplayRecord&"+params+"&id="+id;
	window.open(url,'','height=500px, width=1000px,scrollbars=yes, resizable=yes,directories=no');
}

//�������������ҳ���Ӧid�������ֵ 
function toSetInputValue(arr,formName){	
  if(arr){  
    for(i=0;i<arr.length;i++){		       
      for(j=0;j<arr[i].length;j++){     
        var object = arr[i][j].split("=");
        var id = object[0];
        var value = object[1];
        var inputObj = eval(formName+"."+id);
        if(inputObj != null){
          if(inputObj.length > 1 ){
            var startIndex =  inputObj.length - arr.length;
            eval(formName+"."+id)[i+startIndex].value = value;
		  }else{
		    eval(formName+"."+id).value = value;
		  }
        }
        
      }		      
    } 		    
  }  
}

//�������������ҳ���Ӧid��������Ԫ�ص�innerHTML��ֵ
function toSetInputValue2(arr,flag){
  if(arr && arr.length == 1){  
    flag=flag?flag:'';
    for(i=0;i<arr.length;i++){		       
      for(j=0;j<arr[i].length;j++){
      	
        var object = arr[i][j].split("=");
        var id = object[0];
        var value = object[1];
        if(document.getElementById(id+flag)){
          var element = document.getElementById(id+flag);
         
          if(element.value != null){
          	element.value = value;
          } else if(element.innerHTML != null){
            element.innerHTML = value;
          }
          
        }
      }		      
    } 		    
  }  
}

//�������������ҳ���Ӧid�������ֵ 
function toSetInputValue3(arr,formName,flag){	
  if(arr){  
  	flag=flag?flag:'';
    for(i=0;i<arr.length;i++){		       
      for(j=0;j<arr[i].length;j++){     
        var object = arr[i][j].split("=");
        var id = object[0];
        var value = object[1];
        var inputObj = eval(formName+"."+id+flag);
        
        if(inputObj != null){
          if(inputObj.length > 1 ){
            var startIndex =  inputObj.length - arr.length;
            if(eval(formName+"."+id+flag)[i+startIndex].type=="hidden"){
            	eval(formName+"."+id+flag)[i+startIndex].parentNode.innerHTML+=value;
            }else{
            	eval(formName+"."+id+flag)[i+startIndex].value = value;
            }
            
            //eval(formName+"."+id+flag)[i+startIndex].value = value;
            
		  }else{
			  if(eval(formName+"."+id+flag).type=="hidden"){
				  eval(formName+"."+id+flag).parentNode.innerHTML+=value;
	            }else{
	            	eval(formName+"."+id+flag).value = value;
	            }
		    //eval(formName+"."+id+flag).value = value;
		  }
          
        }
        
        
      }		      
    } 		    
  }  
}

function openWindowAndReturnValue3(actionName,method,paramstring,flag){	 
	  var returnvalue = openWindowWithParams(actionName,method,paramstring);
	  toSetInputValue2(returnvalue,flag); 
}
function openWindowAndReturnValue4(actionName,method,paramstring,flag){	 
	  var returnvalue = openWindowWithParams(actionName,method,paramstring);
	  toSetInputValue4(returnvalue,flag); 
}
//�������������ҳ���Ӧid��������Ԫ�ص�innerHTML��ֵ
function toSetInputValue4(arr,flag){
  if(arr && arr.length > 0){
    flag=flag?flag:'';

    for(i=0;i<arr.length;i++){
    	//alert(arr[i]);
      for(j=0;j<arr[i].length;j++){
      	
        var object = arr[i][j].split("=");
        var id = object[0];
        var value = object[1];
        
        if(i==0){
          	if(document.getElementById(id+flag)){
	          var element = document.getElementById(id+flag);
	         
	          if(element.value != null){
	          	element.value = value;
	          } else if(element.innerHTML != null){
	            element.innerHTML = value;
	          }
	        }
        }else{
        	if(document.getElementById(id+flag)){
	          var element = document.getElementById(id+flag);
	         
	          if(element.value != null){
	          	element.value += ','+value;
	          } else if(element.innerHTML != null){
	            element.innerHTML += ','+value;
	          }
	        }
        }
      }		      
    }
  }
  
}

function openWindowAndReturnValue5(actionName,method,paramstring,flag){	 
	  var returnvalue = openWindowWithParams(actionName,method,paramstring);
	  toSetInputValue5(returnvalue,flag); 
}
//�������������ҳ���Ӧid��������Ԫ�ص�innerHTML��ֵ
function toSetInputValue5(arr,flag){
  if(arr && arr.length > 0){
    flag=flag?flag:'';

    for(i=0;i<arr.length;i++){
    	//alert(arr[i]);
      for(j=0;j<arr[i].length;j++){
      	
        var object = arr[i][j].split("=");
        var id = object[0];
        var value = object[1];
        
    	if(document.getElementById(id+flag)){
          var element = document.getElementById(id+flag);
         
          if(element.value != null){
          	if(element.value==''){
          		element.value = value;
          	}else{
          		element.value += ','+value;
          	}
          } else if(element.innerHTML != null){
            element.innerHTML += ','+value;
          }
          
          
        }
      }		      
    }
  }
  
}




