package com.fehead.config;

import com.fehead.authentication.AddInfoFilter;
import com.fehead.authentication.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.Filter;
import javax.sql.DataSource;

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
 * @author Nightnessss 2019/7/14 17:16
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler feheadAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler feheadAuthenticationFailureHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;
//
//    @Autowired
//    CORSFilter corsFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {

        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        tokenRepository.setCreateTableOnStartup(false);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().authorizeRequests()
                //处理跨域请求中的Preflight请求
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .and()
//                .addFilterBefore(corsFilter, authenticationTokenFilterBean().getClass())
//                .addFilterBefore(addInfoFilterBean(), authenticationTokenFilterBean().getClass())
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)

                .formLogin()
                .loginPage("/api/v1.0/SUSTDelivery/view/oauth")
                .loginProcessingUrl("/api/v1.0/SUSTDelivery/view/form")
                .successHandler(feheadAuthenticationSuccessHandler)
                .failureHandler(feheadAuthenticationFailureHandler)
                .and()
//            .rememberMe()
//                .tokenRepository(persistentTokenRepository())
//                .userDetailsService(userDetailsService)
//                .and()
                .authorizeRequests()
                .antMatchers("/api/v1.0/SUSTDelivery/view/oauth",
                        "/api/v1.0/SUSTDelivery/view/login",
                        "/api/v1.0/SUSTDelivery/view/addInfo").permitAll()
                // swagger start
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll()
                // swagger end
                .mvcMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .anyRequest()
                .authenticated();


    }

    private Filter authenticationTokenFilterBean() throws Exception {
        return new JWTAuthenticationFilter(authenticationManager());
    }

    private Filter addInfoFilterBean() {
        return new AddInfoFilter();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
