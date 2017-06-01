import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user.model";

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  providers: [AuthService],
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  email: string;
  password: string;

  constructor(private router: Router,
              private authService: AuthService) {
  }

  ngOnInit() {
  }

  login() {
    this.authService.fullLogin(this.email, this.password)
      .subscribe((user: User) => {
        console.log(user);
      });

    // this.router.navigate(['/']);
  }

}
