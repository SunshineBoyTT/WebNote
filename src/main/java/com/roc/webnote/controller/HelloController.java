package com.roc.webnote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HelloController {


    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    /**
     * Cookie 处理ß
     *
     * @param userCode
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printWelcome(@CookieValue(value = "userCode", required = false) String userCode) {
        logger.info("index");
        if (StringUtils.isEmpty(userCode)) {
            return "index";
        } else {
            return "redirect:/article/list";
        }
    }

}