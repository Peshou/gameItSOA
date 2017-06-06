
import {Injectable} from "@angular/core";
import {Router} from "@angular/router";

export const RoutesPaths = {
  login: 'login',
  register: 'register',
  home: 'home',
};

@Injectable()
export class NavigationService {

  constructor(private _router: Router) {
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
}
