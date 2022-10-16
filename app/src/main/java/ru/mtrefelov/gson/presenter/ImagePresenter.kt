package ru.mtrefelov.gson.presenter

import ru.mtrefelov.gson.api.ImageContract

class ImagePresenter(view: ImageContract.View) : ImageContract.Presenter {
    private var imageView: ImageContract.View? = view

    override fun onViewCreated() {
        imageView?.showImage()
    }

    override fun onAddedToFavourites() {
        imageView?.returnToMainView()
    }

    override fun onDestroy() {
        imageView = null
    }
}