import {Injectable} from "@angular/core";
import {Router, NavigationEnd} from "@angular/router";
import {Subject, Observable} from "rxjs";

export const RoutesPaths = {
  login: 'login',
  register: 'register',
  home: '',
  games: 'games',
  contactUs: "contact",
  userDetails: "user-details",
  shoppingCart: "cart"
};

@Injectable()
export class NavigationService {

  private currentPath = RoutesPaths.home;
  private currentPathSource = new Subject<string>();
  currentPathSource$ = this.currentPathSource.asObservable();

  constructor(private _router: Router) {
    this.subscribeToRouterEvents();
  }

  subscribeToRouterEvents() {
    this._router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.currentPath = event.url.split('/')[1];
        this.currentPath = this.currentPath.split('?')[0];
        this.currentPathSource.next(this.currentPath)
      }
    });
  }

  currentPath$(): Observable<string> {
    return this.currentPathSource$;
  }

  goToHome() {
    this._router.navigate(['/' + RoutesPaths.home]);
  }

  goToLogin(justRegistered?: boolean) {
    if (justRegistered)
      this._router.navigate(['/' + RoutesPaths.login, {justRegistered: justRegistered}]);
    else
      this._router.navigate(['/' + RoutesPaths.login]);
  }

  goToRegister() {
    this._router.navigate(['/' + RoutesPaths.register]);
  }

  goToShoppingCart() {
    this._router.navigate(['/' + RoutesPaths.shoppingCart]);
  }

  private _parseUrl(urlWithParams: string) {
    let urlAndParams: string[] = urlWithParams.split(";");
    let url = urlAndParams[0];
    let urlParams = urlAndParams[1];

    let hashParams = {};
    if (urlParams) {
      urlParams.split("&").forEach(
        (keyValue: string) => {
          let pair = keyValue.split("=");
          hashParams[pair[0]] = pair[1];
        });
    }
    return {url: url, params: hashParams}
  }

  goToContactUs() {
    this._router.navigate(['/' + RoutesPaths.contactUs]);
  }

  goToGameListScreen() {
    this._router.navigate(['/' + RoutesPaths.games]);
  }

  goToGameDetailsPage(gameId: string) {
    this._router.navigate(['/' + RoutesPaths.games, gameId]);
  }

  goToUserDetailsPage() {
    this._router.navigate(['/' + RoutesPaths.userDetails]);
  }

  goToEditGamePage(gameId: string) {
    this._router.navigate(['/' + RoutesPaths.games, gameId, 'edit']);
  }

  goToSellGames() {
    this._router.navigate(['/' + RoutesPaths.games, 'sell']);
  }
}
