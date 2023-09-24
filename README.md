# xbase

- 基于kotlin java混编

1. BindingActivity
2. BindingFragment
3. BindingLazyFragment
4. 使用的utils工具等，点击防重，多次点击监听，点击音效，常用工具等
5. 此框架用于快速开发，基础模板。
6. 栈的管理 StackManager， 继承BindingActivity即可
7. 栈的管理 也可以参考 BindingActivity, 让自己的基类（eg:xxxBaseActivity）继承IStackChild 实现方法即可，灵活



## 使用
- 建议将源码下载下载放到自己工程里，方便自己修改，因为可能会随时变化，更方便自己维护。
- 此基础库，只是提供一些基础的功能，方便自己快速开发，可以直接引用，也可以下载后修改。

1. 继承BindingActivity实现自己的BaseActivity
2. 继承BindingFragment实现自己的BaseFragment
3. 继承BindingLazyFragment实现自己的BaseLazyFragment
4. 常用工具类自己摸索，多读源码，多看注释

### gradle引入

最新版本:[![](https://img.shields.io/maven-central/v/io.github.pichsy/xbase)](https://img.shields.io/maven-central/v/io.github.pichsy/xbase)
      
       
            implementation 'io.github.pichsy:xbase:3.0.0'

### 第三方库的引入（必须）
- 下方的库需要自己引入
- 自己可根据最新版本进行 升级下方的库

```groovy

    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.annotation:annotation:1.6.0'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.squareup.okio:okio:3.2.0'
    
    // MMKV 代替SharedPreference, 强力推荐
    implementation 'com.tencent:mmkv-static:1.2.12'
    implementation 'com.google.code.gson:gson:2.10.1'
    implementation 'com.github.bumptech.glide:glide:4.15.1'
    
    // 协程
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1'
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1"
    implementation 'com.github.bumptech.glide:okhttp3-integration:4.9.0'


    implementation 'com.github.getActivity:EasyWindow:10.3'
    implementation 'com.gitee.pichs:xwidget:3.2.3'


```
       

## 升级日志

##### 3.0.0版本 由于jitpack仓库对gradle高版本不友好，所有改为用 maven中央仓库
-  优化代码，增加扩展方法，更实用，功能更全，也更简单。

##### 2.0.4版本 优化TimerManager的功能。防止重复开启引起的崩溃。

##### 2.0.3版本 修复LazyFragment继承的方法的open属性，GsonUtils 创建的Gson对象支持序列化时忽略@Expose注解的功能

##### 2.0.2版本 优化工具类，
1. 新增 BindingActivity方法给自己的BaseActivity用的。
2. 工具类方法优化
3. 工具类新增扩展函数。Int.dp等。

##### 2.0.1版本 优化工具类

##### 2.0.0版本，破坏性升级

- 开发过程中发现，很多东西都用不着，且kotlin盛行的当下，java有些落后，所以升级成kotlin版本
- 剔除mvp模式
- 增加utils，合并xutils工程到此项目中。废弃 xutils项目
- 开发中发现很多东西都很累赘，并且很难用到，造成代码冗余，所以精简项目，只保留实用的代码。其他代码建议写到自己项目的base中去，因为可能会随时变化，更方便自己维护。

##### 1.1版本 常规升级，老项目请最高引用到此版本

- 停止维护


