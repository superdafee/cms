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

    // 考虑到通过学生批量上传时，家长的频繁查找，设定该缓存
    private Map<String, Parent> parentsMap = Collections.synchronizedMap(new HashMap<String, Parent>());
    private Map<String, String> studentsMap = Collections.synchronizedMap(new HashMap<String, String>());

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

//    /**
//     *  更新登录信息（登录时间）
//     * @param userId Long
//     */
//    @Transactional
//    public void updateLoginInfo(Long userId) {
//
//        Student user = studentDao.findOne(userId);
//
//        // 插入登录信息
//        user.setLastLoginTime(dateProvider.getDate());
//        studentDao.save(user);
//    }
//
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

//        if (!fileData.isEmpty()) {
//            try {
//                Workbook wb = Workbook.getWorkbook(fileData.getInputStream());
//                Sheet sheet = wb.getSheet(0);
//                for (int i = 1; i < sheet.getRows(); i++) {
//                    // 学生信息
//                    Student student = new Student();
//                    if (StringUtils.isEmpty(sheet.getCell(0, i).getContents())) {
//                        errMess.append("第").append(i+1).append("行记录请输入格式正确的学生姓名！");
//                        continue;
//                    } else {
//                        if (studentsMap.containsKey(sheet.getCell(0, i).getContents())) {
//                            // 相同记录存在的前提下，忽略后面的
//                            errMess.append("第").append(i+1).append("行记录学号已经存在！");
//                            continue;
//                        } else {
//
//                            if (studentDao.findByUsernameAndSchoolId(sheet.getCell(0, i).getContents(), school.getId()) != null) {
//                                errMess.append("第").append(i+1).append("行记录学号已经存在！");
//                                continue;
//                            }
//                        }
//                    }
//
//                    student.setUsername(sheet.getCell(0, i).getContents());
//                    if ( !RegexValidatorUtil.isName(sheet.getCell(1, i).getContents())){
//                        errMess.append("第").append(i+1).append("行记录姓名只能是中文或英文字母,且不能为空！");
//                        continue;
//                    }
//                    student.setRealname(sheet.getCell(1, i).getContents());
//                    if (!"0".equals(sheet.getCell(2, i).getContents()) && !"1".equals(sheet.getCell(2, i).getContents())) {
//                        errMess.append("第").append(i+1).append("行记录性别只能是0或1，且不能为空！");
//                        continue;
//                    }
//                    student.setGender(sheet.getCell(2, i).getContents());
//                    if (sheet.getCell(3, i) != null && StringUtils.isNotBlank(sheet.getCell(3, i).getContents())) {
//                        if ( !RegexValidatorUtil.isBirthday(sheet.getCell(3, i).getContents(), "yyyy-MM-dd")){
//                            errMess.append("第").append(i+1).append("行记录请输入格式正确的出生日期！");
//                            continue;
//                        }
//                    }
//
//                    if (sheet.getCell(4, i) != null && StringUtils.isNotBlank(sheet.getCell(4, i).getContents())) {
//                        if ( !RegexValidatorUtil.isBirthday(sheet.getCell(4, i).getContents(), "yyyy-MM-dd")){
//                            errMess.append("第").append(i+1).append("行记录请输入格式正确的入学日期！");
//                            continue;
//                        }
//                    }
//
//                    if (DateUtil.StringToDate(sheet.getCell(3, i).getContents(), DateUtil.FORMAT).getTime()
//                        >= DateUtil.StringToDate(sheet.getCell(4, i).getContents(), DateUtil.FORMAT).getTime()) {
//                        errMess.append("第").append(i+1).append("行记录出生日期不得大于入学日期！");
//                        continue;
//                    }
//
//                    student.setBirthdayStr(sheet.getCell(3, i).getContents());
//                    student.setEnrollTimeStr(sheet.getCell(4, i).getContents());
//
//                    String failgoupNum = sheet.getCell(5, i).getContents();
//                    if (StringUtils.isEmpty(failgoupNum)) {
//                        errMess.append("第").append(i+1).append("行记录留级次数只能输入0~3之间的整数，且不能为空！");
//                        continue;
//                    } else if (StringUtils.isNotEmpty(failgoupNum) && !RegexValidatorUtil.isNumber(failgoupNum)) {
//                        errMess.append("第").append(i+1).append("行记录留级次数只能输入0~3之间的整数，且不能为空！");
//                        continue;
//                    } else if (Integer.parseInt(failgoupNum) > 3) {
//                        errMess.append("第").append(i+1).append("行记录留级次数只能输入0~3之间的整数，且不能为空！");
//                        continue;
//                    }
//                    student.setFailgoupNum(Integer.parseInt(failgoupNum));
//
//                    SClass sClass = null;
//                    // 班级信息
//                    String className = sheet.getCell(6, i).getContents();
//                    String startYear = sheet.getCell(7, i).getContents();
//                    String phase = sheet.getCell(8, i).getContents();
//
//                    if (StringUtils.isEmpty(className)) {
//                        errMess.append("第").append(i+1).append("行记录所属班级名不能为空！");
//                        continue;
//                    }
//                    if (StringUtils.isEmpty(startYear)) {
//                        errMess.append("第").append(i+1).append("行记录入学年份/级格式只能是大于2000的整数，且不能为空！");
//                        continue;
//                    } else if (!RegexValidatorUtil.isNumber(startYear)) {
//                        errMess.append("第").append(i+1).append("行记录入学年份/级格式只能是大于2000的整数，且不能为空！");
//                        continue;
//                    } else if (startYear.compareTo("2000") < 0) {
//                        errMess.append("第").append(i+1).append("行记录入学年份/级格式只能是大于2000的整数，且不能为空！");
//                        continue;
//                    }
//                    if (StringUtils.isEmpty(phase)) {
//                        errMess.append("第").append(i+1).append("行记录阶段格式只能是“小学或初中”，且不能为空！");
//                        continue;
//                    } else if (!phaseType.containsKey(phase)) {
//                        errMess.append("第").append(i+1).append("行记录阶段格式只能是“小学或初中”，且不能为空！");
//                        continue;
//                    } else {
//                        String inputStr = phaseType.get(phase);
//                        if (!phaseList.contains(inputStr)) {
//                            errMess.append("第").append(i+1).append("行记录该学校没有").append(phase).append("阶段！");
//                            continue;
//                        } else {
//                            sClass = classDao.findClassByInfo(inputStr, startYear, className, school.getId());
//                            if (sClass == null) {
//                                errMess.append("第").append(i+1).append("行记录该学校没有该班级信息，请先注册相关班级信息！");
//                                continue;
//                            }
//                        }
//                    }
//
//                    String mac = sheet.getCell(9, i).getContents();
//                    String pn = sheet.getCell(10, i).getContents();
//                    if (StringUtils.isEmpty(mac)) {
//                        errMess.append("第").append(i+1).append("行记录答题器MAC地址只能填写字母（A-F）、数字和横线，且不能为空！");
//                        continue;
//                    } else if (StringUtils.isNotEmpty(mac) && !RegexValidatorUtil.isMac(mac)) {
//                        errMess.append("第").append(i+1).append("行记录答题器MAC地址只能填写字母（A-F）、数字和横线，且不能为空！");
//                        continue;
//                    } else {
//                        String result = findByMac(mac, null);
//                        if ("false".equals(result)) {
//                            errMess.append("第").append(i+1).append("行记录答题器MAC地址已存在！");
//                            continue;
//                        }
//                    }
//                    student.setMac(mac);
//
//                    if (StringUtils.isNotEmpty(pn) && pn.length() > 20) {
//                        errMess.append("第").append(i+1).append("行记录高拍仪设备编号最大长度不得超过20位！");
//                        continue;
//                    }
//                    student.setPn(pn);
//
//                    // 家长信息
//                    Parents parents = new Parents();
//                    String parentName = sheet.getCell(11, i).getContents();
//                    String parentGender = sheet.getCell(12, i).getContents();
//                    String relation = sheet.getCell(13, i).getContents();
//                    String mobilePhone = sheet.getCell(14, i).getContents();
//
////                    if (StringUtils.isEmpty(parentName) && (
////                            StringUtils.isNotEmpty(relation) || StringUtils.isNotEmpty(mobilePhone))) {
//                    if (StringUtils.isEmpty(parentName)) {
//                        errMess.append("第").append(i+1).append("行记录家长姓名只能是中文或英文字母，且不能为空！");
//                        continue;
//                    } else if (StringUtils.isNotEmpty(parentName) && !RegexValidatorUtil.isName(parentName)) {
//                        errMess.append("第").append(i+1).append("行记录家长姓名只能是中文或英文字母，且不能为空！");
//                        continue;
//                    }
//
//                    if (StringUtils.isEmpty(parentGender)) {
//                        errMess.append("第").append(i+1).append("行记录家长性别只能是M或F，且不能为空！");
//                        continue;
//                    } else if (StringUtils.isNotEmpty(parentGender) && (!"M".equals(parentGender)
//                             && !"F".equals(parentGender))) {
//                        errMess.append("第").append(i+1).append("行记录家长性别只能是M或F，且不能为空！");
//                        continue;
//                    }
//
////                    if (StringUtils.isEmpty(relation) && (
////                            StringUtils.isNotEmpty(parentName) || StringUtils.isNotEmpty(mobilePhone))) {
//                    if (StringUtils.isEmpty(relation)) {
//                        errMess.append("第").append(i+1).append("行记录关系不能为空！");
//                        continue;
//                    }
//
//                    Parents record = null;
//                    boolean parentIsExist = false;
////                    if (StringUtils.isEmpty(mobilePhone) && (StringUtils.isNotEmpty(parentName)
////                            || StringUtils.isNotEmpty(relation))) {
//                    if (StringUtils.isEmpty(mobilePhone)) {
//                        errMess.append("第").append(i+1).append("行记录请输入格式正确的手机号码！");
//                        continue;
//                    } else if (StringUtils.isNotEmpty(mobilePhone) && !RegexValidatorUtil.isMobile(mobilePhone)) {
//                        errMess.append("第").append(i+1).append("行记录请输入格式正确的手机号码！");
//                        continue;
//                    }
////                    else if (StringUtils.isNotEmpty(mobilePhone) && StringUtils.isNotEmpty(parentName)) {
////                        if (parentsMap.containsKey(mobilePhone + parentName)) {
////                            record = parentsMap.get(mobilePhone + parentName);
////                            parentIsExist = true;
////                        } else {
////
////                            record = parentsDao.findByNameAndMobile(parentName, mobilePhone);
////
////                            if (record == null && parentsDao.findByPhone(mobilePhone) != null) {
////                                errMess.append("第").append(i+1).append("行记录系统已存在相同的手机号码！");
////                                continue;
////                            } else if (record != null) {
////                                parentIsExist = true;
////                            }
////                        }
////                    }
//                    else if (StringUtils.isNotEmpty(mobilePhone)) {
//                        if (parentsMap.containsKey(mobilePhone)) {
//                            errMess.append("第").append(i+1).append("行记录系统已存在相同的手机号码！");
//                            continue;
//                        } else {
//                            record = parentsDao.findByPhone(mobilePhone);
//                            if (record != null) {
//                                errMess.append("第").append(i+1).append("行记录系统已存在相同的手机号码！");
//                                continue;
//                            }
//                        }
//                    }
//
////                    parents.setRealname(parentName);
////                    parents.setRelation(relation);
////                    parents.setMobilePhone(mobilePhone);
//
//                    // 学生班级关联关系
//                    ClassStudent classStudent = new ClassStudent();
//
//                    classStudent.setCreatetime(dateProvider.getDate());
//                    classStudent.setUpdateTime(dateProvider.getDate());
//                    classStudent.setSchoolId(ShiroUserUtil.getCurrentUser().getSchoolId());
//                    classStudent.setIsDeleted("N");
//
//                    if (StringUtils.isNotEmpty(mobilePhone)) {
//                        entryptPassword(student, mobilePhone);
//                    }
//                    setTime(student);
//                    student.setRegisterTime(dateProvider.getDate());
//                    student.setIsDeleted("N");
//                    student.setFailgoupNum(0);
//                    student.setUpdateTime(dateProvider.getDate());
//                    student.setSchool(school);
//
//                    try {
//                        if (StringUtils.isNotEmpty(mobilePhone)) {
//                            if (parentIsExist) {
//                                student.setMyParent(record);
//                            } else {
//                                parents.setRealname(parentName);
//                                parents.setRelation(relation);
//                                parents.setMobilePhone(mobilePhone);
//                                parents.setRegisterTime(dateProvider.getDate());
//                                parents.setIsDeleted("N");
//                                parents.setGender(parentGender);
////                                entryptPasswordForParents(parents);
//                                student.setMyParent(parents);
//                                parentsDao.save(parents);
//                            }
//                        }
//
//                        student.setsClass(sClass);
//                        student = studentDao.save(student);
//                        classStudent.setClassId(sClass.getId());
//                        classStudent.setStudentId(student.getId());
//
//                        classStudentDao.save(classStudent);
//
//                        studentsMap.put(sheet.getCell(0, i).getContents(), sheet.getCell(0, i).getContents());
//                        if (parentIsExist) {
//                            parentsMap.put(mobilePhone + parentName, record);
//                        } else {
//                            parentsMap.put(mobilePhone + parentName, parents);
//                        }
//
//                        setupExtInfo(student.getId());
//                    } catch (Exception e) {
//                        logger.error("添加学生信息出错。学校ID{} 操作员{}", ShiroUserUtil.getCurrentUser().getSchoolId(), ShiroUserUtil.getCurrentUser().getUsername());
//                        errMess.append("第").append(i+1).append("行记录：新增失败！");
//                    }
//                }
//            } catch (Exception ex) {
//                errMess.append("学生数据导入发生未知错误，请稍后再试或联系管理员。错误信息：").append(ex.getMessage());
//                logger.error("学生数据导入发生未知错误! 学校ID{} 操作员{}", ShiroUserUtil.getCurrentUser().getSchoolId(), ShiroUserUtil.getCurrentUser().getUsername());
//                logger.error(ex.getMessage(), ex);
//            }
//        }

        return errMess.toString();
    }

