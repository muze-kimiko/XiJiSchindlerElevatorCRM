
//ȡ����ת�ɴ�д����        !!����λС��   �������Ĵ�д
function Number_to_Chinese1(Num){
   for(i=Num.length-1;i>=0;i--)
   {
    Num = Num.replace(",","")//�滻Num�еġ�,��
    Num = Num.replace(" ","")//�滻Num�еĿո�
   }
   Num = Num.replace("��","")//�滻�����ܳ��ֵģ��ַ�
   if(isNaN(Num)) { //��֤������ַ��Ƿ�Ϊ����
    alert("����Сд����Ƿ���ȷ");
    return;
   }
   //---�ַ�������ϣ���ʼת����ת������ǰ�������ֱַ�ת��---//
   var fuhao="";//����ʱ,��дǰ����ӷ���"-"
   if(Num<0){
   	fuhao="��";
   }
   part = String(Num).split(".");
   newchar = ""; 
   //С����ǰ����ת��
   for(i=part[0].length-1;i>=0;i--){
   if(part[0].length > 10){ alert("λ�������޷�����");return "";}//����������ʰ�ڵ�λ����ʾ
    tmpnewchar = ""
    perchar = part[0].charAt(i);
    //��������תΪ��д����
    switch(perchar){
    case "0": tmpnewchar="��" + tmpnewchar ;break;
    case "1": tmpnewchar="Ҽ" + tmpnewchar ;break;
    case "2": tmpnewchar="��" + tmpnewchar ;break;
    case "3": tmpnewchar="��" + tmpnewchar ;break;
    case "4": tmpnewchar="��" + tmpnewchar ;break;
    case "5": tmpnewchar="��" + tmpnewchar ;break;
    case "6": tmpnewchar="½" + tmpnewchar ;break;
    case "7": tmpnewchar="��" + tmpnewchar ;break;
    case "8": tmpnewchar="��" + tmpnewchar ;break;
    case "9": tmpnewchar="��" + tmpnewchar ;break;
    }
     //�жϵ�λ
    switch(part[0].length-i-1){
    case 0: tmpnewchar = tmpnewchar +"Ԫ" ;break;
    case 1: if(perchar!=0)tmpnewchar= tmpnewchar +"ʰ" ;break;
    case 2: if(perchar!=0)tmpnewchar= tmpnewchar +"��" ;break;
    case 3: if(perchar!=0)tmpnewchar= tmpnewchar +"Ǫ" ;break;
    case 4: tmpnewchar= tmpnewchar +"��" ;break;
    case 5: if(perchar!=0)tmpnewchar= tmpnewchar +"ʰ" ;break;
    case 6: if(perchar!=0)tmpnewchar= tmpnewchar +"��" ;break;
    case 7: if(perchar!=0)tmpnewchar= tmpnewchar +"Ǫ" ;break;
    case 8: tmpnewchar= tmpnewchar +"��" ;break;
    case 9: tmpnewchar= tmpnewchar +"ʰ" ;break;
    }
    newchar = tmpnewchar + newchar;
   }
   //С����֮�����ת��
   if(Num.indexOf(".")!=-1){
   if(part[1].length > 2) {
    alert("С����֮��ֻ�ܱ�����λ,ϵͳ���Զ��ض�");
    part[1] = part[1].substr(0,2)
    }
   for(i=0;i<part[1].length;i++){
    tmpnewchar = ""
    perchar = part[1].charAt(i)
     //С������תΪ��д����
    switch(perchar){
    case "0": tmpnewchar="��" + tmpnewchar ;break;
    case "1": tmpnewchar="Ҽ" + tmpnewchar ;break;
    case "2": tmpnewchar="��" + tmpnewchar ;break;
    case "3": tmpnewchar="��" + tmpnewchar ;break;
    case "4": tmpnewchar="��" + tmpnewchar ;break;
  case "5": tmpnewchar="��" + tmpnewchar ;break;
    case "6": tmpnewchar="½" + tmpnewchar ;break;
    case "7": tmpnewchar="��" + tmpnewchar ;break;
    case "8": tmpnewchar="��" + tmpnewchar ;break;
    case "9": tmpnewchar="��" + tmpnewchar ;break;
    }
   //�жϵ�λ
    if(i==0)tmpnewchar =tmpnewchar + "��";
    if(i==1)tmpnewchar = tmpnewchar + "��";
    newchar = newchar + tmpnewchar;
   }
   }
   //�滻�������ú���
   while(newchar.search("����") != -1)
    newchar = newchar.replace("����", "��");
   newchar = newchar.replace("����", "��");
   newchar = newchar.replace("����", "��");
   newchar = newchar.replace("����", "��");
   newchar = newchar.replace("��Ԫ", "Ԫ");
   newchar = newchar.replace("���", "");
   newchar = newchar.replace("���", ""); 


   if (newchar.charAt(newchar.length-1) == "Ԫ" || newchar.charAt(newchar.length-1) == "��")
    newchar = fuhao+newchar+"��"
    //document.write(newchar);
    return newchar;
  }
  
//ȡ����ת�ɴ�д���ĸ�ֵ��һ��text�ؼ�
  function Number_to_Chinese2(num,objName)
{
	document.getElementById(objName).value=Number_to_Chinese1(num);
}
  