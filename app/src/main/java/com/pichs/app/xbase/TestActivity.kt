package com.pichs.app.xbase

import android.os.Bundle
import android.view.View
import com.pichs.app.xbase.databinding.ActivityMainBinding
import com.pichs.app.xbase.databinding.ActivityTestBinding
import com.pichs.xbase.binding.AbstractBaseActivity

class TestActivity : AbstractBaseActivity() {
    private lateinit var binding: ActivityTestBinding

    override fun getContentView(): View? {
        binding = ActivityTestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun beforeOnCreate(savedInstanceState: Bundle?) {

    }

    override fun afterOnCreate() {

    }


}