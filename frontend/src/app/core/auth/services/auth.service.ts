import { HttpClient } from '@angular/common/http';
import { computed, inject, Injectable, PLATFORM_ID, signal } from '@angular/core';
import {
  AuthResponse,
  LoginRequest,
  RegisterRequest,
  User,
  UserRole,
} from '@core/auth/models/user.model';
import { Observable, tap } from 'rxjs';
import { enviroment } from '../../../../enviroments/enviroment';
import { isPlatformBrowser } from '@angular/common';

const TOKEN_KEY = 'access_token';
const REFRESH_KEY = 'refresh_token';
const USER_KEY = 'current_user';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly platformId = inject(PLATFORM_ID);
  private readonly isBrowser = isPlatformBrowser(this.platformId);
  private readonly _currentUser = signal<User | null>(this.readStoredUser());
  readonly currentUser = this._currentUser.asReadonly();
  readonly isLoggedIn = computed(() => !!this._currentUser());
  readonly role = computed<UserRole | null>(() => this._currentUser()?.role ?? null);

  constructor(private http: HttpClient) {}

  getToken(): string | null {
    return this.isBrowser ? localStorage.getItem(TOKEN_KEY) : null;
  }

  getRefreshToken(): string | null {
    return this.isBrowser ? localStorage.getItem(REFRESH_KEY) : null;
  }

  private persistSession(res: AuthResponse): void {
    if (!res?.token || !res?.role) {
      return;
    }

    const user: User = { username: res.username, role: res.role };

    localStorage.setItem(TOKEN_KEY, res.token);
    if (res.refreshToken) {
      localStorage.setItem(REFRESH_KEY, res.refreshToken);
    }
    localStorage.setItem(USER_KEY, JSON.stringify(user));
    this._currentUser.set(user);
  }

  private readStoredUser(): User | null {
    if (!this.isBrowser) return null;
    const raw = localStorage.getItem(USER_KEY);
    if (!raw) return null;
    try {
      return JSON.parse(raw) as User;
    } catch (error) {
      return null;
    }
  }

  private isTokenExpired(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      if (!payload) return false;
      return Date.now() >= payload.exp * 1000;
    } catch (error) {
      return false;
    }
  }

  login(payload: LoginRequest): Observable<AuthResponse> {
    const url = `${enviroment.apiUrl}${enviroment.authEndpoints.login}`;
    return this.http.post<AuthResponse>(url, payload).pipe(tap((res) => this.persistSession(res)));
  }

  register(payload: RegisterRequest): Observable<AuthResponse> {
    const url = `${enviroment.apiUrl}${enviroment.authEndpoints.register}`;
    return this.http.post<AuthResponse>(url, payload).pipe(tap((res) => this.persistSession(res)));
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(REFRESH_KEY);
    localStorage.removeItem(USER_KEY);
    this._currentUser.set(null);
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    if (!token) return false;
    return !this.isTokenExpired(token);
  }

  hasRole(...roles: UserRole[]): boolean {
    const current = this.role();
    return !!current && roles.includes(current);
  }

  homeRouteForRole(role?: UserRole | null): string {
    switch (role ?? null) {
      case 'ADMIN':
        return '/dashboard/admin';
      case 'SUPERVISOR':
        return '/dashboard/supervisor';
      case 'PROVEEDOR':
        return '/dashboard/proveedor';
      case 'USER':
        return '/dashboard/usuario';
      default:
        return '/login';
    }
  }
}
