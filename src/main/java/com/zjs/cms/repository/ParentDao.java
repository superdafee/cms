/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zjs.cms.repository;

import com.zjs.cms.entity.Parent;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ParentDao extends PagingAndSortingRepository<Parent, Long>, JpaSpecificationExecutor<Parent> {

    @Query("select p from Parent p where p.openid=?1")
    public Parent queryByOpenid(String openid);
}
