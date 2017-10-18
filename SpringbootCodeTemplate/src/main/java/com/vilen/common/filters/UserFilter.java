package com.vilen.common.filters;

import com.vilen.SpringbootCodeTemplate.beans.User;
import com.vilen.common.utils.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.vilen.common.utils.UserUtil.clearAllUserInfo;

/**
 * Created by vilen on 2017/10/18.
 */
@WebFilter(filterName = "userFilter",urlPatterns = "/*")
public class UserFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        fillUserInfo((HttpServletRequest)servletRequest);
        filterChain.doFilter(servletRequest,servletResponse);
        clearAllUserInfo();
    }

    private void fillUserInfo(HttpServletRequest request) {
        // 用户信息
        User user = getUserFromSession(request);
        if (user != null) {
            UserUtil.setUser(user);
        }
        // 语言
        String locale = getLocaleFromCookies(request);
        if (locale != null) {
            UserUtil.setLocale(locale);
        }
    }

    private User getUserFromSession(HttpServletRequest request) {
        //TODO 如果不参加session ，model.addAttribute(UserUtil.KEY_USER,username);出错
        HttpSession session = request.getSession(true);
        if (session ==null) {
            return null;
        }
        // 从 session 中获取用户信息放到工具类中
        return (User) session.getAttribute(UserUtil.KEY_USER);
    }

    private String getLocaleFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies==null) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (UserUtil.KEY_LANG.equals(cookies[i].getName())) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    @Override
    public void destroy() {

    }
}
