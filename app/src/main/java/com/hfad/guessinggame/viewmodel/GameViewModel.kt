package com.hfad.guessinggame.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    val words = listOf("Android", "Activity", "Fragment") //доступные для угадывания слова
    val secretWord = words.random().uppercase()//слово, которое пользователь должен угадать
    val secretWordDisplay = MutableLiveData<String>()// как отображается это слово
    var correctGuesses = ""          // правильно
    val incorrectGuesses = MutableLiveData<String>(" ")      // неправильно
    val livesLeft = MutableLiveData<Int>(2) // жизни

    init {
        // определите, как должно отобрадаться секретное слово и обновите экран. Запускается, когда класс инициализирован.
        //value - установка значения свойства secretWordDisplay (liveData)
        secretWordDisplay.value = deriveSecretWordDisplay()
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
                secretWordDisplay.value = deriveSecretWordDisplay()
                //За каждое неверное предположение обновляйте неверные догадки и жизни
            } else {
                incorrectGuesses.value += "$guess"
                /* строка livesLeft-- удаляеся,поскольку свойство value может быть null,
                мы не можем, сказать, вычесть единицу из его значения. Вместо нее используем:*/
                livesLeft.value = livesLeft.value?.minus(1)
            }
        }
    }

    /*Игра считается выигранной, если секретное слово соответствует отображаемому секретному слову.
    liveData: если value null, Elvis возвращает 0. Это значит, что функция возвращет true, когда
    значение liveLeft null или <=0*/
    fun isLost() = (livesLeft.value ?: 0) <= 0

    //игра проиграна, если у пользователя кончились жизни
    fun isWon() = secretWord.equals(secretWordDisplay.value, true)
    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "Выиграл!"
        else if (isLost()) message = "Проиграл!"
        message += "Было загадано $secretWord."
        return message //wonLostMessage() возвращает строку, выиграл ли пользователь и каким было секретное слово
    }

}