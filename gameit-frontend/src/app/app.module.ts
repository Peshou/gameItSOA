import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {AppComponent} from './app.component';
import {RouterModule, Routes} from "@angular/router";
import {PageNotFoundComponent} from "./components/not-found/not-found.component";
import {HomeComponent} from "./components/home/home.component";
import {WrapperComponent} from "./components/wrapper/wrapper.component";
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {RoutesPaths} from "./services/navigation.service";
import {CustomFormsModule} from "ng2-validation";
import {GameListComponent} from "./components/game-list/game-list.component";
import {InfiniteScrollModule} from "ngx-infinite-scroll";
import {ShoppingCartService} from "./services/shopping-cart.service";
import {NavbarComponent} from "./components/wrapper/navbar/navbar.component";
import {LoadingComponent} from "./util/loading/loading.component";
import {GameDetailsComponent} from "./components/game-details/game-details.component";
import {ContactComponent} from "./components/contact/contact.component";
import {UserDetailsComponent} from "./components/user-details/user-details.component";
import {ShoppingCartComponent} from "./components/shopping-cart/shopping-cart.component";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";
import {SellGameComponent} from "./components/sell-game/sell-game.component";
import {EditGameComponent} from "./components/edit-game/edit-game.component";
import {FileUploadModule} from "ng2-file-upload";

const appRoutes: Routes = [
  {path: RoutesPaths.login, component: LoginComponent},
  {path: RoutesPaths.register, component: RegisterComponent},
  {
    path: '',
    component: WrapperComponent,
    children: [
      {
        path: RoutesPaths.games,
        children: [
          {path: '', component: GameListComponent},
          {
            path: ':id', children: [
            {path: '', component: GameDetailsComponent},
            {path: 'edit', component: EditGameComponent},
          ]
          },

          {path: 'sell', component: SellGameComponent}
        ]
      },
      {path: RoutesPaths.contactUs, component: ContactComponent},
      {path: RoutesPaths.userDetails + "/:id", component: UserDetailsComponent},
      {path: RoutesPaths.shoppingCart, component: ShoppingCartComponent},
      {path: RoutesPaths.home, component: HomeComponent},
      {path: '', redirectTo: '/' + RoutesPaths.home, pathMatch: 'full'},
    ]
  },
  {path: '**', component: PageNotFoundComponent}
];
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PageNotFoundComponent,
    WrapperComponent,
    NavbarComponent,
    LoginComponent,
    RegisterComponent,
    GameListComponent,
    LoadingComponent,
    GameDetailsComponent,
    ContactComponent,
    UserDetailsComponent,
    ShoppingCartComponent,
    SellGameComponent,
    EditGameComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    ReactiveFormsModule,
    BrowserAnimationsModule, // required animations module
    ToastrModule.forRoot({
      positionClass: 'toast-top-right',
      preventDuplicates: false,
    }),
    CustomFormsModule,
    FileUploadModule,
    InfiniteScrollModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [ShoppingCartService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
