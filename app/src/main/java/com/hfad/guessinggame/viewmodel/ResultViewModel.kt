package com.hfad.guessinggame.viewmodel

import androidx.lifecycle.ViewModel

class ResultViewModel (finalResult: String): ViewModel() {
    val result = finalResult

}
/**
 * Так как ResultFragment принимает аргументы (String),
 * нам нужно добавить свойство в ResultViewModel для хранения результата.
 * Мы также будем использовать конструктор String, чтобы убедиться, что это свойство установлено,
 * как только будет создан ResultViewModel. конструктор String, чтобы убедиться,
 * что это свойство установлено, как только будет создан ResultViewModel. */