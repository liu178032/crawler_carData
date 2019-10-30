package com.crawlerCarData.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @ClassName BrandCount
 * @Author wb-lyc575020
 * @Date 2019/10/30 12:05
 * @Description 汽车品牌系列
 **/
@Data
public class Brand2 implements Serializable {
    private static final long serialVersionUID = 5876614435257955560L;

    /**
     * 系列名称
     */
    private String seriesName;

    /**
     * 当前系列下的具体车型
     */
    private List<Brand3> carModels;
}
