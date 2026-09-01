import { Routes } from '@angular/router';
import { authGuard } from '@core/auth/guards/auth.guard';
import { roleGuard } from '@core/auth/guards/role.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    loadComponent: () => import('@features/auth/login/login').then((m) => m.Login),
  },
  {
    path: 'register',
    loadComponent: () => import('@features/auth/register/register').then((m) => m.Register),
  },

  {
    path: 'dashboard',
    canActivate: [authGuard],
    loadComponent: () =>
      import('@features/dashboard/layout-dashboard/layout-dashboard').then(
        (m) => m.LayoutDashboard,
      ),
    children: [
      {
        path: 'admin',
        canActivate: [roleGuard(['ADMIN'])],
        loadComponent: () =>
          import('@features/dashboard/admin-dashboard/admin-dashboard').then(
            (m) => m.AdminDashboard,
          ),
      },
      {
        path: 'supervisor',
        canActivate: [roleGuard(['SUPERVISOR'])],
        loadComponent: () =>
          import('@features/dashboard/supervisor-dashboard/supervisor-dashboard').then(
            (m) => m.SupervisorDashboard,
          ),
      },
      {
        path: 'usuario',
        canActivate: [roleGuard(['USER'])],
        loadComponent: () =>
          import('@features/dashboard/user-dashboard/user-dashboard').then((m) => m.UserDashboard),
      },
      {
        path: 'proveedor',
        canActivate: [roleGuard(['PROVEEDOR'])],
        loadComponent: () =>
          import('@features/dashboard/user-dashboard/user-dashboard').then((m) => m.UserDashboard),
      },
    ],
  },

  { path: '**', redirectTo: 'login' },
];
