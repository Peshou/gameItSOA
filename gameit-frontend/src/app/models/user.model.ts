import {deserialize, deserializeAs} from "cerialize";
import {Authority} from "./authority.model";
import {Deserialization} from "./shared/deserialization.model";

export class User extends Deserialization  {
  @deserialize id: string;
  @deserialize createdAt: number;
  @deserialize updatedAt: number;
  @deserialize username: string;
  @deserialize password: string;
  @deserialize email: string;
  @deserializeAs(Authority) authorities: Authority[];

  isAdmin() {
    return !!this.authorities.find((authority: Authority) => {
      return authority.name == "ADMIN";
    });
  }
}
