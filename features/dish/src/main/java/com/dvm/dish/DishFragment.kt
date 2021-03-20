package com.dvm.dish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

private const val DISH_ID_KEY = "param1"

class DishFragment : Fragment() {

    private var dishId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dishId = it.getString(DISH_ID_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dish, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(dishId: String) =
            DishFragment().apply {
                arguments = Bundle().apply {
                    putString(DISH_ID_KEY, dishId)
                }
            }
    }
}