import {deserialize, deserializeAs, UnderscoreCase, SerializeKeysTo, DeserializeKeysFrom} from "cerialize";
import {Deserialization} from "./shared/deserialization.model";
import {User} from "./user.model";

export class Game extends Deserialization  {
  @deserialize id: string;
  @deserialize createdAt: number;
  @deserialize updatedAt: number;
  @deserialize name: string;
  @deserialize releaseYear: number;
  @deserialize description: string;
  @deserialize category: string;
  @deserialize gamePrice: number;
  @deserialize imagePaths: string[];
  @deserialize itemsLeft: number;
  @deserialize discountPercent: number;
  @deserialize userGameOrders: any;
  @deserializeAs(User) userSeller: User;
}
