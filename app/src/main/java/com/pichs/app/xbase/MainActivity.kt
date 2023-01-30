package com.pichs.app.xbase

import android.os.Bundle
import android.widget.Toast
import com.pichs.app.xbase.databinding.ActivityMainBinding
import com.pichs.base.binding.BindingActivity
import com.pichs.base.cache.CacheHelper
import com.pichs.base.clickhelper.ClickHelper
import com.pichs.base.clickhelper.MultiClickHelper

class MainActivity : BindingActivity<ActivityMainBinding>() {
    override fun beforeOnCreate(savedInstanceState: Bundle?) {}
    override fun afterOnCreate() {
        binding.btn.setOnClickListener {
            Toast.makeText(applicationContext, "按钮", Toast.LENGTH_SHORT).show()
        }

        ClickHelper.clicks(binding.btn1) {
            CacheTestHelper.get().setString("存储一个字符串","我是老da")
//            CacheHelper.get().setString("存储一个字符串","我是老六")
            Toast.makeText(applicationContext, "单点", Toast.LENGTH_SHORT).show()
        }

        MultiClickHelper.clicks(binding.btn2).setTimes(1).call { times ->
//           val str=  CacheHelper.get().getString("存储一个字符串","我是老八")
           val str=  CacheTestHelper.get().getString("存储一个字符串","我是老i9")
            Toast.makeText(applicationContext, "点了：${times}次+$str", Toast.LENGTH_SHORT).show()
        }
    }
}