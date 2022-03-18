package com.circleappsstudio.foggyweather.ui.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.databinding.FragmentMoreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : Fragment(R.layout.fragment_more) {

    private lateinit var binding: FragmentMoreBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoreBinding.bind(view)
        navController = Navigation.findNavController(view)

        links()
    }

    private fun links() {
        goToFacebook()
        goToTwitter()
        sendEmail()
        goToWebPage()
        goToPrivacyPolicy()
        rateApp()
        goToPlayStoreMoreApps()
    }

    private fun goToFacebook() {
        /*
            Method to navigate to Circle Apps Studio Facebook URL page.
         */
        binding.btnFacebook.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    resources.getString(R.string.circle_apps_studio_facebook_url)
                )
            )

            requireContext().startActivity(intent)

        }
    }

    private fun goToTwitter() {
        /*
            Method to navigate to Circle Apps Studio Twitter URL page
         */
        binding.btnTwitter.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    resources.getString(R.string.circle_apps_studio_twitter_url)
                )
            )

            requireContext().startActivity(intent)

        }
    }

    private fun sendEmail() {
        /*
            Method to create an e-mail message to "soporte.circleappsstudio@gmail.com" address.
        */
        binding.btnSendEmail.setOnClickListener {

            val emailIntent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse(
                    resources.getString(R.string.circle_apps_studio_mail_to)
                )
            )

            startActivity(
                Intent.createChooser(
                    emailIntent, resources.getString(R.string.choose_title)
                )
            )

        }

    }

    private fun goToWebPage() {
        /*
            Method to navigate to Circle Apps Studio URL web page.
         */
        binding.btnWebPage.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    resources.getString(R.string.circle_apps_studio_web_page)
                )
            )

            requireContext().startActivity(intent)

        }
    }

    private fun goToPrivacyPolicy() {
        /*
            Method to navigate to Circle Apps Studio's URL privacy policy.
        */
        binding.btnPrivacyPolicy.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    resources.getString(R.string.circle_apps_studio_privacy_policy)
                )
            )

            requireContext().startActivity(intent)

        }
    }

    private fun rateApp() {
        /*
            Method to navigate to Time4Sleep's page in Google Play Store to rate it.
        */
        binding.btnRateApp.setOnClickListener {

            val packageName: String = requireContext().packageName.toString()

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("${resources.getString(R.string.google_play_store_base_url)}$packageName")
            )

            requireContext().startActivity(intent)

        }
    }

    private fun goToPlayStoreMoreApps() {
        /*
            Method to navigate to Circle Apps Studio's main page in Google Play Store.
         */
        binding.btnMoreApps.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    resources.getString(R.string.google_play_store_more_apps_url)
                )
            )

            requireContext().startActivity(intent)

        }

    }

}