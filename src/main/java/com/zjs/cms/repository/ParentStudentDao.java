/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zjs.cms.repository;

import com.zjs.cms.entity.ParentStudentCon;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.SQLException;

public interface ParentStudentDao extends PagingAndSortingRepository<ParentStudentCon, Long>, JpaSpecificationExecutor<ParentStudentCon> {

    @Modifying
    @Query("update ParentStudentCon p set p.isdeleted=?2 where p.student.id=?1")
    void updateFlg(Long id, String flg);

    @Query("select p from ParentStudentCon p where p.parent.openid=?1")
    public ParentStudentCon queryByOpenid(String openid) throws SQLException;
}
