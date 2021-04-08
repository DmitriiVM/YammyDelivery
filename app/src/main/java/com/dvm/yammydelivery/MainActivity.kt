package com.dvm.yammydelivery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.dvm.navigation.Navigator
import com.dvm.updateservice.UpdateService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var router : Router

    @Inject
    lateinit var navigator : Navigator

    @Inject
    lateinit var updateService: UpdateService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        lifecycleScope.launch {
//            updateService.update()
        }
    }

    override fun onStart() {
        super.onStart()
        router = Router(findNavController(R.id.fragmentContainerView))
        navigator.navigationTo = { router.navigateTo(it) }
    }
}