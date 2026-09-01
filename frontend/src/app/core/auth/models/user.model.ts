export type UserRole = 'ADMIN' | 'USER' | 'SUPERVISOR' | 'PROVEEDOR';

export interface User {
  username: string;
  role: UserRole;
}

export interface AuthResponse {
  token: string;
  refreshToken?: string;
  username: string;
  role: UserRole;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  password: string;
  role?: UserRole;
}
