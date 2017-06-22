package com.solomon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by xuehaipeng on 2017/6/12.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(Model model) {
//        model.addAttribute("path", name);
        return "index";
    }
}
