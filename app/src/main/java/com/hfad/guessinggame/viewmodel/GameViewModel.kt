package com.hfad.guessinggame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val words = listOf("Android", "Activity", "Fragment") //доступные для угадывания слова
    private val secretWord = words.random().uppercase()//слово, которое пользователь должен угадать
    private val _secretWordDisplay = MutableLiveData<String>()// как отображается это слово
    val secretWordDisplay: LiveData<String>
        get() = _secretWordDisplay
    private var correctGuesses = ""          // правильно
    private val _incorrectGuesses = MutableLiveData<String>(" ")      // неправильно
    val incorrectGuesses: LiveData<String>
        get() = _incorrectGuesses
    private val _livesLeft = MutableLiveData<Int>(2) // жизни
    val livesLeft: LiveData<Int>
        get() = _livesLeft

    //добавляю свойство для завершения игры в ViewModel вместо GameFragment
    private val _gameOver = MutableLiveData<Boolean>(false)
    val gameOver: LiveData<Boolean>
        get() = _gameOver

    init {
        // определите, как должно отображаться секретное слово и обновите экран. Запускается, когда класс инициализирован.
        //value - установка значения свойства secretWordDisplay (liveData)
        _secretWordDisplay.value = deriveSecretWordDisplay()
    }

    // Эта функция создает строку для того, как секретное слово должно отображаться на экране
    private fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString()) /*Вызовите checkLetter для каждой буквы в секретном слове
        и добавьте ее возвращаемое значение в конец отображаемой переменной*/

        }
        return display
    }

    /*Эта функция проверяет, содержит ли секретное слово букву, которую угадал пользователь,
    если да, то возвращает букву. Если нет, то он возвращает "_"*/
    private fun checkLetter(str: String) = when (correctGuesses.contains(str)) {
        true -> str
        false -> "_"
    }

    fun makeGuess(guess: String) {//Эта функция вызывается каждый раз, когда пользователь делает предположение
        if (guess.length == 1) {
            /*Для каждого правильного предположения обновляйте
             правильные догадки и отображайте секретное слово*/
            if (secretWord.contains(guess)) {

                correctGuesses += guess
                _secretWordDisplay.value = deriveSecretWordDisplay()
                //За каждое неверное предположение обновляйте неверные догадки и жизни
            } else {
                _incorrectGuesses.value += "$guess"
                /* строка livesLeft-- удаляеся,поскольку свойство value может быть null,
                мы не можем, сказать, вычесть единицу из его значения. Вместо нее используем:*/
                _livesLeft.value = livesLeft.value?.minus(1)
            }
            if (isWon() || isLost()) _gameOver.value = true
        }
    }

    /*Игра считается выигранной, если секретное слово соответствует отображаемому секретному слову.
    liveData: если value null, Elvis возвращает 0. Это значит, что функция возвращет true, когда
    значение liveLeft null или <=0*/
    private fun isLost() = (livesLeft.value ?: 0) <= 0

    //игра проиграна, если у пользователя кончились жизни
    private fun isWon() = secretWord.equals(secretWordDisplay.value, true)
    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "Выиграл!"
        else if (isLost()) message = "Проиграл!"
        message += "Было загадано $secretWord."
        return message //wonLostMessage() возвращает строку, выиграл ли пользователь и каким было секретное слово
    }

    //Глава 13: Метод для завершения игры
    fun finishGame() {
        _gameOver.value = true

    }

}
/**
 * Глава 12
 * 1) livesLeft, incorrectGuesses и secretWordDisplay -
 * это свойства LiveData, которые ссылаются на те же базовые объекты,
 * что и их вспомогательные свойства MutableLiveData.
 * 2) GameFragment не может обновить ни одно из этих свойств, но он реагирует,
 * когда GameViewModel обновляет любое из вспомогательных свойств,
 * потому что они ссылаются на один и тот же базовый объект.*/