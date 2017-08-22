import {Deserialization} from "./shared/deserialization.model";
import {autoserialize} from "cerialize";
export class ContactInfo extends Deserialization {
  @autoserialize name: string;
  @autoserialize email: string;
  @autoserialize message: string;
}
