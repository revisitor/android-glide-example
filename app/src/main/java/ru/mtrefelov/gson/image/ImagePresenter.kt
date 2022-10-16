package ru.mtrefelov.gson.image

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