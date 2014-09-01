package com.zjs.cms.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件操作工具类
 * @author wf
 *
 */
public class PropertiesUtil {
	/**
	 * 通过属性文件路径和key获取value
	 * @param proPath
	 * @param key
	 * @return
	 */
	private static String getValue(String proPath,String key)
	{
		InputStream input = PropertiesUtil.class.getResourceAsStream(proPath);
		try
		{
			Properties ps = new Properties();
			ps.load(input) ;
			String key_str = ps.getProperty(key) ;
			if(key_str!=null)
			{
				return key_str ;
			}
			return null ;
		}
		catch(Exception e)
		{
			return null ;
		}
		finally
		{
			try
			{
				if(input!=null)
				{
					input.close() ;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace() ;
			}
		}
	}
	/**
	 * 获取application.properties文件属性值
	 * @param key
	 * @return
	 */
	public static String getDefaultValue(String key){
		return getValue("/application.properties", key);
	}
	/**
     * 获取config.properties文件属性值
     * @param key
     * @return
     */
    public static String getConfigValue(String key){
        return getValue("/config.properties", key);
    }
    /**
     * 获取phoneApp.properties文件属性值
     * @param key
     * @return
     */
    public static String getPhoneAppValue(String key){
        return getValue("/phoneApp.properties", key);
    }
    /**
     * 获取phoneApp.properties文件属性值
     * @param key
     * @return
     */
    public static String getWeixinAppValue(String key){
        return getValue("/weixin.properties", key);
    }
	
}
