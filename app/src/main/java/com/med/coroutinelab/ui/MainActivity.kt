package com.med.coroutinelab.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.med.coroutinelab.databinding.ActivityMainBinding
import com.med.coroutinelab.ui.lab1_channel_basics.Lab1Activity
import com.med.coroutinelab.ui.lab2_fan_out_in.Lab2Activity
import com.med.coroutinelab.ui.lab3_event_bus.Lab3Activity
import com.med.coroutinelab.ui.lab4_flow_basics.Lab4Activity
import com.med.coroutinelab.ui.lab5_stateflow_sharedflow.Lab5Activity
import com.med.coroutinelab.ui.lab6_flow_operators.Lab6Activity
import com.med.coroutinelab.ui.lab7_flow_lifecycle.Lab7Activity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLab1.setOnClickListener { start(Lab1Activity::class.java) }
        binding.btnLab2.setOnClickListener { start(Lab2Activity::class.java) }
        binding.btnLab3.setOnClickListener { start(Lab3Activity::class.java) }
        binding.btnLab4.setOnClickListener { start(Lab4Activity::class.java) }
        binding.btnLab5.setOnClickListener { start(Lab5Activity::class.java) }
        binding.btnLab6.setOnClickListener { start(Lab6Activity::class.java) }
        binding.btnLab7.setOnClickListener { start(Lab7Activity::class.java) }
    }

    private fun start(cls: Class<*>) = startActivity(Intent(this, cls))
}