//    /**
//     * 取得需要传输的学生名单
//     * @param schoolCode 学校编码
//     * @param timeStamp 时间戳
//     * @return 学生名单
//     * @throws Exception
//     */
//    public List<Student> getTransferData(String schoolCode, String timeStamp) throws Exception {
//        SimpleDateFormat sdf = new SimpleDateFormat(WebServiceConstants.DATE_FORMAT);
//        if (WebServiceConstants.TIMESTAMP_FOR_FIRSTTIME.equals(timeStamp)) {
//            // 需要传输所有数据的时候（只用在第一次传输时）
//            return studentDao.findTransferData(schoolCode);
//        }
//        return studentDao.findTransferData(schoolCode, sdf.parse(timeStamp));
//    }
//
//    /**
//     * 取得需要传输的学生和班级的关联信息
//     * @param schoolCode 学校编码
//     * @param timeStamp 时间戳
//     * @return 学生名单
//     * @throws Exception
//     */
//    public List<ClassStudent> getTransferDataForClassStudent(String schoolCode, String timeStamp) throws Exception {
//        SimpleDateFormat sdf = new SimpleDateFormat(WebServiceConstants.DATE_FORMAT);
//        if (WebServiceConstants.TIMESTAMP_FOR_FIRSTTIME.equals(timeStamp)) {
//            // 需要传输所有数据的时候（只用在第一次传输时）
//            return classStudentDao.findTransferData(schoolCode);
//        }
//        return classStudentDao.findTransferData(schoolCode, sdf.parse(timeStamp));
//    }

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

