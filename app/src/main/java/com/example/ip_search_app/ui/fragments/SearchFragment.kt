package com.example.ip_search_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ip_search_app.databinding.FragmentSearchBinding
import com.example.ip_search_app.ui.viewModels.SearchViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SearchViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = SupportMapFragment.newInstance()
        requireActivity().
        supportFragmentManager
            .beginTransaction()
            .add(binding.map.id, mapFragment)
            .commit()
        // Observe the latitude and longitude LiveData variables
        viewModel.latitude.observe(viewLifecycleOwner) { lat ->
            viewModel.longitude.observe(viewLifecycleOwner) { lon ->
                viewModel.ipInfo.observe(viewLifecycleOwner) { ip ->
                    // Call the getMapAsync function when latitude and longitude are updated
                    mapFragment.getMapAsync { googleMap ->
                        // Use the updated latitude and longitude values to update the map
                        val latLng = LatLng(lat, lon)
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                        googleMap.clear()
                        googleMap.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(ip.data?.ip)
                        )
                    }
                }
            }
        }
        viewModel.getUserIp("json")
        binding.btnSearch.setOnClickListener {
            val ip = binding.etEnterId.text.toString()
            if (ip.isNotEmpty() && android.util.Patterns.IP_ADDRESS.matcher(ip).matches()) {
                viewModel.getIpInformation(ip)
            } else {
                Toast.makeText(requireContext(), "Пожалуйста, введите действительный IP-адрес", Toast.LENGTH_SHORT).show()
            }
        }
    }
}