import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';

import {AppComponent} from './app.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
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
          {path: ':id', component: GameDetailsComponent}
        ]
      },
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
    GameDetailsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    NgbModule.forRoot(),
    HttpModule,
    ReactiveFormsModule,
    CustomFormsModule,
    InfiniteScrollModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [ShoppingCartService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
