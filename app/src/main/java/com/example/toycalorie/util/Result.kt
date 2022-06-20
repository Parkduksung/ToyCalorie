package com.example.toycalorie.util

import java.lang.Exception

/**
 * 성공,실패 여부를 확인하는 클래스
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

