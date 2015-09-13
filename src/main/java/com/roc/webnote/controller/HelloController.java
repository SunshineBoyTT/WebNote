package com.roc.webnote.controller;

import com.roc.webnote.entity.Article;
import com.roc.webnote.lib.QiNiuTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class HelloController {

    private static final String imgFolder = "images/";

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("message", "Hello world!");
        logger.info("index");
        return "index";
    }



    @RequestMapping(value = "editor", method = RequestMethod.GET)
    public String editorRouter() {
        return "editor";
    }

    @RequestMapping(value = "article")
    @ResponseBody
    public String addArticle(@ModelAttribute Article article) {
        System.out.println(article.getTitle());
        System.out.println(article.getCategory());
        System.out.println(article.getContent());
        System.out.println(article.getTagListStr());


        return "OK";
    }


}