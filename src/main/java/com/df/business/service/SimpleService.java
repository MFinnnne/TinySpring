package com.df.business.service;

import com.df.anno.Autowired;
import com.df.anno.Component;
import com.df.business.dao.SimpleDao;

/**
 * @author MFine
 * @version 1.0
 * @date 2021/11/29 22:06
 **/

@Component
public class SimpleService {

    @Autowired
    private SimpleDao simpleDao;

    public String hello() {
        return simpleDao.accessDb();
    }

}
