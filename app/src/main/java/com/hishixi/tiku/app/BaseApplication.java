package com.hishixi.tiku.app;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDexApplication;
import android.support.v4.BuildConfig;
import android.support.v4.content.LocalBroadcastManager;

import com.bumptech.glide.request.target.ViewTarget;
import com.hishixi.tiku.R;
import com.hishixi.tiku.db.entity.DaoMaster;
import com.hishixi.tiku.db.entity.DaoSession;
import com.hishixi.tiku.injector.component.DaggerApplicationComponent;
import com.hishixi.tiku.injector.module.ApplicationModule;
import com.liulishuo.filedownloader.FileDownloader;
import com.mcxiaoke.packer.helper.PackerNg;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;


/**
 * @author guolin
 */
public class BaseApplication extends MultiDexApplication {
    public static final String TAG = "BaseApplication";
    public static BaseApplication mApp;
    // 存放所有开启的activity,便于退出时使用
    public List<Activity> mActivities;
    public List<Activity> mCenterTaskActivitys;
    // 本地广播
    public LocalBroadcastManager mLocalBroadcastManager;
    //线程池
    public static ExecutorService executor = null;
    private com.hishixi.tiku.injector.component.ApplicationComponent applicationComponent;
    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        /**
         * 仅仅是缓存Application的Context，不耗时
         */
        FileDownloader.init(getApplicationContext());
        mActivities = new ArrayList<>();
        mCenterTaskActivitys = new ArrayList<>();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        initThreadPool();
        this.initializeInjector();
        this.initializeLeakDetection();
        initLogger();
        initDataBase();
//        initImageLoader(this);
        initBugly();
        fixGlide();
        initPackerNg();
//        initLeakCanary();
//        initTinker();
        initJpush();
    }

    /**
     * 初始化jpush推送
     */
    private void initJpush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    /**
     * 初始化Tinker热补丁
     */
    private void initTinker() {
        // 我们可以从这里获得Tinker加载过程的信息
//        if (BuildConfig.TINKER_ENABLE) {
//            ApplicationLike tinkerApplicationLike =
//                    TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
//
//            // 初始化TinkerPatch SDK
//            TinkerPatch.init(tinkerApplicationLike)
//                    .reflectPatchLibrary()
//                    .setPatchRollbackOnScreenOff(true)
//                    .fetchPatchUpdate(true)//如果设为true那么每次启动都会访问后台拉取补丁
//                    .setPatchRestartOnSrceenOff(true);
//
//        }

    }

    /**
     * 检测内存泄漏
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    /**
     * 初始化图片加载工具
     *
     * @param context context
     */
//    private void initImageLoader(Context context) {
//        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
//                context);
//        config.threadPriority(Thread.NORM_PRIORITY - 2);
//        config.denyCacheImageMultipleSizesInMemory();
//        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
//        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
//        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        config.writeDebugLogs(); // Remove for release app
//
//        // Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(config.build());
//
//    }

    /**
     * 初始化bug追踪
     */
    private void initBugly() {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy
                (getApplicationContext());
//        strategy.setAppChannel("myChannel");  //设置渠道
        strategy.setAppVersion(com.hishixi.tiku.utils.ActivityUtils.getVersionName());      //App的版本
        strategy.setAppPackageName("com.hishixi.tiku");  //App的包名
        CrashReport.setUserId(com.hishixi.tiku.utils.CacheUtils.getAccountId(getApplicationContext()));
        //本次启动后的异常日志用户ID都将是9527
        CrashReport.initCrashReport(getApplicationContext(), "6fcb182406", false);
    }

    /**
     * 初始化日志打印器
     */
    private void initLogger() {
        Logger
                .init("mentor")                 // default PRETTYLOGGER or use just
                // init()
//                .methodCount(2)                 // default 2
//                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)       // default LogLevel.FULL
                .methodOffset(2);
    }

    /**
     * 初始化PackerNg 打包 让友盟能读取到渠道名
     */
    private void initPackerNg() {
        // 如果没有使用PackerNg打包添加渠道，默认返回的是""
// com.mcxiaoke.packer.helper.PackerNg
        final String market = PackerNg.getMarket(this);
        Logger.d("market=======" + market);
// 或者使用 PackerNg.getMarket(Context,defaultValue)
// 之后就可以使用了，比如友盟可以这样设置
//        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this,
//                "56848577e0f55a974f00025e", market));
    }

    /**
     * 初始化数据库
     */
    private void initDataBase() {
        //创建数据库 hishiximentor.db
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "hishiximentor" +
                ".db", null);
        //获取可用数据库
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(database);
        //获取Dao对象管理者
        mDaoSession = daoMaster.newSession();
    }

    /**
     * 获取Dao对象管理者
     *
     * @return DaoSession
     */
    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * 修复glide的imageview.setTag（） bug
     */
    private void fixGlide() {
        ViewTarget.setTagId(R.id.glide_tag);
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public com.hishixi.tiku.injector.component.ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }

    /**
     * 初始化线程池
     */
    private void initThreadPool() {
        int cpuCores = com.hishixi.tiku.utils.ActivityUtils.getNumberOfCPUCores();
        executor = new ThreadPoolExecutor(cpuCores, cpuCores * 2 + 1,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());//设置核心线程数
    }

}
