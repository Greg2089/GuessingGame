package com.hfad.guessinggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.hfad.guessinggame.databinding.FragmentResultBinding
import com.hfad.guessinggame.viewmodel.ResultViewModel
import com.hfad.guessinggame.viewmodel.ResultViewModelFactory


class ResultFragment : Fragment() {
    /*Объявил _binding и свойство binding.
       * Получаем доступ к _binding, используя binding property.
       *  Если _binding не равен нулю, он возвращает _binding,
       * если _binding равен нулю, попытка его использования вызывает null pointer exception*/
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    lateinit var viewModel: ResultViewModel//создаем view model factory object
    lateinit var viewModelFactory: ResultViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*Свойству _binding присваивается экземпляр FragmentResultBinding в методе onCreateView()*/
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        //Получить строку, которая была передана в ResultFragment
        val result = ResultFragmentArgs.fromBundle(requireArguments()).result
        //создаем view model factory object
        viewModelFactory = ResultViewModelFactory(result)
        //Передаем ViewModel factory to the view model provider
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ResultViewModel::class.java)//объект создается, если он еще не создан

        //binding.wonLost.text = ResultFragmentArgs.fromBundle(requireArguments()).result
        //показывем результат из ViewModel
        binding.wonLost.text = viewModel.result
        binding.newGameButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
