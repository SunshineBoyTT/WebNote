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

    @RequestMapping(value = "imgupload")
    @ResponseBody
    public String imgUpload(@RequestBody String body) throws IllegalStateException, IOException {
        body = URLDecoder.decode(body, "UTF-8").substring(27);
        logger.info("The request body is :{}", body);
        System.out.println(body);


        UUID uuid = UUID.randomUUID();

        BASE64Decoder decoder      = new BASE64Decoder();
        String        fileName     = imgFolder + uuid + ".png";
        byte[]        decoderBytes = decoder.decodeBuffer(body);
        QiNiuTools.upload(decoderBytes, fileName);


//        FileOutputStream write = new FileOutputStream(new File(fileName));
//        write.write(decoderBytes);
        return uuid.toString();
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