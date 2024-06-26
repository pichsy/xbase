package com.pichs.app.xbase

import android.widget.Toast
import com.pichs.app.xbase.baselibs.BaseActivity
import com.pichs.app.xbase.databinding.ActivityMainBinding
import com.pichs.xbase.cache.CacheHelper
import com.pichs.xbase.clickhelper.ComboClickHelper
import com.pichs.xbase.clickhelper.FastClickHelper
import com.pichs.xbase.clickhelper.MultiClickHelper
import com.pichs.xbase.imageloader.ImageLoader

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun afterOnCreate() {
        binding.btn.setOnClickListener {
            Toast.makeText(applicationContext, "普通点击0", Toast.LENGTH_SHORT).show()
            val bean = AAB(
                "你少", "说话"
            )
            CacheHelper.get().setObject("mmm_obj", bean)
        }

        FastClickHelper.clicks(binding.btn1) {
            val aab = CacheHelper.get().getObject("mmm_obj", AAB::class.java, AAB())
            Toast.makeText(applicationContext, aab?.title + aab?.name, Toast.LENGTH_SHORT).show()
        }

        FastClickHelper.clicks(binding.btn3) {
            Toast.makeText(applicationContext, "全局防重点击3", Toast.LENGTH_SHORT).show()
        }

        FastClickHelper.clicks(binding.btn4) {
            Toast.makeText(applicationContext, "全局防重点击4", Toast.LENGTH_SHORT).show()
        }

        MultiClickHelper.clicks(binding.btn2).setTimeMills(1000).call { times ->
            Toast.makeText(applicationContext, "点击了${times}次", Toast.LENGTH_SHORT).show()
        }

        ComboClickHelper.clicks(binding.btnCombo).setIntervalTime(300).call { times ->
            binding.btnCombo.text = "$times"
        }

        ImageLoader.with()
            .load("https://img0.baidu.com/it/u=189649806,2789154204&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500")
            .diskCacheStrategy(ImageLoader.DiskCacheStrategy.ALL)
            .into(binding.ivImg)

        ImageLoader.with()
            .load("https://img0.baidu.com/it/u=2335072147,3242329848&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500")
            .intoBackground(binding.ivMmm)

        ImageLoader.with()
            .load("https://img2.baidu.com/it/u=3170243357,3097706939&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800")
            .circleCrop()
            .into(binding.ivCircle)
    }
}