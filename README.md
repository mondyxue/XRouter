XRouter
===
###一种基于[ARouter](https://github.com/alibaba/ARouter)的使用封装方案，实现对ARouter的Retrofit2式使用。

>#####Arouter是阿里巴巴开源的Android平台中对页面、服务提供路由功能的中间件，**没用过的务必点击**[传送门](https://github.com/alibaba/ARouter)

---

基础功能
---
#### 1. 依赖配置
```groovy
android {
    defaultConfig {
	    ...
	    javaCompileOptions {
	        annotationProcessorOptions {
	    	    arguments = [ moduleName : project.getName() ]
	        }
	    }
    }
}
dependencies {
    //ARouter
    compile 'com.alibaba:arouter-api:x.x.x'
    annotationProcessor 'com.alibaba:arouter-compiler:x.x.x'
    //XRouter
    compile 'com.mondyxue:xrouter:1.0-SNAPSHOT@aar'
}
repositories {
    maven {
        //XRouter
        url "https://raw.githubusercontent.com/MondyXue/Maven/master"
    }
}
```
#### 2. 给目标页面添加注解
```java
import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "/page/browser")
public class WebViewActivity extends BaseActivity{
    ...
}
```
#### 3. 在Application中添加初始化代码
```java
//初始化uri信息
XRouter.setScheme("xrouter");
XRouter.setAuthority("mondyxue.github.io");

//XRouter初始化
XRouter.init(DemoApplication.this);
```
#### 4. 声明Navigator接口
```java
import com.mondyxue.xrouter.annotation.Extra;
import com.mondyxue.xrouter.annotation.Route;

public interface WebNavigator{
    @Route(path = "/page/browser")
    void openUrl(@Extra("url") String url);
}
```
#### 5. 发起路由
```java
XRouter.getRouter()
       .create(WebNavigator.class)
       .openUrl("https://github.com/Alibaba/ARouter");
```

---

其它使用
---
>XRouter基于ARouter提供了针对几个常用场景的解决方案
#### **1. Navigator进阶用法**
##### a. startActivityForResult
```java
//声明返回类型为ActivityNavigator<T>，T为需要解析的回传数据类型
@Route(path = "/page/login")
ActivityNavigator<UserInfo> toLoginFragment();

//复写Activity和Fragment基类中的onActivityResult方法
public abstract class BaseActivity extends FragmentActivity{
    ...
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //XRouter处理回调逻辑
        XRouter.getRouter()
               .getActivityManager()
               .onActivityResult(BaseActivity.this, requestCode, resultCode, data);
    }
}
//实现Callback
public abstract class UserInfoCallback extends RouteCallback<UserInfo>{
    @Override public UserInfo parseData(int requestCode, int resultCode, Intent data){
        return (UserInfo) data.getSerializableExtra(Extras.UserInfo);
    }
}
//路由发起
XRouter.getRouter()
        create(DemoNavigator.class)
        toLoginFragment()
        startActivityForResult(new UserInfoCallback(){
           @Override public void onResponse(@NonNull UserInfo data){
               Toast.makeText(MainActivity.this, "login success: " + data.toString(), Toast.LENGTH_SHORT).show();
            }
        });
```

##### b. 获取服务
```java
//声明
@Route(path = "/service/userService")
UserService getUserService();

//创建navigator
DemoNavigator navigator = XRouter.getRouter().create(DemoNavigator.class);

//获取UserService
UserService userService = navigator.getUserService();
boolean isLogin = userService.isLogin();
UserInfo userInfo = userService.getUserInfo();

//获取Logger
Logger logger = navigator.getLogger();
logger.e("tag", "msg...");
```
##### b. 如果还不满足，继续组合打法
```java
import com.mondyxue.xrouter.navigator.Navigator;

//声明返回类型为Navigator
@Route(path = "/page/login")
Navigator toLoginFragment();

//创建Navigator
Navigator navigator = XRouter.getRouter()
                            .create(DemoNavigator.class)
                            .toLoginFragment();
//通过Navigator调用支持方法
Uri uri = navigator.uri();
Intent intent = navigator.intent();
Fragment fragment = navigator.fragment();
IProvider provider = navigator.service();
navigator.startActivity();
```
#### **2. RouteType**

