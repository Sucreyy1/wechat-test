package com.test;


import com.test.utils.WechatUtils;
import org.jsoup.nodes.Document;

public class MainTest {

//    public static final String URL = "https://mp.weixin.qq.com/s/4tmGNTeH8rbse3N7WgZGuA";

    public static final String URL = "https://mp.weixin.qq.com/s/O6KLFtP4D4uhNMjZp8YWbg";

    public static void main(String[] args) {
        Document d = WechatUtils.getSourceCodeByUrl(URL);
        String coverImgUrl = WechatUtils.getCoverImgUrl(d);
        String titleText = WechatUtils.getTitleText(d);
        String contentHtml = WechatUtils.getContentHtml(d);
        String pulishTime = WechatUtils.getPulishTime(d);
        String contentText = WechatUtils.getContentText(d);
        String videoUrl = WechatUtils.getVideoUrl(d);
        System.out.println(coverImgUrl);
        System.out.println(titleText);
        System.out.println(contentHtml);
        System.out.println(pulishTime);
        System.out.println(contentText);
        System.out.println(videoUrl);
    }


}
