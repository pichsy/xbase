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

1. 继承BindingActivity实现自己的BaseActivity
2. 继承BindingFragment实现自己的BaseFragment
3. 继承BindingLazyFragment实现自己的BaseLazyFragment
4. 常用工具类自己摸索，多读源码，多看注释

### gradle引入

最新版本:[![](https://jitpack.io/v/com.gitee.pichs/xbase.svg)](https://jitpack.io/#com.gitee.pichs/xbase)
      
       
        implementation 'com.gitee.pichs:xbase:2.0.4'
       

## 升级日志

##### 2.0.4版本 GsonUtils 创建的Gson对象支持序列化时忽略@Expose注解的功能

##### 2.0.3版本 修复LazyFragment继承的方法的open属性

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


