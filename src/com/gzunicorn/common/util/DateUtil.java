package com.gzunicorn.common.util;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.gzunicorn.common.logic.DataOperation;

/**
 * 
 * �뵱ǰ������صĹ�����
 * 
 * @author ���ڷ�
 * 
 */
public class DateUtil {

    // ����ȫ�ֿ��� ��һ�ܣ����ܣ���һ�ܵ������仯
    private static int       weeks           = 0;
    private static int       MaxDate;                                // һ���������
    private static int       MaxYear;                                // һ���������

    public static String     DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String     DATETIME_FORMAT2 = "yyyyMMddHHmmss";
    private static String    DATE_FORMAT     = "yyyy-MM-dd";
    private static String    DATE_FORMAT_1   = "yyyyMMdd";
    private static String    TIME_FORMAT     = "HH:mm";

    public static final long TIME_ONE_HOUR   = 3600L * 1000L;

    public static final long TIME_ONE_DAY    = 24L * TIME_ONE_HOUR;

    /**
     * @param args
     */
    public static void main(String[] args) {

        DateUtil tt = new DateUtil();

        //System.out.println("����ĳһ���������ڼ�:" + tt.getWeek("2009-02-01"));
        //System.out.println("��ݼ�1:" + tt.getNextYear("2009-02-01"));
        //System.out.println("���(2009-02-01)��1:" + tt.getDate("2009-02-01", "yyyy", 1));
        //System.out.println("���(2009-02-01)��1:" + tt.getDate("2009-02-01", "yyyy", -1));
        //System.out.println("�·�(2009-02-01)��1:" + tt.getDate("2009-02-01", "MM", 1));
        //System.out.println("�·�(2009-02-01)��1:" + tt.getDate("2009-02-01", "MM", -1));
        //System.out.println("����(2009-02-01)��1:" + tt.getDate("2009-02-01", "dd", 1));
        //System.out.println("����(2009-02-01)��1:" + tt.getDate("2009-02-01", "dd", -1));
        //System.out.println("���ص�ǰϵͳʱ��.....:" + tt.getDateTimeD());
        //System.out.println("��ȡ��������:" + tt.getNowTime("yyyy-MM-dd"));

        //System.out.println("��ȡ����һ����:" + tt.getMondayOFWeek());
        //System.out.println("��ȡ�����յ�����~:" + tt.getCurrentWeekday());
        //System.out.println("��ȡ����һ����:" + tt.getPreviousWeekday());
        //System.out.println("��ȡ����������:" + tt.getPreviousWeekSunday());
        //System.out.println("��ȡ����һ����:" + tt.getNextMonday());
        //System.out.println("��ȡ����������:" + tt.getNextSunday());
        //System.out.println("�����Ӧ�ܵ�����������:" + tt.getNowTime("yyyy-MM-dd"));
        //System.out.println("��ȡ���µ�һ������:" + tt.getFirstDayOfMonth());
        //System.out.println("��ȡ�������һ������:" + tt.getDefaultDay());
        //System.out.println("��ȡ���µ�һ������:" + tt.getPreviousMonthFirst());
        //System.out.println("��ȡ�������һ�������:" + tt.getPreviousMonthEnd());
        //System.out.println("��ȡ���µ�һ������:" + tt.getNextMonthFirst());
        //System.out.println("��ȡ�������һ������:" + tt.getNextMonthEnd());
        //System.out.println("��ȡ����ĵ�һ������:" + tt.getCurrentYearFirst());
        //System.out.println("��ȡ�������һ������:" + tt.getCurrentYearEnd());
        //System.out.println("��ȡȥ��ĵ�һ������:" + tt.getPreviousYearFirst());
        //System.out.println("��ȡȥ������һ������:" + tt.getPreviousYearEnd());
        //System.out.println("��ȡ�����һ������:" + tt.getNextYearFirst());
        //System.out.println("��ȡ�������һ������:" + tt.getNextYearEnd());
        //System.out.println("��ȡ�����ȵ�һ�쵽���һ��:" + tt.getThisSeasonTime(11));
        //System.out.println("��ȡ��������֮��������2008-12-1~2008-9.29:" + DateUtil.getTwoDay("2008-12-1", "2008-9-29"));
        //System.out.println("ȡ��ĳ����һ���еĶ�����:" + tt.getWeekOfYear("2009-05-07"));
        // //System.out.println("��+1= " + getWeekOrAdd("200806",1));
        // //System.out.println("��-5= " + getWeekOrAdd("200903",-5));

        //System.out.println("20090201 - 25����= " + DateUtil.getDate("20090201", "MM", -25));
        ////System.out.println("200902 - 2��= " + getWeekOrAdd("200902", -5));

        //System.out.println("����ĳ�ܵĿ�ʼ����=" + getDateByWeekFirst("200918"));
        //System.out.println("����ĳ�ܵĽ�������=" + getDateByWeekLast("200918"));

        //System.out.println("����ĳһ���ڷ��������ڼ�=" + getWeek("2009-05-07"));

        //System.out.println("��ѯĳ�����һ��=" + getDefaultDay("200902"));

        //System.out.println("����ĳ�·ݵ�����=" + getMonthDays("200902"));
        //System.out.println("�����������ڼ�������·�=" + compareMonth("200908", "200912"));
        //System.out.println("�����������ڼ��������=" + getTwoWeek("2009-12-08", "2009-12-01"));

        //System.out.println("��ǰʱ��" + getDateTime());
        //System.out.println("��ǰʱ��" + getDateTimeD());

        //System.out.println("��ǰ����" + getCurDate());
        ////System.out.println("������" + DateUtil.getWeekOrAdd("200906", -(1 + 1)));

        //System.out.println("����������" + compareWeek("200908", "200912"));

        //System.out.println("��ǰʱ��=" + DateUtil.getDateTime());
        //System.out.println("��������֮����������=" + DateUtil.compareDay("20090908", "20090902"));
        ////System.out.println("������=" + DateUtil.getDayOrAdd("2009-09-08", 5));

        String y = DateUtil.getCurDate().replaceAll("-", "");

        String year = y.substring(2, 4);
        String month = y.substring(4, 6);

        //System.out.println("��λ��" + year);
        //System.out.println("��λ��" + month);
    }

