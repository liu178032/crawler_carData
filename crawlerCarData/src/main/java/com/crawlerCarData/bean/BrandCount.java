package com.crawlerCarData.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;


/**
 * @ClassName BrandCount
 * @Author wb-lyc575020
 * @Date 2019/10/30 12:05
 * @Description 字母开头品牌
 **/
@Data
public class BrandCount implements Serializable {
    private static final long serialVersionUID = 3867212093286336773L;

    /**
     * 开头字母
     */
    private String bigLetter;

    /**
     * 开头字母的汽车品牌
     */
    private List<Brand1> carBrands;

}
