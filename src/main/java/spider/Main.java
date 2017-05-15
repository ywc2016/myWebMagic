package spider;

import us.codecraft.webmagic.Spider;

/**
 * Created by ywcrm on 2017/5/12.
 */
public class Main {
    public static void main(String[] args) {
        xiaomiNote2Main();
    }

    public static void xiaomi6Main() {
        Xiaomi6PageProcessor xiaomi6PageProcessor = new Xiaomi6PageProcessor();
        long startTime, endTime;
        System.out.println("【爬虫开始】请耐心等待一大波数据到你碗里来...");
        startTime = System.currentTimeMillis();
        // 从论坛首页开始抓，开启5个线程，启动爬虫
        Spider.create(xiaomi6PageProcessor).addUrl(xiaomi6PageProcessor.getFirstSite()).thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】耗时约"
                + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");
    }

    public static void xiaomiHomePageMain() {
        XiaomiHomePageProcessor xiaomiHomePageProcessor = new XiaomiHomePageProcessor();
        long startTime, endTime;
        System.out.println("【爬虫开始】请耐心等待一大波数据到你碗里来...");
        startTime = System.currentTimeMillis();
        // 从论坛首页开始抓，开启5个线程，启动爬虫
        Spider.create(xiaomiHomePageProcessor).addUrl(xiaomiHomePageProcessor.getFirstSite()).thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】耗时约"
                + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");
    }

    public static void xiaomiNote2Main() {
        XiaomiNote2Processor xiaomiNote2Processor = new XiaomiNote2Processor();
        long startTime, endTime;
        System.out.println("【爬虫开始】请耐心等待...");
        startTime = System.currentTimeMillis();
        // 从论坛首页开始抓，开启5个线程，启动爬虫
        Spider.create(xiaomiNote2Processor).addUrl(xiaomiNote2Processor.getFirstSite()).thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】耗时约"
                + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");
    }

}
