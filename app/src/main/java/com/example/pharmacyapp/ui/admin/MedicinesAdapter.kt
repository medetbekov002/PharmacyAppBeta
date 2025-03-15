package com.example.pharmacyapp.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmacyapp.data.model.Medicine
import com.example.pharmacyapp.databinding.ItemMedicineAdminBinding

class MedicinesAdapter(
    private val onEditClick: (Medicine) -> Unit,
    private val onDeleteClick: (Medicine) -> Unit
) : RecyclerView.Adapter<MedicinesAdapter.MedicineViewHolder>() {

    private var medicines: List<Medicine> = emptyList()

    fun setMedicines(newMedicines: List<Medicine>) {
        medicines = newMedicines
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = ItemMedicineAdminBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(medicines[position])
    }

    override fun getItemCount(): Int = medicines.size

    inner class MedicineViewHolder(
        private val binding: ItemMedicineAdminBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(medicine: Medicine) {
            binding.medicineName.text = medicine.name
            binding.medicineDescription.text = medicine.description
            binding.medicinePrice.text = "₽${medicine.price}"
            binding.medicineQuantity.text = "В наличии: ${medicine.quantity} шт."

            binding.editButton.setOnClickListener {
                onEditClick(medicine)
            }

            binding.deleteButton.setOnClickListener {
                onDeleteClick(medicine)
            }
        }
    }
} 