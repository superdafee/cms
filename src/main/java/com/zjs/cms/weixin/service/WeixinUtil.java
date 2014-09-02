package com.zjs.cms.weixin.service;


import com.zjs.cms.utils.DateUtil;
import com.zjs.cms.utils.PropertiesUtil;
import com.zjs.cms.weixin.entity.AccessToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: mayufeng
 * Date: 14-1-2
 * Time: 上午10:00
 * To change this template use File | Settings | File Templates.
 */
public class WeixinUtil {
    /**
     * 验证
     * @param request
     * @return
     */
    public static boolean validate(HttpServletRequest request) throws NoSuchAlgorithmException {
        String token="tudouweixin2014";
        String  signature=request.getParameter("signature");
        String  timestamp =request.getParameter("timestamp");
        String  nonce =request.getParameter("nonce");
        String[] array={token,timestamp,nonce};
        Arrays.sort(array);
        String tmpStr=array[0]+array[1]+array[2];
        MessageDigest md = null;
        md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(tmpStr.getBytes());
        tmpStr= byteToStr(digest);
        //tmpStr= DigestUtils.sha1Hex(tmpStr);
        System.out.println("tmpStr="+tmpStr);
        System.out.println("signature="+signature);
        if(tmpStr.equals(signature.toUpperCase())){
            return  true;
        }
        return  false;
    }
    /**
     * 验证
     * @param request
     * @return
     */
    public static boolean zValidate(HttpServletRequest request) throws NoSuchAlgorithmException {
        String token="zjsweixin2014";
        String  signature=request.getParameter("signature");
        String  timestamp =request.getParameter("timestamp");
        String  nonce =request.getParameter("nonce");
        String[] array={token,timestamp,nonce};
        Arrays.sort(array);
        String tmpStr=array[0]+array[1]+array[2];
        MessageDigest md = null;
        md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(tmpStr.getBytes());
        tmpStr= byteToStr(digest);
        //tmpStr= DigestUtils.sha1Hex(tmpStr);
        System.out.println("tmpStr="+tmpStr);
        System.out.println("signature="+signature);
        if(tmpStr.equals(signature.toUpperCase())){
            return  true;
        }
        return  false;
    }
    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

