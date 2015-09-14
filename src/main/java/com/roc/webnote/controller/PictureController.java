package com.roc.webnote.controller;

import com.roc.webnote.lib.QiNiuTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.UUID;

/**
 * Created by yp-tc-m-2795 on 15/9/13.
 */
@Controller
@RequestMapping("/picture")
public class PictureController {
    private static final String imgFolder = "images/";
    private static       Logger logger    = LoggerFactory.getLogger(PictureController.class);

    @RequestMapping(value = "", method = RequestMethod.POST)
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

        return uuid.toString();
    }
}
