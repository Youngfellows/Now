package top.wefor.now.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class Constants {

    public static final String PREFS_NAME = "now";
    public static final String MY_EMAIL_QQ = "315721484@qq.com";
    public static final String MY_EMAIL_GOOGLE = "XunMengWinter@gmail.com";
    public static final String JAVA_SCRIPT_ENABLED = "isJavaScriptEnabled";
    public static final String IS_FIRST = "isFirst";

    public static final String COVER_IMAGE = "coverImage";
    public static final String HEAD_IMAGES = "headImages";
    public static final String DIR = "NowView";
    public static final String IMAGE_DIR = DIR + File.separator + "image";
    public static final String COVER_IMAGE_PATH = IMAGE_DIR + File.separator + "cover.jpg";
    public static final String WEB_CACHE_DIR = "webDetail";
    public static final String REALM_PRIMARY_KEY = "pk";


    public static final String COVER_SOURCE = "cover_source";
    public static final int TYPE_NG = 0;
    public static final int TYPE_BD = 1;
    public static final int TYPE_MAC = 2;
    public static final int TYPE_COLOR = 3;

    public static final int PAGE_COUNT = 7;
    public static final int PROFILE_SETTING = 1;

    // WeChat needs only sign, QQ don't need
    public static final String QQ_APP_ID = "1104867379";
    public static final String WECHAT_APP_ID = "wx8a7d12758ddd5649";
    public static final String WECHAT_APP_SECRET = "d4624c36b6795d1d99dcf0547af5443d";

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);


    // requestCode
    public static final int WELCOME_ACTIVITY = 11;

    public static final int LIST_PAGE_SIZE = 7;
    public static final int LIST_FIRST_PAGE = 1;
}