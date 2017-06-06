import {deserialize, UnderscoreCase, SerializeKeysTo, DeserializeKeysFrom} from "cerialize";

export class Authority {
  @deserialize name: string;
}
