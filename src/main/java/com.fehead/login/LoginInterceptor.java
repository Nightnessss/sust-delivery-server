package com.fehead.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * @author Nightnessss 2019/10/14 19:53
 */
//public class LoginInterceptor implements HandlerInterceptor {
//
//    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        logger.info("==========登录状态拦截=========");
//
//        HttpSession session = request.getSession();
//        logger.info("sessionId：" + session.getId());
//
//        // 获取用户信息，如果没有用户信息直接返回提示信息
//        Object userId = session.getAttribute("userId");
//        if (userId == null) {
//            logger.info("没有登录");
//            response.sendRedirect(request.getContextPath()+"/api/v1.0/SUSTDelivery/view/oauth");
//            return false;
//        } else {
//            logger.info("用户：" + session.getAttribute("userId"));
//        }
//
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
//
//    }
//}
