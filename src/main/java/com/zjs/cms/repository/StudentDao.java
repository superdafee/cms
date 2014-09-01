/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zjs.cms.repository;

import com.zjs.cms.entity.Student;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentDao extends PagingAndSortingRepository<Student, Long>, JpaSpecificationExecutor<Student> {

}
