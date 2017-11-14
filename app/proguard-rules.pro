# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\android_studio_sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志
-dontskipnonpubliclibraryclasses
-keepattributes SourceFile,LineNumberTable

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}

# keep 所有的 javabean
-keep public class com.hishixi.mentor.mvp.model.entity.**{*;}
-keep public class com.hishixi.mentor.mvp.model.**{*;}
-keep class com.hishixi.mentor.db.**{*;}
#-keep public class com.hishixi.mentor.mvp.model.LoginModel{*;}

#keep 所有utils
#-keep  class com.hishixi.mentor.utils.**{*;}
#-keep  class com.hishixi.mentor.net.**{*;}
#-keep  class com.hishixi.mentor.constants.**{*;}

# 保持 Serializable 不被混淆
-keep public class * implements java.io.Serializable {
    public *;
}

#如果引用了v4或者v7包
-dontwarn android.support.**
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-dontwarn android.support.v7.widget.**
-keep class android.support.v7.widget.** {*;}

#保持注解类不被混淆
-keepattributes *Annotation*

#不混淆httpmime
-keep class org.apache.http.** {*;}
-dontwarn org.apache.http.**

#zxing#
-keep class com.google.zxing.** {*;}
-dontwarn com.google.zxing.**

#universalimageloader
-dontwarn com.nostra13.universalimageloader.**
-keep class com.nostra13.universalimageloader.** {*;}

##如果使用了gson
# keep 泛型
-keepattributes Signature
-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}
-keep class sun.misc.Unsafe {*;}
-keep class com.antew.redditinpictures.library.imgur.** { *; }
-keep class com.antew.redditinpictures.library.reddit.** { *; }

# GreenDao配置
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties
# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use Rx:
-dontwarn rx.**

#bugly配置
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

# OkHttp配置
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keep class com.squareup.okhttp.** { *; }
-keep class okio.** { *; }
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-keep class com.bumptech.glide.integration.** { *; }
-dontwarn com.bumptech.glide.integration.**

#shareSDK配置
#-keep class cn.sharesdk.** { *; }
#-keep class com.sina.** { *; }
#-keep class **.R$.* { *; }
#-keep class **.R { *; }
#-dontwarn cn.sharesdk.**
#-dontwarn **.R$*
#-keep class m.framework.** { *; }

##nineoldandrois包
#-dontwarn com.nineoldandroids.**
#-keep class com.nineoldandroids.**{*;}

#webview和js交互
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keep class **.Webview2JsInterface { *; }  #保护WebView对HTML页面的API不被混淆
-keepclassmembers class * extends android.webkit.WebViewClient {  #如果你的项目中用到了webview的复杂操作 ，最好加入
     public void *(android.webkit.WebView,java.lang.String,android.graphics.Bitmap);
     public boolean *(android.webkit.WebView,java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebChromeClient {  #如果你的项目中用到了webview的复杂操作 ，最好加入
     public void *(android.webkit.WebView,java.lang.String);
}
# webview + js
# keep 使用 webview 的类
#-keepclassmembers class com.goldnet.mobile.activity.InfoDetailActivity {
#   public *;
#}
# keep 使用 webview 的类的所有的内部类
#-keepclassmembers   class com.goldnet.mobile.activity.InfoDetailActivity$*{
#    *;
#}

#环信客服
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**

# Huawei push
-keep class com.huawei.android.pushagent.** {*;}
-keep class com.huawei.android.pushselfshow.** {*;}
-keep class com.huawei.android.microkernel.** {*;}
-keep class com.baidu.mapapi.** {*;}
-keep class com.hianalytics.android.** {*;}
-dontwarn com.huawei.android.pushagent.**
-dontwarn com.huawei.android.pushselfshow.**
-dontwarn com.huawei.android.microkernel.**
-dontwarn com.github.mikephil.charting.data.**

#mipush
-keep class com.xiaomi.push.** {*;}
-dontwarn com.xiaomi.push.**
#-dontwarn com.xiaomi.push.service.a.a
-keepclasseswithmembernames class com.xiaomi.**{*;}
-keep public class * extends com.xiaomi.mipush.sdk.PushMessageReceiver
-dontwarn com.hyphenate.**

#不混淆org.apache.http.legacy.jar
-dontwarn android.net.compatibility.**
-dontwarn android.net.http.**
-dontwarn com.android.internal.http.multipart.**
-dontwarn org.apache.commons.**
-dontwarn org.apache.http.**
-keep class org.apache.http.**{*;}
-keep class android.net.compatibility.**{*;}
-keep class android.net.http.**{*;}
-keep class com.android.internal.http.multipart.**{*;}
-keep class org.apache.commons.**{*;}

#友盟统计混淆
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.shixi.gaodun.R$*{
public static final int *;
}

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#butterknife混淆
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#multidex混淆
-keep class android.support.multidex.** { *; }
-dontwarn android.support.multidex.**
#logger
-keep class com.orhanobut.logger.** { *; }
-dontwarn com.orhanobut.logger.**
#tinker
-keep class com.tencent.tinker.** { *; }
-dontwarn com.tencent.tinker.**

#微信
-keep class com.tencent.mm.opensdk.** { *;}
-keep class com.tencent.wxop.** { *;}
-keep class com.tencent.mm.sdk.** { *;}

#lambda表达式混淆
-dontwarn java.lang.invoke.*
# banner 的混淆代码
#-keep class com.youth.banner.** {*; }
#微信sdk
#-keep class com.tencent.mm.opensdk.** {
#   *;}
#-keep class com.tencent.wxop.** {
#   *;}
#-keep class com.tencent.mm.sdk.** {
#   *;}
# OkHttp3
-dontwarn okhttp3.logging.**
-dontwarn okhttp3.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
#-keepattributes Signature-keepattributes Exceptions
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontwarn com.netease.**
-dontwarn io.netty.**
-keep class com.netease.** {*;}
#如果 netty 使用的官方版本，它中间用到了反射，因此需要 keep。如果使用的是我们提供的版本，则不需要 keep
-keep class io.netty.** {*;}
#JPUSH
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *;}