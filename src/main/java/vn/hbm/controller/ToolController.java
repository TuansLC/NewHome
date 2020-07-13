package vn.hbm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hbm.bean.CalmBean;

@Controller
@RequestMapping(value = "/tools")
@Slf4j
public class ToolController {

    @GetMapping("/")
    public String index() {
        return "/tools/index";
    }

    @GetMapping("/calm")
    public String calmGet() {
        return "/tools/calm";
    }

    @PostMapping("/calm")
    public String calmPost(@ModelAttribute("frmObj")CalmBean frm) {
        log.info(frm.getSmsTextPlain());
        return "/tools/calm";
    }
}

