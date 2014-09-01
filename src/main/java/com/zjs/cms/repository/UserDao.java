/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zjs.cms.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.zjs.cms.entity.User;

import java.util.List;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	User findByLoginName(String loginName);

    @Query("select p from User p where p.openid=?1 ")
    User findByOpenId(String openId);
}
