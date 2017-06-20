import {Injectable} from '@angular/core';
import {
  Http, Headers, RequestOptionsArgs, Response, Request, RequestMethod, RequestOptions,
} from "@angular/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable()
export class BaseService {

  constructor(public _http: Http) {
  }

  private _buildAuthHeader(): string {
    return 'Bearer ' + sessionStorage.getItem('access-token');
  }

  public get(endpoint: string, params?: any, options?: RequestOptionsArgs): Observable<Response> {
    return this._request(RequestMethod.Get, endpoint, null, params, options).retry(5);
  }

  public post(endpoint: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    return this._request(RequestMethod.Post, endpoint, body, null, options).retry(5);
  }

  public put(endpoint: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    return this._request(RequestMethod.Put, endpoint, body, null, options);
  }

  public delete(endpoint: string, options?: RequestOptionsArgs): Observable<Response> {
    return this._request(RequestMethod.Delete, endpoint, null, null, options);
  }

  public patch(endpoint: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    return this._request(RequestMethod.Patch, endpoint, body, null, options);
  }

  public head(endpoint: string, options?: RequestOptionsArgs): Observable<Response> {
    return this._request(RequestMethod.Head, endpoint, null, null, options);
  }

  private _request(method: RequestMethod, endpoint: string, body?: any, queryParams?: any, options?: RequestOptionsArgs): Observable<Response> {
    const url = environment.baseApiUrl + '/' + endpoint;

    let requestOptions = new RequestOptions(Object.assign({
      method: method,
      url: url,
      body: body,
      search: queryParams
    }, options));
    if (!requestOptions.headers) {
      requestOptions.headers = new Headers();
    }
    if (sessionStorage.getItem('access-token')) {
      requestOptions.headers.set("Authorization", this._buildAuthHeader())
    }
    return Observable.create((observer) => {
      this._http.request(new Request(requestOptions))
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
