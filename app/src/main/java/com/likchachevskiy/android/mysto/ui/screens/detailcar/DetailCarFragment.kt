package com.likchachevskiy.android.mysto.ui.screens.detailcar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.likchachevskiy.android.mysto.R
import com.likchachevskiy.android.mysto.databinding.FragmentCarDetailBinding
import com.likchachevskiy.android.mysto.domain.entity.Car

class DetailCarFragment : Fragment(R.layout.fragment_car_detail) {

    private var _binding: FragmentCarDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailCarViewModel
    private lateinit var expectCar: Car

    private var indexImage = 0
    private var imageId = R.drawable.ic_no_car_preview

    private val imageIdList = listOf(
        R.drawable.yellow_car,
        R.drawable.supercar,
        R.drawable.lamborghini,
        R.drawable.sport_car,
        R.drawable.police_car
    )

    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarDetailBinding.inflate(inflater, container, false)

        expectCar = arguments?.getParcelable("car", Car::class.java) as Car

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.fabCarUpdatePhoto.setOnClickListener {
            indexImage++
            if (indexImage > imageIdList.size - 1) indexImage = 0
            imageId = imageIdList[indexImage]
            binding.ivUpdateCarPhoto.setImageResource(imageId)

        }

        viewModel = ViewModelProvider(this)[DetailCarViewModel::class.java]

        val image = binding.ivUpdateCarPhoto
        val ownerName = binding.etCarInfoOwnerName
        val producerName = binding.etCarInfoProducer
        val model = binding.etCarInfoModel
        val plateNumber = binding.etCarInfoPlateNumber

        image.setImageResource(expectCar.photo)
        ownerName.setText(expectCar.ownerName)
        producerName.setText(expectCar.carProducer)
        model.setText(expectCar.carModel)
        plateNumber.setText(expectCar.plateNumber)

        binding.imBtnUpdate.setOnClickListener {
            if (imageId == R.drawable.ic_no_car_preview) {
                imageId = expectCar.photo
            }
            val ownerNameUpdate = binding.etCarInfoOwnerName.text
            val producerNameUpdate = binding.etCarInfoProducer.text
            val modelUpdate = binding.etCarInfoModel.text
            val plateNunberUpdate = binding.etCarInfoPlateNumber.text

            val upDateCar = Car(
                expectCar.id,
                modelUpdate.toString(),
                producerNameUpdate.toString(),
                plateNunberUpdate.toString(),
                imageId,
                ownerNameUpdate.toString()
            )
            viewModel.updateCar(upDateCar) {}

            findNavController().navigate(R.id.action_detailCarFragment_to_carListFragment)
        }

        binding.imBtnCarDetailBackStart.setOnClickListener {
            findNavController().navigate(R.id.action_detailCarFragment_to_carListFragment)
        }

        binding.imBtnDelete.setOnClickListener {
            viewModel.delete(expectCar) {}
            findNavController().navigate(R.id.action_detailCarFragment_to_carListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}