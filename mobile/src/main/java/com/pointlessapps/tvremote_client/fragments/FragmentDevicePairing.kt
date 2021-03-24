package com.pointlessapps.tvremote_client.fragments

import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pointlessapps.tvremote_client.App
import com.pointlessapps.tvremote_client.R
import com.pointlessapps.tvremote_client.databinding.FragmentDevicePairingBinding
import com.pointlessapps.tvremote_client.viewModels.ViewModelDevicePairing

class FragmentDevicePairing :
	FragmentBase<FragmentDevicePairingBinding>(FragmentDevicePairingBinding::inflate) {

	private val viewModel by viewModels<ViewModelDevicePairing>()

	override fun created() {
		arrayOf(
			"0", "1", "2", "3",
			"4", "5", "6", "7",
			"8", "9", "A", "B",
			"C", "D", "E", "F"
		).forEach { symbol ->
			val id = resources.getIdentifier("button${symbol}", "id", requireContext().packageName)
			root.root.findViewById<View>(id).setOnClickListener {
				viewModel.addSymbol(symbol)
			}
		}
		root.buttonClear.setOnClickListener { viewModel.clearSecret() }

		viewModel.secret.observe(this) { secret ->
			(1..4).forEach {
				val id = resources.getIdentifier(
					"textSecret${it}",
					"id",
					requireContext().packageName
				)
				root.root.findViewById<AppCompatTextView>(id).text =
					if (secret.length >= it) secret[it - 1].toString() else ""
			}

			if (secret.length == 4) {
				viewModel.setPairingSecret(secret)
				findNavController().navigate(R.id.action_devicePairing_to_remote)
			}
		}

		onDispatchKeyEvent = lambda@{
			if (it.keyCode == KeyEvent.KEYCODE_BACK) {
				viewModel.disconnect()
				viewModel.forgetDevice()
				findNavController().navigateUp()

				return@lambda true
			}
			return@lambda false
		}
	}
}