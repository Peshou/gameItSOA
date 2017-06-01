import { Injectable } from '@angular/core';
import {
  Http, Headers, RequestOptionsArgs, Response, Request, RequestMethod, RequestOptions,
} from "@angular/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable()
export class BaseService {

  constructor(public _http: Http) { }

  private _buildAuthHeader(): string {
    return 'Bearer '+sessionStorage.getItem('access-token');
  }
  public get(endpoint: string, options?: RequestOptionsArgs): Observable<Response> {
    return this._request(RequestMethod.Get, endpoint, null, options);
  }
  public post(endpoint: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    return this._request(RequestMethod.Post, endpoint, body, options);
  }
  public put(endpoint: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    return this._request(RequestMethod.Put, endpoint, body, options);
  }
  public delete(endpoint: string, options?: RequestOptionsArgs): Observable<Response> {
    return this._request(RequestMethod.Delete, endpoint, null, options);
  }
  public patch(endpoint: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    return this._request(RequestMethod.Patch, endpoint, body, options);
  }
  public head(endpoint: string, options?: RequestOptionsArgs): Observable<Response> {
    return this._request(RequestMethod.Head, endpoint, null, options);
  }
  private _request(method: RequestMethod, endpoint: string, body?: any, options?: RequestOptionsArgs): Observable<Response> {
    const url = environment.baseApiUrl+'/'+endpoint;
    let requestOptions = new RequestOptions(Object.assign({
      method: method,
      url: url,
      body: body
    }, options));
    if (!requestOptions.headers) {
      requestOptions.headers = new Headers();
    }
    if (sessionStorage.getItem('access-token')) {
      requestOptions.headers.set("Authorization", this._buildAuthHeader())
    }
    return Observable.create((observer) => {
      this._http.request(new Request(requestOptions))
        .map(res=> res.json())
        .subscribe(
          (res) => {
            observer.next(res);
            observer.complete();
          },
          (err) => {
            observer.error(err);
          })
    })
  }
}
