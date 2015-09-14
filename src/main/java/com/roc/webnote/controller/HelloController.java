package com.roc.webnote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HelloController {


    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printWelcome(@CookieValue("userCode") String userCode) {
        logger.info("index");
        if (null != userCode) {
            return "redirect:/article/list";
        }
        return "index";
    }


    // TODO should be removed
    @RequestMapping(value = "editor", method = RequestMethod.GET)
    public String editorRouter() {
        return "editor";
    }

}