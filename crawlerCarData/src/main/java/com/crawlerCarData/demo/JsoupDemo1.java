package com.crawlerCarData.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.crawlerCarData.bean.Brand1;
import com.crawlerCarData.bean.Brand2;
import com.crawlerCarData.bean.Brand3;
import com.crawlerCarData.bean.BrandCount;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupDemo1 {
    public static BrandCount getBrand1(String args) throws IOException {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BrandCount brandCount = new BrandCount();
        brandCount.setBigLetter(args);
        Document doc = Jsoup.connect("https://www.autohome.com.cn/grade/carhtml/" + args + ".html").timeout(80000)
            .get();
        Element body = doc.body();
        Elements dl = body.select("dl");
        System.out.println(dl.size());
        List<Brand1> brand1s = new ArrayList<Brand1>();
        for (int i = 0; i < dl.size(); i++) {
            Brand1 brand1 = new Brand1();
            Element element = dl.get(i);
            Elements select = element.select("dt div a");
            Elements select1 = element.select("dt a img");
            //            一级品牌名
            System.out.println(select.text());
            brand1.setBrandName(select.text());
            //            一级品牌LOG
            System.out.println(select1.attr("src"));
            brand1.setLogUrl(select1.attr("src"));

            Elements select2 = element.select("dd ul li h4");
            List<Brand2> brand2s = new ArrayList<>();
            for (int j = 0; j < select2.size(); j++) {
                Brand2 brand2 = new Brand2();
                List<Brand3> string = JsoupDemo2.getString(select2.get(j).select("a").attr("href"));
                brand2.setSeriesName(select2.get(j).text());
                brand2.setCarModels(string);
                brand2s.add(brand2);
                System.out.println("    " + select2.get(j).text() + " **********   " + select2.get(j).select("a")
                    .attr("href"));
            }
            brand1.setCarSeries(brand2s);
            brand1s.add(brand1);
        }
        brandCount.setCarBrands(brand1s);
        return brandCount;
    }
}
