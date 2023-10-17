package uz.mobile.developer.sdl.presentation.ui.screen.fargment

import MySharedPreference
import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.mobile.developer.sdl.R
import uz.mobile.developer.sdl.databinding.FragmentSplashScreenBinding
import uz.mobile.developer.sdl.util.hasConnection
import kotlin.concurrent.thread

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var mySharedPreference: MySharedPreference
    private var _brandTextShadow: Int = 11
    private var _brandText: String? = "Soft Dev..."
    private var _brandTextShadowText: String = "Soft Dev..."
    private var _brandTextShadowText2: String = "Soft Dev.. "
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        mySharedPreference = MySharedPreference(requireContext())
        countTime()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun countTime() {
        thread(start = true) {
            Thread.sleep(300L)
            if (_brandText?.isNotEmpty() == true) {
                binding.brandText.text =
                    binding.brandText.text.toString() + _brandText!![0].toString()
                _brandText = _brandText!!.substring(1)
                countTime()
            } else {
                if (_brandTextShadow == 0) {
                    activity!!.runOnUiThread {
                    if (checkConnection()) {
                           if (mySharedPreference.hasAccount){
                               findNavController().navigate(R.id.homeScreen)
                           }else{
                               findNavController().navigate(R.id.loginScreen)
                           }

                    } else {
                        showRetryDialog()
                    }
                    }
                } else {
                    lastDotShadow()
                    countTime()
                }

            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun lastDotShadow() {
        thread(start = true) {
            Thread.sleep(1000)
            activity!!.runOnUiThread {
                binding.apply {
                    if (_brandTextShadow % 2 != 0) {
                        brandText.text = _brandTextShadowText2
                    } else {
                        brandText.text = _brandTextShadowText
                    }
                }
            }
            if (_brandTextShadow == 0) {
                _brandText = null
                countTime()
            } else {
                _brandTextShadow--
                lastDotShadow()
            }
        }
    }

    private fun showRetryDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.connection_dialog)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
        val btn = dialog.findViewById<AppCompatButton>(R.id.btn)
        dialog.setCancelable(false)
        dialog.show()
        btn.setOnClickListener {
            dialog.dismiss()
            checkConnection()
        }
    }

    private fun checkConnection(): Boolean {
        return hasConnection()
    }
}