package com.zjs.cms.frame;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

/**
 * Modify SearchFilter in SpringSide4 to support SQL Clause as follows.
 * IN, IS NULL, IS NOT NULL
 *
 * @author Yuxuan Yang
 */
public class ExtSearchFilter {

    public enum Operator {
        EQ, LIKE, GT, LT, GTE, LTE, IN, ISNULL, ISNOTNULL, OR, BETWEEN, NEQ,ORLIKE
    }

    public String fieldName;
    public Object value;
    public Operator operator;

    public List<?> values = null;

    public List<?> getValues() {
        return values;
    }

    public void setValues(List<?> values) {
        this.values = values;
    }

    public ExtSearchFilter(String fieldName, Operator operator) {
        this.fieldName = fieldName;
        this.operator = operator;
    }

    public ExtSearchFilter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    public static Map<String, ExtSearchFilter> parse(Map<String, Object> searchParams) {
        Map<String, ExtSearchFilter> filters = Maps.newHashMap();

        for (Entry<String, Object> entry : searchParams.entrySet()) {
            // 过滤掉空值
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            String[] names = StringUtils.split(entry.getKey(), "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(entry.getKey() + " is not a valid search filter name");
            }
            ExtSearchFilter filter = new ExtSearchFilter(names[1], Operator.valueOf(names[0]), value);
            if (value instanceof List) {
                filter.setValues((List) value);
            }
            filters.put(filter.fieldName, filter);
        }

        return filters;
    }

    /**
     * 返回可重复key值的map 用于时间搜索
     *
     * @param searchParams
     * @return
     */
    public static IdentityHashMap<String, ExtSearchFilter> parse2(Map<String, Object> searchParams) {
        IdentityHashMap<String, ExtSearchFilter> filters = Maps.newIdentityHashMap();

        for (Entry<String, Object> entry : searchParams.entrySet()) {
            // 过滤掉空值
            Object value = entry.getValue();
            if (StringUtils.isBlank((String) value)) {
                continue;
            }
            String[] names = StringUtils.split(entry.getKey(), "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(entry.getKey() + " is not a valid search filter name");
            }
            ExtSearchFilter filter = new ExtSearchFilter(names[1], Operator.valueOf(names[0]), value);
            if (value instanceof List) {
                filter.setValues((List) value);
            }
            filters.put(filter.fieldName, filter);
        }

        return filters;
    }

}