//    /**
//     * 根据高拍仪编号取得学生列表
//     * @param pn 高拍仪编号
//     * @return 学生列表
//     */
//    public List<Student> getStudentsByPn(String pn) {
//        return studentDao.findByPn(pn);
//    }
//
//    /**
//     * 根据家长手机号取得学生列表
//     * @param phone 家长手机号
//     * @return 学生列表
//     */
//    public List<Student> getStudentsByParentPhone(String phone) {
//        return studentDao.findByParentPhone(phone);
//    }
//
//    @Transactional
//    public void saveStudentInfo(Student student) {
//        try {
//            studentDao.save(student);
//
//        } catch (Exception ex) {
//            logger.error("保存信息出错。");
//            throw new ServiceException(ex.getMessage(), ex);
//        }
//    }
//
    /**
     * 创建动态查询条件组合.
     */
    private Specification<Student> buildSpecification(Map<String, Object> filterParams) {
        Map<String, ExtSearchFilter> filters = ExtSearchFilter.parse2(filterParams);
        return ExtDynamicSpecifications.bySearchFilter(filters.values(), Student.class);
    }
//
//    /**
//     * 设定安全的密码，生成随机的salt并经过1次 sha-1 hash
//     */
//    private void entryptPassword(Student user, String parentsPhone) {
//        byte[] salt = Digests.generateSalt(Constants.SALT_SIZE);
//        salt = StringUtil.toUnsignedByte(salt);
//        user.setSalt(Encodes.encodeHex(salt));
//
//        // 手机后六位
//        String phoneLast6 = parentsPhone.substring(parentsPhone.length() - 6);
//
//        byte[] hashPassword = Digests.sha1(phoneLast6.getBytes(), salt, Constants.HASH_INTERATIONS);
//        user.setPassword(Encodes.encodeHex(hashPassword));
//    }
//
//    /**
//     * 设定安全的密码，生成随机的salt并经过1次 sha-1 hash
//     */
//    private void entryptPasswordForParents(Parents parents) {
//        byte[] salt = Digests.generateSalt(Constants.SALT_SIZE);
//        salt = StringUtil.toUnsignedByte(salt);
//        parents.setSalt(Encodes.encodeHex(salt));
//
//        byte[] hashPassword = Digests.sha1(Constants.DEFAULT_PASSWORD_PARENTS.getBytes(), salt, Constants.HASH_INTERATIONS);
//        parents.setPassword(Encodes.encodeHex(hashPassword));
//    }
//


