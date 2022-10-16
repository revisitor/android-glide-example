package ru.mtrefelov.gson.api

import ru.mtrefelov.gson.model.Photo

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