import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { IPhoto } from './photo';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class PhotoService {

  constructor(private http: HttpClient) { }

  getAllPhotos(page: number, size: number, type: string): Observable<IPhoto[]> {
    return this.http.get<IPhoto[]>('/api/files?page=' + page + '&size=' + size + '&type=' + type)
      .pipe(
        map(response => response)
      );
  }
}
