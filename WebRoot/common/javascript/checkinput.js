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
 //���������Ƿ���������
function checkthisvalue(obj){
  if(isNaN(obj.value)){
    alert("����������!");
    obj.value="0";
    obj.focus();
    return false;
  }
}

//ҳ�������ı������ֵȥ��ǰ��ո�
function inputTextTrim(){
  var inputs = document.getElementsByTagName("input");
    for(var i=0;i<inputs.length;i++){
      if(inputs[i].type == "text"){
    	  if(inputs[i].value.indexOf(" ")>=0){
    		  inputs[i].value = inputs[i].value.trim();
    	  }       
      }
    }
}

/*
���������*�ű�ǵ��ı�������ֵ�Ƿ�Ϊ�ղ���ʾ�����������¸�ʽ
����: <td>����</td><td><input type="text"/>*</td> 
            ����ֵʱ����   ����������Ϊ�ա�
���� element ����div��table��form����
*/
function checkColumnInput(element){
  var inputs = element.getElementsByTagName("input");
  var textareas = element.getElementsByTagName("textarea");
  var selects = element.getElementsByTagName("select");
  var msg = "";

  for(var i=0;i<inputs.length;i++){
	  
    if(inputs[i].type == "text"
      && getFirstSpecificParentNode("td",inputs[i]) != null && inputs[i].parentNode.innerHTML.indexOf("*")>=0 
      && inputs[i].value.trim() == ""){
      	//searchval��searchvalNo1��Ϊ��ѯ������ƣ�����Ҫ�ж�
      	if(inputs[i].name != "searchval" && inputs[i].name != "searchvalNo1"){
      		msg += getFirstSpecificParentNode("td",inputs[i]).previousSibling.innerHTML + "����Ϊ��\n";	
      	}
    }
  }
  
  for(var i=0;i<textareas.length;i++){
      if(textareas[i].parentNode.innerHTML.indexOf("*")>=0 && getFirstSpecificParentNode("td",textareas[i]) != null && textareas[i].value.trim() == ""){          
          msg += getFirstSpecificParentNode("td",textareas[i]).previousSibling.innerHTML + "����Ϊ��\n";                       
      }
  }
  
  for(var i=0;i<selects.length;i++){
		  if(selects[i].value.trim()=="" && selects[i].parentNode.innerHTML.indexOf("*")>0){
				  msg+="��ѡ��"+selects[i].parentNode.previousSibling.innerHTML.replace(/:$/gi,"")+"\n";
			  
		  }
	  }
  
  if(msg != ""){
    alert(msg);
    return false;
  } 
  return true;
}

/*
���table��*�ű�ǵ��ı�������ֵ�Ƿ�Ϊ�ղ���ʾ�����������¸�ʽ
����: <table id="XX">
   <tr id="XX"><td>����*</td><tr>
     <tr><td><input type="text"/></td><tr> 
     <table>
            ����ֵʱ����   ����������Ϊ�ա�
*/
function checkRowInput(tableId,titleRowId){
  var tableObject = document.getElementById(tableId);
  var rows = tableObject.rows; 
  var titleRowIndex = 0;
  var msg = "";

  for(var i=0;i<rows.length;i++){
    var cells = tableObject.rows[i].cells;
    var inputs = rows[i].getElementsByTagName("input");
    var textareas = rows[i].getElementsByTagName("textarea");
    if(rows[i].id == titleRowId){
      titleRowIndex = i; 
    }
    
    for(var j=0;j<inputs.length;j++){
      var cellIndex = getFirstSpecificParentNode("td",inputs[j]).cellIndex;
      
      if(rows[titleRowIndex].cells[cellIndex]!=null&&rows[titleRowIndex].cells[cellIndex].innerHTML.indexOf("*")>=0){
        if(inputs[j].type=="text" && inputs[j].value==""){
          var title = rows[titleRowIndex].cells[cellIndex].innerHTML;            
          msg += "��"+(i-titleRowIndex)+"�� "+title.replace(/\s*<+.*>+\s*/g,"")+"����Ϊ��\n";    
        }        
      }
      
    }  
    
    for(var j=0;j<textareas.length;j++){
        var cellIndex = getFirstSpecificParentNode("td",textareas[j]).cellIndex;        
        if(rows[titleRowIndex].cells[cellIndex]!=null&&rows[titleRowIndex].cells[cellIndex].innerHTML.indexOf("*")>=0){
          if(textareas[j].value==""){
            var title = rows[titleRowIndex].cells[cellIndex].innerHTML;            
            msg += "��"+(i-titleRowIndex)+"�� "+title.replace(/\s*<+.*>+\s*/g,"")+"����Ϊ��\n";    
          }        
        }
        
      }

    if(msg != ""){
      alert(msg);
      return false;
    } 
    
  }

  return true;
}

function checkEmpty(elementId,titleRowId){	
	if(titleRowId == null || titleRowId == ""){
		return(checkColumnInput(document.getElementById(elementId)));
	}else{
		return(checkRowInput(elementId,titleRowId));
	}				
}

//����Ԫ��ָ���ĸ��ڵ����
function getFirstSpecificParentNode(parentTagName,childObj){
  var parentObj = childObj;  
  while(parentObj){
	if(parentObj.nodeName.toLowerCase() == "form"){
      return null;
	}
    if(parentObj.nodeName.toLowerCase() == parentTagName.toLowerCase()){      
      return parentObj;
    }
    parentObj = parentObj.parentNode;
  }
  return null;
}