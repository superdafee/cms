package com.zjs.cms.web.account;

import com.google.common.collect.Maps;
import com.zjs.cms.entity.Student;
import com.zjs.cms.service.account.ShiroDbRealm;
import com.zjs.cms.service.account.StudentService;
import com.zjs.cms.utils.Constants;
import com.zjs.cms.utils.URLUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * StudentController负责学生管理页面
 * 
 * @author Yuxuan Yang
 */
@Controller
@RequestMapping(value = "/student")
public class StudentController {

    private static Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
    static {
        sortTypes.put("auto", "自动");
        sortTypes.put("realname", "姓名");
    }

    @RequestMapping(value = "list")
    public String list(@RequestParam(value = "sortType", defaultValue = "realname_asc") String sortType,
                       @RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, HttpServletRequest request) {

        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        if (searchParams == null || searchParams.isEmpty()) {
            searchParams = URLUtil.mergeSearchParamInFlashMap(request, "search_");
        }

        searchParams.put("EQ_isdeleted", "N");

        String searchName= request.getParameter("search_LIKE_realname");
        if (searchName!=null && !searchName.equals("")){
            searchParams.put("LIKE_realname", searchName);
        }
        Page<Student> students = studentService.getAllStudents(searchParams, pageNumber, Constants.PAGE_SIZE, sortType);
        model.addAttribute("students", students);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypes", sortTypes);
        model.addAttribute("searchName",searchName);
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

        model.addAttribute("searchParamsMap", searchParams);

        if ("1".equals(request.getParameter("uploadSuccessFlg"))) {
            model.addAttribute("message", "批量导入学生信息成功！");
        }

        return "account/studentList";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model,HttpServletRequest request) throws  Exception {
        Student student = new Student();

        model.addAttribute("student", student);
        model.addAttribute("action", "add");

        return "account/studentForm";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@Valid Student user, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        Map<String, String> dtoMap = new HashMap<String, String>();
        dtoMap.put("parentsName", request.getParameter("parentsName"));
        dtoMap.put("relation", request.getParameter("relation"));
        dtoMap.put("parentMobile", request.getParameter("parentMobile"));
        dtoMap.put("parentPhone", request.getParameter("parentPhone"));


        studentService.add(user, dtoMap);
        redirectAttributes.addFlashAttribute("message", "学生添加成功");
        return "redirect:/student/list";

    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model, HttpServletRequest request) throws  Exception {

        Student student = studentService.findById(id);
        model.addAttribute("student", student);

        // 先做成一个家长一个孩子的结构
        model.addAttribute("parentStudent", student.getParentList().get(0));

        // 为了返回列表时，保留页数和查询条件，将信息存于Hidden中
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
        model.addAttribute("sortType", request.getParameter("sortType"));
        model.addAttribute("page", request.getParameter("page"));

        model.addAttribute("action", "update");

        return "account/studentForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("preloadStudent") Student user, HttpServletRequest request,
                         RedirectAttributes redirectAttributes) {

        String parentStudentId = request.getParameter("parentStudentId");

        Map<String, String> dtoMap = new HashMap<String, String>();
        dtoMap.put("parentsName", request.getParameter("parentsName"));
        dtoMap.put("relation", request.getParameter("relation"));
        dtoMap.put("parentMobile", request.getParameter("parentMobile"));
        dtoMap.put("parentPhone", request.getParameter("parentPhone"));
        studentService.update(user, dtoMap, parentStudentId);

        redirectAttributes.addFlashAttribute("message", "学生更新成功");

        String page = request.getParameter("page");
        String sortType = request.getParameter("sortType");
        String searchParams = request.getParameter("searchParams");
        redirectAttributes.addFlashAttribute("searchParams", searchParams);

        return "redirect:/student/list?page=" + page + "&sortType=" + sortType;
    }

    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        studentService.delete(id);
        redirectAttributes.addFlashAttribute("message", "学生删除成功");

        String page = request.getParameter("page");
        String sortType = request.getParameter("sortType");
        Map<String, Object> searchParamsMap = Servlets.getParametersStartingWith(request, "search_");
        String searchParams = Servlets.encodeParameterStringWithPrefix(searchParamsMap, "search_");
        redirectAttributes.addFlashAttribute("searchParams", searchParams);

        return "redirect:/student/list?page=" + page + "&sortType=" + sortType;
    }

    @RequestMapping(value = "deleteBySelected/{ids}")
    public String deleteBySelected(@PathVariable("ids") String ids, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        studentService.deleteBySelected(ids);
        redirectAttributes.addFlashAttribute("message", "学生删除成功");

        String page = request.getParameter("page");
        String sortType = request.getParameter("sortType");
        Map<String, Object> searchParamsMap = Servlets.getParametersStartingWith(request, "search_");
        String searchParams = Servlets.encodeParameterStringWithPrefix(searchParamsMap, "search_");
        redirectAttributes.addFlashAttribute("searchParams", searchParams);

        return "redirect:/student/list?page=" + page + "&sortType=" + sortType;
    }

    @RequestMapping("downloadTpl")
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {

        InputStream ipsm = null;
        OutputStream opsm = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=batchUploadStudent.xls");
            String path = request.getSession().getServletContext().getRealPath("/");
            ipsm = new FileInputStream(new File(path+"tpl/batchUploadStudent.xls"));
            opsm = response.getOutputStream();
            byte[] b = new byte[1024];
            int length = 0;
            while ((length=ipsm.read(b)) > 0) {
                opsm.write(b, 0, length);
            }
        } catch (Exception e) {
            response.getWriter().write("模版下载失败！");
            logger.error("学生信息上传模版下载失败！", e);
        } finally {
            try {
                if (opsm != null) {
                    opsm.close();
                }
                if (ipsm != null) {
                    ipsm.close();
                }
            } catch (Exception ex) {}
        }
    }

    @RequestMapping("uploadStudent")
    public void uploadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            commonsMultipartResolver.setDefaultEncoding("utf-8");
            commonsMultipartResolver.setMaxUploadSize(Constants.UPLOAD_FILE_SIZE);
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
                MultipartFile fileData = multipartRequest.getFile("fileData");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(studentService.upload(fileData));
            }
        } catch ( MaxUploadSizeExceededException ex) {
            response.getWriter().write("上传文件超过大小限制(" + Constants.UPLOAD_FILE_SIZE/(1000*1000) + "M)");
        } catch (Exception ex) {
            response.getWriter().write("学生信息导入发生错误，请稍后再试或联系管理员。");
        }
    }

    /**
     * 使用@ModelAttribute, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
     * 因为仅update()方法的form中有id属性，因此本方法在该方法中执行.
     */
    @ModelAttribute("preloadStudent")
    public Student getStudent(@RequestParam(value = "id", required = false) Long id) {
        if (id != null) {
            return studentService.findById(id);
        }
        return null;
    }


}