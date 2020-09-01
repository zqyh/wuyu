package com.whackode.itrip.util;

import com.alibaba.fastjson.JSONObject;
import com.whackode.itrip.pojo.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Token验证
 */
@Component
public class ValidationToken {

    private Logger logger = Logger.getLogger(ValidationToken.class);


    //private RedisAPI redisAPI;
    @Autowired RedisUtils redisAPI;

   /* public RedisAPI getRedisAPI() {
        return redisAPI;
    }
    public void setRedisAPI(RedisAPI redisAPI) {
        this.redisAPI = redisAPI;
    }*/
    public User getCurrentUser(String tokenString){
        //根据token从redis中获取用户信息
			/*
			 test token:
			 key : token:1qaz2wsx
			 value : {"id":"100078","userCode":"myusercode","userPassword":"78ujsdlkfjoiiewe98r3ejrf","userType":"1","flatID":"10008989"}

			*/
        User itripUser = null;
        if(null == tokenString || "".equals(tokenString)){
            return null;
        }
        try{
            String userInfoJson = (String) redisAPI.get(tokenString);
            itripUser = JSONObject.parseObject(userInfoJson,User.class);
        }catch(Exception e){
            itripUser = null;
            logger.error("get userinfo from redis but is error : " + e.getMessage());
        }
        return itripUser;
    }

}
