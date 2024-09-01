package com.example.projectcontact.repository.barangay

import com.example.projectcontact.model.Barangay

interface BarangayRepo {
    suspend fun getBarangay(barangay: String): Barangay
    suspend fun getBarangays() : List<Barangay>
}