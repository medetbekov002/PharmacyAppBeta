package com.example.pharmacyapp.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pharmacyapp.R
import com.example.pharmacyapp.databinding.FragmentPaymentBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentFragment : Fragment() {
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        loadSampleData()
    }

    private fun setupUI() {
        with(binding) {
            // Set headers
            paymentTitle.text = getString(R.string.payment_title)
            paymentSubtitle.text = getString(R.string.payment_subtitle)
            balanceTitle.text = getString(R.string.balance_title)

            // Set payment method buttons
            creditCardButton.text = getString(R.string.credit_card)
            qrCodeButton.text = getString(R.string.qr_payment)

            // Set input hints
            cardNumberInput.hint = getString(R.string.card_number)
            expiryDateInput.hint = getString(R.string.expiry_date)
            cvvInput.hint = getString(R.string.cvv)
            cardHolderInput.hint = getString(R.string.card_holder)
            saveCardCheckbox.text = getString(R.string.save_card)
        }
    }

    private fun loadSampleData() {
        val currentLocale = requireContext().resources.configuration.locales[0]
        val currencyFormat = NumberFormat.getCurrencyInstance(currentLocale)
        val dateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", currentLocale)

        // Sample balance
        val balance = 1234.56
        binding.balanceAmount.text = currencyFormat.format(balance)

        // Sample transaction
        val transaction = Transaction(
            id = "TRX123",
            amount = 45.99,
            date = Date(),
            status = TransactionStatus.SUCCESS,
            paymentMethod = PaymentMethod(
                id = "CC1",
                type = PaymentType.CREDIT_CARD,
                details = "**** **** **** 1234",
                lastUsed = Date()
            )
        )

        with(binding) {
            lastTransactionInfo.text = getString(R.string.payment_success)
            lastTransactionAmount.text = currencyFormat.format(transaction.amount)
            lastTransactionDate.text = dateFormat.format(transaction.date)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 