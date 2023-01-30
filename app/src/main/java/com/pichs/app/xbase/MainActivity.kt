package com.pichs.app.xbase

import android.os.Bundle
import android.widget.Toast
import com.pichs.app.xbase.databinding.ActivityMainBinding
import com.pichs.base.binding.BindingActivity
import com.pichs.base.clickhelper.ClickHelper
import com.pichs.base.clickhelper.MultiClickHelper

class MainActivity : BindingActivity<ActivityMainBinding>() {
    override fun beforeOnCreate(savedInstanceState: Bundle?) {}
    override fun afterOnCreate() {
        binding.btn.setOnClickListener {
            Toast.makeText(applicationContext, "按钮", Toast.LENGTH_SHORT).show()
        }

        ClickHelper.clicks(binding.btn1) {
            Toast.makeText(applicationContext, "单点", Toast.LENGTH_SHORT).show()
        }

        MultiClickHelper.clicks(binding.btn2).setTimes(1).call { times ->
            Toast.makeText(applicationContext, "点了：${times}次", Toast.LENGTH_SHORT).show()
        }
    }
}