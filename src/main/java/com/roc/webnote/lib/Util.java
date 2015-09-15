package com.roc.webnote.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * 字符编码
 * Created by yp-tc-m-2795 on 15/9/15.
 */
public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static String getUtf8String(String input) {
        String jvmCharset = System.getProperty("file.encoding");
        logger.info("JVM编码信息: {}", jvmCharset);
        System.out.println("JVM编码信息: " + jvmCharset);
        if (jvmCharset.equals("UTF-8")) {
            return input;
        } else {
            try {
                return new String(input.getBytes(jvmCharset), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.info("文本转码失败,源信息: {},失败信息: {}", e.getMessage());
                return input;
            }
        }
    }
}