    private DateUtil () {

    }

    /**
     * Convert date and time to string like "yyyy-MM-dd HH:mm".
     */
    public static String formatDateTime(Date d) {
        return new SimpleDateFormat(DATETIME_FORMAT).format(d);
    }

    /**
     * Convert time to string like "HH:mm".
     */
    public static String formatTime(Date d) {
        return new SimpleDateFormat(TIME_FORMAT).format(d);
    }

    /**
     * Convert date to String like "yyyy-MM-dd".
     */
    public static String formatDate(Date d) {
        return new SimpleDateFormat(DATE_FORMAT).format(d);
    }

    /**
     * Parse date like "yyyy-MM-dd".
     */
    public static Date parseDate(String d) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(d);
        } catch (ParseException e) {}
        return null;
    }

    /**
     * Parse date like "yyyyMMdd".
     */
    public static Date parseDateD(String d) {
        try {
            return new SimpleDateFormat(DATE_FORMAT_1).parse(d);
        } catch (ParseException e) {}
        return null;
    }

    /**
     * 
     * Parse date and time like "yyyy-MM-dd HH:mm:ss".
     */
    public static Date parseDateTime(String dt) {
        try {
            return new SimpleDateFormat(DATETIME_FORMAT).parse(dt);
        } catch (Exception e) {}
        return null;
    }

    /**
     * ����ĳһ���ڵ���һ������
     * 
     * @param date
     *            ���ڸ�ʽΪyyyy-MM-dd
     * @return ��һ�������
     */
    public static String getNextYear(String sdate) {

        String str = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, Integer.parseInt(sdate.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(sdate.substring(5, 7)) - 1);
        c.set(Calendar.DATE, Integer.parseInt(sdate.substring(8, 10)));
        c.add(Calendar.YEAR, 1);

        str = sdf.format(c.getTime());
        return str;
    }

    /**
     * ����ĳһ����������.
     * 
     * @param sdate
     *            ��ʽΪyyyy-MM-dd��yyyyMMdd
     * @param part
     *            ��Ҫ�ı�Ĳ��֡�yyyy-��ʾ�꣬MM-��ʾ��,dd-��ʾ��
     * @param i
     *            ��Ҫ�ı����,�����Ǹ���
     * @return
     */
    public static String getDate(String sdate, String part, int i) {
        String str = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();

        sdate = sdate.replaceAll("-", "");

        c.set(Calendar.YEAR, Integer.parseInt(sdate.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(sdate.substring(4, 6)) - 1);
        c.set(Calendar.DATE, Integer.parseInt(sdate.substring(6, 8)));

        if ("yyyy".equalsIgnoreCase(part)) {
            c.add(Calendar.YEAR, i);
        } else if ("MM".equalsIgnoreCase(part)) {
            c.add(Calendar.MONTH, i);
        } else if ("dd".equalsIgnoreCase(part)) {
            c.add(Calendar.DATE, i);
        }
        str = sdf.format(c.getTime());
        return str;
    }

    /**
     * ������������֮�������ٸ���
     * 
     * @param year1
     *            ��ʼ���¸�ʽΪyyyyMM
     * @param year2
     *            �������¸�ʽΪyyyyMM
     * @return
     */
    public static int compareMonth(String year1, String year2) {
        int r = 0;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(year1.replaceAll("-", "").substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(year1.replaceAll("-", "").substring(4, 6)) - 1);
        c.set(Calendar.DATE, 1);

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, Integer.parseInt(year2.replaceAll("-", "").substring(0, 4)));
        c2.set(Calendar.MONTH, Integer.parseInt(year2.replaceAll("-", "").substring(4, 6)) - 1);
        c2.set(Calendar.DATE, 1);

        r = compare(c2, c, 2);

        return r;
    }

    /**
     * ������������֮����������
     * 
     * @param year1
     *            ��ʼ���¸�ʽΪyyyyWW
     * @param year2
     *            �������¸�ʽΪyyyyWW
     * @return
     */
    public static int compareWeek(String year1, String year2) {
        int r = 0;

        String startDate = DateUtil.getDateByWeekFirst(year1).replaceAll("-", "");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(startDate.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(startDate.substring(4, 6)) - 1);
        c.set(Calendar.DATE, Integer.parseInt(startDate.substring(6, 8)));

        String endDate = DateUtil.getDateByWeekFirst(year2).replaceAll("-", "");
        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, Integer.parseInt(endDate.substring(0, 4)));
        c2.set(Calendar.MONTH, Integer.parseInt(endDate.substring(4, 6)) - 1);
        c2.set(Calendar.DATE, Integer.parseInt(endDate.substring(6, 8)));

        r = compare(c2, c, Calendar.DATE);
        // ���������������7��������

        return r / 7;
    }

    /**
     * ������������֮�������ٸ���
     * 
     * @param startDate
     *            ��ʼ���¸�ʽΪyyyyMMdd��yyyy-MM-dd
     * @param endDate
     *            �������¸�ʽΪyyyyMMdd��yyyy-MM-dd
     * @return
     */
    public static int compareDay(String startDate, String endDate) {
        int r = 0;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(startDate.replaceAll("-", "").substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(startDate.replaceAll("-", "").substring(4, 6)) - 1);
        c.set(Calendar.DATE, Integer.parseInt(startDate.replaceAll("-", "").substring(6, 8)));

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, Integer.parseInt(endDate.replaceAll("-", "").substring(0, 4)));
        c2.set(Calendar.MONTH, Integer.parseInt(endDate.replaceAll("-", "").substring(4, 6)) - 1);
        c2.set(Calendar.DATE, Integer.parseInt(endDate.replaceAll("-", "").substring(6, 8)));

        r = compare(c2, c, 5);

        return r;
    }

    /**
     * ��������֮����������|����|����
     * 
     * @param c1
     * @param c2
     * @param what
     * @return
     */
    public static int compare(Calendar c1, Calendar c2, int what) {
        int number = 0;
        switch (what) {
        case Calendar.YEAR:
            number = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
            break;
        case Calendar.MONTH:
            int years = compare(c1, c2, Calendar.YEAR);
            number = 12 * years + (c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH));
            break;
        case Calendar.DATE:
            number = (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / (1000 * 60 * 60 * 24));
            break;
        default:
            number = (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / (1000 * 60 * 60 * 24));
            break;
        }
        return number;
    }

    /**
     * �·������������
     * 
     * @param yearMonth
     *            ���£���ʽΪyyyyMM
     * @param part
     *            ��Ҫ�ı�Ĳ��֡�yyyy-��ʾ�꣬MM-��ʾ��
     * @param i
     * @return
     */
    public static String getYearMonth(String yearMonth, String part, int i) {
        String str = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, Integer.parseInt(yearMonth.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(yearMonth.substring(4, 6)) - 1);
        c.set(Calendar.DATE, Integer.parseInt("5"));

        if ("yyyy".equals(part)) {
            c.add(Calendar.YEAR, i);
        } else if ("MM".equals(part)) {
            c.add(Calendar.MONTH, i);
        }

        str = sdf.format(c.getTime());
        return str;
    }

    /*
     * ȡ��ĳ��ĳ�ܵĵ�һ�� ���ڽ���:2008-12-29��2009-01-04����2008������һ��,2009-01-05Ϊ2009���һ�ܵĵ�һ��
     * @param year @param week @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar calFirst = Calendar.getInstance();
        calFirst.set(year, 0, 7);
        Date firstDate = getFirstDayOfWeek(calFirst.getTime());

        Calendar firstDateCal = Calendar.getInstance();
        firstDateCal.setTime(firstDate);

        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, firstDateCal.get(Calendar.DATE));

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, (week - 1) * 7);
        firstDate = getFirstDayOfWeek(cal.getTime());

        return firstDate;
    }

    /**
     * ȡ��ĳ��ĳ�ܵ����һ��
     * ���ڽ���:2008-12-29��2009-01-04����2008������һ��,2009-01-04Ϊ2008�����һ�ܵ����һ��
     * 
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar calLast = Calendar.getInstance();
        calLast.set(year, 0, 7);
        Date firstDate = getLastDayOfWeek(calLast.getTime());

        Calendar firstDateCal = Calendar.getInstance();
        firstDateCal.setTime(firstDate);

        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, firstDateCal.get(Calendar.DATE));

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, (week - 1) * 7);
        Date lastDate = getLastDayOfWeek(cal.getTime());

        return lastDate;
    }

    /**
     * ����ĳ���ܵĿ�ʼ����
     * 
     * @param yearweek
     *            ��ʽΪ:yyyyWW
     * @return
     */
    public static String getDateByWeekFirst(String yearweek) {
        if (yearweek == null) return null;

        if (yearweek.length() != 6) {
            return null;
        }

        int year = Integer.parseInt(yearweek.substring(0, 4));
        int ww = Integer.parseInt(yearweek.substring(4, 6));

        Date d = getFirstDayOfWeek(year, ww);
        return DateUtil.formatDate(d);
    }

    /**
     * ����ĳ���ܵĽ�������
     * 
     * @param yearweek
     *            ��ʽΪ:yyyyWW
     * @return
     */
    public static String getDateByWeekLast(String yearweek) {
        if (yearweek == null) return null;

        if (yearweek.length() != 6) {
            return null;
        }

        int year = Integer.parseInt(yearweek.substring(0, 4));
        int ww = Integer.parseInt(yearweek.substring(4, 6));
        Date d = getFirstDayOfWeek(year, ww);

        return DateUtil.getDate(DateUtil.formatDate(d), "dd", 6);
    }


    /*
     * ȡ��ĳ�����(��)�����һ�� @param date @param num(�����ɸ�) @return
     */
    public static Date getAnotherDate(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, num);
        return c.getTime();
    }

    /**
     * /** ȡ��ĳ����һ���еĶ�����
     * 
     * @param date
     *            ��ʽΪyyyy-MM-dd
     * @return ���صĸ�ʽΪyyyyWW
     * 
     */
    public static String getWeekOfYear(String sdate) {
        String str = null;

        Calendar c = new GregorianCalendar();

        c.set(Calendar.YEAR, Integer.parseInt(sdate.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(sdate.substring(5, 7)) - 1);
        c.set(Calendar.DATE, Integer.parseInt(sdate.substring(8, 10)));
        c.setFirstDayOfWeek(Calendar.MONDAY);

        c.setMinimalDaysInFirstWeek(7);

        int week = c.get(Calendar.WEEK_OF_YEAR);

        int lastDay = Integer.parseInt(sdate.substring(8));

        // �����λ��С��7�����������Ѿ�����51��
        if (lastDay < 7 && week > 51) {
            // ˵�����ܷ�Χ�Ǵ�ȥ�꿪ʼ��
            sdate = DateUtil.getDate(sdate, "yyyy", -1);
            str = sdate.substring(0, 4) + String.valueOf(week);
        } else {
            str = sdate.substring(0, 4);
            str += week < 10 ? "0" + week : String.valueOf(week);
        }

        return str;
    }

    /** */
    /**
     * ȡ��ĳ�������ܵĵ�һ��
     * 
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }

    /** */
    /**
     * ȡ��ĳ�������ܵ����һ��
     * 
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }

    /**
     * ��ȡ��ǰ����
     * 
     * @return ���ص�ǰ����,��ʽΪyyyy-MM-dd
     */
    public static String getCurDate() {
        String str = null;
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        str = myFormatter.format(c.getTime());
        return str;
    }

    /**
     * ��ȡ��ǰ����ʱ��
     * 
     * @return ���ص�ǰ����,��ʽΪyyyy-MM-dd hh:mm:ss
     */
    public static String getCurDateTime() {
        return DateUtil.getDateTime();
    }

    /**
     * ��ȡ��ǰ����
     * 
     * @return ���ص�ǰ����
     */
    public static Date getCurDateD() {
        Date d = null;
        Calendar c = Calendar.getInstance();
        d = c.getTime();
        return d;
    }

    /**
     * �õ��������ڼ�ļ������
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            java.util.Date date = myFormatter.parse(sj1);
            java.util.Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * �õ��������ڼ�ļ������
     */
    public static String getTwoWeek(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            java.util.Date date = myFormatter.parse(sj1);
            java.util.Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (7 * 24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * ����һ�����ڣ����������ڼ����ַ���
     * 
     * @param sdate
     * @return
     */
    public static String getWeek(String sdate) {
        // ��ת��Ϊʱ��
        Date date = DateUtil.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour�д�ľ������ڼ��ˣ��䷶Χ 1~7
        // 1=������ 7=����������������
        String week = new SimpleDateFormat("EEEE").format(c.getTime());
        if ("Monday".equals(week)) {
            week = "����һ";
        }
        if ("Tuesday".equals(week)) {
            week = "���ڶ�";
        }
        if ("Wednesday".equals(week)) {
            week = "������";
        }
        if ("Thursday".equals(week)) {
            week = "������";
        }
        if ("Friday".equals(week)) {
            week = "������";
        }
        if ("Saturday".equals(week)) {
            week = "������";
        }
        if ("Sunday".equals(week)) {
            week = "������";
        }
        return week;
    }

    /**
     * ����ʱ���ʽ�ַ���ת��Ϊʱ�� yyyy-MM-dd
     * 
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * ����ʱ��֮�������
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals("")) return 0;
        if (date2 == null || date2.equals("")) return 0;
        // ת��Ϊ��׼ʱ��
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        java.util.Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {}
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    // ���㵱�����һ��,�����ַ���
    public static String getDefaultDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
        lastDate.add(Calendar.MONTH, 1);// ��һ���£���Ϊ���µ�1��
        lastDate.add(Calendar.DATE, -1);// ��ȥһ�죬��Ϊ�������һ��

        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * ��ȡĳ���µ����һ��
     * 
     * @param yearMM
     *            ���£���ʽyyyy-MM��yyyyMM
     * @return yyyy-MM-dd
     */
    public static String getDefaultDay(String yearMM) {

        yearMM = yearMM.replaceAll("-", "").substring(0, 6);
        yearMM = yearMM.substring(0, 4) + "-" + yearMM.substring(4, 6) + "-01";

        String str = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, Integer.parseInt(yearMM.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.parseInt(yearMM.substring(5, 7)) - 1);
        c.set(Calendar.DATE, Integer.parseInt(yearMM.substring(8, 10)));

        c.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
        c.add(Calendar.MONTH, 1);// ��һ���£���Ϊ���µ�1��
        c.add(Calendar.DATE, -1);// ��ȥһ�죬��Ϊ�������һ��

        str = sdf.format(c.getTime());
        return str;
    }

    /**
     * ����ĳ�·ݵ�����
     * 
     * @param yearMM
     *            ��ʽΪyyyy-MM-dd��yyyy-MM��yyyyMM
     * @return
     */
    public static int getMonthDays(String yearMM) {

        String date = yearMM.replaceAll("-", "").substring(0, 6);
        String d = getDefaultDay(date);
        int days = Integer.parseInt(d.substring(8, 10));
        return days;
    }

    // ���µ�һ��
    public static String getPreviousMonthFirst() {
        String str = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
        lastDate.add(Calendar.MONTH, -1);// ��һ���£���Ϊ���µ�1��
        // lastDate.add(Calendar.DATE,-1);//��ȥһ�죬��Ϊ�������һ��

        str = sdf.format(lastDate.getTime());
        return str;
    }

    // ��ȡ���µ�һ��
    public static String getFirstDayOfMonth() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // ��ñ��������յ�����
    public static String getCurrentWeekday() {
        weeks = 0;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // ��ȡ����ʱ��
    public static String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// ���Է�����޸����ڸ�ʽ
        String hehe = dateFormat.format(now);
        return hehe;
    }

    // ��õ�ǰ�����뱾������������
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // ��ý�����һ�ܵĵڼ��죬�������ǵ�һ�죬���ڶ��ǵڶ���......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // ��Ϊ���й����һ��Ϊ��һ�����������1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    // ��ñ���һ������
    public static String getMondayOFWeek() {
        weeks = 0;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // �����Ӧ�ܵ�����������
    public static String getSaturday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // ������������յ�����
    public static String getPreviousWeekSunday() {
        weeks = 0;
        weeks--;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // �����������һ������
    public static String getPreviousWeekday() {
        weeks--;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // �����������һ������
    public static String getNextMonday() {
        weeks++;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // ������������յ�����
    public static String getNextSunday() {

        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    private static int getMonthPlus() {
        Calendar cd = Calendar.getInstance();
        int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
        cd.set(Calendar.DATE, 1);// ����������Ϊ���µ�һ��
        cd.roll(Calendar.DATE, -1);// ���ڻع�һ�죬Ҳ�������һ��
        MaxDate = cd.get(Calendar.DATE);
        if (monthOfNumber == 1) {
            return -MaxDate;
        } else {
            return 1 - monthOfNumber;
        }
    }

    // ����������һ�������
    public static String getPreviousMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, -1);// ��һ����
        lastDate.set(Calendar.DATE, 1);// ����������Ϊ���µ�һ��
        lastDate.roll(Calendar.DATE, -1);// ���ڻع�һ�죬Ҳ���Ǳ������һ��
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // ����¸��µ�һ�������
    public static String getNextMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// ��һ����
        lastDate.set(Calendar.DATE, 1);// ����������Ϊ���µ�һ��
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // ����¸������һ�������
    public static String getNextMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// ��һ����
        lastDate.set(Calendar.DATE, 1);// ����������Ϊ���µ�һ��
        lastDate.roll(Calendar.DATE, -1);// ���ڻع�һ�죬Ҳ���Ǳ������һ��
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // ����������һ�������
    public static String getNextYearEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// ��һ����
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        lastDate.roll(Calendar.DAY_OF_YEAR, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // ��������һ�������
    public static String getNextYearFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// ��һ����
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        str = sdf.format(lastDate.getTime());
        return str;

    }

    // ��ñ����ж�����
    public static int getMaxYear() {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.DAY_OF_YEAR, 1);// ��������Ϊ�����һ��
        cd.roll(Calendar.DAY_OF_YEAR, -1);// �����ڻع�һ�졣
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        return MaxYear;
    }

    private static int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// ��õ�����һ���еĵڼ���
        cd.set(Calendar.DAY_OF_YEAR, 1);// ��������Ϊ�����һ��
        cd.roll(Calendar.DAY_OF_YEAR, -1);// �����ڻع�һ�졣
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        if (yearOfNumber == 1) {
            return -MaxYear;
        } else {
            return 1 - yearOfNumber;
        }
    }

    // ��ñ����һ�������
    public static String getCurrentYearFirst() {
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus);
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        return preYearDay;
    }

    // ��ñ������һ������� *
    public static String getCurrentYearEnd() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// ���Է�����޸����ڸ�ʽ
        String years = dateFormat.format(date);
        return years + "-12-31";
    }

    // ��������һ������� *
    public static String getPreviousYearFirst() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// ���Է�����޸����ڸ�ʽ
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        years_value--;
        return years_value + "-1-1";
    }

    // ����������һ�������
    public static String getPreviousYearEnd() {
        weeks--;
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks + (MaxYear - 1));
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        getThisSeasonTime(11);
        return preYearDay;
    }

    // ��ñ�����
    public static String getThisSeasonTime(int month) {
        int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_month = array[season - 1][0];
        int end_month = array[season - 1][2];

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// ���Է�����޸����ڸ�ʽ
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);

        int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate = years_value + "-" + start_month + "-" + start_days + ";" + years_value + "-" + end_month
                + "-" + end_days;
        return seasonDate;

    }

    /**
     * ��ȡĳ��ĳ�µ����һ��
     * 
     * @param year
     *            ��
     * @param month
     *            ��
     * @return ���һ��
     */
    private static int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    /**
     * �Ƿ�����
     * 
     * @param year
     *            ��
     * @return
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * ����ϵͳ��ǰ���ں�ʱ��
     * 
     * @param dateformat
     *            ��ʽ yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateTime() {
        String nowTime = "";
        Date dt = new Date();// �������Ҫ��ʽ,��ֱ����dt,dt���ǵ�ǰϵͳʱ��
        DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);// ������ʾ��ʽ
        nowTime = df.format(dt);
        return nowTime;
    }
    /**
     * ����ϵͳ��ǰ���ں�ʱ��
     * 
     * @param dateformat
     *            ��ʽ yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateTime(String format) {
        String nowTime = "";
        Date dt = new Date();// �������Ҫ��ʽ,��ֱ����dt,dt���ǵ�ǰϵͳʱ��
        DateFormat df = new SimpleDateFormat(format);// ������ʾ��ʽ
        nowTime = df.format(dt);
        return nowTime;
    }

    /**
     * ����ϵͳ��ǰ���ں�ʱ��
     * 
     * @return
     */
    public static Date getDateTimeD() {
        Date d = null;
        Calendar c = Calendar.getInstance();
        d = c.getTime();
        return d;
    }

    /**
     * ���� N��,NСʱ,N���� ������ڸ�ʽ
     * 
     * @param dateYMDHMS
     * @param format
     * @param unit
     * @param amount
     * @return teset addDate("20110615 17:25:00", "yyyyMMdd HH:mm:ss", "days",
     *         1);
     */
    public static String addDate(String dateYMDHMS, String format, String unit, int amount) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            calendar.setTime(sdf.parse(dateYMDHMS));
            if (unit.equals("day") || unit.equals("days")) {
                calendar.add(Calendar.DAY_OF_YEAR, amount);
            } else if (unit.equals("hour") || unit.equals("hours")) {
                calendar.add(Calendar.HOUR_OF_DAY, amount);
            } else if (unit.equals("minute") || unit.equals("minutes")) {
                calendar.add(Calendar.MINUTE, amount);
            }
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ����2λ���� �� 11>>A,12>>B �� 10>>A,11>>B,12>>C �˲��չ�ϵ�ɱ�BarCodeYearMonth����
     * 
     * @param date
     * @return
     */
    public static String getTwoBitBarCodeYearAndMonth() {
        String yearmonth = "";

        ArrayList list = null;
        Session hs = null;

        Connection conn = null;
        try {
            hs = HibernateUtil.getSession();
            conn = hs.connection();

            String y = DateUtil.getCurDate().replaceAll("-", "");

            String year = y.substring(2, 4);
            String month = y.substring(5, 6);
            // 1.����
            String sqlQuery = " select  dbo.fn_GetTwoBitBarCodeYearAndMonth('" + year + "','" + month + "' ) as ym";
            //System.out.println(sqlQuery);
            // sqlQuery = " select
            // dbo.fn_GetTwoBitBarCodeYearAndMonth('2011','06' ) as ym";
            //System.out.println(sqlQuery);

            DataOperation op = new DataOperation();
            op.setCon(hs.connection());
            List barcodelist = op.getSPList(sqlQuery);
            if (null != barcodelist && !barcodelist.isEmpty()) {
                Map map = (Map) barcodelist.get(0);
                String Twobitbarcodeyearandmonth = (String) map.get("ym");
                yearmonth = Twobitbarcodeyearandmonth;
            }
        } catch (HibernateException hex) {
            DebugUtil.print(hex, "HibernateUtil��Hibernate Session �򿪳���");
        } catch (Exception e) {
            DebugUtil.print(e, "HibernateUtil��ResultSet �򿪳���");
        } finally {
            try {
                hs.close();
            } catch (HibernateException hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }
        }

        return yearmonth;
    }
}

// the ends
