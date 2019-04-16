package com.baizhi.dao;

import com.baizhi.entity.Product;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;

@CacheNamespace
public interface ProductMapper extends Mapper<Product> {
}
