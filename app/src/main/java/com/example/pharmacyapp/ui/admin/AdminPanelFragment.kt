package com.example.pharmacyapp.ui.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmacyapp.R
import com.example.pharmacyapp.data.UserPreferences
import com.example.pharmacyapp.data.model.Medicine
import com.example.pharmacyapp.data.model.Discount
import com.example.pharmacyapp.databinding.FragmentAdminPanelBinding
import com.example.pharmacyapp.databinding.DialogAddMedicineBinding
import com.example.pharmacyapp.databinding.DialogAddDiscountBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class AdminPanelFragment : Fragment() {

    private var _binding: FragmentAdminPanelBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreferences: UserPreferences
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var medicinesAdapter: MedicinesAdapter
    private lateinit var discountsAdapter: DiscountsAdapter
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    
    private var selectedImageUri: Uri? = null
    private var currentMedicineDialogBinding: DialogAddMedicineBinding? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let { imageUri ->
                selectedImageUri = imageUri
                currentMedicineDialogBinding?.medicineImageView?.setImageURI(imageUri)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminPanelBinding.inflate(inflater, container, false)
        userPreferences = UserPreferences(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUsersRecyclerView()
        setupMedicinesRecyclerView()
        setupDiscountsRecyclerView()
        
        loadUsers()
        loadMedicines()
        loadDiscounts()

        binding.addMedicineButton.setOnClickListener {
            showAddMedicineDialog()
        }

        binding.addDiscountButton.setOnClickListener {
            showAddDiscountDialog()
        }

        binding.logoutButton.setOnClickListener {
            userPreferences.logout()
            findNavController().navigate(R.id.action_adminPanel_to_login)
        }
    }

    private fun setupUsersRecyclerView() {
        usersAdapter = UsersAdapter()
        binding.usersRecyclerView.adapter = usersAdapter
    }

    private fun setupMedicinesRecyclerView() {
        medicinesAdapter = MedicinesAdapter(
            onEditClick = { medicine ->
                showEditMedicineDialog(medicine)
            },
            onDeleteClick = { medicine ->
                showDeleteMedicineDialog(medicine)
            }
        )
        binding.medicinesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = medicinesAdapter
        }
    }

    private fun setupDiscountsRecyclerView() {
        discountsAdapter = DiscountsAdapter(
            onEditClick = { discount ->
                showEditDiscountDialog(discount)
            },
            onDeleteClick = { discount ->
                showDeleteDiscountDialog(discount)
            }
        )
        binding.discountsRecyclerView.adapter = discountsAdapter
    }

    private fun loadUsers() {
        val users = userPreferences.getAllUsers()
        usersAdapter.setUsers(users)
    }

    private fun loadMedicines() {
        val medicines = userPreferences.getMedicines()
        medicinesAdapter.setMedicines(medicines)
    }

    private fun loadDiscounts() {
        val discounts = userPreferences.getDiscounts()
        discountsAdapter.setDiscounts(discounts)
    }

    private fun showAddMedicineDialog() {
        val dialogBinding = DialogAddMedicineBinding.inflate(layoutInflater)
        currentMedicineDialogBinding = dialogBinding
        selectedImageUri = null

        dialogBinding.selectImageButton.setOnClickListener {
            openImagePicker()
        }
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.add_medicine)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.add) { dialog, _ ->
                val name = dialogBinding.nameEditText.text.toString()
                val description = dialogBinding.descriptionEditText.text.toString()
                val price = dialogBinding.priceEditText.text.toString().toDoubleOrNull()
                val category = dialogBinding.categoryEditText.text.toString()
                val quantity = dialogBinding.quantityEditText.text.toString().toIntOrNull()

                if (name.isBlank() || description.isBlank() || price == null || category.isBlank() || quantity == null) {
                    Snackbar.make(binding.root, R.string.fill_all_fields, Snackbar.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val medicine = Medicine(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    description = description,
                    price = price,
                    category = category,
                    quantity = quantity,
                    imageUrl = selectedImageUri?.toString() ?: ""
                )

                userPreferences.addMedicine(medicine)
                loadMedicines()
                Snackbar.make(binding.root, R.string.medicine_added, Snackbar.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun showAddDiscountDialog() {
        val dialogBinding = DialogAddDiscountBinding.inflate(layoutInflater)
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Добавление скидки")
            .setView(dialogBinding.root)
            .setPositiveButton("Добавить") { _, _ ->
                val title = dialogBinding.titleEditText.text.toString()
                val description = dialogBinding.descriptionEditText.text.toString()
                val percentageText = dialogBinding.percentageEditText.text.toString()
                val validUntilText = dialogBinding.validUntilEditText.text.toString()

                if (validateDiscountInput(title, description, percentageText, validUntilText)) {
                    try {
                        val validUntil = dateFormat.parse(validUntilText)?.time ?: 0
                        val discount = Discount(
                            id = UUID.randomUUID().toString(),
                            title = title,
                            description = description,
                            percentage = percentageText.toInt(),
                            validUntil = validUntil
                        )
                        userPreferences.saveDiscount(discount)
                        loadDiscounts()
                        Snackbar.make(binding.root, "Скидка добавлена", Snackbar.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        showError("Неверный формат даты")
                    }
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun validateDiscountInput(
        title: String,
        description: String,
        percentage: String,
        validUntil: String
    ): Boolean {
        if (title.isBlank()) {
            showError("Введите название акции")
            return false
        }
        if (description.isBlank()) {
            showError("Введите описание")
            return false
        }
        if (percentage.isBlank() || percentage.toIntOrNull() == null || 
            percentage.toInt() <= 0 || percentage.toInt() > 100) {
            showError("Введите корректный процент скидки (1-100)")
            return false
        }
        if (validUntil.isBlank()) {
            showError("Введите дату окончания акции")
            return false
        }
        return true
    }

    private fun showEditMedicineDialog(medicine: Medicine) {
        val dialogBinding = DialogAddMedicineBinding.inflate(layoutInflater)
        currentMedicineDialogBinding = dialogBinding
        selectedImageUri = medicine.imageUrl?.let { Uri.parse(it) }
        
        // Заполняем поля текущими значениями
        dialogBinding.nameEditText.setText(medicine.name)
        dialogBinding.descriptionEditText.setText(medicine.description)
        dialogBinding.priceEditText.setText(medicine.price.toString())
        dialogBinding.categoryEditText.setText(medicine.category)
        dialogBinding.quantityEditText.setText(medicine.quantity.toString())
        
        // Загружаем изображение, если оно есть
        selectedImageUri?.let { uri ->
            dialogBinding.medicineImageView.setImageURI(uri)
        }

        dialogBinding.selectImageButton.setOnClickListener {
            openImagePicker()
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.edit_medicine)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.save) { dialog, _ ->
                val name = dialogBinding.nameEditText.text.toString()
                val description = dialogBinding.descriptionEditText.text.toString()
                val price = dialogBinding.priceEditText.text.toString().toDoubleOrNull()
                val category = dialogBinding.categoryEditText.text.toString()
                val quantity = dialogBinding.quantityEditText.text.toString().toIntOrNull()

                if (name.isBlank() || description.isBlank() || price == null || category.isBlank() || quantity == null) {
                    Snackbar.make(binding.root, R.string.fill_all_fields, Snackbar.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val updatedMedicine = medicine.copy(
                    name = name,
                    description = description,
                    price = price,
                    category = category,
                    quantity = quantity,
                    imageUrl = selectedImageUri?.toString() ?: medicine.imageUrl
                )

                userPreferences.updateMedicine(updatedMedicine)
                loadMedicines()
                Snackbar.make(binding.root, R.string.medicine_updated, Snackbar.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun showEditDiscountDialog(discount: Discount) {
        val dialogBinding = DialogAddDiscountBinding.inflate(layoutInflater)
        
        // Заполняем поля текущими значениями
        dialogBinding.titleEditText.setText(discount.title)
        dialogBinding.descriptionEditText.setText(discount.description)
        dialogBinding.percentageEditText.setText(discount.percentage.toString())
        dialogBinding.validUntilEditText.setText(dateFormat.format(Date(discount.validUntil)))

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Редактирование скидки")
            .setView(dialogBinding.root)
            .setPositiveButton("Сохранить") { _, _ ->
                val title = dialogBinding.titleEditText.text.toString()
                val description = dialogBinding.descriptionEditText.text.toString()
                val percentageText = dialogBinding.percentageEditText.text.toString()
                val validUntilText = dialogBinding.validUntilEditText.text.toString()

                if (validateDiscountInput(title, description, percentageText, validUntilText)) {
                    try {
                        val validUntil = dateFormat.parse(validUntilText)?.time ?: 0
                        val updatedDiscount = discount.copy(
                            title = title,
                            description = description,
                            percentage = percentageText.toInt(),
                            validUntil = validUntil
                        )
                        userPreferences.updateDiscount(updatedDiscount)
                        loadDiscounts()
                        Snackbar.make(binding.root, "Скидка обновлена", Snackbar.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        showError("Неверный формат даты")
                    }
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun showDeleteMedicineDialog(medicine: Medicine) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_medicine)
            .setMessage(getString(R.string.delete_medicine_confirmation, medicine.name))
            .setPositiveButton(R.string.delete) { _, _ ->
                userPreferences.deleteMedicine(medicine.id)
                loadMedicines()
                Snackbar.make(binding.root, R.string.medicine_deleted, Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun showDeleteDiscountDialog(discount: Discount) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Удаление скидки")
            .setMessage("Вы уверены, что хотите удалить скидку ${discount.title}?")
            .setPositiveButton("Удалить") { _, _ ->
                userPreferences.deleteDiscount(discount.id)
                loadDiscounts()
                Snackbar.make(binding.root, "Скидка удалена", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        getContent.launch(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentMedicineDialogBinding = null
        _binding = null
    }

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }
} 