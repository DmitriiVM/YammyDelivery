package com.dvm.menu_impl.di

import com.dvm.database.DateAdapter
import com.dvm.menu_api.MenuNavHost
import com.dvm.menu_api.domain.CategoryInteractor
import com.dvm.menu_api.domain.DishInteractor
import com.dvm.menu_api.domain.DishRepository
import com.dvm.menu_api.domain.FavoriteInteractor
import com.dvm.menu_api.domain.HintInteractor
import com.dvm.menu_api.domain.ReviewInteractor
import com.dvm.menu_impl.DefaultMenuNavHost
import com.dvm.menu_impl.MenuDatabase
import com.dvm.menu_impl.data.database.DefaultCategoryRepository
import com.dvm.menu_impl.data.database.DefaultDishRepository
import com.dvm.menu_impl.data.database.DefaultFavoriteRepository
import com.dvm.menu_impl.data.database.DefaultHintRepository
import com.dvm.menu_impl.data.database.DefaultReviewRepository
import com.dvm.menu_impl.data.network.DefaultMenuApi
import com.dvm.menu_impl.domain.api.MenuApi
import com.dvm.menu_impl.domain.interactor.DefaultCategoryInteractor
import com.dvm.menu_impl.domain.interactor.DefaultDishInteractor
import com.dvm.menu_impl.domain.interactor.DefaultFavoriteInteractor
import com.dvm.menu_impl.domain.interactor.DefaultHintInteractor
import com.dvm.menu_impl.domain.interactor.DefaultReviewInteractor
import com.dvm.menu_impl.domain.repository.CategoryRepository
import com.dvm.menu_impl.domain.repository.FavoriteRepository
import com.dvm.menu_impl.domain.repository.HintRepository
import com.dvm.menu_impl.domain.repository.ReviewRepository
import com.dvm.menu_impl.presentation.category.presentation.CategoryViewModel
import com.dvm.menu_impl.presentation.dish.DishViewModel
import com.dvm.menu_impl.presentation.favorite.FavoriteViewModel
import com.dvm.menu_impl.presentation.main.MainViewModel
import com.dvm.menu_impl.presentation.menu.MenuViewModel
import com.dvm.menu_impl.presentation.search.SearchViewModel
import com.dvm.menudatabase.HintEntity
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuModule = module {

    viewModel {
        MenuViewModel(
            categoryInteractor = get(),
            dishInteractor = get(),
            navigator = get()
        )
    }

    viewModel { params ->
        CategoryViewModel(
            categoryInteractor = get(),
            dishInteractor = get(),
            cartInteractor = get(),
            navigator = get(),
            arguments = params.get(),
            savedState = get()
        )
    }

    viewModel {
        FavoriteViewModel(
            cartInteractor = get(),
            navigator = get(),
            dishInteractor = get()
        )
    }

    viewModel {
        MainViewModel(
            datastore = get(),
            cartInteractor = get(),
            navigator = get(),
            dishInteractor = get()
        )
    }

    viewModel {
        SearchViewModel(
            categoryInteractor = get(),
            dishInteractor = get(),
            hintInteractor = get(),
            cartInteractor = get(),
            navigator = get(),
            savedState = get()
        )
    }

    viewModel { params ->
        DishViewModel(
            dishId = params.get(),
            favoriteInteractor = get(),
            reviewInteractor = get(),
            cartInteractor = get(),
            datastore = get(),
            navigator = get(),
            dishInteractor = get(),
            savedState = get()
        )
    }

    factory<MenuApi> {
        DefaultMenuApi(
            client = get()
        )
    }

    factory<CategoryRepository> {
        DefaultCategoryRepository(
            categoryQueries = get<MenuDatabase>().categoryQueries
        )
    }

    factory<DishRepository> {
        DefaultDishRepository(
            dishQueries = get<MenuDatabase>().dishQueries,
            recommendedQueries = get<MenuDatabase>().recommendedQueries,
            reviewsQueries = get<MenuDatabase>().reviewQueries,
        )
    }

    factory<FavoriteRepository> {
        DefaultFavoriteRepository(
            favoriteQueries = get<MenuDatabase>().favoriteQueries
        )
    }

    factory<HintRepository> {
        DefaultHintRepository(
            hintQueries = get<MenuDatabase>().hintQueries
        )
    }

    factory<ReviewRepository> {
        DefaultReviewRepository(
            reviewsQueries = get<MenuDatabase>().reviewQueries
        )
    }

    factory<CategoryInteractor> {
        DefaultCategoryInteractor(
            categoryRepository = get(),
            menuApi = get()
        )
    }

    factory<DishInteractor> {
        DefaultDishInteractor(
            dishRepository = get(),
            menuApi = get(),
            reviewInteractor = get()
        )
    }

    factory<FavoriteInteractor> {
        DefaultFavoriteInteractor(
            favoriteRepository = get(),
            menuApi = get(),
            datastore = get()
        )
    }

    factory<HintInteractor> {
        DefaultHintInteractor(
            hintRepository = get()
        )
    }

    factory<ReviewInteractor> {
        DefaultReviewInteractor(
            reviewRepository = get(),
            menuApi = get()
        )
    }

    single {
        MenuDatabase(
            AndroidSqliteDriver(
                schema = MenuDatabase.Schema,
                context = get(),
                name = "MenuDatabase"
            ),
            hintEntityAdapter = HintEntity.Adapter(DateAdapter)
        )
    }

    factory<MenuNavHost> {
        DefaultMenuNavHost()
    }
}