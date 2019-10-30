package com.crawlerCarData.demo;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import com.crawlerCarData.bean.Brand1;
import com.crawlerCarData.bean.Brand2;
import com.crawlerCarData.bean.BrandCount;
import com.crawlerCarData.util.DownloadPicFromUrl;
import org.springframework.core.io.ClassPathResource;

/**
 * @ClassName BrandCount
 * @Author wb-lyc575020
 * @Date 2019/10/30 12:05
 * @Description 汽车品牌
 **/
public class JsoupDemo {
    public static void main(String[] args) throws IOException {
        List<BrandCount> brandCounts = new ArrayList<>();
        //ABCDEFGHIJKLMNOPQRSTUVWXY 可以遍历26个字母获取全部品牌数据;因为数据量较大,对网络要求过高;不建议这么操作
        String letter = "P";
        BrandCount brand1 = JsoupDemo1.getBrand1(letter);
        brandCounts.add(brand1);
        List<Brand1> carBrands = brand1.getCarBrands();
        System.out.println("开始下载log...");
        carBrands.forEach(brand11 -> {
            DownloadPicFromUrl.downloadPicture(brand11.getLogUrl(), brand11.getBrandName());
            brand11.setLogUrl("log/" + brand11.getBrandName() + ".png");
        });
        System.out.println("结束下载log...");
        System.out.println("保存数据开始");
        String str = JSON.toJSON(brandCounts).toString();
        FileWriter writer;
        try {
            Path rootLocation = Paths.get("car");
            if (Files.notExists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            URI uri = rootLocation.toUri();
            String basePath = uri.toURL().getPath();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
            String ldtStr = LocalDateTime.now().format(dtf);
            writer = new FileWriter(basePath + "\\carData" + ldtStr + letter + ".json");
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("保存数据结束");
        System.out.println("统计: " + letter);
        System.out.println("品牌数:" + carBrands.size());
        int carSeriesCount = 0;
        int carModelsCount = 0;
        for (Brand1 aa : carBrands) {
            List<Brand2> carSeries = aa.getCarSeries();
            carSeriesCount += carSeries.size();
            for (Brand2 bb : carSeries) {
                carModelsCount += bb.getCarModels().size();
            }
        }
        System.out.println("系列数:" + carSeriesCount);
        System.out.println("型号数:" + carModelsCount);

    }
}
