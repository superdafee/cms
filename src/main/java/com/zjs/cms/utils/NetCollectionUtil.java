package com.zjs.cms.utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2014/7/25.
 */
public class NetCollectionUtil {
    public static void doCollect(int pageno) throws IOException {

        //pageno最大值为3526

        URL url = new URL("http://v.ecnupress.com.cn/mvp/v.do?id="+pageno);
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line ;
        boolean h1 = true;
        boolean h2 = true;

        String regex;
        Pattern pData;
        Matcher mData;

        StringBuffer filepath = new StringBuffer("D:\\NET_COURSE\\");
        String course = null;
        File file;
        File html;
        while ((line = reader.readLine())!=null){
//            System.out.println(line);
            if(line.indexOf("h1")>0 && h1){
                regex = "<h1>(.*?)</h1>";
                pData = Pattern.compile(regex);
                mData = pData.matcher(line);
                while (mData.find()){
                    course = mData.group(1);
                    System.out.println(course);
                    if(course==null||course.equals("")){
                        break;
                    }
                    filepath.append(course+"\\");
                }
                h1 = false;
                continue;
            }
            if(line.indexOf("h2")>0 && h2){

                regex = "<h2>(.*?)</h2>";
                pData = Pattern.compile(regex);
                mData = pData.matcher(line);
                while (mData.find()){
                    course = mData.group(1);
                    System.out.println(course);
                    String[] subcourse = course.split("\\|");
                    filepath.append(subcourse[1]+"\\"+subcourse[0]+"\\");
                }
                h2 = false;
                continue;
            }
            if(line.indexOf("source")>0){
                regex = "<source src=\"(.*?)\"";
                pData = Pattern.compile(regex);
                mData = pData.matcher(line);
                while (mData.find()){
                    course = mData.group(1);
                    System.out.println(course);
                }
                break;
            }
        }
        System.out.println(filepath.toString());
        file = new File(filepath.toString());
        if(!file.exists()){
            file.mkdirs();
        }
        if(course!=null && !course.equals("/resource/00000000-0000-0000-0000-000000000000.mp4")){
            html = new File(filepath.toString()+course.substring(course.lastIndexOf("/")+1, course.length()-4)+".html");
            if(!html.exists()){
                html.createNewFile();
                FileOutputStream fos = new FileOutputStream(html);
                StringBuffer sb = new StringBuffer();
                sb.append("<html>");
                sb.append("<section id=\"content\" class=\"body\"> ");
                sb.append("<video id=\"videoplay\" class=\"video-js vjs-default-skin\" ");
                sb.append("controls preload=\"auto\" width=\"100%\" height=\"100%\" ");
                sb.append("poster=\"\" ");
                sb.append("data-setup='{\"example_option\":true}'> ");
                sb.append("<source src=\"http://v.ecnupress.com.cn"+course+"\" type=\"video/mp4\"></source> ");
                sb.append("</video> ");
                sb.append("</section><!-- /#content --> ");
                sb.append("</html> ");
                fos.write(sb.toString().getBytes());
                fos.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        for(int i=1; i<= 3526; i++){
            doCollect(i);
        }
//        System.out.println("/resource/00000000-0000-0000-0000-000000000000.mp4"
//                .substring("/resource/00000000-0000-0000-0000-000000000000.mp4".lastIndexOf("/")+1,
//                        "/resource/00000000-0000-0000-0000-000000000000.mp4".length()-4));
    }
}
