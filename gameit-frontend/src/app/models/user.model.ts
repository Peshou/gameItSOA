import {deserialize, deserializeAs, autoserializeAs, autoserialize} from "cerialize";
import {Authority} from "./authority.model";
import {Deserialization} from "./shared/deserialization.model";

export class User extends Deserialization  {
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
}
