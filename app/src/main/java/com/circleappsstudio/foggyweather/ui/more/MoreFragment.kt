package com.circleappsstudio.foggyweather.ui.more

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.databinding.FragmentMoreBinding

class MoreFragment : Fragment(R.layout.fragment_more) {

    private lateinit var binding: FragmentMoreBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMoreBinding.bind(view)

    }

}