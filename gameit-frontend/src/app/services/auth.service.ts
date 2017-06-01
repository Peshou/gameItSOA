import {Injectable} from "@angular/core";
import {Subject} from "rxjs/Subject";
import {Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {BaseService} from "./base.service";
import {User} from "../models/user.model";
import {Deserialize} from "cerialize";
import {Response, URLSearchParams} from "@angular/http";

@Injectable()
export class AuthService extends BaseService {
  private isLoggedInSource = new Subject<boolean>();
  isLoggedIn$ = this.isLoggedInSource.asObservable();

  constructor(private http: Http) {
    super(http);
    this.isLoggedInSource.next(sessionStorage.getItem('access-token') != null);
  }

  isLoggedIn(): boolean {
    return sessionStorage.getItem('access-token') != null;
  }

  fullLogin(email: string, password: string) {
    let login = this.login(email, password);
    let getUserDetails = this.getUserDetails();
    return Observable.forkJoin(login, getUserDetails).flatMap((result: any[]) => {
        let successLogin = result[0];
        let user = result[1];

        return Observable.of(user);
    });
  }

  login(email: string, password: string): Observable<any> {
    const endpoint = 'my-auth/login';
    let params = {};
    params['username']= email;
    params['password']= password;
      return this.post(endpoint, params).map((json: Response) => {
      let token = json.headers.get("Authorization");
      if (token) {
        token = token.split(" ")[1];
      }

      sessionStorage.setItem('access-token', token);
        return true;
      })
      .catch((error: any) => {
        this.isLoggedInSource.next(false);
        return Observable.throw(error || 'Server error');
      });
  }

  getUserDetails() {
    const endpoint = 'my-auth/me';
    return this.get(endpoint).map((json: Response) => {
      let user = Deserialize(json, User);
      sessionStorage.setItem('user', JSON.stringify(json));
      this.isLoggedInSource.next(true);
      return user;
    }) .catch((error: any) => {
      this.isLoggedInSource.next(false);
      return Observable.throw(error || 'Server error');
    });
  }
  signup(displayName: string, email: string, password: string, phone: string): Observable<User> {
    const endpoint = 'v2/auth/sign_up';
    let params = new URLSearchParams();
    params.append('email', email);
    params.append('password', password);
    params.append('display_name', password);
    if (phone) {
      params.append('phone_number', password);
    }
    return this.http.post(endpoint, null, params)
      .map((res: Response) => res.json())
      .map((json) => {
        let user = Deserialize(json, User);
        sessionStorage.setItem('access-token', user.access_token);
        sessionStorage.setItem('user', JSON.stringify(json));
        this.isLoggedInSource.next(true);
        return user;
      })
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
    callback();
  }
}
