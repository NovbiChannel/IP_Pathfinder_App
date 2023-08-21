package com.example.ip_search_app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ip_search_app.databinding.FragmentHistoryBinding
import com.example.ip_search_app.ui.adapters.HistoryAdapter
import com.example.ip_search_app.ui.viewModels.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyAdapter: HistoryAdapter
    private val viewModel: HistoryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.getDataStorage.observe(viewLifecycleOwner) {
            historyAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() = binding.rvHistory.apply {
        historyAdapter = HistoryAdapter()
        adapter = historyAdapter
        layoutManager = LinearLayoutManager(requireContext())
        // Создаем объект ItemTouchHelper и задаем свой обработчик событий свайпа
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                // Не нужно обрабатывать перемещение элементов списка
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Получаем позицию свайпнутого элемента
                val position = viewHolder.adapterPosition
                // Выполняем вашу функцию, например:

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        viewModel.deleteItemForDb(historyAdapter.getItem(position)).let {
                            adapter?.notifyItemRemoved(position)
                        }
                    } catch (t: Throwable) {
                        Log.e("TestData", "catch")
                    }
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(this)
    }
}