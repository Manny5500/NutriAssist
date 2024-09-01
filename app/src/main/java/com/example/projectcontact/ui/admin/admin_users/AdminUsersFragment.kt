package com.example.projectcontact.ui.admin.admin_users

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectcontact.MarvaStructure
import com.example.projectcontact.databinding.FragmentAdminUsersBinding
import com.example.projectcontact.model.User
import com.example.projectcontact.util.ToastUtil.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AdminUsersFragment : Fragment(), MarvaStructure, UserAdapter.OnItemClickListener{

    private var _binding: FragmentAdminUsersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel : AdminUsersViewModel by viewModels()
    private val adapter by lazy { UserAdapter(this) }
    private var action = "archive"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminUsersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.viewModel = viewModel

        setupRecyclerView()
        observers()
        events()
        return root
    }

    private fun setupRecyclerView(){
        with(binding.recycler){
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@AdminUsersFragment.adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun observers() {
        with(viewModel){
            filteredList.observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
            action.observe(viewLifecycleOwner){
                this@AdminUsersFragment.action = it
            }
        }
    }

    override fun events() {

        binding.userPicker.setOnItemClickListener { _, _, _, _ ->
            viewModel.filter()
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let{viewModel.setSearchQuery(newText)}
                return true
            }

        })
    }

    override fun onClick(user: User) {
        val intent = Intent(requireContext(), UserDetails::class.java ).apply {
            putExtra("action", this@AdminUsersFragment.action)
            putExtra("user", user)
        }
        startActivity(intent)
    }
}