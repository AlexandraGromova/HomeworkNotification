package ru.intersvyaz.course.android.retrofit.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import ru.intersvyaz.course.android.retrofit.R
import ru.intersvyaz.course.android.retrofit.ui.userdetails.UserDetailsFragment

class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private val mainAdapter: MainAdapter by lazy { MainAdapter(arrayListOf()) }

    //TODO
    private val userCLickListener by lazy {
        MainAdapter.UserCLickListener { id ->
            val userFragment = UserDetailsFragment()
            val bundle = Bundle()
            bundle.putString(UserDetailsFragment.USER_ID, id)
            userFragment.arguments = bundle
            findNavController().navigate(R.id.toUserDetailsFragment, bundle)
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.navHost, userFragment, UserDetailsFragment.TAG).addToBackStack("").commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()

        //todo
//        runBlocking {
//            delay(7000)
//        }
    }

    private fun setupUI() {
        mainAdapter.userCLickListener = userCLickListener
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = mainAdapter
        }
    }

    private fun setupObservers() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            users ?: return@observe
            mainAdapter.addUsers(users)
        }
    }
}