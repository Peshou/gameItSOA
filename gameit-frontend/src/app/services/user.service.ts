import {Injectable} from "@angular/core";
import {BehaviorSubject} from "rxjs";
import {User} from "../models/user.model";
import {Serialize} from "cerialize";
import {BaseService} from "./base.service";
import {Http} from "@angular/http";
@Injectable()
export class UserService extends BaseService {

  private isLoggedInSource = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this.isLoggedInSource.asObservable();

  private userSource = new BehaviorSubject<User>(null);
  userSource$ = this.userSource.asObservable();

  isLoggedIn() {
    return this.isLoggedInSource.getValue();
  }

  constructor(http: Http) {
    super(http);
    this.isLoggedInSource.next(sessionStorage.getItem('access-token') != null);
    this.userSource.next(this.getUserFromSession());
  }

  onUserDidLogin(user: User) {
    this.isLoggedInSource.next(true);
    this.userSource.next(user);
  }

  onUserDidFailToLogin() {
    this.isLoggedInSource.next(false);
    this.userSource.next(null);
  }

  onLogoutUser() {
    this.removeUserFromSession();
    this.isLoggedInSource.next(false);
    this.userSource.next(null);
  }

  onDidUpdateUser(user: User) {
    sessionStorage.setItem('user', JSON.stringify(Serialize(user)));
    this.userSource.next(user);
  }

  private removeUserFromSession() {
    sessionStorage.removeItem('access-token');
    sessionStorage.removeItem('user')
  }

  getUserFromSession(): User {
    let json = JSON.parse(sessionStorage.getItem('user'));
    return new User().deserialize(json);
  }

  getUser(userId: string) {
    const endpoint = "my-auth/users/" + userId;
    return this.get(endpoint, null).map((res: any) => {
      return new User().deserialize(res.json());
    });
  }
}
