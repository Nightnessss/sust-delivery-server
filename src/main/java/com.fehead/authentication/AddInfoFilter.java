package com.fehead.authentication;


import com.fehead.model.UserModel;
import com.fehead.service.CloudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import java.io.IOException;

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
 * @author Nightnessss 2019/10/19 22:38
 */

/**
 * 添加信息的 Filter ，目前并无软用
 */
public class AddInfoFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CloudService cloudService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String thirdPartyId = servletRequest.getParameter("username");
        String avatar = servletRequest.getParameter("password");
        String telphone = servletRequest.getParameter("telphone");
        String email = servletRequest.getParameter("email");
        UserModel userModel = cloudService.alreadyLogin(thirdPartyId);
        if (userModel != null && userModel.getTelphone() == null && userModel.getEmail() == null) {
            userModel.setTelphone(telphone);
            userModel.setEmail(email);
            logger.info(userModel.toString());
            int id = cloudService.addUser(userModel);
            userModel.setId(id);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
