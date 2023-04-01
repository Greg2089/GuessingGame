package com.hfad.guessinggame.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ResultViewModelFactory(private val finalResult:String):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java))
            return ResultViewModel (finalResult) as T
        throw IllegalArgumentException ("Unknown ViewModel")
    }
}

/** Создание класса Factory:
 * 1) class ResultViewModelFactory(private val finalResult:String) - Строка в этом конструкторе
 * необходима для создания объекта ResultViewModel.
 * 2) Классу нужно реализовать интерфейс ViewModelProvider.Factory
 * 3)  if (modelClass.isAssignableFrom(ResultViewModel::class.java))
return ResultViewModel (finalResult) as T - проверка является ли ViewModel, которую ViewModelProvider
 хочет создать, правильным типом.
   4) throw IllegalArgumentException ("Unknown ViewModel") - выбрасывается исключение, если тип
ViewModel некорректный
 * */