import {Injectable} from "@angular/core";
import {Game} from "../models/game.model";

@Injectable()
export class ShoppingCartService {
  private static SHOPPING_CART_STORAGE_KEY = "shoppingCart";
  private cartItems: Game[] = [];

  addItem(game: Game, numberOfItems: number = 1) {
    for (let index = 0; index < numberOfItems; index++) {
      this.cartItems.push(game);
    }

    this.saveCartItems();
  }

  clearCart() {
    this.cartItems = [];
    sessionStorage.removeItem(ShoppingCartService.SHOPPING_CART_STORAGE_KEY);

    return this.cartItems;
  }

  getCartItems() {
    if (!this.cartItems || !this.cartItems.length) {
      this.cartItems = new Game().deserialize(JSON.parse(sessionStorage.getItem(ShoppingCartService.SHOPPING_CART_STORAGE_KEY)));
    }

    return this.cartItems;
  }

  saveCartItems() {
    console.log(JSON.stringify(this.cartItems));
    sessionStorage.setItem(ShoppingCartService.SHOPPING_CART_STORAGE_KEY, JSON.stringify(this.cartItems));
  }

  getTotalPrice() {
    return this.cartItems.reduce((priceSum: number, currentGame: Game) => {
      return priceSum + currentGame.getPriceWithDiscount() * currentGame.quantity;
    }, 0);
  }

  removeItem(gameToRemove: Game, onlyRemoveOneItem = false) {
    this.cartItems = this.getCartItems().filter((game: Game) => {
      if (gameToRemove.id === game.id) {
        if (onlyRemoveOneItem) {
          game.quantity--;
        } else {
          return false;
        }

        if (!game.quantity) {
          return false;
        }
      }

      return true;
    });

    this.saveCartItems();

    return this.cartItems;
  }
}
