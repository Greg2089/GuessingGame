package com.hfad.guessinggame

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hfad.guessinggame.databinding.FragmentGameBinding
import com.hfad.guessinggame.viewmodel.GameViewModel


class GameFragment : Fragment() {
    /*Объявил _binding и свойство binding.
    * Получаем доступ к _binding, используя свойство привязки.
    *  Если _binding не равен нулю, он возвращает _binding,
    * если _binding равен нулю, попытка его использования вызывает null pointer exception*/
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    //Определил свойство viewModel, которое будет инициализировано позже в коде
    private lateinit var viewModel: GameViewModel


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Метод", "Вызывается")
        /*Свойству _binding присваивается экземпляр FragmentGameBinding в методе onCreateView()*/
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        /* :: в коде ниже используется для получения ссылки на класс GameViewModel.
         Мы должны использовать этот синтаксис, потому что метод get () ViewModelProvider требует ссылки
         на класс GameViewModel вместо объекта GameViewModel.*/
        //Устанавливаю свойство viewModel
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        /*updateScreen() Изменения из главы 12: удаляем updateScreen(),
        так как мы используем оперативные данные (live data) для обновления значений, отображаемых на экране.*/
        viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer { newValue ->
            binding.incorrectGuesses.text = "Неверные буквы $newValue"
        })
        viewModel.livesLeft.observe(viewLifecycleOwner, Observer { newValue ->
            binding.lives.text = "Количество жизней $newValue"
        })
        viewModel.secretWordDisplay.observe(viewLifecycleOwner, Observer { newValue ->
            binding.word.text = newValue
        })
        //это заставляет фрагмент наблюдать за моделью представления свойства gameOver
        viewModel.gameOver.observe(viewLifecycleOwner, Observer { newValue ->
            if (newValue) {
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        })
        binding.guessButton.setOnClickListener {
            //Вызоваем makeGuess, чтобы разобраться с предположениями пользователя
            /** Методы makeGuess(),isWon(), isLost (), wonLostMessage() должны быть доступны через свойство viewModel*/
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null  // сбросить текст редактирования
            // Изменения глава 12: удаляем updateScreen()  // обновить экран
            // если пользователь выиграл или проиграл перейдите к ResultFragment передав значение сообщения

            /* Глава12: Удаляем эту логику
            if (viewModel.isWon() || viewModel.isLost()) {
                 val action =
                     GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                 view.findNavController().navigate(action)
             }*/

        }
        return view

    }

    /* Глава 12:Удаляем этот метод, т.к. используем live data
    // Установис TextView макета
    fun updateScreen() {
        binding.word.text = viewModel.secretWordDisplay
        binding.lives.text = "У Вас осталось ${viewModel.livesLeft} жизней. "
        binding.incorrectGuesses.text = "Неверные буквы ${viewModel.incorrectGuesses}"

    }*/

    /* Когда фрагмент больше не имеет доступа к своему макету, установите для свойства _binding значение null*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("Метод", "Вызывается")
    }


}
