package com.roc.webnote.repository;

import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Also singleton
 * Created by yp-tc-m-2795 on 15/9/13.
 */
public class BaseDao {
    protected static SqlSessionFactory sessionFactory = null;

    static {
        sessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
    }

}
