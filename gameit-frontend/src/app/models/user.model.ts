import {deserialize, deserializeAs, autoserializeAs, autoserialize} from "cerialize";
import {Authority} from "./authority.model";
import {Deserialization} from "./shared/deserialization.model";

export class User extends Deserialization {
  @autoserialize id: string;
  @autoserialize createdAt: number;
  @autoserialize updatedAt: number;
  @autoserialize username: string;
  @deserialize password: string;
  @autoserialize email: string;
  @autoserializeAs(Authority) authorities: Authority[];

  isAdmin() {
    return !!this.authorities.find((authority: Authority) => {
      return authority.name == "ADMIN";
    });
  }

  produceDeepCopy() {
    let userCopy: User = new User();
    userCopy.id = this.id;
    userCopy.createdAt = this.createdAt;
    userCopy.updatedAt = this.updatedAt;
    userCopy.username = this.username;
    userCopy.password = this.password;
    userCopy.email = this.email;
    userCopy.authorities = this.authorities;

    return userCopy;
  }

  printAuthorities() {
    return `Authorities:${this.authorities.map((authority: Authority) => {
      return " " + authority.name;
    })}`;
  }
}
