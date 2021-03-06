import {
  autoserialize, autoserializeAs, deserialize, deserializeAs
} from "cerialize";
import {Deserialization} from "./shared/deserialization.model";
import {User} from "./user.model";

export class Game extends Deserialization {
  static PAGINATED_ARRAY_NAME = "games";
  static PAGE_SIZE = 7;
  @autoserialize id: string;
  @autoserialize createdAt: number;
  @autoserialize updatedAt: number;
  @autoserialize name: string;
  @autoserialize releaseYear: number;
  @autoserialize description: string;
  @autoserialize category: string;
  @autoserialize gamePrice: number;
  @deserialize previewImage: string;
  @autoserialize itemsLeft: number;
  @autoserialize discountPercent: number;
  @autoserialize userGameOrders: any;
  @autoserializeAs(User) userSeller: User;

  quantity: number = 1;

  getPreviewImage() {
    return this.previewImage ? "data:image/png;base64," + this.previewImage : "/assets/images/nopreview.png";
  }

  getPriceWithDiscount() {
    return this.gamePrice - (this.gamePrice * (this.discountPercent / 100));
  }
}
