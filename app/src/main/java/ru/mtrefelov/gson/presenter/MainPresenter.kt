package ru.mtrefelov.gson.presenter

import ru.mtrefelov.gson.api.MainContract
import ru.mtrefelov.gson.model.Photo
import ru.mtrefelov.gson.model.network.HttpClientConfiguration
import ru.mtrefelov.gson.model.network.WrapperRepository

import timber.log.Timber

class MainPresenter(view: MainContract.View) : MainContract.Presenter {
    private val wrapperRepository = WrapperRepository()
    private val gson = HttpClientConfiguration.gson

    private var mainView: MainContract.View? = view

    override fun onViewCreated() {
        wrapperRepository.fetchWrapper {
            it.photoPage.photos.run {
                log()
                mainView?.renderPhotos(this)
            }
        }
    }

    private fun List<Photo>.log() {
        slice(4..lastIndex step 5).forEach {
            it.logJson()
        }
    }

    private fun Photo.logJson() = gson.toJson(this).also(Timber::d)

    override fun onImageClicked(imageUrl: String) {
        Timber.i(imageUrl)
        mainView?.openImageView(imageUrl)
    }

    override fun onDestroy() {
        mainView = null
    }
}