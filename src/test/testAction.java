package test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.hibernate.basedata.personnelmanage.PersonnelManageMaster;

public class testAction
{
	static BaseDataImpl bd=new BaseDataImpl();
	
	public static void main(String[] args)
	{		
		try{
			
			int dday=DateUtil.compareDay("2018-07-27", "2021-07-26");//��������֮����������
			System.out.println(dday);
			
//			String aa="kkk;bbb��ccc";
//			aa=aa.replaceAll("��", ";");
//			System.out.println(aa);
//			String[] a=aa.split(";");
//			System.out.println(Arrays.toString(a));
			
			//String[] titleid={"username","operdate","storagename","maintpersonnel","elevatorno","ydlh","isgzfz","iszfwt","jffkwt","ycjkwt","rem"};
			//System.out.println(Arrays.toString(titleid));
			
//			int datenum=DateUtil.compareDay("2018-01-02","2018-01-02");
//			System.out.println(datenum);//0
//			datenum=DateUtil.compareDay("2018-01-02","2018-01-01");
//			System.out.println(datenum);//-1
//			datenum=DateUtil.compareDay("2018-01-02","2018-01-03");
//			System.out.println(datenum);//1
			//ContractTotal=30000.0;dday=366;yearcon=29918.03
			
//			String curdate=DateUtil.getNowTime("yyyyMMddHHmmss");
//			System.out.println(curdate);
			
//			String year = CommonUtil.getNowTime("yyyy");
//			String prefix = year+"-";
//			String suffix="";
//			String r1str = CommonUtil.genNo("MaintContractMaster", "r1", prefix, suffix, 4,"r1");
//			System.out.println(r1str);
				
//			String ddaystr=DateUtil.getTwoDay("2018-07-01", "2017-07-01");
//			System.out.println(ddaystr);
//			
//			int dday=DateUtil.compareDay("2017-07-01", "2018-07-01");
//			System.out.println(dday);
			
//			String singleno="BX2017010002";
//			if(singleno.indexOf("BX")<0){
//				System.out.println(">>>N");
//			}else{
//				System.out.println(">>>Y");
//			}
			
//			String maintdate="2013-09-10";
//			String hmaintdate="2013-10-03";
//			int result = maintdate.compareTo(hmaintdate);
//			System.out.println(result);
			  
//			String numnostr="10,20,30,40,";
//			System.out.println(numnostr);
//			numnostr=numnostr.substring(0,numnostr.length()-1);
//			System.out.println(numnostr);
			
//			String[] bystr={
//					"���","����","����","����","����","����","����",
//					"����","����","����","����","����","����",
//					"����","����","����","����","����","����","����",
//					"����","����","����","����","����","����","����",
//					"���","����","����","����","����","����",
//					"����","����","����","����","����","����","����",
//					"����","����","����","����","����","����",
//					"����","����","����","����","����","����","����"
//					};
//			int a=1;
//			int bylen=53;
//			for(int i=0;i<bystr.length;i++){
//				if(a%bylen==1 || a%bylen==28){
//					//1,28 ���
//					System.out.println(a+"=="+bystr[i]);
//				}else if(a%bylen==14 || a%bylen==41){
//					//14,41 ����
//					System.out.println(a+"=="+bystr[i]);
//				}else if(a%bylen==8 || a%bylen==21 || a%bylen==34 || a%bylen==47){
//					//8,21,34,47 ����
//					System.out.println(a+"=="+bystr[i]);
//				}else{
//					//����
//					System.out.println(a+"=="+bystr[i]);
//				}
//				a++;
//			}
			
//			String workdate="2017-08-21";
//			System.out.println(workdate);
//			String workdate2=workdate.substring(workdate.indexOf("-")+1,workdate.length());
//			System.out.println(workdate2);
//			String workdate3=workdate.substring(workdate.lastIndexOf("-")+1,workdate.length());
//			System.out.println(workdate3);
			
//			String date="2017-01-01";
//			String eDate="2017-03-01";
//			String eDate2=DateUtil.getDate(eDate, "MM", 3);//��ǰ�����·ݼ�1 ��
//			System.out.println(DateUtil.compareDay(date,eDate2)>0);
			
//			String[] r5={"aaa","bbb","ccc"};
//			String r5str="";
//			if(r5!=null && r5.length>0){
//				for(int r=0;r<r5.length;r++){
//   				 if(r==r5.length-1){
//   					r5str+=r5[r];
//   				 }else{
//   					r5str+=r5[r]+",";
//   				 }
//   			 }
//			}
//			System.out.println(">>>"+r5str);
			
//			String datenum=DateUtil.getDate("2014-07-11", "MM", 24);
//			System.out.println(">>>"+datenum);
			
			
//			String imei="1234567890,11111111111";
//			String imeip=imei.replaceAll(",", "','");
//
//    		System.out.println(imeip);
			
			
//			String[] titlename={"���ޱ��","ά���ֲ�","ά��վ","���ۺ�ͬ��","���޵�λ����","����ʱ��","���޵��ݱ��","���޷�ʽ",
//					"�Ƿ�����","��������","��������","���ϴ���","ά������","���Ϸ���","�Ƿ���֧��"};
//			List list = java.util.Arrays.asList(titlename);
//			
//			System.out.println("list>>>"+list.toString());
//			
//			String[] strings=(String[])list.toArray();
//			System.out.println("strings>>>"+Arrays.toString(strings));
			
//			String imei="1234567890,11111111111";
//			String imeip="11111111111";
//
//			if(imei.trim().indexOf(imeip.trim())>-1){
//				System.out.println("ok");
//    		}else{
//    			System.out.println("no");
//    		}
			
			
//			String no="2017010410112";
//			Integer intHao = Integer.parseInt(no.substring(no.length()-4,no.length()));
//			int intHao2=intHao+1;  
//			DecimalFormat df = new DecimalFormat("0000");  
//			String sigleno=df.format(intHao2);
//			System.out.println(sigleno+","+intHao+","+no);
			
//			String year = CommonUtil.getNowTime("yyyy");
//			String prefix = year+"-";
//			String suffix="";
//			String r9string = CommonUtil.genNo("MaintContractMaster", "r9", prefix, suffix, 4,"r9");
//			System.out.println(r9string);
			
//			String faultCode="ER123,ER456��ER789��000��������";
//			String faultCodestr="";
//			String[] faultCodes=faultCode.split(",");
//			for(int i=0;i<faultCodes.length;i++){
//				String[] faultCodes2=faultCodes[i].split("��");
//				for(int j=0;j<faultCodes2.length;j++){
//					if(faultCodes2[j].indexOf("ER")>-1){
//						faultCodestr+=faultCodes2[j]+",";
//					}else{
//						faultCodestr+="ER"+faultCodes2[j]+",";
//					}
//				}
//			}
//			faultCodestr=faultCodestr.substring(0,faultCodestr.length()-1);
//			System.out.println(">>>"+faultCodestr);	
			
//			int datenum=DateUtil.compareDay("2016-02-05","2016-02-05");
//			System.out.println(">>>"+datenum);	
					
//			double usedDuration=CommonUtil.getMinute("2016-11-23 11:08:43", "2016-11-23 11:17:25");
//			double usedDuration2=CommonUtil.getMinute("2016-11-23 11:09:03", "2016-11-23 11:17:01");
//			System.out.println(">>>"+usedDuration+";"+usedDuration2);
			
//			int logic=2;
//			System.out.println(">>>��ʼ");
//			for(int i=0;i<logic-1;i++){
//				System.out.println(">>>"+logic);
//			}
//			System.out.println(">>>����");
			
//			PersonnelManageMaster ta=new PersonnelManageMaster();
//			ta.setBillno(null);
//			Object obj = ta;
//			Object obj2 = new Object();
//			Class<?> t = obj.getClass();
//			Class<?> t2 = t.getSuperclass();
//			
//			Field field = t2.getDeclaredField("billno"); 
//			Method method = t2.getMethod("getBillno");
//			String str = method.invoke(obj, null) + "";
//			//System.out.println(null + "");
			
//			testAction.test1();
//			String endDate="";
			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			Date eDate=sdf.parse("2016-07-03");
//			Date auditDate=sdf.parse("2016-06-03");
//			
//			if(auditDate.after(eDate)){
//				endDate="2016-06-03";
//			}else{
//				endDate="2016-07-03";
//			}
//			String num=bd.numToChinese(1);
//			System.out.println(num);
//			num=num.substring(0,1);
//			System.out.println(num);
			
//			String billnostr="a��b��c,";
//			billnostr=billnostr.substring(0,billnostr.length()-1);
//			System.out.println(billnostr);
//			billnostr=billnostr.replaceAll("��", "");
//			System.out.println(billnostr);
			
//			String strarr="ab,cdb";
//			if(strarr.indexOf("ab,")>-1){
//				System.out.print("����");
//			}else{
//				System.out.print(strarr);
//			}
			
//			String[] proarr={"0","4","1","2","3"};
//
//			for(int i=0;i<proarr.length;i++){
//				System.out.println(">>>"+proarr[i]);
//			}
			
			//System.out.println("2016-08-01;2016-08-30="+DateUtil.compareDay("2016-09-01","2016-08-30"));
			
//			String path="D:\\contract\\���س���֪ͨ��·��.txt";
//			FileReader freader=new FileReader(path);
//			BufferedReader reader= new BufferedReader(freader);
//			String content=reader.readLine();
//			System.out.println(content.trim());
//			reader.close();
//			freader.close();
			
			//String path="D:\\contract\\���س���֪ͨ��·��.txt";
			//File file=new File(path);    
			//InputStream ips = new FileInputStream(new File(path));
//			if(!file.exists())    
//			{    
//			    try {    
//			        file.createNewFile();    
//			    } catch (IOException e) {    
//			        // TODO Auto-generated catch block    
//			        e.printStackTrace();    
//			    }    
//			}  
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void test1(){
		
		try {
			String year1=CommonUtil.getToday().substring(2,4);
			String year2=CommonUtil.getToday().substring(0,4);
			String month=CommonUtil.getToday().substring(5, 7);
			String time=CommonUtil.getTodayTime();
			
			String a=String.format("%03d",1);
			
			/*JSONArray array=new JSONArray();
			JSONObject object1=new JSONObject();
			JSONObject object2=new JSONObject();
			
			
			List list1=bd.getPullDownList("AdvisoryComplaintsManage_IssueSort2");
			List list2=bd.getPullDownList("AdvisoryComplaintsManage_IssueSort3");
			object1.put("A", list1);
			object2.put("B", list2);
			array.put(0,list1);
			array.put(1,list2);*/
//			String[] billno1 = CommonUtil.getBillno(year1,"QualityCheckManagement", 1);		
//			String[] billno2=CommonUtil.getBillno(year1,"bbbbb", 1, 5);
			
			//System.out.println(a);
			//System.out.println(year2);
			//System.out.println(month);
			//System.out.println(CommonUtil.getToday()+" "+time);
			//System.out.println(CommonUtil.getTodayTimeFormat());
//			//System.out.println(array.toString());
//			//System.out.println("billno1="+Arrays.toString(billno1));
//			//System.out.println("billno2="+Arrays.toString(billno2));
			/**
			billno1=[150000000001, 150000000002, 150000000003, 150000000004, 150000000005]
			billno2=[1500001]
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
     * ���벻�㳤��
     * @param length ����
     * @param number ����
     * @return
     */
    private void lpad(int length, int number) {
        String f = "%0" + length + "d";
        String numstr= String.format(f, number);
        //System.out.println("numstr="+numstr);
    }
    /**
	 * ����Ԫ������ת��Ϊ��д��ĸ
	 * @param cellNum ���� 
	 * @return char
	 */
    private void getCellChar(int cellNum){
		//System.out.println(cellNum+"=="+(char) (cellNum + 65)); 
	}
	
}
