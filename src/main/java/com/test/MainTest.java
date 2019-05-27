package com.test;


import com.test.utils.WechatUtils;
import org.jsoup.nodes.Document;

public class MainTest {

//    // 个人公众号文章(成功获取封面图和文章)
//    public static final String URL = "https://mp.weixin.qq.com/s/4tmGNTeH8rbse3N7WgZGuA";
//    // 个人公众号视频测试文章(失败。源码结构不同，不知道是不是上传方式造成)
//    public static final String URL = "https://mp.weixin.qq.com/s/hpvKt0fY76AJ9sxVp-bINA";
//    // 第三方公众号图文+视频文章（成功获取封面图，视频和图文）
//    public static final String URL = "https://mp.weixin.qq.com/s/O6KLFtP4D4uhNMjZp8YWbg";
    // 第三方公众号图文文章(成功获取封面图和图文)
    public static final String URL = "https://mp.weixin.qq.com/s/6vPCduJ9vEj_DVXY9BZqxw";

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
