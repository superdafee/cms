package com.zjs.cms.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间处理类
 *
 * @author mayufeng
 */
public class DateUtil {
    public static final String GMT = "GMT+08:00";
    public static final String FORMAT = "yyyy-MM-dd";
    public static final String FORMAT2 = "yyyyMMddHHmmss";
    public static final String FORMAT3 = "yyyyMMdd";
    public static final String FORMAT4 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT5 = "yyyy-MM-dd HH:mm";

    /**
     * @param data
     * @param format
     * @return
     */
    public static String getDateFormat(Date data, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone(GMT));
        sdf.format(data);
        return sdf.format(data);
    }

    /**
     * 只使用于生成pdf防止空指针
     *
     * @param data
     * @param format
     * @return
     */
    public static String getDateFormatPDF(Date data, String format) {
        if (data == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone(GMT));
        sdf.format(data);
        return sdf.format(data);
    }

    /**
     * 字符串转化为实际类型
     *
     * @param str ,fmt
     * @return
     * @throws java.text.ParseException
     */
    public static Date StringToDate(String str, String fmt) throws ParseException {
        if (str != null && !"".equals(str)) {
            SimpleDateFormat sdf = new SimpleDateFormat(fmt);
            return sdf.parse(str);
        }
        return null;
    }

    /**
     * 时间增加天数
     *
     * @param date
     * @param days
     * @return
     */
    public static Date dateAddDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * 获取当天时间long类型
     *
     * @return
     * @throws java.text.ParseException
     */
    public static long getCurrentDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        String data = sdf.format(new Date());
        return sdf.parse(data).getTime();
    }

    /**
     * 取得给定两个日期相差的天数
     *
     * @param oldDate
     * @param newDate
     * @return
     */
    public static int diffDate(long oldDate, long newDate) {

        if (newDate > oldDate) {
            newDate = newDate + oldDate;
            oldDate = newDate - oldDate;
            newDate = newDate - oldDate;
        }

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(oldDate);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(newDate);
        // 先判断是否同年
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);

        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int maxDays = 0;
        int day = 0;
        if (y1 - y2 > 0) {
            day = numerical(maxDays, d1, d2, y1, y2, calendar2);
        } else {
            day = d1 - d2;
        }

        return day;
    }

    /**
     * 日期间隔计算
     *
     * @param maxDays  用于记录一年中有365天还是366天
     * @param d1       表示在这年中过了多少天
     * @param d2       表示在这年中过了多少天
     * @param y1       当前为XXXX年
     * @param y2       当前为XXXX年
     * @param calendar 根据日历对象来获取一年中有多少天
     * @return 计算后日期间隔的天数
     */
    public static int numerical(int maxDays, int d1, int d2, int y1, int y2, Calendar calendar) {
        int day = d1 - d2;
        int betweenYears = y1 - y2;
        List<Integer> d366 = new ArrayList<Integer>();

        if (calendar.getActualMaximum(Calendar.DAY_OF_YEAR) == 366) {
            day += 1;
        }

        for (int i = 0; i < betweenYears; i++) {
            calendar.set(Calendar.YEAR, (calendar.get(Calendar.YEAR)) + 1);
            maxDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
            if (maxDays != 366) {
                day += maxDays;
            } else {
                d366.add(maxDays);
            }
            if (i == betweenYears - 1 && betweenYears > 1 && maxDays == 366) {
                day -= 1;
            }
        }

        for (int i = 0; i < d366.size(); i++) {
            if (d366.size() >= 1) {
                day += d366.get(i);
            }
        }

        return day;
    }

    /**
     * 是否是周末
     *
     * @param date
     * @return
     */
    public static boolean isWeekend(Date date) {
        boolean weekend = false;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            weekend = true;
        }
        return weekend;
    }

    /**
     * 根据入学年份,学期生产年级
     *
     * @param startYear
     * @param term
     * @return
     * @throws java.text.ParseException
     */
    public static int produceGrade(String startYear, String term, String type) throws ParseException {
        int result = 0;
        if (type.contains("1")) {
            result = 0;
        } else if (type.contains("2")) {
            result = 6;
        } else if (type.contains("3")) {
            result = 9;
        }
        int sYear = Integer.parseInt(startYear);
        int currentYear = Integer.parseInt(term.substring(5, 9));
        return result + (currentYear - sYear);
    }

    /**
     * 做成学期列表
     *
     * @return 学期列表
     */
