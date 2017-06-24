import {Injectable} from "@angular/core";
import {Subject} from "rxjs/Subject";
import {Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {BaseService} from "./base.service";
import {User} from "../models/user.model";
import {Deserialize} from "cerialize";
import {Response, URLSearchParams} from "@angular/http";
import {UserService} from "./user.service";
import {RouterLink} from "@angular/router";
import {Authorities, Authority} from "../models/authority.model";

@Injectable()
export class AuthService extends BaseService {
  private isLoggedInSource = new Subject<boolean>();
  isLoggedIn$ = this.isLoggedInSource.asObservable();

  constructor(private http: Http, private _userService: UserService) {
    super(http);
    this.isLoggedInSource.next(sessionStorage.getItem('access-token') != null);
  }

  isLoggedIn(): boolean {
    return sessionStorage.getItem('access-token') != null;
  }

  fullLogin(username: string, password: string) {
    console.log("using login");
    return this.login(username, password).switchMap((result) => result ? this.getUserDetails() : Observable.throw("error"));
  }

  login(username: string, password: string): Observable<any> {
    const endpoint = 'my-auth/login';
    let params = {
      username: username,
      password: password
    };

    return this.post(endpoint, params).map((json: Response) => {
      let token = json.headers.get("Authorization");
      if (token) {
        token = token.split(" ")[1];
      } else {
        return false;
      }

      sessionStorage.setItem('access-token', token);
      return true;
    }).catch((error: any) => {
      this._userService.onUserDidFailToLogin();
      return Observable.throw(error || 'Server error');
    });
  }

  getUserDetails() {
    const endpoint = 'my-auth/me';
    return this.get(endpoint).map((json: Response) => {
      console.log("getUserDetails");
      let user = new User().deserialize(json.json());
      sessionStorage.setItem('user', JSON.stringify(user));

      this._userService.onUserDidLogin(user);
      return user;
    }).catch((error: any) => {
      this._userService.onUserDidFailToLogin();
      return Observable.throw(error || 'Server error');
    });
  }

  signup(userToBeCreated: User, isSellerAccount: boolean): Observable<User> {
    const endpoint = 'my-auth/users';

    let params = {
      user: userToBeCreated
    };

    if(isSellerAccount) {
      params["authorities"] = [Authorities.SELLER, Authorities.BUYER];
    }

    return this.post(endpoint, params)
      .map((res: Response) => res.json())
      .catch((error: any) => {
        this.isLoggedInSource.next(false);
        return Observable.throw(error || 'Server error');
      });
  }

  resetPassword(email: string): Observable<boolean> {
    const endpoint = 'v1/auth/forgot_password';
    let params = new URLSearchParams();
    params.append('email', email);
    return this.http.post(endpoint, null, params)
      .map((res: Response) => res.json())
      .map((json) => {
        return true;
      })
      .catch((error: any) => {
        this.isLoggedInSource.next(false);
        return Observable.throw(error || 'Server error');
      });
  }

  logOut(callback: () => void) {
    sessionStorage.removeItem('access-token');
    sessionStorage.removeItem('user');
    this.isLoggedInSource.next(false);
    callback && callback();
  }
}

interface RegisterFormData {
  username: string,
  password: string,
  email: string
}
