package com.dvm.cart_impl.domain

import com.dvm.cart_api.domain.CartInteractor
import com.dvm.cart_api.domain.model.Address
import com.dvm.cart_api.domain.model.Cart
import com.dvm.cart_api.domain.model.CartDishItem
import com.dvm.cart_api.domain.model.CartItem
import com.dvm.menu_api.domain.DishRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DefaultCartInteractor(
    private val repository: CartRepository,
    private val dishRepository: DishRepository,
    private val api: CartApi
) : CartInteractor {

    override fun cartItems(): Flow<List<CartDishItem>> =
        repository.cartItems()
            .map { list ->
                list.map { cartItem ->
                    val dish = dishRepository.getDish(cartItem.dishId)
                    CartDishItem(
                        dishId = cartItem.dishId,
                        quantity = cartItem.quantity,
                        name = dish.name,
                        image = dish.image,
                        price = dish.price
                    )
                }
            }

    override fun totalQuantity(): Flow<Int> =
        repository.totalQuantity()

    override suspend fun getDishCount(): Int =
        repository.getDishCount()

    override suspend fun addToCart(cartItem: CartItem) =
        repository.addToCart(cartItem)

    override suspend fun addToCart(cartItems: List<CartItem>) =
        repository.addToCart(cartItems)

    override suspend fun clearCart() =
        repository.clearCart()

    override suspend fun addPiece(dishId: String) =
        repository.addPiece(dishId)

    override suspend fun removePiece(dishId: String) =
        repository.removePiece(dishId)

    override suspend fun getCart(token: String): Cart =
        api.getCart(token)

    override suspend fun updateCart(
        token: String,
        promocode: String,
        items: Map<String, Int>
    ): Cart =
        api.updateCart(
            token = token,
            promocode = promocode,
            items = items
        )

    override suspend fun checkInput(address: String): Address =
        api.checkInput(address)

    override suspend fun checkCoordinates(latitude: Double, longitude: Double): Address =
        api.checkCoordinates(latitude, longitude)
}
