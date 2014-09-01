package com.zjs.cms.web.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ParentsController负责家长相关信息的操作
 * 
 * @author Yuxuan Yang
 */
@Controller
@RequestMapping(value = "/parent")
public class ParentController {

    private static Logger logger = LoggerFactory.getLogger(ParentController.class);

//
//    @Resource
//    private ParentsService parentsService;
//    @Resource
//    private StudentService studentService;
//
//    /**
//     * Ajax请求校验username是否唯一。
//     */
//    @RequestMapping(value = "checkNameAndPhone")
//    @ResponseBody
//    public String findByNameAndMobile(@RequestParam("id") String id, @RequestParam("realname") String realname,
//                                      @RequestParam("mobilePhone") String mobilePhone) {
//        if (StringUtils.isNotEmpty(id)) {
//            Parents parents = parentsService.findById(Long.parseLong(id));
//            // 从修改页面
////            if (!parents.getRealname().equals(realname) || !parents.getMobilePhone().equals(mobilePhone)) {
////                if (parentsService.findByNameAndMobile(realname, mobilePhone) != null) {
////                    return "false";
////                }
////            }
//            if (!parents.getMobilePhone().equals(mobilePhone)) {
//                if (parentsService.findByPhone(mobilePhone) != null) {
//                    return "false";
//                }
//            }
//        } else {
//            if (parentsService.findByPhone(mobilePhone) != null) {
//                return "false";
//            }
//        }
//        return "true";
//    }
//
//    /**
//     * 进入修改家长密码页面
//     * @param model
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "modifyPassword")
//    public String modifyPassword(Model model ,HttpServletRequest request){
//        ShiroUserUtil.checkAnyRoles(Constants.USER_TYPE_PARENTS, Constants.USER_TYPE_SCHL_ADMIN);
//        return "parent/info/editParentPassword";
//    }
//
//    /**
//     * 进入修改密码的界面
//     * @param model
//     * @param redirectAttributes
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "toModifyPassword",method = RequestMethod.POST)
//    public String toModifyPassword(Model model,RedirectAttributes redirectAttributes,HttpServletRequest request){
//        ShiroUserUtil.checkAnyRoles(Constants.USER_TYPE_PARENTS, Constants.USER_TYPE_SCHL_ADMIN);
//        String oldPassword = request.getParameter("oldPassword");
//        String plainPassword = request.getParameter("plainPassword");
//
//        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
//        Parents parents =parentsService.findById(user.id)  ;
//
//        byte[] hashPassword = Digests.sha1(oldPassword.getBytes(), Encodes.decodeHex(parents.getSalt()), Constants.HASH_INTERATIONS);
//        String olddPassword = Encodes.encodeHex(hashPassword) ; //olddPassword表示把填写的旧密码加了密的
//
//        if (olddPassword.equals(parents.getPassword())) {
//            parents.setPlainPassword(plainPassword);
//            parentsService.plainPassword(parents);
//            parentsService.savePassword(parents);
//            redirectAttributes.addFlashAttribute("message", "密码修改成功");
//            return "redirect:/parent/viewParentsInformation";
//        } else {
//            redirectAttributes.addFlashAttribute("message", "密码修改失败，旧密码不正确。");
//            return "redirect:/parent/modifyPassword";
//        }
//    }
//
//    /**
//     * 查看家长个人信息
//     * @param model
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "viewParentsInformation")
//    public String viewTeacherInformation(  Model model, HttpServletRequest request) {
//        ShiroUserUtil.checkAnyRoles(Constants.USER_TYPE_PARENTS, Constants.USER_TYPE_SCHL_ADMIN);
//        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
//        Parents parents = parentsService.findById(user.id);
//        model.addAttribute("isVIP", user.isVIP());
//        model.addAttribute("parents", parents);
//        return "parent/info/viewParentsInformation";
//    }
//
//    /**
//     * 编辑家长信息
//     * @param model
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "editParentsInformation")
//    public String editParentsInformation(  Model model, HttpServletRequest request) {
//        SecurityUtils.getSubject().checkRole(Constants.USER_TYPE_PARENTS);
//        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
//        Parents parents = parentsService.findById(user.id);
//        model.addAttribute("parents", parents);
//        return "parent/info/editParentsInformation";
//    }
//
//    /**
//     * 修改家长个人信息
//     * @param model
//     * @param request
//     * @param response
//     * @return
//     * @throws java.text.ParseException
//     */
//    @RequestMapping(value = "modifyParentsInformation")
//    public String modifyParentsInformation(Model model,HttpServletRequest request,HttpServletResponse response) throws ParseException {
//        SecurityUtils.getSubject().checkRole(Constants.USER_TYPE_PARENTS);
//        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
//        Parents parents = parentsService.findById(user.id);
//
//        parents.setRealname(request.getParameter("realname"));
//        parents.setGender(request.getParameter("gender"));
//        parents.setJob(request.getParameter("job"));
//        String birthday=request.getParameter("birthday");
//        if(birthday!=null && !birthday.equals("")){
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            parents.setBirthday(sdf.parse(request.getParameter("birthday")));
//        }
//        parents.setAddress(request.getParameter("address"));
//        parents.setQq(request.getParameter("qq"));
////        parents.setMobilePhone(request.getParameter("mobilePhone"));
//        parents.setAvatarPath(request.getParameter("avatarPath"));
//        parents.setEmail(request.getParameter("email"));
//        parentsService.saveInformation(parents);
//
//        user.setPicPath(parents.getAvatarPath());
//
//        model.addAttribute("parents", parents);
//        model.addAttribute("message", "修改个人信息成功");
//        return "redirect:/parent/viewParentsInformation";
//    }
//
//    @RequestMapping(value = "viewChildrenInfo")
//    public String viewChildrenInfo(Model model, HttpServletRequest request) {
//        ShiroUserUtil.checkAnyRoles(Constants.USER_TYPE_PARENTS, Constants.USER_TYPE_SCHL_ADMIN);
//        ShiroDbRealm.ShiroUser user = ShiroUserUtil.getCurrentUser();
//        Student student = user.getDefaultChild();
//        Student defaultChild = studentService.findById(student.getId());
//
//        model.addAttribute("student", defaultChild);
//        return "parent/info/viewChildrenInfo";
//    }
//
//    @RequestMapping(value="uploadParentsPicture")
//    public String uploadParentsPicture(Model model,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
//        ShiroUserUtil.checkAnyRoles(Constants.USER_TYPE_PARENTS, Constants.USER_TYPE_SCHL_ADMIN);
//        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtil.getCurrentUser();
//        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        commonsMultipartResolver.setDefaultEncoding("utf-8");
//        commonsMultipartResolver.setMaxUploadSize(1 * 1000 * 1000);
//        String newfileName ="";
//        String relativePath="";
//        PrintWriter pw=null;
//        if (commonsMultipartResolver.isMultipart(request)) {
//            //转换成多部分request
//            MultipartHttpServletRequest multipartRequest=null;
//            try
//            {//文件大于1M request 会转化失败必须捕捉提示
//                pw=response.getWriter();
//                multipartRequest = commonsMultipartResolver.resolveMultipart(request);
//            }
//            catch (MultipartException e1)
//            {
//                pw.write("error2");
//                return null;
//
//            }catch (IOException e2)
//            {
//                pw.write("error2");
//                return null;
//
//            }
//            String  content = new File(new File(request.getSession().getServletContext()
//                    .getRealPath("/")).getParent()).getParent();
//            relativePath= PropertiesUtil.getConfigValue("main.upload")+"parents/";
//            String path = content+relativePath;
//            File dirPath = new File(path);
//            if (!dirPath.exists()) {
//                dirPath.mkdirs();
//            }
//            List<MultipartFile>  FileList = multipartRequest.getFiles("fileName");
//            if(FileList==null || FileList.size()<=0){
//                pw.write("error1");
//                return null;
//            }
//            for (MultipartFile Filedata : FileList)
//            {
//                if(Filedata.getSize()<=0L){
//                    pw.write("error1");
//                    return null;
//                }
//                if(Filedata.getSize()>(1 * 1000 * 1000)){
//                    pw.write("error2");
//                    return null;
//                }
//                if(Filedata.getOriginalFilename()!=null && !Filedata.getOriginalFilename().equals("")){
//                    String endsuffix= FileUtil.getFileSuffix(Filedata.getOriginalFilename());
//                    newfileName = shiroUser.getId()+"_"+ DateUtil.getDateFormat(new Date(), DateUtil.FORMAT2)+"."+endsuffix;
//
//                    if (!Filedata.isEmpty()) {
//                        try
//                        {
//                            FileCopyUtils.copy(Filedata.getBytes(), new File(path + File.separator + newfileName));
////                            parentsService.updateHeadPic(relativePath+newfileName,shiroUser.getId());
//                            //重新登录更新图片
////                            shiroUser.setPicPath(relativePath+newfileName);
//                        }
//                        catch (IOException e)
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//        pw.write(relativePath+newfileName);
//        return null;
//    }
}