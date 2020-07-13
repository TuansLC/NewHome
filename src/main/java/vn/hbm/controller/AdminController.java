package vn.hbm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hbm.bean.CalmBean;

@Controller
@RequestMapping(value = "/admin")
@Slf4j
public class AdminController {

    @GetMapping("/")
    public String index() {
        return "/admin/index";
    }

    @GetMapping("/login")
    public String adminLogin() {
        log.info("xxxxx");
        return "/admin/login";
    }
}

