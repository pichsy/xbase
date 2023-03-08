package com.pichs.app.xbase

import android.os.Bundle
import android.widget.Toast
import com.pichs.app.xbase.databinding.ActivityMainBinding
import com.pichs.base.binding.BindingActivity
import com.pichs.base.cache.CacheHelper
import com.pichs.base.clickhelper.ClickHelper
import com.pichs.base.clickhelper.FastClickHelper
import com.pichs.base.clickhelper.MultiClickHelper
import com.pichs.base.imageloader.ImageLoader
import com.pichs.base.utils.GsonUtils
import kotlin.text.Typography.times

class MainActivity : BindingActivity<ActivityMainBinding>() {
    override fun beforeOnCreate(savedInstanceState: Bundle?) {}
    override fun afterOnCreate() {
        binding.btn.setOnClickListener {
            Toast.makeText(applicationContext, "普通点击0", Toast.LENGTH_SHORT).show()
            val bean = AAB(
                "你少", "说话"
            )
            CacheHelper.get().setObject("mmm_obj", bean)
        }

        ClickHelper.clicks(binding.btn1) {
            Toast.makeText(applicationContext, "单个防重点击1", Toast.LENGTH_SHORT).show()
            val abb = CacheHelper.get().getObject<AAB>("mmm_obj", null)
            Toast.makeText(applicationContext, GsonUtils.toJson(abb), Toast.LENGTH_SHORT).show()
        }

        FastClickHelper.clicks(binding.btn3) {
            Toast.makeText(applicationContext, "全局防重点击3", Toast.LENGTH_SHORT).show()
        }

        FastClickHelper.clicks(binding.btn4) {
            Toast.makeText(applicationContext, "全局防重点击4", Toast.LENGTH_SHORT).show()
        }

        MultiClickHelper.clicks(binding.btn2).setTimes(1).call { times ->
            Toast.makeText(applicationContext, "点击了${times}次", Toast.LENGTH_SHORT).show()
        }

        ImageLoader.with()
            .load("https://img0.baidu.com/it/u=189649806,2789154204&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500")
            .diskCacheStrategy(ImageLoader.DiskCacheStrategy.ALL)
            .into(binding.ivImg)

        ImageLoader.with()
            .load("https://img0.baidu.com/it/u=2335072147,3242329848&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500")
            .into(binding.ivMmm)

        ImageLoader.with()
            .load("https://img2.baidu.com/it/u=3170243357,3097706939&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800")
            .circleCrop()
            .into(binding.ivCircle)
    }
}