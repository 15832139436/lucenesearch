package com.baizhi.jedisutil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class JedisUtil {

    private static Properties ps = new Properties();
    private static JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

    static{
        InputStream is = JedisUtil.class.getResourceAsStream("jedis.properties");
        try {
            ps.load(is);
            jedisPoolConfig.setMaxIdle(Integer.parseInt((String) ps.get("maxIdle")));
            jedisPoolConfig.setMaxTotal(Integer.parseInt((String) ps.get("maxTotal")));
            jedisPoolConfig.setMaxWaitMillis(Integer.parseInt((String) ps.get("maxWaitMillis")));
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Jedis getJedis(){
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,"192.168.50.128",6379);
        return jedisPool.getResource();
    }


}
