package vn.hbm.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.hbm.core.common.Common;
import vn.hbm.core.jpa.AuthUser;
import vn.hbm.core.service.CommonService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    CommonService commonService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        try {
            List<Object[]> lstObj = commonService.findBySQL("", "SELECT * FROM auth_user WHERE user_name = ? AND passwd = ? ",
                    new String[]{username, password});
            if(Common.isEmpty(lstObj)) {
                return null;
            } else {
                AuthUser user = new AuthUser();
                user = Common.convertToBean(lstObj.get(0), user);
                List<GrantedAuthority> grantedAuths = new ArrayList();
                grantedAuths.add(new SimpleGrantedAuthority(username.toUpperCase()));

                Authentication auth = new UsernamePasswordAuthenticationToken(user, "", grantedAuths);
                return auth;
            }
        } catch (Exception ex) {
            log.error("", ex);
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
