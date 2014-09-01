package com.zjs.cms.frame;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.zjs.cms.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springside.modules.utils.Collections3;

import com.google.common.collect.Lists;

/**
 * Modify DynamicSpecifications in SpringSide4 to support SQL Clause as follows.
 * IN, IS NULL, IS NOT NULL
 *
 * @author Yuxuan Yang
 */
public class ExtDynamicSpecifications {
    private static final ConversionService conversionService = new DefaultConversionService();

    public static <T> Specification<T> bySearchFilter(final Collection<ExtSearchFilter> filters, final Class<T> clazz) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (Collections3.isNotEmpty(filters)) {

                    List<Predicate> predicates = Lists.newArrayList();
                    List<Predicate> predicateOr = new ArrayList<Predicate>();
                    ;
                    for (ExtSearchFilter filter : filters) {
                        // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
                        String[] names = StringUtils.split(filter.fieldName, ".");
                        Path expression = root.get(names[0]);
                        for (int i = 1; i < names.length; i++) {
                            expression = expression.get(names[i]);
                        }

                        // convert value from string to target type
                        Class attributeClass = expression.getJavaType();
                        if (!ExtSearchFilter.Operator.IN.equals(filter.operator)) {
                            if (!attributeClass.equals(String.class) && filter.value instanceof String
                                    && conversionService.canConvert(String.class, attributeClass)) {
                                if (attributeClass.equals(Date.class) || attributeClass.equals(Timestamp.class)) {
                                    try {
                                        filter.value = DateUtil.StringToDate(filter.value.toString(), DateUtil.FORMAT);
                                    } catch (ParseException e) {
                                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                    }
                                }
                                filter.value = conversionService.convert(filter.value, attributeClass);

                            }
                        }

                        // logic operator
                        switch (filter.operator) {
                            case EQ:
                                predicates.add(builder.equal(expression, filter.value));
                                break;
                            case NEQ:
                                predicates.add(builder.notEqual(expression, filter.value));
                                break;
                            case LIKE:
                                predicates.add(builder.like(expression, "%" + filter.value + "%"));
                                break;
                            case GT:
                                predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
                                break;
                            case LT:
                                predicates.add(builder.lessThan(expression, (Comparable) filter.value));
                                break;
                            case GTE:
                                predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case LTE:
                                predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case IN:
                                Predicate predicate = expression.in(filter.values);
                                predicates.add(predicate);
                                break;
                            case ISNULL:
                                predicates.add(builder.isNull(expression));
                                break;
                            case ISNOTNULL:
                                predicates.add(builder.isNotNull(expression));
                                break;
                            case OR:
                                //不知道啥原因必须先收集or的最后add进去predicates,否则还是and
                               predicateOr.add(builder.equal(expression, filter.value));
                                //predicates.add(builder.or(expression.isNotNull(),,builder.equal(expression, filter.value)));
                                break;
                            case ORLIKE:
                                //不知道啥原因必须先收集or的最后add进去predicates,否则还是and
//                                predicateOr.add(builder.equal(expression, filter.value));
                                predicateOr.add(builder.like(expression, "%" + filter.value + "%"));
                                //predicates.add(builder.or(expression.isNotNull(),,builder.equal(expression, filter.value)));
                                break;
                        }
                    }
                    //or关联
                    if (predicateOr != null && predicateOr.size() > 0) {
                        int paraNum = predicateOr.size();
                        Predicate[] orArray = new Predicate[paraNum];
                        for (int i = 0; i < paraNum; i++) {
                            orArray[i] = predicateOr.get(i);
                        }
                        predicates.add(builder.or(orArray));
                    }
                    // 将所有条件用 and 联合起来
                    if (predicates.size() > 0) {
                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }

                return builder.conjunction();
            }
        };
    }
}
