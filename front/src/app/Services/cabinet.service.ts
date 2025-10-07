import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cabinet } from '../Models/Cabinet';

@Injectable({
  providedIn: 'root'
})
export class CabinetService {
  private apiUrl = 'http://localhost:8020/cabinet';

  constructor(
    private http: HttpClient,
  ) { }
  updateCabinet(cabinet: Cabinet) {
    return this.http.put(`${this.apiUrl}/update`, cabinet);
  }
}
