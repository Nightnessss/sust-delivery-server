package com.fehead.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fehead.error.BusinessException;
import com.fehead.error.EmBusinessError;
import com.fehead.model.UserMeModel;
import com.fehead.model.UserModel;
import com.fehead.model.YbReturnModel;
import com.fehead.response.CommonReturnType;
import com.fehead.service.CloudService;
import com.fehead.util.PostUtil;
import com.fehead.util.UnicodeUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 写代码 敲快乐
 * だからよ...止まるんじゃねぇぞ
 * ▏n
 * █▏　､⺍
 * █▏ ⺰ʷʷｨ
 * █◣▄██◣
 * ◥██████▋
 * 　◥████ █▎
 * 　　███▉ █▎
 * 　◢████◣⌠ₘ℩
 * 　　██◥█◣\≫
 * 　　██　◥█◣
 * 　　█▉　　█▊
 * 　　█▊　　█▊
 * 　　█▊　　█▋
 * 　　 █▏　　█▙
 * 　　 █
 *
 * @author Nightnessss 2019/10/14 20:12
 */
@RestController
@RequestMapping("/api/v1.0/SUSTDelivery/view")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class LoginController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

//    private static final String backurl = "http://192.168.43.7:8081/blank";
//    private static final String backurl = "http://192.168.0.110:8081/blank";

    private static final String backurl = "http://192.168.43.7:8081/blank";
//    private static final String backurl = "http://10.111.118.205:8081/blank";
//    private static final String backurl = "http://express.duizhankeji.com:8080/blank";


    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PostUtil postUtil;
    @Autowired
    private UnicodeUtil unicodeUtil;
    @Autowired
    private CloudService cloudService;

    /**
     * 获取 access_token 并跳转至信息补充界面
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/oauth")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonReturnType oauth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        logger.info("开始获取access_token...");
//        String getTokenUrl = "http://nightnessss.cn:8018/page/oauth?callback=" + backurl;
        String getTokenUrl = "http://yiban.sust.edu.cn/yibanapi/?backurl=" + backurl;
//        response.sendRedirect(getTokenUrl);
        Map<String,String> data = new HashMap<>();
        data.put("url",getTokenUrl);
        return CommonReturnType.creat(data);
    }

    /**
     * 获得当前用户信息并封装成 UserModel 传回给前端
     * @param request access_token
     * @param response
     * @return UserModel
     * @throws IOException
     * @throws BusinessException
     */
    @GetMapping("/login")
    public CommonReturnType login(HttpServletRequest request, HttpServletResponse response) throws IOException, BusinessException {

        // 获取前端传来的 access_token
        HttpSession session = request.getSession();
        String accessToken = request.getParameter("access_token");
        logger.info("access_token:" + accessToken);
        if (accessToken == null || accessToken.isEmpty()) {
            logger.info("access_token为空");
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "access_token为空");
        }

        // 获取用户信息，将返回的信息封装在 YbReturnModel 中
        String getUserInfo = "https://openapi.yiban.cn/user/me?" +
                "access_token=" + accessToken;

        session.setAttribute("access_token", accessToken);
        String userInfo = postUtil.sendGet(getUserInfo);
        YbReturnModel ybReturnModel = new YbReturnModel();
        try {
            ybReturnModel = objectMapper.readValue(userInfo, YbReturnModel.class);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessError.DATA_SELECT_ERROR);
        }

        // 从 YbReturnModel 中提取用户信息，封装在 UserMeModel 中
        UserMeModel userMeModel = ybReturnModel.getInfo();
        String thirdPartyId = userMeModel.getYb_userid();
        // 如果改用户信息已存数据库，则返回数据库中该用户信息，否则添加用户到数据库并返回不完全信息，由前端引导用户进行信息补全
        UserModel userModel = cloudService.alreadyLogin(thirdPartyId);
        if (userModel != null) {
//            session.setAttribute("userId", userModel.getId());
            return CommonReturnType.creat(userModel);
        }
        String displayName = unicodeUtil.unicode2String(userMeModel.getYb_usernick());
        String avatar = unicodeUtil.unicode2String(userMeModel.getYb_userhead());
        userModel = new UserModel("易班", thirdPartyId, avatar, displayName);

        int id = cloudService.addUser(userModel);
        userModel.setId(id);

//        session.setAttribute("userModel", userModel);
//        session.setAttribute("access_token", accessToken);


//        System.out.println(session.getId());
        return CommonReturnType.creat(userModel);
    }

//    @PostMapping("/addInfo")
//    public CommonReturnType addInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String telphone = request.getParameter("telphone");
//        String email = request.getParameter("email");
//        logger.info("PARAM: telphone " + telphone);
//        logger.info("PARAM: email " + email);
//        HttpSession session = request.getSession();
////        String accessToken = (String) session.getAttribute("access_token");
////        System.out.println(accessToken);
////        String getUserInfo = "https://openapi.yiban.cn/user/me?" +
////                "access_token=" + accessToken;
////
////        String userInfo = postUtil.sendGet(getUserInfo);
////        YbReturnModel ybReturnModel = objectMapper.readValue(userInfo, YbReturnModel.class);
////        UserMeModel userMeModel = ybReturnModel.getInfo();
////        String thirdPartyId = userMeModel.getYb_userid();
////        String displayName = unicodeUtil.unicode2String(userMeModel.getYb_usernick());
////        String avatar = unicodeUtil.unicode2String(userMeModel.getYb_userhead());
////        UserModel userModel = new UserModel(telphone, email, "易班", thirdPartyId, avatar, displayName);
//        System.out.println(session.getId());
//        UserModel userModel = (UserModel) session.getAttribute("userModel");
//        session.removeAttribute("userModel");
//        userModel.setTelphone(telphone);
//        userModel.setEmail(email);
//
//        logger.info(userModel.toString());
//        int userId = cloudService.addUser(userModel);
//        logger.info("userId:" + userId);
//
//        return CommonReturnType.creat("success");
//    }

    /**
     * 用户信息补全
     * @param request
     * @param response
     * @return UserModel
     * @throws BusinessException
     */
    @PostMapping("/addInfo")
    public CommonReturnType addInfo(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        String thirdPartyId = request.getParameter("username");
        String telphone = request.getParameter("telphone");
        String email = request.getParameter("email");
        UserModel userModel = cloudService.alreadyLogin(thirdPartyId);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.SERVICE_REQUIRE_AUTHENTICATION);
        }
        if (userModel.getTelphone() == null && userModel.getEmail() == null) {
            userModel.setTelphone(telphone);
            userModel.setEmail(email);
            logger.info(userModel.toString());
            int id = cloudService.addInfo(userModel);
            userModel.setId(id);
        } else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "信息已补充");
        }
        return CommonReturnType.creat(userModel);
    }
}
