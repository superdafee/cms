package com.zjs.cms.repository;

import com.zjs.cms.entity.School;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by dafee on 2014/9/3.
 */
public interface SchoolDao extends PagingAndSortingRepository<School, Long>, JpaSpecificationExecutor<School> {

}
