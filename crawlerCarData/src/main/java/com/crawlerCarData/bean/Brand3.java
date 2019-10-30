package com.crawlerCarData.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * @ClassName BrandCount
 * @Author wb-lyc575020
 * @Date 2019/10/30 12:05
 * @Description TODO
 **/
@Data
public class Brand3 implements Serializable {
    private static final long serialVersionUID = 4321521473165898621L;

    /**
     * 车型名称
     */
    private String modelName;

    /**
     * 驱动
     */
    private String drive;

    /**
     * 档位
     */
    private String gear;

    /**
     * 价格
     */
    private String price;

}
