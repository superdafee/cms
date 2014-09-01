package com.zjs.cms.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * URL处理类
 * @author Yuxuan Yang
 *
 */
public final class URLUtil {

    private static Logger logger = LoggerFactory.getLogger(URLUtil.class);

    /**
     * URLEncode请求参数
     * 注意：只对等号后面的值做UTF-8编码
     * @param reqParams
     * @return
     */
    public static String encodeReqParams(String reqParams) {

        String resultStr = "";
        StringBuilder resultReqParam = new StringBuilder("");
        try {
            if (StringUtils.isNotEmpty(reqParams)) {
                String[] paramMaps = reqParams.split("&");
                for (String param : paramMaps) {
                    if (param != null && param.indexOf("=") != -1) {
                        String[] entryset = param.split("=");
                        resultReqParam.append(entryset[0]).append("=");
                        if (entryset.length > 1) {
                            resultReqParam.append(URLEncoder.encode(entryset[1], "UTF-8")).append("&");
                        }
                    }
                }
                if (resultReqParam.toString().length() > 0) {
                    resultStr = resultReqParam.toString();
                    resultStr = resultStr.substring(0, resultStr.length() - 1);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            logger.error("字符编码出错!", ex);
        }
        return resultStr;
    }

    /**
     * 将从FlashMap中取得的检索参数做成map,用在列表展示action.
     * <pre>使用原因: 参数带有中文时，做urlEncode，redirect后通过addFlashAttribute增加的属性将丢失。<br/>
     * 为此，将检索的参数(search_)也放入FlashMap中，这样url中不再含有URLEncode的参数，FlashMap正常被传递。<br/>
     * </pre>
     * @param request
     * @param prefix
     * @return
     */
    public static Map<String, Object> mergeSearchParamInFlashMap(HttpServletRequest request, String prefix) {

        Map<String, Object> returnMap = new TreeMap<String, Object>();

        Map flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null && flashMap.containsKey("searchParams")) {
            String searchQuery = (String) flashMap.get("searchParams");
            if (StringUtils.isNotEmpty(searchQuery)) {
                String[] paramMaps = searchQuery.split("&");
                for (String param : paramMaps) {
                    if (param != null && param.contains("=")) {
                        String[] entrySet = param.split("=");
                        String unprefixed = entrySet[0].substring(prefix.length());
                        if (entrySet.length > 1) {
                            returnMap.put(unprefixed, entrySet[1]);
                        }
                    }
                }
            }
        }

        return returnMap;
    }
}
