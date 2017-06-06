import {Injectable} from "@angular/core";
import {BehaviorSubject} from "rxjs";
import {User} from "../models/user.model";
import {Serialize} from "cerialize";
@Injectable()
export class UserService {

  private isLoggedInSource = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this.isLoggedInSource.asObservable();

  private userSource = new BehaviorSubject<User>(null);
  userSource$ = this.userSource.asObservable();

  isLoggedIn() {
    return this.isLoggedInSource.getValue();
  }

  constructor() {
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

  private getUserFromSession() {
    let json = JSON.parse(sessionStorage.getItem('user'));
    return new User().deserialize(json);
  }
}
