/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zjs.cms.web.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Test管理的Controller, 使用Restful风格的Urls:
 *
 * 
 * @author dafee
 */
@Controller
@RequestMapping(value = "/testme")
public class TestController {

//	private static final String PAGE_SIZE = "3";

//	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
//	static {
//		sortTypes.put("auto", "自动");
//		sortTypes.put("title", "标题");
//	}


	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {

		return "test/test";
	}
    @RequestMapping(value = "list1", method = RequestMethod.GET)
    public String list1() {

        return "test/test2";
    }
    @RequestMapping(value = "list3", method = RequestMethod.GET)
    public String list3() {

        return "test/test3";
    }
    @RequestMapping(value = "list2", method = RequestMethod.GET)
    public String list2() {

        return "weixin/hw_main";
    }

}
