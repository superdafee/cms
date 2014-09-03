package com.zjs.cms.service.school;

import com.zjs.cms.entity.School;
import com.zjs.cms.entity.Student;
import com.zjs.cms.frame.ExtDynamicSpecifications;
import com.zjs.cms.frame.ExtSearchFilter;
import com.zjs.cms.repository.SchoolDao;
import com.zjs.cms.utils.DataUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dafee on 2014/9/3.
 */
@Component
@Transactional(readOnly = true)
public class SchoolService {

    @Resource
    private SchoolDao schoolDao;

    public List<School> getListByRegion(String regionCode) throws Exception {

        Map<String, Object> pmap = new HashMap<String, Object>();
        pmap.put("EQ_region", regionCode);

        return schoolDao.findAll(buildSpecification(pmap));
    }

    /**
     * 创建动态查询条件组合.
     */
    private Specification<School> buildSpecification(Map<String, Object> filterParams) {
        Map<String, ExtSearchFilter> filters = ExtSearchFilter.parse2(filterParams);
        return ExtDynamicSpecifications.bySearchFilter(filters.values(), School.class);
    }
}
