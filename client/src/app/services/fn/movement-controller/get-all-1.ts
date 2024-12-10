import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CustomPageDtoObject } from '../../models/custom-page-dto-object';
import { Pageable } from '../../models/pageable';

export interface GetAll1$Params {
  pageable: Pageable;
}

export function getAll1(http: HttpClient, rootUrl: string, params: GetAll1$Params, context?: HttpContext): Observable<StrictHttpResponse<CustomPageDtoObject>> {
  const rb = new RequestBuilder(rootUrl, getAll1.PATH, 'get');
  if (params) {
    rb.query('pageable', params.pageable, {});
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<CustomPageDtoObject>;
    })
  );
}

getAll1.PATH = '/api/v1/movement/all';
