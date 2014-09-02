package com.zjs.cms.frame;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;

/**
 * 解决中文乱码问题，包装Get请求对象
 * @author  Yuxuan Yang
 */
public class GetHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private String charset = "UTF-8";

    public GetHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 获得被装饰对象的引用和采用的字符编码 
     * @param request
     * @param charset
     */
    public GetHttpServletRequestWrapper(HttpServletRequest request,
                                        String charset) {
        super(request);
        this.charset = charset;
    }

    /**
     * 实际上就是调用被包装的请求对象的getParameter方法获得参数，然后再进行编码转换 
     */
    public String getParameter(String name) {
        String value = super.getParameter(name);
        value = value == null ? null : convert(value);
        return value;
    }

    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null || values.length == 0) {
            // Do nothing, no values found at all.
        } else if (values.length > 1) {
            for (String val : values) {
                val = val == null ? null : convert(val);
            }
        } else {
            values[0] =  values[0] == null ? null : convert( values[0]);
        }
        return values;
    }

    public String getQueryString() {
        String value = super.getQueryString();
        value = value == null ? null : convert(value);
        return value;
    }

    public String convert(String target) {
        try {
            return new String(target.trim().getBytes("ISO-8859-1"), charset);
        } catch (UnsupportedEncodingException e) {
            return target;
        }
    }
}