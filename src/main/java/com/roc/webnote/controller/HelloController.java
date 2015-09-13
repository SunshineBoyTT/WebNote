package com.roc.webnote.controller;

import com.roc.webnote.entity.Article;
import com.roc.webnote.lib.QiNiuTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URLDecoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
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

    @RequestMapping(value = "images/{uuid}.png")
    public void downloadImage(@PathVariable String uuid, HttpServletResponse response) throws IOException {

        String img = imgFolder + uuid + ".png";
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("image/png");

        ServletOutputStream out;

//        File file = new File(img);

        try {

            out = response.getOutputStream();


            byte[] buffer = toByteArray(img);
            out.write(buffer);
            out.flush();
            out.close();
//            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
//        out.w

    }

    public static byte[] toByteArray(String filename) throws IOException {

        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(filename, "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                                                 fc.size()).load();
            System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                // System.out.println("remain");
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}