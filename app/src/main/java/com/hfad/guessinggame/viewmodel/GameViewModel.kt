package com.hfad.guessinggame.viewmodel

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    val words = listOf("Android", "Activity", "Fragment") //доступные для угадывания слова
    val secretWord = words.random().uppercase()//слово, которое пользователь должен угадать
    var secretWordDisplay = ""// как отображается это слово
    var correctGuesses = ""          // правильно
    var incorrectGuesses = ""       // неправильно
    var livesLeft = 8                   // жизни

    init {
        // определите, как должно отобрадаться секретное слово и обновите экран. Запускается, когда класс инициализирован.
        secretWordDisplay = deriveSecretWordDisplay()
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