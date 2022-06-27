package com.dvm.menu_impl.domain.interactor

import android.util.Log
import com.dvm.menu_api.domain.FavoriteInteractor
import com.dvm.menu_api.domain.model.Favorite
import com.dvm.menu_impl.domain.api.MenuApi
import com.dvm.menu_impl.domain.repository.FavoriteRepository
import com.dvm.preferences.api.DatastoreRepository
import kotlin.coroutines.cancellation.CancellationException

internal class DefaultFavoriteInteractor(
    private val favoriteRepository: FavoriteRepository,
    private val menuApi: MenuApi,
    private val datastore: DatastoreRepository
) : FavoriteInteractor {

    override suspend fun getFavorites(): List<String> =
        favoriteRepository.getFavorites()

    override suspend fun toggleFavorite(dishId: String) {
        val currentIsFavorite = favoriteRepository.isFavorite(dishId)
        if (currentIsFavorite) {
            favoriteRepository.deleteFromFavorite(dishId)
        } else {
            favoriteRepository.addToFavorite(dishId)
        }

        if (!datastore.isAuthorized()) return
        try {
            menuApi.changeFavorite(
                token = requireNotNull(datastore.getAccessToken()),
                favorites = mapOf(dishId to !currentIsFavorite)
            )
        } catch (exception: CancellationException) {
            throw CancellationException()
        } catch (exception: Exception) {
            Log.e(TAG, "Can't change favorite status: $exception")
        }
    }

    override suspend fun addListToFavorite(favorites: List<String>) {
        favoriteRepository.addListToFavorite(favorites)
    }

    override suspend fun deleteFavorites() {
        favoriteRepository.deleteFavorites()
    }

    override suspend fun getFavorite(token: String, lastUpdateTime: Long?): List<Favorite> =
        menuApi.getFavorite(token, lastUpdateTime)

    override suspend fun updateFavorites(lastUpdateTime: Long) {
        val token = requireNotNull(datastore.getAccessToken())

        val remoteFavorites = menuApi.getFavorite(
            token = token,
            lastUpdateTime = lastUpdateTime
        )
            .map { it.dishId }
        val localFavorites = favoriteRepository.getFavorites()
        val favoritesToLocal =
            remoteFavorites
                .filter { !localFavorites.contains(it) }
        val favoritesToRemote =
            localFavorites
                .filter { !remoteFavorites.contains(it) }

        favoriteRepository.addListToFavorite(favoritesToLocal)

        menuApi.changeFavorite(
            token = token,
            favorites = favoritesToRemote.associateWith { true }
        )
    }

    companion object {
        private const val TAG = "DishViewModel"
    }
}