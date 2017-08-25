import {Deserialization} from "./shared/deserialization.model";
import {Game} from "./game.model";
import {deserializeAs, deserialize} from "cerialize";
import {User} from "./user.model";

export class UserGameOrder extends Deserialization {
  static PAGE_SIZE = 7;
  @deserializeAs(Game) game: Game;
  @deserializeAs(User) user: User;
  @deserialize createdAt: number;
  @deserialize updatedAt: number;
  @deserialize paymentProcessorChargeId: string;
}
