package com.hfad.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.hfad.guessinggame.databinding.FragmentGameBinding


class GameFragment : Fragment() {
    /*Объявил _binding и свойство binding.
    * Получаем доступ к _binding, используя свойство привязки.
    *  Если _binding не равен нулю, он возвращает _binding,
    * если _binding равен нулю, попытка его использования вызывает null pointer exception*/
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    val words = listOf("Android", "Activity", "Fragment") //доступные для угадывания слова
    val secretWord = words.random().uppercase()//слово, которое пользователь должен угадать
    var secretWordDisplay = ""// как отображается это слово
    var correctGuesses = ""          // правильно
    var incorrectGuesses = ""       // неправильно
    var livesLeft = 8                   // жизни


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*Свойству _binding присваивается экземпляр FragmentOrderBinding в методе onCreateView()*/
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        // определите, как должно отобрадаться секретное слово и обновите экран
        secretWordDisplay = deriveSecretWordDisplay()
        updateScreen()

        binding.guessButton.setOnClickListener {
            //Вызовите makeGuess, чтобы разобраться с предположениями пользователя
            makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null  // сбросить текст редактирования
            updateScreen()  // обновить экран
            // если пользователь выиграл или проиграл перейдите к ResultFragment передав значение сообщения
            if (isWon() || isLost()) {
                val action =
                    GameFragmentDirections.actionGameFragmentToResultFragment(wonLostMessage())
                view.findNavController().navigate(action)
            }

        }
        return view

    }

    /* Когда фрагмент больше не имеет доступа к своему макету, установите для свойства _binding значение null*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // установите TextView макета
    fun updateScreen() {
        binding.word.text = secretWordDisplay
        binding.lives.text = "У Вас осталось $livesLeft жизней. "
        binding.incorrectGuesses.text = "Неверных догадок $incorrectGuesses"
    }

    // Эта функция создает строку для того, как секретное слово должно отображаться на экране
    fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString()) /*Вызовите checkLetter для каждой буквы в секретном слове
        и добавьте ее возвращаемое значение в конец отображаемой переменной*/

        }
        return display
    }

    /*Эта функция проверяет, содержит ли секретное слово букву, которую угадал пользователь,
    если да, то возвращает букву. Если нет, то он возвращает "_"*/
    fun checkLetter(str: String) = when (correctGuesses.contains(str)) {
        true -> str
        false -> "_"
    }

    fun makeGuess(guess: String) {//Эта функция вызывается каждый раз, когда пользователь делает предположение
        if (guess.length == 1) {
            /*Для каждого правильного предположения обновляйте
             правильные догадки и отображайте секретное слово*/
            if (secretWord.contains(guess)) {

                correctGuesses += guess
                secretWordDisplay = deriveSecretWordDisplay()
                //За каждое неверное предположение обновляйте неверные догадки и жизни
            } else {
                incorrectGuesses += "$guess"
                livesLeft--
            }
        }
    }

    //Игра считается выигранной, если секретное слово соответствует отображаемому секретному слову
    fun isLost() = livesLeft <= 0

    //игра проиграна, если у пользователя кончились жизни
    fun isWon() = secretWord.equals(secretWordDisplay, true)
    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "Выиграл!"
        else if (isLost()) message = "Проиграл!"
        message += "Было загадано $secretWord."
        return message //wonLostMessage() возвращает строку, выиграл ли пользователь и каким было секретное слово
    }

}
