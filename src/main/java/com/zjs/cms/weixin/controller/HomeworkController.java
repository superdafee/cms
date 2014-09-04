package com.zjs.cms.weixin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2014/9/4.
 */
@Controller
@RequestMapping(value = "/weixin/hw")
public class HomeworkController {

    @RequestMapping("list/{course}")
    public String list(@PathVariable("course") int course, HttpServletRequest request) throws Exception {
        request.setAttribute("course", course);
        return "weixin/hw";
    }

    @RequestMapping("view/{course}")
    public String view(@PathVariable("course") int course, HttpServletRequest request) throws Exception {
        request.setAttribute("course", course);
        return "weixin/hw_view";
    }

    @RequestMapping("video/{item}")
    public String video(@PathVariable("item") int item, HttpServletRequest request) throws Exception {
        String vid = null;
        String name = null;
        switch (item) {
            case 1 :
                vid = "m0136pcewsf";
                name="榨油问题";
                break;
            case 2 :
                vid = "n0136sfre5o";
                name="两桶水";
                break;
            case 3 :
                vid = "y0136abpba9";
                name="裁衣服";
                break;
            case 4 :
                vid = "u01362x0yjs";
                name="修改病句";
                break;
            case 5 :
                vid = "n0136gjsygn";
                name="卜算子++咏梅++（毛泽东）";
                break;
            case 6 :
                vid = "e01360vcu82";
                name="《哲学家的最后一课》";

        }
        request.setAttribute("vid", vid);
        request.setAttribute("name", name);
        return "weixin/hw_video";
    }

    @RequestMapping("practice/{id}")
    public String practice(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        String qname = null;
        switch (id){
            case 1 : qname = "问题一";break;
            case 2 : qname = "问题二";break;
            case 3 : qname = "问题三";break;
        }
        request.setAttribute("qname", qname);
        return "weixin/hw_practice";
    }
}
