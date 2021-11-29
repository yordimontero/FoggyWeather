package com.circleappsstudio.foggyweather.ui.more

import android.content.Intent
import android.net.Uri
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
            Método para navegar hacia la URL del Facebook de Circle Apps Studio.
         */
        binding.btnFacebook.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/CircleAppsStudio/"))
            requireContext().startActivity(intent)
        }
    }

    private fun goToTwitter() {
        /*
            Método para navegar hacia la URL del Twitter de Circle Apps Studio.
         */
        binding.btnTwitter.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/CircleApps_"))
            requireContext().startActivity(intent)
        }
    }

    private fun sendEmail() {
        /*
            Método encargado de crear un mensaje al correo electrónico "soporte.circleappsstudio@gmail.com".
        */
        binding.btnSendEmail.setOnClickListener {

            val emailIntent =
                Intent(
                    Intent.ACTION_SENDTO,
                    Uri.parse("mailto:soporte.circleappsstudio@gmail.com")
                )
            startActivity(Intent.createChooser(emailIntent, "Chooser title"))

        }

    }

    private fun goToWebPage() {
        /*
            Método para navegar hacia la URL de la página web de Circle Apps Studio.
         */
        binding.btnWebPage.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://circleappsstudio.com"))
            requireContext().startActivity(intent)
        }
    }

    private fun goToPolicyPrivacy() {
        /*
            Método para navegar hacia la URL de la política de privacidad de Circle Apps Studio.
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
            Método para navegar hacia la ficha de Time4Sleep en Google Play Store.
         */
        binding.btnRateApp.setOnClickListener {
            val packageName: String = requireContext().packageName
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
            requireContext().startActivity(intent)
        }
    }

    private fun goToPlayStoreMoreApps() {
        /*
            Método para navegar hacia la página principal de Circle Apps Studio en Google Play Store.
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