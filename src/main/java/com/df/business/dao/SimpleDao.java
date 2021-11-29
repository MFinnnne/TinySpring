package com.df.business.dao;

import com.df.anno.Component;

/**
 * @author MFine
 * @version 1.0
 * @date 2021/11/29 22:56
 **/
@Component
public class SimpleDao {


   public String accessDb(){
       return "hello mysql";
   }
}
