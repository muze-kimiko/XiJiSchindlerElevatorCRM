/*
 * Created on 2005-8-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gzunicorn.common.dataimport.importutil;

import java.text.*;
import java.text.ParseException;
import org.apache.poi.hssf.usermodel.HSSFCell;


/**
 * Created on 2006-03-04
 * Title: ͨ����
 * Description: ת��Excel�ļ��е�Cell��ʽ(ϵͳ��ת���������߼�У��),�ṩ��̬ģʽ
 * 
 * @author rr
 * @version 1.2
 */
public class TranExcelCellUtil {

    private static TranExcelCellUtil tranExcelCellUtil = new TranExcelCellUtil();

    private TranExcelCellUtil() {
    }

    public static TranExcelCellUtil getInstance() {
        return tranExcelCellUtil;
    }

    /**
     * ͨ�õ�Cell��ת������
     * ��ʱ���ַ���������ת��
     * @param cell
     * @return String
     */
    public static String tranCell(HSSFCell cell) {
        String returnValue = "";
        if(cell == null){
            return returnValue;
        }
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
            // ��Cell�ĸ�ʽΪ�ַ���ʱ
            returnValue = cell.getStringCellValue().trim();
        } else {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC || cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
                // ��Cell�ĸ�ʽΪ������ʱ
                // ��ֹ���ֿ�ѧ�������ı�ʾ,�ṩ�����ݴ���
                NumberFormat defForm = new DecimalFormat("#.######");
                returnValue = String.valueOf(defForm.format(cell.getNumericCellValue()));
            }
        }
        return returnValue;
    }



    /**
     * ת��Excel�е�����Cell ֧��Cell�еĸ�ʽΪ�� Cell��ʽΪ�ַ�����20050505,2005-05-12��
     * Cell��ʽΪ�������͵����и�ʽ
     * 
     * @param cell
     * @return String ����""ֵ��Ϊ����
     * @throws ParseException
     */
    public static String tranDate(HSSFCell cell){
        String strDate = "";
        if(cell == null){
            return strDate;
        }
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
            // ��Cell�ĸ�ʽΪ�ַ���ʱ
            try{
              
              /*�ȳ�����dateToStr��strToDate����ȥת����
                ��ʽΪ��2005-05-12��2004-5-1,2004-5-01,2004-05-15
                �Ķ�����ת���ɹ����ṩ�����ݴ���*/
                
                strDate = ComUtil.dateToStr(ComUtil.formatStrToDate(cell.getStringCellValue().trim(), ""), "");    
            }catch(ParseException e){
                //ʧ��������������ת��
                if (cell.getStringCellValue().trim().length()==8 && cell.getStringCellValue().trim().indexOf('-')<0 ) {
                    // 20050505ת��Ϊ2005-05-12
                    strDate = cell.getStringCellValue().trim().substring(0, 4)
                            + "-"
                            + cell.getStringCellValue().trim().substring(4, 6)
                            + "-"
                            + cell.getStringCellValue().trim().substring(6, 8);
                } else {
                    if (cell.getStringCellValue().trim().length()==10 && cell.getStringCellValue().trim().indexOf('-')>-1) {
                        // 2005-05-05ת��Ϊ2005-05-12
                        strDate = cell.getStringCellValue().trim();
                    }
                }
            }
        } else {
            // ��Cell�ĸ�ʽΪ������ʱ��������Excel��Ҳ�������͵ģ�
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                // ��Cell�ĸ�ʽΪ������ʱ
                // ��ֹ���ֿ�ѧ�������ı�ʾ
                NumberFormat defForm = new DecimalFormat("#");
                if (String.valueOf(defForm.format(cell.getNumericCellValue()))
                        .trim().length() == 8) {
                    // ����20050501Ϊ����Cell�����
                    String str = String.valueOf(
                            defForm.format(cell.getNumericCellValue())).trim();
                    strDate = str.substring(0, 4) + "-" + str.substring(4, 6)
                            + "-" + str.substring(6, 8);
                } else {
                    strDate = ComUtil.dateToStr(cell.getDateCellValue(), "");
                }

            }
        }
        return strDate;
    }

   
}
