package com.zjs.cms.service.account;

import com.zjs.cms.entity.Parent;
import com.zjs.cms.frame.ExtDynamicSpecifications;
import com.zjs.cms.frame.ExtSearchFilter;
import com.zjs.cms.repository.ParentDao;
import com.zjs.cms.repository.ParentStudentDao;
import com.zjs.cms.service.ServiceException;
import com.zjs.cms.utils.ShiroUserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用户管理类.
 * 
 * @author Yuxuan Yang
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class ParentService {

	private static Logger logger = LoggerFactory.getLogger(ParentService.class);

//    @Resource
//	private StudentDao studentDao;
    @Resource
    private ParentDao parentDao;
    @Resource
    private ParentStudentDao parentStudentDao;

    /**
     * 取得家长列表
     * @param filterParams  Map<String, Object>
     * @param pageNumber  int
     * @param pageSize int
     * @param sortType 排序类型
     * @return 家长列表
     */
    public Page<Parent> getAllParents(Map<String, Object> filterParams,
                                         int pageNumber, int pageSize, String sortType) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
        Specification<Parent> spec = buildSpecification(filterParams);

        return parentDao.findAll(spec, pageRequest);
    }

    /**
     *  根据Id取得学生信息
     * @param id  Long
     * @return Student
     */
    public Parent findById(Long id) {
        return parentDao.findOne(id);
    }


    @Transactional
    public void update(Parent user) throws ServiceException {

        try {
            Date currentDate = new Date();


        } catch (Exception ex) {
            logger.error("更新家长信息出错。操作员{}", ShiroUserUtil.getCurrentUser().getName());
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    /**
     * 根据id删除学生信息
     * @param id Long
     */
    @Transactional
    public void delete(Long id) throws ServiceException {
        try {
            Parent parent = parentDao.findOne(id);
            parent.setIsdeleted("Y");
            parentDao.save(parent);
            parentStudentDao.updateFlg(id, "Y");
        } catch (Exception ex) {
            logger.error("删除家长信息出错。操作员{}", ShiroUserUtil.getCurrentUser().getName());
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    /**
     *  删除已选择的学生信息
     * @param ids String
     */
    @Transactional
    public void deleteBySelected(String ids) throws ServiceException {
        if (StringUtils.isNotEmpty(ids)) {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                delete(Long.parseLong(id));
            }
        }
    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(int pageNumber, int pageSize, String sortType) {
        Sort sort = null;
        if ("asc".equals(sortType.split("_")[1])) {
            sort = new Sort(Sort.Direction.ASC, sortType.split("_")[0]);
        } else {
            sort = new Sort(Sort.Direction.DESC, sortType.split("_")[0]);
        }

        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    /**
     * 创建动态查询条件组合.
     */
    private Specification<Parent> buildSpecification(Map<String, Object> filterParams) {
        Map<String, ExtSearchFilter> filters = ExtSearchFilter.parse2(filterParams);
        return ExtDynamicSpecifications.bySearchFilter(filters.values(), Parent.class);
    }
}