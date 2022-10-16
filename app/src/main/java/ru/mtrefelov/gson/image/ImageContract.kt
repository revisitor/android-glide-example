package ru.mtrefelov.gson.image

import ru.mtrefelov.gson.base.*

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