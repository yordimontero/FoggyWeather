package com.circleappsstudio.foggyweather.ui.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.circleappsstudio.foggyweather.R
import com.circleappsstudio.foggyweather.databinding.FragmentMoreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : Fragment(R.layout.fragment_more) {

    private lateinit var binding: FragmentMoreBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoreBinding.bind(view)

        links()
    }

    private fun links() {
        goToFacebook()
        goToTwitter()
        sendEmail()
        goToWebPage()
        goToPolicyPrivacy()
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
                Uri.parse("https://www.facebook.com/CircleAppsStudio/")
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
                Uri.parse("https://twitter.com/CircleApps_")
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
                Uri.parse("mailto:soporte.circleappsstudio@gmail.com")
            )

            startActivity(Intent.createChooser(emailIntent, "Chooser title"))

        }

    }

    private fun goToWebPage() {
        /*
            Method to navigate to Circle Apps Studio URL web page.
         */
        binding.btnWebPage.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://circleappsstudio.com")
            )

            requireContext().startActivity(intent)

        }
    }

    private fun goToPolicyPrivacy() {
        /*
            Method to navigate to Circle Apps Studio's URL privacy policy.
        */
        binding.btnPrivacyPolicy.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://circleappsstudio.com/inicio/politicaprivacidad")
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
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
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
                Uri.parse("https://play.google.com/store/apps/dev?id=5626559995007331377")
            )

            requireContext().startActivity(intent)

        }

    }

}