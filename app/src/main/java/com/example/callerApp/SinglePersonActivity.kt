package com.example.callerApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SinglePersonActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_person)

//        var frag = SinglePersonFragment.newInstance("","")

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container,SinglePersonFragment.newInstance(
                intent.getStringExtra("number").toString(),
                intent.getStringExtra("name").toString()
            ))
            .commit()
    }

//    override fun onClickFragmentButton() {
//        Toast.makeText(this, "El botón ha sido pulsado", Toast.LENGTH_SHORT).show()
//    }
}