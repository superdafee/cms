package com.zjs.cms.utils;

import com.zjs.cms.frame.ExtDynamicSpecifications;
import com.zjs.cms.frame.ExtSearchFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

/**
 * Created by dafee on 2014/9/3.
 */
public class DataUtil {
    /**
     * 创建动态查询条件组合.
     */
    public static <T> Specification<T> buildSpecification(Map<String, Object> filterParams, Class<T> t) {
        Map<String, ExtSearchFilter> filters = ExtSearchFilter.parse2(filterParams);
        return ExtDynamicSpecifications.bySearchFilter(filters.values(), t);
    }
}
