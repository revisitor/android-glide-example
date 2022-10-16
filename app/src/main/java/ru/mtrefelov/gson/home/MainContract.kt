package ru.mtrefelov.gson.home

import ru.mtrefelov.gson.base.BasePresenter
import ru.mtrefelov.gson.base.BaseView
import ru.mtrefelov.gson.home.model.Photo

interface MainContract {
    interface Presenter : BasePresenter {
        fun onViewCreated()
        fun onImageClicked(imageUrl: String)
    }

    interface View : BaseView<Presenter> {
        fun renderPhotos(photos: List<Photo>)
        fun openImageView(imageUrl: String)
        fun showSnackbar(imageUrl: String)
    }
}