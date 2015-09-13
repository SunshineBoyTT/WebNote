package com.roc.webnote.repository;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yp-tc-m-2795 on 15/8/19.
 */
public class MyBatisConnectionFactory {
    private static SqlSessionFactory sqlSessionFactory = null;

    /**
     * 单例模式生成sqlSessionFactory
     *
     * @return
     * @throws IOException
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            synchronized (MyBatisConnectionFactory.class) {
                if (sqlSessionFactory == null) {
                    String resource = "mybatis-config.xml";
                    InputStream inputStream = null;
                    try {
                        inputStream = Resources.getResourceAsStream(resource);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sqlSessionFactory = new SqlSessionFactoryBuilder()
                            .build(inputStream);
                }
            }
        }
        return sqlSessionFactory;
    }

    private MyBatisConnectionFactory() {
    }
}
