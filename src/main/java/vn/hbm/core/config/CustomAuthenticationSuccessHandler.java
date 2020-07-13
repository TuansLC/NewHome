package vn.hbm.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import vn.hbm.core.jpa.AuthUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String targetLink = "/";
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        request.getSession().setAttribute("fullName", authUser.getFullName());
        request.getSession().setAttribute("userId", authUser.getId());
        request.getSession().setAttribute("userName", authUser.getUserName());
        List<GrantedAuthority> lstAuthorities = new ArrayList<GrantedAuthority>(authentication.getAuthorities());
        targetLink = "/";
        if (response.isCommitted()) {
            log.warn("Response has already been committed. Unable to redirect to " + targetLink);
        } else {
            redirectStrategy.sendRedirect(request, response, targetLink);
        }
    }
}
