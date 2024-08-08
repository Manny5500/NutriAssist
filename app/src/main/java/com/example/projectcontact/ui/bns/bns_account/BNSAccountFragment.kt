package com.example.projectcontact.ui.bns.bns_account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.projectcontact.MarvaStructure
import com.example.projectcontact.databinding.FragmentBnsAccountBinding
import com.example.projectcontact.util.ToastUtil.show
import com.example.projectcontact.util.UserPreferences
import com.example.projectcontact.util.UserPreferences.getUserId
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BNSAccountFragment : Fragment(), MarvaStructure {

    private var _binding: FragmentBnsAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var selectImageLauncher:ActivityResultLauncher<String>
    private  val viewModel:BNSAccountViewModel by viewModels()
    private lateinit var userId:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBnsAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        userId = getUserId(requireContext()) ?: ""
        viewModel.getUser(userId)
        binding.lifecycleOwner = this

        setSelectImageLauncher()
        observers()
        events()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun observers() {
        viewModel.user.observe(viewLifecycleOwner){
            binding.user = it
        }
    }

    override fun events() {
        binding.imagePersonnel.setOnClickListener {
            selectImageLauncher.launch("image/*")
        }
        binding.btnDeleteAccount.setOnClickListener {
            viewModel.requestForDeletion(userId)
        }
        binding.btnUndoDeleteAccount.setOnClickListener{
            viewModel.unrequestForDeletion(userId)
        }
    }

    fun setSelectImageLauncher(){
        selectImageLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
                uri?.let{
                    viewModel.uploadImage(it, userId)
                }
            }
    }
}