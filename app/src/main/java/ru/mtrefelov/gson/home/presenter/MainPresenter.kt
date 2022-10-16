package ru.mtrefelov.gson.home.presenter

import ru.mtrefelov.gson.home.MainContract
import ru.mtrefelov.gson.home.model.WrapperRepository

class MainPresenter(view: MainContract.View) : MainContract.Presenter {
    private val wrapperRepository = WrapperRepository()
    private var mainView: MainContract.View? = view

    override fun onViewCreated() {
        wrapperRepository.getWrapper {
            mainView?.renderPhotos(it.photoPage.photos)
        }
    }

    override fun onImageClicked(imageUrl: String) {
        mainView?.openImageView(imageUrl)
    }

    override fun onDestroy() {
        mainView = null
    }
}