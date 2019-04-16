package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="t_product")
public class Product {

    @Id
    @Excel(name="id")
    private String id;
    @Excel(name="名字")
    private String name;
    @Excel(name="价格")
    private Double price;
    private String description;
    @Excel(name="图片",type=2)
    private String img_path;
    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name="上传时间",databaseFormat = "yyyy-MM-dd HH:mm:ss")
    private Date uptime;
    private String city;

}
