package com.zjs.cms.web.account;

import com.google.common.collect.Maps;
import com.zjs.cms.entity.Parent;
import com.zjs.cms.service.account.ParentService;
import com.zjs.cms.utils.Constants;
import com.zjs.cms.utils.URLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * ParentsController负责家长相关信息的操作
 * 
 * @author Yuxuan Yang
 */
@Controller
@RequestMapping(value = "/parent")
public class ParentController {

    private static Logger logger = LoggerFactory.getLogger(ParentController.class);

    @Resource
    private ParentService parentService;
//    @Resource
//    private StudentService studentService;
//

    private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
    static {
        sortTypes.put("auto", "自动");
        sortTypes.put("realname_asc", "姓名");
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
        Page<Parent> parents = parentService.getAllParents(searchParams, pageNumber, Constants.PAGE_SIZE, sortType);
        model.addAttribute("parents", parents);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypes", sortTypes);
        model.addAttribute("searchName",searchName);
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

        model.addAttribute("searchParamsMap", searchParams);

        return "account/parentList";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model, HttpServletRequest request) throws  Exception {

        Parent parent = parentService.findById(id);
        model.addAttribute("parent", parent);

        // 为了返回列表时，保留页数和查询条件，将信息存于Hidden中
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
        model.addAttribute("sortType", request.getParameter("sortType"));
        model.addAttribute("page", request.getParameter("page"));

        model.addAttribute("action", "update");

        return "account/parentForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("preloadParent") Parent user, HttpServletRequest request,
                         RedirectAttributes redirectAttributes) {


        parentService.update(user);

        redirectAttributes.addFlashAttribute("message", "家长更新成功");

        String page = request.getParameter("page");
        String sortType = request.getParameter("sortType");
        String searchParams = request.getParameter("searchParams");
        redirectAttributes.addFlashAttribute("searchParams", searchParams);

        return "redirect:/parent/list?page=" + page + "&sortType=" + sortType;
    }

    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        parentService.delete(id);
        redirectAttributes.addFlashAttribute("message", "家长删除成功");

        String page = request.getParameter("page");
        String sortType = request.getParameter("sortType");
        Map<String, Object> searchParamsMap = Servlets.getParametersStartingWith(request, "search_");
        String searchParams = Servlets.encodeParameterStringWithPrefix(searchParamsMap, "search_");
        redirectAttributes.addFlashAttribute("searchParams", searchParams);

        return "redirect:/parent/list?page=" + page + "&sortType=" + sortType;
    }

    @RequestMapping(value = "deleteBySelected/{ids}")
    public String deleteBySelected(@PathVariable("ids") String ids, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        parentService.deleteBySelected(ids);
        redirectAttributes.addFlashAttribute("message", "家长删除成功");

        String page = request.getParameter("page");
        String sortType = request.getParameter("sortType");
        Map<String, Object> searchParamsMap = Servlets.getParametersStartingWith(request, "search_");
        String searchParams = Servlets.encodeParameterStringWithPrefix(searchParamsMap, "search_");
        redirectAttributes.addFlashAttribute("searchParams", searchParams);

        return "redirect:/parent/list?page=" + page + "&sortType=" + sortType;
    }

    /**
     * 使用@ModelAttribute, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
     * 因为仅update()方法的form中有id属性，因此本方法在该方法中执行.
     */
    @ModelAttribute("preloadParent")
    public Parent getParent(@RequestParam(value = "id", required = false) Long id) {
        if (id != null) {
            return parentService.findById(id);
        }
        return null;
    }
}