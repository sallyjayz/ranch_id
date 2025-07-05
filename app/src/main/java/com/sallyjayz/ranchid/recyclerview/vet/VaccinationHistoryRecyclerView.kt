package com.sallyjayz.ranchid.recyclerview.vet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sallyjayz.ranchid.databinding.VaccinationHistoryItemBinding
import com.sallyjayz.ranchid.model.vet.livestockwithvaccinationhistory.VaccinationHistory

/**
 * Created by Salama Jatau on 26-Mar-24.
 */
class VaccinationHistoryRecyclerView(private var vaccinationHistory:ArrayList<VaccinationHistory>) :
    RecyclerView.Adapter<VaccinationHistoryRecyclerView.VaccinationHistoryViewHolder>() {

    override fun onBindViewHolder(
        holder: VaccinationHistoryViewHolder,
        position: Int
    ) {
        val item = vaccinationHistory[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VaccinationHistoryViewHolder {
        val binding = VaccinationHistoryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return VaccinationHistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = vaccinationHistory.size

    class VaccinationHistoryViewHolder(val binding: VaccinationHistoryItemBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun bind(vaccinationHistory: VaccinationHistory) {
                binding.vaccineName.text = vaccinationHistory.drug
                binding.dosage.text = vaccinationHistory.dosage
                binding.animalTag.text = vaccinationHistory.tag_id
                binding.dateVaccinated.text = vaccinationHistory.date_of_vaccination
                binding.time.text = vaccinationHistory.time_of_vaccination
                binding.nextAppointment.text = vaccinationHistory.next_appointment
            }

    }


}