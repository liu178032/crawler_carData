package com.crawlerCarData.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.crawlerCarData.bean.Brand3;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JsoupDemo2 {
    public static List<Brand3> getString(String args) throws IOException {
        List<Brand3> brand3s = new ArrayList<>();
        System.out.println(args);
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.connect("https:" + args).timeout(999999).get();
        Element body = doc.body();
        //        加判断 判断新页面布局,旧页面布局
        Elements selectwra = body.select("div[class=wrapper] div[class=container]");
        if ("".equals(selectwra.text())) {
            //            旧布局
            Elements select1 = body.select("div[class=name] a");
            for (Element ele : select1) {
                Brand3 brand3 = new Brand3();
                brand3.setModelName(ele.text());
                System.out.println(ele.text());
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Document href = Jsoup.connect("https://www.autohome.com.cn/" + ele.attr("href")).timeout(999999).get();
                Element body1 = href.body();
                Elements select2 = body1.select("ul[class=baseinfo-list] li");
                System.out.println(select2.get(6).select("span").text());
                brand3.setDrive(select2.get(6).select("span").text());

                brand3s.add(brand3);
            }
        } else {
            //            新布局
            //        检索dl
            Elements select = body.select("div[class=spec-wrap active] dl dd");
            System.out.println(select.size());
            //        System.out.println(select.get(0));
            for (int i = 0; i < select.size(); i++) {
                Brand3 brand3 = new Brand3();
                Element element = select.get(i);
                Elements select1 = element.select("div[class=spec-name] div[class=name-param] p");
                //            三级品牌名字
                System.out.println(select1.get(0).text());
                brand3.setModelName(select1.get(0).text());
                Elements select2 = element.select("div[class=spec-name] div[class=name-param] p span");
                if (select2.size() >= 1) {
                    //            辅助参数一
                    System.out.println("    " + select2.get(0).text());
                    brand3.setDrive(select2.get(0).text());
                }
                if (select2.size() >= 2) {
                    //            辅助参数二
                    System.out.println("    " + select2.get(1).text());
                    brand3.setGear(select2.get(1).text());
                }
                Elements selectspan = element.select("div[class=spec-guidance] p[class=guidance-price] span");
                brand3.setPrice(selectspan.text());
                //            辅助参数三
                System.out.println("    " + selectspan.text());
                brand3s.add(brand3);
            }
            //        获取参数一
            String href = body.select("div[class=athm-title__name athm-title__name--blue] a").attr("href");
            String s = href.split("/")[1];
            Elements select1 = body.select("ul[class=dropdown-con] li");
            for (Element element : select1) {
                String a = element.select("a").attr("data-yearid");
                String s1 = "https://www.autohome.com.cn/ashx/series_allspec.ashx?s=" + s + "&y=" + a;
                System.out.println(s1);
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Document doc1 = Jsoup.connect("https://www.autohome.com.cn/ashx/series_allspec.ashx?s=" + s + "&y=" + a)
                    .timeout(999999).get();
                JSONObject jsonObject = JSON.parseObject(doc1.body().text());
                List<JSONObject> group = (List<JSONObject>)jsonObject.get("Spec");
                if (group != null) {
                    for (int i = 0; i < group.size(); i++) {
                        Brand3 brand3 = new Brand3();
                        System.out.println(group.get(i).get("Name"));
                        brand3.setModelName(group.get(i).get("Name").toString());
                        brand3.setDrive(group.get(i).get("DrivingModeName").toString());
                        brand3.setGear(group.get(i).get("Transmission").toString());
                        if (!group.get(i).get("Price").toString().contains("暂无")) {
                            brand3.setPrice(group.get(i).get("Price").toString());
                        }
                        brand3s.add(brand3);
                    }
                }
            }
        }
        return brand3s;

    }
}
