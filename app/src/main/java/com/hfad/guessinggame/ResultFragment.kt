package com.hfad.guessinggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.hfad.guessinggame.databinding.FragmentResultBinding


class ResultFragment : Fragment() {
    /*Объявил _binding и свойство binding.
       * Получаем доступ к _binding, используя свойство привязки.
       *  Если _binding не равен нулю, он возвращает _binding,
       * если _binding равен нулю, попытка его использования вызывает null pointer exception*/
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*Свойству _binding присваивается экземпляр FragmentResultBinding в методе onCreateView()*/
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.wonLost.text = ResultFragmentArgs.fromBundle(requireArguments()).result
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