```java
import com.mondyxue.xrouter.constant.RouteType;
import com.alibaba.android.arouter.facade.annotation.Route;

//在ARouter的Route注解中
//RouteType中有几个辅助flag值，可用于ARouter的Route注解中的extras值Route(extras=...)

RouteType.GreenChannel//绿色通道，不经过拦截器
RouteType.Fragment//Fragment标记
RouteType.Activity//Activity标记
RouteType.Service//标记是否为实现了IProvider的服务类
RouteType.WithinTitlebar//标记此页面是否已有标题栏（Toolbar等）
RouteType.Login//标记访问此页面是否需要登录权限
RouteType.Main//标记此页面是否为App首页

//组合打法
RouteType.TitlebarFragment = Fragment | WithinTitlebar;
RouteType.LoginActivity = Activity | Login;

```
#### **2. 登录拦截**
##### a. 配置extras标记
```java
@Route(path = "/page/userInfo", extras = RouteType.LoginFragment)
public class UserInfoFragment extends BaseFragment{
    ...
}
```
##### b. 实现登录拦截器
```java
@Interceptor(priority = 4, name = "LoginInterceptor")
public class LoginInterceptor extends com.mondyxue.xrouter.interceptor.LoginInterceptor implements IInterceptor{

    //返回当前用户是否已登录
    @Override public boolean isLogin(){
        return XRouter.getRouter()
                      .create(DemoNavigator.class)
                      .getUserService()
                      .isLogin();
    }

    //调用登录服务，在登录回调中执行原拦截器回调
    @Override protected void onInterrupt(final Postcard postcard, final InterceptorCallback callback){
        XRouter.getRouter()
               .create(DemoNavigator.class)
               .toLoginFragment()
               .startActivityForResult(new UserInfoCallback(){
                   @Override public void onResponse(@NonNull UserInfo data){
                       postcard.withSerializable(Extras.UserInfo, data);
                       callback.onContinue(postcard);
                   }
                   @Override public void onError(Throwable throwable){
                       callback.onInterrupt(throwable);
                   }
                   @Override public void onCancel(){
                       callback.onInterrupt(new RuntimeException("login cancel!"));
                   }
               });
    }

}
```
#### **2. Fragment拦截**
##### a. 配置extras标记
```java
@Route(path = "/page/text", extras = RouteType.Fragment)
public class TextFragment extends BaseFragment{
    ...
}
```
##### b. 配置Fragment容器Activity
```java
@Route(path = "/page/contanier", extras = RouteType.Activity | RouteType.GreenChannel)
public class ContanierActivity extends BaseActivity{

    @Override protected int getRootLayout(){
        //RouteType.Titlebar标记值可通过此方式获取
        boolean withinTitlebar = getIntent().getBooleanExtra(RouteExtras.WithinTitlebar, false);
        //根据标记值决定容器的布局是否有标题栏
        return withinTitlebar ? R.layout.activity_contanier
                              : R.layout.activity_contanier_within_titlebar;
    }

    @Override protected void init(){

        Intent intent = getIntent();

        boolean withinTitlebar = intent.getBooleanExtra(RouteExtras.WithinTitlebar, false);
        if(!withinTitlebar){
            //判断并设置Title
            String title = intent.getStringExtra(RouteExtras.Title);
            ((TextView) findViewById(R.id.tv_title)).setText(title);
        }

        //获取拦截器中传过来的Fragment路由path
        String path = intent.getStringExtra(RouteExtras.PathTo);
        if(!TextUtils.isEmpty(path)){
        
            //使用XRouter根据path获取fragment实例
            final Fragment fragment = XRouter.getRouter().build(path).navigator().fragment();
            if(fragment != null){
                fragment.setArguments(intent.getExtras());
                runOnUiThread(new Runnable(){
                    @Override public void run(){
                        getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fl_contanier, fragment)
                                .hide(fragment)
                                .show(fragment)
                                .commitAllowingStateLoss();
                    }
                });
                return;
            }

        }

        finish();
    }

}
```
##### c. 实现Fragment拦截器
```java
@Interceptor(priority = 8, name = "FragmentInterceptor")
public class FragmentInterceptor extends com.mondyxue.xrouter.interceptor.FragmentInterceptor implements IInterceptor{
    @Override protected String getFragmentContainerPath(){
        //返回Fragment容器Activity路径
        return "/page/contanier";
    }
}
```
##### d. 还有个MainIntercepter，用处应该不大，就不在文档写了

---

混淆
---

如果你使用混淆并且用到Fragment拦截器的话，需要在混淆配置中添加以下规则:
```
#XRouter:使用Fragment拦截器
-keep class com.alibaba.android.arouter.facade.Postcard{*;}
```

---

后话
---
小弟不才，第一次分享代码到Github，码代码这茬，一个人容易出事，而且项目来不及写注释，所以特此贴上Q群二维码，如果大家有什么建议和槽点，欢迎能够多多交流。

[Demo下载](https://raw.githubusercontent.com/MondyXue/XRouter/master/static/demo.apk)

![](https://raw.githubusercontent.com/MondyXue/XRouter/master/static/qrcode_xrouter_group.png)
