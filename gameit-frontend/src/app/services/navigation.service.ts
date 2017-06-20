import {Injectable} from "@angular/core";
import {Router, NavigationEnd} from "@angular/router";
import {Subject, Observable} from "rxjs";

export const RoutesPaths = {
  login: 'login',
  register: 'register',
  home: '',
  games: 'games',
  contactUs: "contact"
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

  goToLogin(errorMsg?: string) {
    if (errorMsg)
      this._router.navigate(['/' + RoutesPaths.login, {error: errorMsg}]);
    else
      this._router.navigate(['/' + RoutesPaths.login]);
  }

  goToRegister() {
    this._router.navigate(['/' + RoutesPaths.register]);
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
}
