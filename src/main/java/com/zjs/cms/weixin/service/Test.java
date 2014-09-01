package com.zjs.cms.weixin.service;

import com.zjs.cms.utils.DateUtil;
import com.zjs.cms.utils.PropertiesUtil;
import com.zjs.cms.weixin.entity.AccessToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;


import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: mayufeng
 * Date: 14-2-11
 * Time: 下午4:22
 * To change this template use File | Settings | File Templates.
 */
public class Test {
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
                System.out.print(response.getStatusLine().toString());
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

    public static String weiXinDownFile(String access_token,String media_id) throws IOException {
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
                        out = new FileOutputStream(new File("E:\\test.jpg"));
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
     *  验证token是否过期
     * @return
     */
    public boolean validateToken(AccessToken token) throws ParseException {
        Date startTime=DateUtil.StringToDate(token.getStartTime(), DateUtil.FORMAT2);
        Date endTime=new Date();
        if(endTime.getTime()-startTime.getTime()>7200000L){
            return  false;
        }
     return  true;
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
        prop.setProperty("tokenDate",startTime);
        // 文件输出流
        FileOutputStream fos = new FileOutputStream(PropertiesUtil.class.getResource("/weixin.properties").getPath());
        System.out.print(PropertiesUtil.class.getResource("/weixin.properties").getPath());
        // 将Properties集合保存到流中
        prop.store(fos,"进行替换时间");
        fos.close();// 关闭流
    }

    /**
     * 上传文件
     * @param path
     * @param uploadType
     * @return
     */
    public static String uploadFile(String path,String uploadType,String access_token) throws IOException {

        String result="";
        String url=WeixinConstant.UPLOAD_URL;
        String encoding="UTF-8";
        HttpClient httpclient = new DefaultHttpClient();
        //请求处理页面
        HttpPost httppost = new HttpPost(url);
        //创建待处理的文件
        FileBody media = new FileBody(new File(path));
        //创建待处理的表单域内容文本
        StringBody type = new StringBody(uploadType);
        StringBody token = new StringBody(access_token);
        //对请求的表单域进行填充
        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("media", media);
        reqEntity.addPart("type", type);
        reqEntity.addPart("access_token",token);
        //设置请求
        httppost.setEntity(reqEntity);
        //执行                     test2
        HttpResponse response = httpclient.execute(httppost);
        //HttpEntity resEntity = response.getEntity();
        //System.out.println(response.getStatusLine());
        if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
            HttpEntity entity = response.getEntity();
            //显示内容
            if (entity != null) {
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
        }
        // 关闭连接
        httpclient.getConnectionManager().shutdown();
        return result;
    }
    public static void main(String[] args) throws IOException {
        // AccessToken token= getAccessToken();
      // System.out.println(token.getAccessToken());
//        String meadio="AwL8e8vVkBxxRhZ3EHRKPjVAIKZx0Haqq_P6aDp1gnBKLN0cvjceyuJqxdeuYUoo";
//        String token="0UErGDMDBBa9atV8Ld0R-v0cVTu4CLh3wEuH9Pk6Sv1tu58U6mdxqkTkTY_Ryq7d1xZIYO_qZVEnfuzoGCEZq46guxTL-X_H_ZL5xm4AV_M3QKxHTRPs1sboI8_cS09VXyxq1dGSAz8w9-lls72yow";
//        String result=weiXinDownFile(token,meadio);
//        //recordTokenDate("20140213100001");
//        //System.out.println(result);
//      // String s= uploadFile("d:/1.jpg","image",token);
//        System.out.println(result);
        String result="";
        String url="http://127.0.0.1:9090/plugins/msgfileupload/fileuploadservlet";
        String encoding="UTF-8";
        HttpClient httpclient = new DefaultHttpClient();
       // httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
        //请求处理页面
        HttpPost httppost = new HttpPost(url);
        //创建待处理的文件
        File file=new File("D:/1/1.txt");
        String fileType = file.getName();
        fileType = fileType.substring(fileType.lastIndexOf('.') + 1);
        FileBody media = new FileBody(file);
        //创建待处理的表单域内容文本
        //对请求的表单域进行填充
        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("media", media);
        reqEntity.addPart("fileType", new StringBody("file",Charset.forName("UTF-8")));
        reqEntity.addPart("fullJID", new StringBody("test1@mayu-pc/Spark 2.6.3",Charset.forName("UTF-8")));
        reqEntity.addPart("roomJID", new StringBody("roo1@conference.mayu-pc",Charset.forName("UTF-8")));
       // reqEntity.addPart("msgXml", new StringBody("<message id=\"ci4tV-52\" to=\"1@conference.admin-pc\" from=\"1@conference.admin-pc/test6\" type=\"groupchat\"><body>test6@admin-pc/Spark 2.6.3</body><x xmlns=\"jabber:x:event\"><offline/><delivered/><displayed/><composing/></x></message>",Charset.forName("UTF-8")));
       // reqEntity.addPart("msgXml", new StringBody("<message id=\"ci4tV-52\" to=\"room1@conference.mayu-pc\" from=\"room1@conference.mayu-pc/test1\" type=\"groupchat\"><body>test1@mayu-pc/Spark 2.6.3</body><x xmlns=\"jabber:x:event\"><offline/><delivered/><displayed/><composing/></x></message>",Charset.forName("UTF-8")));

        //设置请求
        httppost.setEntity(reqEntity);
        //执行
        HttpResponse response = httpclient.execute(httppost);
        //HttpEntity resEntity = response.getEntity();
        //System.out.println(response.getStatusLine());
        if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
            HttpEntity entity = response.getEntity();
            //显示内容
            if (entity != null) {
                InputStream is = entity.getContent();
                try
                {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, encoding));
                    result=reader.readLine();
                    System.out.println(result);
                } catch (Exception e)
                {
                    e.printStackTrace();
                } finally
                {
                    if (is != null) is.close();
                }
            }
        }
        // 关闭连接
        httpclient.getConnectionManager().shutdown();

    }
}
