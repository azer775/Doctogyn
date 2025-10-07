import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Cabinet } from '../Models/Cabinet';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }
  set token(token: string) {
    localStorage.setItem('token', token);
  }
  get token() {
    return localStorage.getItem('token') as string;
  }
  isTokenValid() {
    const token = this.token;
    if (!token) {
      return false;
    }
    // decode the token
    const jwtHelper = new JwtHelperService();
    // check expiry date
    const isTokenExpired = jwtHelper.isTokenExpired(token);
    if (isTokenExpired) {
      localStorage.clear();
      return false;
    }
    return true;
  }

  isTokenNotValid() {
    return !this.isTokenValid();
  }

  get userRoles(): string[] {
    const token = this.token;
    if (token) {
      const jwtHelper = new JwtHelperService();
      const decodedToken = jwtHelper.decodeToken(token);
      console.log(decodedToken.authorities);
      return decodedToken.authorities;
    }
    return [];
  }
  get userRols(): string[] {
    const token = this.token;
    if (token) {
      const jwtHelper = new JwtHelperService();
      const decodedToken = jwtHelper.decodeToken(token);
      console.log(decodedToken.authorities);
      return decodedToken.authorities;
    }
    return [];
  }
  test(){
    const token = this.token;
    const jwtHelper = new JwtHelperService();
    const decodedToken = jwtHelper.decodeToken(token);
    console.log(decodedToken.sub);
  }

  get cabinet(): Cabinet | null {
    const token = this.token;
    if (token) {
      const jwtHelper = new JwtHelperService();
      const decodedToken = jwtHelper.decodeToken(token);
      if (decodedToken.cabinet) {
        const cabinet = new Cabinet();
        cabinet.id = decodedToken.cabinet.id;
        cabinet.adress = decodedToken.cabinet.adress;
        cabinet.tel = decodedToken.cabinet.tel;
        cabinet.fertilityRate = decodedToken.cabinet.fertilityRate;
        cabinet.gynecologyRate = decodedToken.cabinet.gynecologyRate;
        cabinet.obstetricsRate = decodedToken.cabinet.obstetricsRate;
        cabinet.echographyRate = decodedToken.cabinet.echographyRate;
        return cabinet;
      }
    }
    return null;
  }
}
