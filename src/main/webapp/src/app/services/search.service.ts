import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs/index";
import {ApiResponse} from "../models/api.response";

@Injectable()
export class SearchService {

  //baseQueryUrl: string = '/search/';
  baseQueryUrl: string = 'http://localhost:8080/search/';
  query: string = '0/1/false';

  constructor(private http: HttpClient) { }

  getCompaniesByInitialSetup(payload): Observable<ApiResponse> {
    this.query = payload.offset + "/" + payload.size + "/" + payload.aggregate;
    return this.http.get<ApiResponse>(this.baseQueryUrl + this.query);
  }

  getCurrencies(): Observable<ApiResponse> {
    this.query = "currencies";
    return this.http.get<ApiResponse>(this.baseQueryUrl + this.query);
  }

  getTypes(): Observable<ApiResponse> {
    this.query = "types";
    return this.http.get<ApiResponse>(this.baseQueryUrl + this.query);
  }

  getTotals(payload): Observable<ApiResponse> {
    this.query = "t/" + payload.name + "/" + payload.currency + "/" + payload.type;
    return this.http.get<ApiResponse>(this.baseQueryUrl + this.query);
  }

  getIntersections(payload): Observable<ApiResponse> {
    this.query = "p/" + payload.name + "/" + payload.currency + "/" + payload.type;
    return this.http.get<ApiResponse>(this.baseQueryUrl + this.query);
  }
}
