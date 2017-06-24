import {deserialize, UnderscoreCase, SerializeKeysTo, DeserializeKeysFrom} from "cerialize";

export class Authority {
  @deserialize name: string;
}

export const Authorities = {
  ADMIN: "ADMIN",
  SELLER: "SELLER",
  BUYER: "BUYER"
};
