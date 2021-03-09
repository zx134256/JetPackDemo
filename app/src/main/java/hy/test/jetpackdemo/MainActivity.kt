package hy.test.jetpackdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import hy.test.jetpackdemo.viewModel.LiveDataVMFactory
import hy.test.jetpackdemo.viewModel.LiveDataViewModel
import hy.test.jetpackdemo.viewModel.LiveDataViewModelFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}