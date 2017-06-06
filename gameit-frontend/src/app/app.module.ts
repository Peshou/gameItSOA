import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
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

const appRoutes: Routes = [
  {path: RoutesPaths.login, component: LoginComponent},
  {path: RoutesPaths.register, component: RegisterComponent},
  {
    path: '',
    component: WrapperComponent,
    children: [
      {path: '', redirectTo: '/' + RoutesPaths.home, pathMatch: 'full'},
      {path: RoutesPaths.home, component: HomeComponent},
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
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    NgbModule.forRoot(),
    HttpModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
