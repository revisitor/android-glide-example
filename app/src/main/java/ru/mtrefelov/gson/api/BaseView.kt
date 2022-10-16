package ru.mtrefelov.gson.api

interface BaseView<T> {
    fun setPresenter(presenter: T)
}