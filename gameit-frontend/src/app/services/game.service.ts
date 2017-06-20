import {Injectable} from "@angular/core";
import {Subject} from "rxjs/Subject";
import {Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {BaseService} from "./base.service";
import {User} from "../models/user.model";
import {Deserialize} from "cerialize";
import {Response, URLSearchParams} from "@angular/http";
import {UserService} from "./user.service";
import {Game} from "../models/game.model";
import {PaginatedResource} from "../models/paginanted-resource.model";

@Injectable()
export class GameService extends BaseService {

  constructor(private http: Http) {
    super(http);
  }

  getAllGames(pageNumber?: number, pageSize?: number) {
    const endpoint = 'my-gateway/games';
    let params = {};
    if (pageNumber != null) params["page"] = pageNumber;
    if (pageSize != null) params["size"] = pageSize;

    return this.get(endpoint, params)
      .map((res: Response) => {
        console.log(res.json());
        return new PaginatedResource().deserializeGeneric(res.json(), Game);
      });
  }
}
