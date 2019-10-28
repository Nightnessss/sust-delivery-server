package com.fehead.authentication;

import com.fehead.model.UserModel;
import com.fehead.service.CloudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
 * @author Nightnessss 2019/7/16 16:19
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CloudService cloudService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 由于是易班登录，这里的 security 登录部分通过获取数据库内信息绕过
        UserModel userModel = cloudService.alreadyLogin(username);

        logger.info(username);
        logger.info(userModel.toString());
        String userId = String.valueOf(userModel.getId());

        String avatar = userModel.getAvatar();
        logger.info("登录用户ID：" + userId);

        String password = passwordEncoder.encode(avatar);

//        logger.info("加密后的密码：" + password);

        return new User(userId, password,
                true, true, true, true,
                AuthorityUtils.createAuthorityList("admin"));
    }
}
