
//ѡ��ֿ�����ʱ����ʾ���Ľ��㿪ʼ����
 function ChoiceStorageID(key){
        if(key==""){
        	return null;
        }
		var detailRequest = new AjaxRequest();
		var url = '/XJSCRM/goodsBalanceAction.do?method=getStartDate';
		var detailRequest1 = new AjaxRequest();
		var url1 = '/XJSCRM/goodsBalanceAction.do?method=getEdnDate';
		url+="&key="+key;
		url1+="&key1="+key;
		detailRequest.url = url;
		detailRequest1.url = url1;
		detailRequest.method="POST";
		detailRequest1.method="POST";
		detailRequest.onComplete = putSelectStartDate;
		detailRequest1.onComplete = putSelectEndDate;
		detailRequest.process();
		detailRequest1.process();
		
}
function putSelectEndDate(req){

   			var enddate=document.getElementById("enddate");
			enddate.value=req.responseText;			
 }	
// �ö�ȡ�����Ľ��㿪ʼ���ڵ�ҳ������ʾ�������ˡ� 
function putSelectStartDate(req){
    try{
		var objXml = req.responseXML;
		var obj=objXml.getElementsByTagName("root");
		if(obj.length==0){
			var startdate=document.getElementById("startdate");
			startdate.value=req.responseText;
			 var showDate=document.getElementById("showDate");
		    showDate.style.display="none";
		}else{
		    alert("����ѡ��Ĳֿ���Щ���ݻ�û�н�����ˣ����ܹ������½ᡣ");
		    var showDate=document.getElementById("showDate");
		    showDate.style.display="";
			var startdate=document.getElementById("startdate");
			startdate.value="";
			clearRow(matter);
	   		var keys=new Array();
			keys = ['seq','billno','operdate','operid','title'];
			var values=new Array();
	   		var xml=req.responseXML;
	   		var root=xml.getElementsByTagName("rows");
	   		for(var i=0;i<root.length;i++){
	   			var property=root[i];
	   			var cols=property.childNodes;
	   			for(var j=0;j<cols.length;j++){
	   				var colsN=cols[j].getAttribute("name");
	   				values[0]=i+1;
	   				if(colsN=="billno"){
	   					values[1]=cols[j].firstChild.nodeValue;
	   				}
	   				if(colsN=="operdate"){
	   					values[2]=cols[j].firstChild.nodeValue;
	   				}
	   				
	   				if(colsN=="operid"){
	   					values[3]=cols[j].firstChild.nodeValue;
	   				}
	   				
	   				if(colsN=="title"){
	   					values[4]=cols[j].firstChild.nodeValue;
	   				}
	   			}
	   			addInstanceRow(matter,keys,values);
	   		}
	 }
	}catch(e){}
}




/**********
 * ���������ֿ�����������½��ա�
 */
 
 getMaxMonthByStorage=function(key){
 	if(key=="")return false;
 	var req=new AjaxRequest();
 	var url = '/XJSCRM/goodsBalanceAction.do?method=getMaxMonthByStorageDate';
		url+="&key="+key;
	req.url=url;
	req.method="POST";
	req.onComplete=showMaxDate;
	req.process();
 }
 
 showMaxDate=function(req){
 	//����½�����
 	var txt=req.responseText;
 	//ҳ������ʾ�������߳�������
 	var date=document.getElementsByName("masterR3")[0].value;
 	if(date>txt){
 		//ǰ�˵Ľű���֤����
 		checkDate();
 		return true;
 	}else{
 		alert("�˲ֿ���"+date+"��"+txt+"���Ѿ��������½ᣬ�����ܹ����г���������");
 		return false;		
 	}
 }
  
  /**
   * ������������
   */
  function showMaxD(){
   		var storageid=document.getElementsByName("masterR1")[0].value;
   	    getMaxMonthByStorage(storageid);
  }
/***************************************
*�����ѡ��Ĳɹ���ʽ��Ҫ�Զ������ɳ��ⵥ��ʱ�򣬰ѳ��ⵥ�еı���������Ŀ
��ʾ�������û��������롣
****************************************/

function StockModeStyle(key){
	var smRequest=new AjaxRequest();
	var url='/XJSCRM/sparepartAction.do?method=getStockModeStyle';
	url+="&key="+key;
	smRequest.url=url;
	smRequest.method="POST";
	smRequest.onComplete=putStockMode;
	smRequest.process();
}


/********************************
*�Ѷ�ȡ�������ݣ��ŵ�һ���ؼ���ȥ���ˡ�Ȼ�������ؼ��е�VALUE�����Ƿ���ʾ����
*���ϲ��ź������˵���Ϣ�ȡ�
*********************************/
function putStockMode(req){
	var txt=req.responseText;
	var stockmode=document.getElementById("stockmodestyle");
	stockmode.value=txt;
	showTakeGoods();
}


//����stockmodestyle�е�Value�����жϣ����ΪY,����ʾ��Щ��������ݣ�������ʾ����
function showTakeGoods(){
	var stockmode=document.getElementById("stockmodestyle");
	if(stockmode && stockmode.value=="Y"){
		 document.getElementById("takeGoods").style.display="";
		 document.getElementById("takeG").style.display="";
	}else{
		 document.getElementById("takeGoods").style.display="none";
		 document.getElementById("takeG").style.display="none";
	}
}