//    public static List<Term> makeTerm() {
//        Calendar current = Calendar.getInstance();
//        current.setTime(new Date());
//        int year = current.get(Calendar.YEAR);
//        int month = current.get(Calendar.MONTH) + 1;
//
//        int index = 0;
//        int termCnt = 1;
//        if (month < Constants.DEFAULT_NEXT_TERM_BEGINS_MONTH) {
//            index = -1;
//        } else if (month >= Constants.DEFAULT_NEXT_TERM_BEGINS_MONTH
//                && month < Constants.DEFAULT_TERM_BEGINS_MONTH) {
//            termCnt = 2;
//        }
//
//        List<Term> termList = new ArrayList<Term>();
//        String start = null;
//        String end = null;
//        String termStr = null;
//        if (termCnt == 1) {
//            for (int i = 1; i <= 2; i++) {
//                Term term = new Term();
//                start = String.valueOf(year + index);
//                end = String.valueOf(year);
//                termStr = (i == 1 ? "第一学期" : "第二学期");
//                term.setId(start + "_" + end + "_" + i);
//                term.setName(start + "-" + end + termStr);
//                termList.add(term);
//            }
//            Term term = new Term();
//            start = String.valueOf(year);
//            end = String.valueOf(year + 1);
//            term.setId(start + "_" + end + "_" + 1);
//            term.setName(start + "-" + end + "第一学期");
//            termList.add(term);
//        } else {
//            Term termRet = new Term();
//            start = String.valueOf(year - 1);
//            end = String.valueOf(year);
//            termRet.setId(start + "_" + end + "_" + 2);
//            termRet.setName(start + "-" + end + "第二学期");
//            termList.add(termRet);
//            for (int i = 1; i <= 2; i++) {
//                Term term = new Term();
//                start = String.valueOf(year);
//                end = String.valueOf(year + 1);
//                termStr = (i == 1 ? "第一学期" : "第二学期");
//                term.setId(start + "_" + end + "_" + i);
//                term.setName(start + "-" + end + termStr);
//                termList.add(term);
//            }
//        }
//
//        return termList;
//    }

    /**
     * 根据班级信息算出所在学期
     *
     * @return 年级
     */
    public static int getSemesterByClassInfo(String startYear, String phase) {
        int result = 0;
        int temp = 0;
        if ("1".equals(phase)) {
            temp = 0;
        } else if ("2".equals(phase)) {
            temp = 12;
        } else if ("3".equals(phase)) {
            temp = 18;
        }

        Calendar current = Calendar.getInstance();
        current.setTime(new Date());
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH) + 1;

        int sYear = Integer.parseInt(startYear);
        if (sYear == year) {
            result = temp + 1;
        } else if (year > sYear) {
            if (month >= Constants.DEFAULT_NEXT_TERM_BEGINS_MONTH &&
                    month < Constants.DEFAULT_TERM_BEGINS_MONTH) {
                result = temp + (year - sYear) * 2;
            } else if (month >= Constants.DEFAULT_TERM_BEGINS_MONTH) {
                result = temp + (year - sYear) * 2 + 1;
            } else {
                result = temp + (year - sYear) * 2 - 1;
            }
        }

        return result;
    }

    /**
     * 根据班级信息算出所在年级
     *
     * @return 年级
     */
    public static int getGradeByClassInfo(String startYear, String phase) {
        int result = 0;
        int temp = 0;
        if ("1".equals(phase)) {
            temp = 0;
        } else if ("2".equals(phase)) {
            temp = 6;
        } else if ("3".equals(phase)) {
            temp = 9;
        }

        Calendar current = Calendar.getInstance();
        current.setTime(new Date());
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH) + 1;

        int sYear = Integer.parseInt(startYear);
        if (sYear == year) {
            result = temp + 1;
        } else if (year > sYear) {
            if (month < Constants.DEFAULT_TERM_BEGINS_MONTH) {
                result = temp + (year - sYear);
            } else {
                result = temp + (year - sYear) + 1;
            }
        }

        return result;
    }

    /**
     * 算出当前时间所在的学期
     *
     * @return 学期
     */
    public static String getCurrentTerm() {
        Calendar current = Calendar.getInstance();
        current.setTime(new Date());
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH) + 1;

        int index = 0;
        int termCnt = 1;
        if (month < Constants.DEFAULT_NEXT_TERM_BEGINS_MONTH) {
            index = -1;
        } else if (month >= Constants.DEFAULT_NEXT_TERM_BEGINS_MONTH
                && month < Constants.DEFAULT_TERM_BEGINS_MONTH) {
            termCnt = 2;
        }

        String start;
        String end;
        String term;
        if (termCnt == 1) {
            start = String.valueOf(year + index);
            end = String.valueOf(year + 1 + index);
            term = start + "_" + end + "_" + 1;
        } else {
            start = String.valueOf(year - 1);
            end = String.valueOf(year);
            term = start + "_" + end + "_" + 2;
        }

        return term;
    }

    /**
     *根据阶段和场景得出开始时间
     *
     * @return 开始时间
     */
    public static String getStartYearByPhaseAndScene(int scene, String phase) {
        Calendar current = Calendar.getInstance();
        current.setTime(new Date());

        int mode = scene % 2;

        int temp = 0;
        int result = 0;
        if ("1".equals(phase)) {
            temp = 0;
        } else if ("2".equals(phase)) {
            temp = 12;
        } else if ("3".equals(phase)) {
            temp = 18;
        }

        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH) + 1;

        if (mode == 0 && (month >= Constants.DEFAULT_NEXT_TERM_BEGINS_MONTH &&
                month < Constants.DEFAULT_TERM_BEGINS_MONTH)) {
            result = scene - temp;
            result = year - result / 2;
        } else if(mode == 1 &&
                (month < Constants.DEFAULT_NEXT_TERM_BEGINS_MONTH || month >= Constants.DEFAULT_TERM_BEGINS_MONTH)) {
            result = scene - temp;
            result = year - (result - 1) / 2;
            if (month < Constants.DEFAULT_NEXT_TERM_BEGINS_MONTH) {
                result = result - 1;
            }
        }

        return String.valueOf(result);
    }

    public static String formatDuring(long mss) {
        long days = mss / (60 * 60 * 24);
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / (60);
        long seconds = (mss % (60));

        String retTime = "";
        if(days != 0) {
            retTime = retTime + days + "天";
        }
        if(hours != 0) {
            retTime = retTime + hours + ":";
        }
        if(minutes != 0) {
            retTime = retTime + minutes + "'";
        }
        if(seconds != 0) {
            retTime = retTime + seconds + "''";
        }
        return retTime;
    }
    /**
     * 获取当前月的第一天
     */
    public static Date  getCurrentMonthFirstDay() throws ParseException {
        Calendar current = Calendar.getInstance();
        current.setTime(new Date());
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH)+1;
        int day=1;
        return StringToDate(year+"-"+month+"-"+day,DateUtil.FORMAT);
    }

    /**
     * 获取上周下周日期
     * @param weekNum 0,当前周，1，上一周
     * @return
     */
    public static Map<String,String> getDayWeekMap(int weekNum){
       Map<String,String>  map=new LinkedHashMap<String, String>();
        TimeZone zone = TimeZone.getTimeZone("GMT+8"); //获取中国时区
        TimeZone.setDefault(zone); //设置时区
       Calendar calendar=Calendar.getInstance();
        // 取得指定日期所在周的第一天
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(new Date());
     if(weekNum==0){
         calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Monday
         map.put(getDayWeekNum(calendar.get(Calendar.DAY_OF_WEEK))+"",DateUtil.getDateFormat(calendar.getTime(),DateUtil.FORMAT));
        for(int i=0;i<6;i++){
         calendar.add(Calendar.DAY_OF_YEAR,1);
         map.put(getDayWeekNum(calendar.get(Calendar.DAY_OF_WEEK))+"",DateUtil.getDateFormat(calendar.getTime(),DateUtil.FORMAT));
        }

     }else if(weekNum==1){
         calendar.add(Calendar.DAY_OF_YEAR,-7);
         calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Monday
         map.put(getDayWeekNum(calendar.get(Calendar.DAY_OF_WEEK))+"",DateUtil.getDateFormat(calendar.getTime(),DateUtil.FORMAT));
         for(int i=0;i<6;i++){
             calendar.add(Calendar.DAY_OF_YEAR,1);
             map.put(getDayWeekNum(calendar.get(Calendar.DAY_OF_WEEK))+"",DateUtil.getDateFormat(calendar.getTime(),DateUtil.FORMAT));
         }
       }
         System.out.println(map.toString());
         return map;
    }


    /**
     * 对应周天数，符合中国日历，星期天为最后一天
     * @param day
     * @return
     */
    public static int getDayWeekNum(int day){
      int result=0;
      switch (day){
          case 0:
             break;
          case 1:
              return 7;
          case 2:
              return 1;
          case 3:
              return 2;
          case 4:
              return 3;
          case 5:
              return 4;
          case 6:
              return 5;
          case 7:
              return 6;

      }
      return  result;
    }
    public static void main(String[] args){
        getStartYearByPhaseAndScene(7, "1");

    }
}
