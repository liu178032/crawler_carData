package com.crawlerCarData.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @ClassName BrandCount
 * @Author wb-lyc575020
 * @Date 2019/10/30 12:05
 * @Description 汽车品牌
 **/
@Data
public class Brand1 implements Serializable {
    private static final long serialVersionUID = 1995753861393803576L;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌log地址
     */
    private String logUrl;

    /**
     * 当前品牌下的系列
     */
    private List<Brand2> carSeries;

}
