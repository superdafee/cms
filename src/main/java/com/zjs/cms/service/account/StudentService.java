package com.zjs.cms.service.account;

import com.zjs.cms.entity.Parent;
import com.zjs.cms.entity.ParentStudentCon;
import com.zjs.cms.entity.Student;
import com.zjs.cms.frame.ExtDynamicSpecifications;
import com.zjs.cms.frame.ExtSearchFilter;
import com.zjs.cms.repository.ParentDao;
import com.zjs.cms.repository.ParentStudentDao;
import com.zjs.cms.repository.StudentDao;
import com.zjs.cms.service.ServiceException;
import com.zjs.cms.utils.RegexValidatorUtil;
import com.zjs.cms.utils.ShiroUserUtil;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
public class StudentService {

	private static Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Resource
	private StudentDao studentDao;
    @Resource
    private ParentDao parentDao;
    @Resource
    private ParentStudentDao parentStudentDao;

    /**
     * 批量查找学生
     * @param ids
     * @return
     */
    public List<Student> findByIdArray(List<Long> ids) {
        List<Student> list = null;
        try {
            if(ids!=null && ids.size()>0){
                list = (List<Student>) studentDao.findAll(ids);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
  }

    /**
     * 取得学生列表
     * @param filterParams  Map<String, Object>
     * @param pageNumber  int
     * @param pageSize int
     * @param sortType 排序类型
     * @return 系统管理员列表
     */
    public Page<Student> getAllStudents(Map<String, Object> filterParams,
                                         int pageNumber, int pageSize, String sortType) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
        Specification<Student> spec = buildSpecification(filterParams);

        return studentDao.findAll(spec, pageRequest);
    }

    /**
     *  根据Id取得学生信息
     * @param id  Long
     * @return Student
     */
    public Student findById(Long id) {
        return studentDao.findOne(id);
    }

    /**
     * 注册学生信息
     * @param user Student
     */
    @Transactional
    public void add(Student user, Map<String, String> dtoMap) throws ServiceException {

        try {
            Date currentDate = new Date();
            user.setCreatetime(currentDate);
            user.setIsdeleted("N");
            user = studentDao.save(user);

            // 家长信息
            if (dtoMap != null && StringUtils.isNotEmpty(dtoMap.get("parentsName"))) {

                Parent parent = new Parent();
                parent.setCreatetime(currentDate);
                parent.setIsdeleted("N");
                parent.setMobile(dtoMap.get("parentMobile"));
                parent.setRealname(dtoMap.get("parentsName"));
                parent.setPhone(dtoMap.get("parentPhone"));

                parent = parentDao.save(parent);

                ParentStudentCon parentStudentCon = new ParentStudentCon();
                parentStudentCon.setIsdeleted("N");
                parentStudentCon.setCreatetime(currentDate);
                parentStudentCon.setParent(parent);
                parentStudentCon.setStudent(user);
                parentStudentCon.setRelation(Integer.parseInt(dtoMap.get("relation")));

                parentStudentDao.save(parentStudentCon);
            }

        } catch (Exception ex) {
            logger.error("注册学生信息出错。操作员{}", ShiroUserUtil.getCurrentUser().getName());
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Transactional
    public void update(Student user, Map<String, String> dtoMap, String parentStudentId) throws ServiceException {

        try {
            Date currentDate = new Date();
            // 家长信息
            if (StringUtils.isNotEmpty(parentStudentId)) {

                ParentStudentCon parentStudentCon = parentStudentDao.findOne(Long.parseLong(parentStudentId));
                Parent parent = parentStudentCon.getParent();

                if (!dtoMap.isEmpty() && StringUtils.isNotEmpty(dtoMap.get("parentsName"))) {
                    parent.setMobile(dtoMap.get("parentMobile"));
                    parent.setRealname(dtoMap.get("parentsName"));
                    parent.setPhone(dtoMap.get("parentPhone"));

                    parentDao.save(parent);

                    parentStudentCon.setRelation(Integer.parseInt(dtoMap.get("relation")));
                    parentStudentDao.save(parentStudentCon);
                } else {
                    parent.setIsdeleted("Y");
                    parentDao.save(parent);

                    parentStudentCon.setIsdeleted("Y");
                    parentStudentDao.save(parentStudentCon);
                }

            } else if (StringUtils.isEmpty(parentStudentId)) {
                Parent parent = new Parent();
                parent.setCreatetime(currentDate);
                parent.setIsdeleted("N");
                parent.setMobile(dtoMap.get("parentMobile"));
                parent.setRealname(dtoMap.get("parentsName"));
                parent.setPhone(dtoMap.get("parentPhone"));

                parent = parentDao.save(parent);

                ParentStudentCon parentStudentCon = new ParentStudentCon();
                parentStudentCon.setIsdeleted("N");
                parentStudentCon.setCreatetime(currentDate);
                parentStudentCon.setParent(parent);
                parentStudentCon.setStudent(user);
                parentStudentCon.setRelation(Integer.parseInt(dtoMap.get("relation")));

                parentStudentDao.save(parentStudentCon);
            }

            studentDao.save(user);


        } catch (Exception ex) {
            logger.error("更新学生信息出错。操作员{}", ShiroUserUtil.getCurrentUser().getName());
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
            Student student = studentDao.findOne(id);
            student.setIsdeleted("Y");
            studentDao.save(student);
            parentStudentDao.updateFlg(id, "Y");
        } catch (Exception ex) {
            logger.error("删除学生信息出错。操作员{}", ShiroUserUtil.getCurrentUser().getName());
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
     * 批量保存上传来的学生信息
     * @param fileData CommonsMultipartFile
     * @return 上传结果
     */
    @Transactional
    public String upload(MultipartFile fileData) {
        StringBuilder errMess = new StringBuilder();

        Date currentDate = new Date();
        if (!fileData.isEmpty()) {
            try {
                Workbook wb = Workbook.getWorkbook(fileData.getInputStream());
                Sheet sheet = wb.getSheet(0);
                for (int i = 1; i < sheet.getRows(); i++) {
                    // 学生信息
                    Student student = new Student();

                    if ( !RegexValidatorUtil.isName(sheet.getCell(0, i).getContents())){
                        errMess.append("第").append(i+1).append("行记录学生姓名只能是中文或英文字母,且不能为空！");
                        continue;
                    } else if (sheet.getCell(0, i).getContents().length() > 20) {
                        errMess.append("第").append(i+1).append("行记录学生姓名长度不能超过20！");
                        continue;
                    }
                    student.setRealname(sheet.getCell(0, i).getContents());

                    if (StringUtils.isEmpty(sheet.getCell(1, i).getContents())) {
                        errMess.append("第").append(i+1).append("行记录年级只能是1-12的整数，且不能为空！");
                        continue;
                    } else if (!RegexValidatorUtil.isNumber(sheet.getCell(1, i).getContents())) {
                        errMess.append("第").append(i+1).append("行记录年级只能是1-12的整数，且不能为空！");
                        continue;
                    } else if (Integer.parseInt(sheet.getCell(1, i).getContents()) < 0 ||
                            Integer.parseInt(sheet.getCell(1, i).getContents()) > 12) {
                        errMess.append("第").append(i+1).append("行记录年级只能是1-12的整数，且不能为空！");
                        continue;
                    }
                    student.setGrade(Integer.parseInt(sheet.getCell(1, i).getContents()));

                    if (StringUtils.isEmpty(sheet.getCell(2, i).getContents())) {
                        errMess.append("第").append(i+1).append("行记录所属班级名不能为空！");
                        continue;
                    } else if (sheet.getCell(2, i).getContents().length() > 20) {
                        errMess.append("第").append(i+1).append("行记录所属班级名长度不能超过20！");
                        continue;
                    }
                    student.setClassname(sheet.getCell(0, i).getContents());

                    student.setIsdeleted("N");
                    student.setCreatetime(currentDate);

                    // 家长信息
                    Parent parents = new Parent();
                    String parentName = sheet.getCell(3, i).getContents();
                    String mobile = sheet.getCell(5, i).getContents();
                    String phone = sheet.getCell(6, i).getContents();
                    String relation = sheet.getCell(4, i).getContents();

                    if (StringUtils.isEmpty(parentName) && (
                            StringUtils.isNotEmpty(mobile) || StringUtils.isNotEmpty(phone))) {

                        if ( !RegexValidatorUtil.isName(parentName)){
                            errMess.append("第").append(i+1).append("行记录家长姓名只能是中文或英文字母,且不能为空！");
                            continue;
                        } else if (parentName.length() > 20) {
                            errMess.append("第").append(i+1).append("行记录家长姓名长度不能超过20！");
                            continue;
                        }
                    }

                    if (StringUtils.isEmpty(mobile) && (
                            StringUtils.isNotEmpty(parentName) || StringUtils.isNotEmpty(phone))) {

                        if (StringUtils.isEmpty(mobile)) {
                            errMess.append("第").append(i+1).append("行记录请输入格式正确的手机号码！");
                            continue;
                        } else if (StringUtils.isNotEmpty(mobile) && !RegexValidatorUtil.isMobile(mobile)) {
                            errMess.append("第").append(i+1).append("行记录请输入格式正确的手机号码！");
                            continue;
                        }
                    }

                    if (StringUtils.isEmpty(phone) && (
                            StringUtils.isNotEmpty(mobile) || StringUtils.isNotEmpty(parentName))) {

                        if (StringUtils.isEmpty(phone)) {
                            errMess.append("第").append(i+1).append("行记录请输入格式正确的其它电话号码！");
                            continue;
                        } else if (StringUtils.isNotEmpty(phone) && !RegexValidatorUtil.isMobile(phone)) {
                            errMess.append("第").append(i+1).append("行记录请输入格式正确的其它电话号码！");
                            continue;
                        }
                    }

                    if (StringUtils.isEmpty(mobile) && StringUtils.isEmpty(parentName) && StringUtils.isEmpty(phone)
                            && StringUtils.isNotEmpty(relation)) {
                        errMess.append("第").append(i+1).append("行记录请输入家长姓名、手机号以及其他电话号码！");
                        continue;
                    }

                    if (StringUtils.isNotEmpty(mobile) && StringUtils.isNotEmpty(parentName) && StringUtils.isNotEmpty(phone)) {
                        if (StringUtils.isEmpty(relation)) {
                            errMess.append("第").append(i+1).append("行记录请输入关系！");
                            continue;
                        } else if (!getRelationMap().containsKey(relation)) {
                            errMess.append("第").append(i+1).append("行记录请输入正确的关系！");
                            continue;
                        }
                    }

                    try {

                        student = studentDao.save(student);

                        if (StringUtils.isNotEmpty(mobile)) {
                            parents.setRealname(parentName);
                            parents.setMobile(mobile);
                            parents.setPhone(phone);
                            parents.setIsdeleted("N");
                            parents.setCreatetime(currentDate);
                            parents = parentDao.save(parents);

                            ParentStudentCon parentStudentCon = new ParentStudentCon();
                            parentStudentCon.setCreatetime(currentDate);
                            parentStudentCon.setRelation(getRelationMap().get(relation));
                            parentStudentCon.setStudent(student);
                            parentStudentCon.setParent(parents);
                            parentStudentCon.setIsdeleted("N");

                            parentStudentDao.save(parentStudentCon);
                        }

                    } catch (Exception e) {
                        logger.error("添加学生信息出错。操作员{}", ShiroUserUtil.getCurrentUser().getName());
                        errMess.append("第").append(i+1).append("行记录：新增失败！");
                    }
                }
            } catch (Exception ex) {
                errMess.append("学生数据导入发生未知错误，请稍后再试或联系管理员。错误信息：").append(ex.getMessage());
                logger.error("学生数据导入发生未知错误! 操作员{}", ShiroUserUtil.getCurrentUser().getName());
                logger.error(ex.getMessage(), ex);
            }
        }

        return errMess.toString();
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
    private Specification<Student> buildSpecification(Map<String, Object> filterParams) {
        Map<String, ExtSearchFilter> filters = ExtSearchFilter.parse2(filterParams);
        return ExtDynamicSpecifications.bySearchFilter(filters.values(), Student.class);
    }

    private Map<String, Integer> getRelationMap() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("父亲", 1);
        map.put("母亲", 2);
        map.put("祖父", 3);
        map.put("祖母", 4);
        map.put("外祖父", 5);
        map.put("外祖母", 6);
        map.put("其他", 7);

        return map;
    }
}