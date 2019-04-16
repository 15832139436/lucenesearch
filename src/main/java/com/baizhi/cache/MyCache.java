package com.baizhi.cache;

import com.baizhi.jedisutil.JedisUtil;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheException;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

public class MyCache implements Cache {

    private final String id;
    private Map<Object, Object> cache = new HashMap();
    private Jedis jedis = JedisUtil.getJedis();

    /*
    * 获取缓存
    * */
    public Object getObject(Object key) {
        return this.cache.get(key);
    }
    /*
    * 删除单个缓存并且返回
    * */
    public Object removeObject(Object key) {

        return this.cache.remove(key);
    }
    /*
    * 清楚缓存
    * */
    public void clear() {

        this.cache.clear();
    }

    public ReadWriteLock getReadWriteLock() {

        return null;
    }

    public boolean equals(Object o) {
        if (this.getId() == null) {
            throw new CacheException("Cache instances require an ID.");
        } else if (this == o) {
            return true;
        } else if (!(o instanceof Cache)) {
            return false;
        } else {
            Cache otherCache = (Cache)o;
            return this.getId().equals(otherCache.getId());
        }
    }

    public int hashCode() {
        if (this.getId() == null) {
            throw new CacheException("Cache instances require an ID.");
        } else {
            return this.getId().hashCode();
        }
    }

    public MyCache(String id) {

        this.id = id;
    }

    public String getId() {

        return this.id;
    }

    public int getSize() {

        return this.cache.size();
    }

    public void putObject(Object key, Object value) {

        this.cache.put(key, value);
    }

}
