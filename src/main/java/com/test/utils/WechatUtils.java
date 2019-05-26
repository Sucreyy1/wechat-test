package com.test.utils;

import com.test.constant.Constant;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.nodes.Element;
import org.slf4j.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 微信公众号工具类
 */
public class WechatUtils {

    private static Logger logger = LoggerFactory.getLogger(WechatUtils.class);

    private static final String COVER_IMAGE_PATTERN = Constant.WECHAT_COVER_IMAGE_NAME+"\\s*=\\s*\"([^\"]+)";

    private static final String PUBLISH_TIME_PATTERN = Constant.WECHAT_PUBLISH_TIME+"\\s*=\\s*\"([^\"]+)";

    private static Pattern coverImagePattern = Pattern.compile(COVER_IMAGE_PATTERN);

    private static Pattern publishTimePattern = Pattern.compile(PUBLISH_TIME_PATTERN);

    /**
     * 获取文章标题
     * @param document
     * @return
     */
    public static String getTitleText(Document document){
        return document.select(Constant.WECHAT_TITLE).text();
    }

    /**
     * 获取文字内容
     * @param document
     * @return
     */
    public static String getContentText(Document document){
        return document.select(Constant.WECHAT_CONTENT).text();
    }

    /**
     * 获取图文内容的html
     * @param document
     * @return
     */
    public static String getContentHtml(Document document){
        // 公众号源码是用"data-src"来进行资源引用，我们要获取并展示原内容，需要替换为src
        return document.select(Constant.WECHAT_CONTENT).html().replace(Constant.WECHAT_ORIGIN_SRC,Constant.WECHAT_REPLACE_SRC);
    }

    /**
     * 获取文章发布时间
     * @param document
     * @return
     */
    public static String getPulishTime(Document document){
        Matcher matcher = publishTimePattern.matcher(document.data());
        if (matcher.find()){
            return matcher.group().split("=")[1].replace("\"","").trim();
        }
        return null;
    }

//    /**
//     * 获取文章封面图
//     * @param document
//     * @return
//     */
//    public static String getCoverImgUrl(Document document) {
//        //todo 待优化，有没有更好的获取方式
//        String url = null;
//        // 获取所有script标签
//        Elements scripts = document.getElementsByTag("script");
//        for (Element element : scripts) {
//            // 如果是包含封面url的js片段，取出var数组，找到key=msg_cdn_url的value
//            if (element.data().contains(Constant.WECHAT_COVER_IMAGE_NAME)) {
//                String[] data = element.data().split("var");
//                for (String a : data) {
//                    if (a.contains(Constant.WECHAT_COVER_IMAGE_NAME) && a.contains("=")) {
//                        url = a.split("=")[1].replace("\"", "").trim();
//                    }
//                }
//            }
//        }
//        return url;
//    }

    /**
     * 获取文章封面图
     * @param document
     * @return
     */
    public static String getCoverImgUrl(Document document) {
        String data = document.body().data();
        Matcher matcher = coverImagePattern.matcher(data);
        if (matcher.find()){
            return matcher.group().split("=")[1].replace("\"","").trim();
        }
        return null;
    }

    /**
     * 获取视频链接
     * @param document
     * @return
     */
    public static String getVideoUrl(Document document){
        Element videoElements = document.select(Constant.WECHAT_VIDEO_TAG).first();
        return videoElements.attr(Constant.WECHAT_ORIGIN_SRC);
    }

    /**
     * 获取公众号文章链接的源码
     * @param webUrl
     * @return
     */
    public static Document getSourceCodeByUrl(String webUrl){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(webUrl).build();
        Call call = client.newCall(request);
        try {
            Response res = call.execute();
            if (res.isSuccessful() && res.code() == 200)
                return Jsoup.parse(res.body().string());
            else
                logger.warn("请求失败,code:{},message:{}",res.code(),res.message());
        } catch (Exception e) {
            logger.error("获取公众号文章失败...",e);
        }
        return null;
    }
}
