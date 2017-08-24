import {autoserialize} from "cerialize";

export class Authority {
  @autoserialize name: string;
}

export const Authorities = {
  ADMIN: "ADMIN",
  SELLER: "SELLER",
  BUYER: "BUYER"
};
