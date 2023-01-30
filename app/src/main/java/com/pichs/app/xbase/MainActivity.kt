package com.pichs.app.xbase

import android.os.Bundle
import com.pichs.app.xbase.databinding.ActivityMainBinding
import com.pichs.base.binding.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>() {
    override fun beforeOnCreate(savedInstanceState: Bundle?) {}
    override fun afterOnCreate() {
        binding.btn.setOnClickListener {

        }

    }
}