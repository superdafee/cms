package com.zjs.cms.weixin.controller;

import com.zjs.cms.entity.School;
import com.zjs.cms.service.school.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 微信用户控制器
 * Created by dafee on 2014/9/3.
 */
@Controller
@RequestMapping(value = "/weixin/user")
public class UserController {

    @Resource
    private SchoolService schoolService;

    @RequestMapping("bindAccount/{openid}")
    public String bindAccount(@PathVariable("openid") String openid, HttpServletRequest request) throws Exception{
        request.setAttribute("openid", openid);
        request.setAttribute("schoolList", schoolService.getListByRegion("130102"));//TODO 默认为石家庄长安区 功能扩展时需要调整
        return "weixin/account_bind";
    }

    @RequestMapping("fetchSchool")
    @ResponseBody
    public List<School> ajaxGetSchoolJsonList(HttpServletRequest request) throws Exception {

        return schoolService.getListByRegion(request.getParameter("region"));
    }
}
