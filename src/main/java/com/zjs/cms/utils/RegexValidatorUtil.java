/**
 * 
 */
package com.zjs.cms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: Java正则校验器 <br /> 
 * Project: ecardFront <br />
 * ClassName: RegexValidatorUtil <br />
 * Copyright: Copyright (c) 2011 onest<br />
 * @author bushy
 * @version 1.0 2012-5-15上午8:43:05
 */
public class RegexValidatorUtil {

	/**
	 * 校验是否是合法的手机号
	 * @author bushy
	 * @createDate 2012-5-15
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile){
		String regex = "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
		return regexValidator(regex, mobile);
	}

    /**
     * 校验是否是英文数字的组合
     * @param value 内容
     * @return true 是 false 不是
     */
    public static boolean isAlfbetDigit(String value){
        String regex = "^[a-zA-Z0-9]+$";
        return regexValidator(regex, value);
    }

	/**
	 * 校验是否是合法的备用电话(手机号或带区号的固定电话)
	 * @author bushy
	 * @createDate 2012-5-15
	 * @param phone
	 * @return
	 */
	public static boolean isBakPhone(String phone){
		String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		String bakRegex = "[0]{1}[0-9]{2,3}-[0-9]{7,8}";
		if(regexValidator(regex, phone) || regexValidator(bakRegex, phone)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 校验是否是合法邮箱
	 * @author bushy
	 * @createDate 2012-5-15
	 * @param mail
	 * @return
	 */
	public static boolean isMail(String mail){
		String regex = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		return regexValidator(regex, mail);
	}
	
	/**
	 * 校验是否是合法的身份证号
	 * @author bushy
	 * @createDate 2012-5-15
	 * @param card
	 * @return
	 */
	public static boolean isCardNo(String card){
		String regex = "[0-9]{15}|[0-9]{17}[0-9X]";
		return regexValidator(regex, card);
	}
	
	/**
	 * 校验是否是合法的数字
	 * @author bushy
	 * @createDate 2012-5-15
	 * @param no
	 * @return
	 */
	public static boolean isNumber(String no){
		String regex = "^[0-9]+$";
		return regexValidator(regex, no);
	}
	
	/**
	 * 校验是否是合法的月份
	 * @author bushy
	 * @createDate 2012-5-25
	 * @param month
	 * @return
	 */
	public static boolean isMonth(String month){
		String regex = "^(0?[1-9]|1[0-2])$";
		return regexValidator(regex, month);
	}
	
	/**
	 * 校验金额
	 * @author bushy
	 * @createDate 2012-5-25
	 * @param amount
	 * @return
	 */
	public static boolean isAmount(String amount){
		String regex = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";
		return regexValidator(regex, amount);
	}

    /**
     * 校验MAC
     * @createDate 2012-5-25
     * @param amount
     * @return
     */
    public static boolean isMac(String amount){
        String regex = "^([0-9a-fA-F]{2})(([/\\s:-][0-9a-fA-F]{2}){7})$";
        return regexValidator(regex, amount);
    }
	
	/**
	 * 校验年份
	 * @author bushy
	 * @createDate 2012-5-25
	 * @param year
	 * @return
	 */
	public static boolean isYear(String year){
		String regex = "^\\d{4}$";
		return regexValidator(regex, year);
	}
	
	public static boolean isName(String name) {
		String regex = "(^[a-zA-Z ]+)|(^[\u4e00-\u9fa5]+)";
		return regexValidator(regex, name);
	}

    public static boolean isBirthday(String birthday, String patten) {
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        try {
            sdf.parse(birthday);
        } catch (ParseException ex) {
            return false;
        }
        return true;
    }

	/**
	 * 正则对比操作实现
	 * @author bushy
	 * @createDate 2012-5-15
	 * @param regex
	 * @param val
	 * @return
	 */
	private static boolean regexValidator(String regex, String val){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(val);
		return matcher.matches();
	}

}
