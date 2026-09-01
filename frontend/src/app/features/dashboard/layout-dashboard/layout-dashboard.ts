import { CommonModule } from '@angular/common';
import { Component, computed, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { UserRole } from '@core/auth/models/user.model';
import { AuthService } from '@core/auth/services/auth.service';

interface NavItem {
  label: string;
  path: string;
  icon: string;
}

const NAV_BY_ROLE: Record<UserRole, NavItem[]> = {
  ADMIN: [
    { label: 'Resumen', path: '/dashboard/admin', icon: 'gid' },
    { label: 'Usuarios', path: '/dashboard/admin/usuarios', icon: 'users' },
    { label: 'Configuracion', path: '/dashboard/admin/config', icon: 'settings' },
  ],
  SUPERVISOR: [
    { label: 'Resumen', path: '/dashboard/supervisor', icon: 'grid' },
    { label: 'Equipo', path: '/dashboard/supervisor/equipo', icon: 'users' },
    { label: 'Reportes', path: '/dashboard/supervisor/reportes', icon: 'chart' },
  ],
  USER: [
    { label: 'Resumen', path: '/dashboard/usuario', icon: 'grid' },
    { label: 'Mis tareas', path: '/dashboard/usuario/tareas', icon: 'list' },
  ],
  PROVEEDOR: [
    { label: 'Resumen', path: '/dashboard/proveedor', icon: 'grid' },
    { label: 'Ordenes', path: '/dashboard/proveedor/ordenes', icon: 'box' },
    { label: 'Facturas', path: '/dashboard/proveedor/facturas', icon: 'file' },
  ],
};

const ROLE_LABEL: Record<UserRole, string> = {
  ADMIN: 'Administrador',
  SUPERVISOR: 'Supervisor',
  USER: 'Usuario',
  PROVEEDOR: 'Proveedor',
};

const ROLE_ACCENT: Record<UserRole, string> = {
  ADMIN: '#6C4AB6',
  SUPERVISOR: '#2F5D62',
  USER: '#2F6FED',
  PROVEEDOR: '#B6862C',
};

@Component({
  imports: [CommonModule, RouterLink, RouterLinkActive, RouterOutlet],
  selector: 'app-layout-dashboard',
  styleUrl: './layout-dashboard.css',
  templateUrl: './layout-dashboard.html',
})
export class LayoutDashboard {
  private authService = inject(AuthService);

  constructor(private router: Router) {}

  readonly user = this.authService.currentUser;

  readonly navItems = computed<NavItem[]>(() => {
    const role = this.user()?.role;
    return role ? NAV_BY_ROLE[role] : [];
  });

  readonly roleLabel = computed(() => {
    const role = this.user()?.role;
    return role ? ROLE_LABEL[role] : '';
  });

  readonly roleAccent = computed(() => {
    const role = this.user()?.role;
    return role ? ROLE_ACCENT[role] : '#2F5D62';
  });

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
