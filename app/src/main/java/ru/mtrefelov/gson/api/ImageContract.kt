package ru.mtrefelov.gson.api

interface ImageContract {
    interface Presenter : BasePresenter {
        fun onViewCreated()
        fun onAddedToFavourites()
    }

    interface View : BaseView<Presenter> {
        fun showImage()
        fun returnToMainView()
    }
}