//    public void setDateProvider(DateProvider dateProvider) {
//		this.dateProvider = dateProvider;
//	}
//
//    /**
//     * 更新学生头像
//     * @param path
//     * @param id
//     */
//    @Transactional
//    public void updateHeadPic( String path,Long id){
//        studentDao.updateHeadPic(path,id);
//    }
//    /**
//     * 查找一个班所有的学生，包括被删除的
//     * @param classId
//     * @return
//     */
//    public List<Student> findByClassIdAll(Long classId)
//    {
//
//      return studentDao.findByClassIdAll(classId);
//    }
//
//    /**
//     * 创建用户扩展信息
//     *
//     * @param userId 用户Id
//     */
//    @Transactional
//    public void setupExtInfo(Long userId) {
//        try {
//            UserExt userExt = userExtDao.findByStudentIdAndTerm(userId, DateUtil.getCurrentTerm());
//            if (userExt == null) {
//                UserExt newUserExt = new UserExt();
//                newUserExt.setCoin(0L);
//                newUserExt.setPoints(0L);
//                newUserExt.setLevel(1);
//                newUserExt.setStudent(studentDao.findOne(userId));
//                newUserExt.setTerm(DateUtil.getCurrentTerm());
//
//                userExtDao.save(newUserExt);
//            }
//        } catch (Exception ex) {
//            logger.error("注册学生扩展信息出错。操作员{}", ShiroUserUtil.getCurrentUser().getUsername());
//            throw new ServiceException(ex.getMessage(), ex);
//        }
//
//    }
//
//    public List<Student> findBySchoolIds(List<Long> ids) {
//       return studentDao.findbySchoolids(ids);
//    }
//
//    public List<Student> findByClassIds(List<Long> ids) {
//        return studentDao.findByClassIds(ids);
//    }
}