    /**   MsgId------1234567890123456
     *    FromUserName------gh_6c497e959d59
     *    CreateTime------1357290913
     *    Content------helloword
     *    URL------http://mayufeng.ngrok.com/ischool/weixinApi/ischool
     *    ToUserName------AISchool
     *    MsgType------text
     * 解析本来消息
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList){
            map.put(e.getName(), e.getText());
        }
        // 释放资源
        inputStream.close();
        return map;
    }

    /**
     * 获取token
      * @return
     */
   public static AccessToken getAccessToken() throws ClientProtocolException, IOException {
       AccessToken token=new AccessToken();
       String url=WeixinConstant.TOKEN_URL+"?grant_type=client_credential&appid="+WeixinConstant.APP_ID+"&secret="+WeixinConstant.APPSECRET;
       String encoding="UTF-8";
       // 默认的Client类
       HttpClient client = new DefaultHttpClient();
       // 设置为get取连接的方式
       HttpGet get = new HttpGet(url);
       // 得到返回的Response
       HttpResponse response = client.execute(get);
       // 得到返回的Client里面的实体对象信息
       HttpEntity entity = response.getEntity();
       String result="";
       if (entity != null)
       {
           // 得到返回主体内容
           InputStream is = entity.getContent();

           if(response.getStatusLine().getStatusCode()==200){
               try
               {
                   BufferedReader reader = new BufferedReader(
                           new InputStreamReader(is, encoding));
                 result=reader.readLine();
               } catch (Exception e)
               {
                   e.printStackTrace();
               } finally
               {
                   if (is != null) is.close();
               }
           }else {
               response.getStatusLine().toString();
           }
       }
       // 关闭连接
       client.getConnectionManager().shutdown();
       if(!result.contains("err")){
           JSONObject jsonObject= new JSONObject(result);
           String startTime= DateUtil.getDateFormat(new Date(), DateUtil.FORMAT2);
           token.setStartTime(startTime);
           token.setExpiresIn(jsonObject.getInt("expires_in"));
           token.setAccessToken(jsonObject.getString("access_token"));
           recordTokenDate(startTime);
       }
       return  token;
   }
    /**
     * 修改token时间
     * @throws java.io.IOException
     */
    public static void recordTokenDate(String startTime) throws IOException {
        Properties prop = new Properties();// 属性集合对象
        FileInputStream fis =new FileInputStream(PropertiesUtil.class.getResource("/weixin.properties").getFile());// 属性文件输入流
        prop.load(fis);// 将属性文件流装载到Properties对象中
        fis.close();// 关闭流
        prop.setProperty("tokenDate", startTime);
        // 文件输出流
        FileOutputStream fos = new FileOutputStream(PropertiesUtil.class.getResource("/weixin.properties").getPath());
        System.out.print(PropertiesUtil.class.getResource("/weixin.properties").getPath());
        // 将Properties集合保存到流中
        prop.store(fos,"进行替换时间");
        fos.close();// 关闭流
    }
    /**
     * 微信下载文件注意视频文件不能下载
     * @return
     */
    public static String weiXinDownFile(String access_token,String media_id,String fomat) throws IOException {
        String result="";
        String url=WeixinConstant.DOWN_URL+"?access_token="+access_token+"&media_id="+media_id;
        String encoding="UTF-8";
        // 默认的Client类
        HttpClient client = new DefaultHttpClient();
        // 设置为get取连接的方式
        HttpGet get = new HttpGet(url);
        // 得到返回的Response
        HttpResponse response = client.execute(get);
        // 得到返回的Client里面的实体对象信息
        HttpEntity entity = response.getEntity();
        if (entity != null)
        {
            String type= entity.getContentType().getValue();
            // 得到返回主体内容
            if(response.getStatusLine().getStatusCode()==200){
                if(type.contains("image")){ //图片类型
                    InputStream in = entity.getContent();
                    FileOutputStream out=null;
                    try {
                        out = new FileOutputStream(new File("E:\\"+DateUtil.getDateFormat(new Date(),DateUtil.FORMAT2)+"."+fomat));
                        byte[] b = new byte[1024];
                        int len = 0;
                        while((len=in.read(b))!= -1){
                            out.write(b,0,len);
                        }
                        result="true";

                    }catch (IOException e) {
                        e.printStackTrace();
                    }finally{
                        if (in != null) in.close();
                        if (out != null) out.close();
                    }
                }else if(type.contains("voice")){
                    InputStream in = entity.getContent();
                    FileOutputStream out=null;
                    try {
                        out = new FileOutputStream(new File("E:\\"+DateUtil.getDateFormat(new Date(),DateUtil.FORMAT2)+".jpg"));
                        byte[] b = new byte[1024];
                        int len = 0;
                        while((len=in.read(b))!= -1){
                            out.write(b,0,len);
                        }
                        result="true";

                    }catch (IOException e) {
                        e.printStackTrace();
                    }finally{
                        if (in != null) in.close();
                        if (out != null) out.close();
                    }
                }else if(type.contains("text")){ //文本类型
                    // 得到返回主体内容
                    InputStream is = entity.getContent();
                    try
                    {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(is, encoding));
                        result=reader.readLine();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    } finally
                    {
                        if (is != null) is.close();
                    }
                }
            }else {
                System.out.print(response.getStatusLine().toString());
            }
        }
        // 关闭连接
        client.getConnectionManager().shutdown();
        return  result;
    }

    /**
     * 根据url下载
     * @param access_token
     * @param url
     * @return
     * @throws java.io.IOException
     */
    public static String weiXinDownByUrl(String access_token,String url) throws IOException {
        String result="";
        String encoding="UTF-8";
        // 默认的Client类
        HttpClient client = new DefaultHttpClient();
        // 设置为get取连接的方式
        HttpGet get = new HttpGet(url);
        // 得到返回的Response
        HttpResponse response = client.execute(get);
        // 得到返回的Client里面的实体对象信息
        HttpEntity entity = response.getEntity();
        if (entity != null)
        {
            String type= entity.getContentType().getValue();
            // 得到返回主体内容
            if(response.getStatusLine().getStatusCode()==200){
                if(type.contains("image")){ //图片类型
                    InputStream in = entity.getContent();
                    FileOutputStream out=null;
                    try {
                        out = new FileOutputStream(new File("E:\\"+DateUtil.getDateFormat(new Date(),DateUtil.FORMAT2)+".jpg"));
                        byte[] b = new byte[1024];
                        int len = 0;
                        while((len=in.read(b))!= -1){
                            out.write(b,0,len);
                        }
                        result="true";

                    }catch (IOException e) {
                        e.printStackTrace();
                    }finally{
                        if (in != null) in.close();
                        if (out != null) out.close();
                    }
                }
            }else {
                System.out.print(response.getStatusLine().toString());
            }
        }
        // 关闭连接
        client.getConnectionManager().shutdown();
        return  result;
    }

    public static String getOpenId(String code) {
        URLConnection connection;
        InputStream fin = null;
        String openId = "";
        try {

            String appId = PropertiesUtil.getWeixinAppValue("weixin.appId");
            String secret = PropertiesUtil.getWeixinAppValue("weixin.secret");

            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
            connection = new URL(url).openConnection();
            connection.setConnectTimeout(30000);
            connection.connect();

            fin = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(fin, "utf8"));
            StringBuffer buffer = new StringBuffer();
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
            }

            JSONObject jsonObject = new JSONObject(buffer.toString());

            openId =  jsonObject.getString("openid");
        } catch (Exception ex) {

        } finally {
            connection = null;
            if (fin != null) {
                try {
                    fin.close();
                } catch (Exception ex) {
                    // do nothing
                }
            }
        }

        return openId;
    }
}
