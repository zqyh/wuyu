package com.whackode.itrip.util;

import com.alibaba.fastjson.JSON;
import com.whackode.itrip.exception.TokenValidationFailedException;
import com.whackode.itrip.pojo.entity.User;
import com.whackode.itrip.util.constant.Constants;
import cz.mallat.uasparser.UserAgentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class TokenUtil {
    @Autowired
    private RedisUtils redisUtils;

    private String tokenPrefix = "token:";//统一加入 token前缀标识

    public String generateToken(String agent, User user) {
        try {
            UserAgentInfo userAgentInfo = UserAgentUtil.getUasParser().parse(
                    agent);
            StringBuilder sb = new StringBuilder();
            sb.append(tokenPrefix);//统一前缀
            if (userAgentInfo.getDeviceType().equals(UserAgentInfo.UNKNOWN)) {
                if (UserAgentUtil.CheckAgent(agent)) {
                    sb.append("MOBILE-");
                } else {
                    sb.append("PC-");
                }
            } else if (userAgentInfo.getDeviceType()
                    .equals("Personal computer")) {
                sb.append("PC-");
            } else
                sb.append("MOBILE-");
//			sb.append(user.getUserCode() + "-");
            sb.append(MD5Util.getMd5(user.getUserCode(),32) + "-");//加密用户名称
            sb.append(user.getId() + "-");
            sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                    + "-");
            sb.append(MD5Util.getMd5(agent, 6));// 识别客户端的简化实现——6位MD5码

            return sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void save(String token, User user) {
        if (token.startsWith(tokenPrefix+"PC-")) {
            redisUtils.putValue(token, JsonUtils.toJsonString(user), Constants.Token.SESSION_TIMEOUT);
        }
        else{
            redisUtils.putValue(token, JSON.toJSONString(user));// 手机认证信息永不失效
        }
    }

    public User load(String token) {
        return JSON.parseObject(redisUtils.getValue(token), User.class);
    }

    public void delete(String token) {
        if (redisUtils.getValue(token) !=null){
            redisUtils.delete(token);
        }
    }

    private boolean exists(String token) {
        return redisUtils.isExpire(token);
    }

    public String replaceToken(String agent, String token)
            throws TokenValidationFailedException {

        // 验证旧token是否有效
        if (!exists(token)) {// token不存在
            throw new TokenValidationFailedException("未知的token或 token已过期");// 终止置换
        }
        Date TokenGenTime;// token生成时间
        try {
            String[] tokenDetails = token.split("-");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            TokenGenTime = formatter.parse(tokenDetails[3]);
        } catch (ParseException e) {
            throw new TokenValidationFailedException("token格式错误:" + token);
        }

        long passed = Calendar.getInstance().getTimeInMillis()
                - TokenGenTime.getTime();// token已产生时间
        if (passed < Constants.Token.REPLACEMENT_PROTECTION_TIMEOUT * 1000) {// 置换保护期内
            throw new TokenValidationFailedException("token处于置换保护期内，剩余"
                    + (Constants.Token.REPLACEMENT_PROTECTION_TIMEOUT * 1000 - passed) / 1000
                    + "(s),禁止置换");
        }
        // 置换token
        String newToken = "";
        User user = this.load(token);
        long ttl = redisUtils.expire(token);// token有效期（剩余秒数 ）
        if (ttl > 0 || ttl == -1) {// 兼容手机与PC端的token在有效期
            newToken = this.generateToken(agent, user);
            this.save(newToken, user);// 缓存新token
            redisUtils.putValue(token,JSON.toJSONString(user), Constants.Token.REPLACEMENT_DELAY);// 2分钟后旧token过期，注意手机端由永久有效变为2分钟（REPLACEMENT_DELAY默认值）后失效
        } else {// 其它未考虑情况，不予置换
            throw new TokenValidationFailedException("当前token的过期时间异常,禁止置换");
        }
        return newToken;
    }

    public boolean validate(String agent, String token) {
        if (!exists(token)) {// token不存在
            return false;
        }
        try {
            Date TokenGenTime;// token生成时间
            String agentMD5;
            String[] tokenDetails = token.split("-");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            TokenGenTime = formatter.parse(tokenDetails[3]);
            long passed = Calendar.getInstance().getTimeInMillis()
                    - TokenGenTime.getTime();
            if(passed>Constants.Token.SESSION_TIMEOUT*1000)
                return false;
            agentMD5 = tokenDetails[4];
            if(MD5Util.getMd5(agent, 6).equals(agentMD5))
                return true;
        } catch (ParseException e) {
            return false;
        }
        return false;
    }
}
