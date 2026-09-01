import { inject } from '@angular/core';
import { CanActivateChildFn, Router } from '@angular/router';
import { UserRole } from '@core/auth/models/user.model';
import { AuthService } from '@core/auth/services/auth.service';

export function roleGuard(allowedRoles: UserRole[]): CanActivateChildFn {
  return () => {
    const authService = inject(AuthService);
    const router = inject(Router);

    if (!authService.isAuthenticated()) {
      router.navigate(['/login']);
      return false;
    }

    const currentRole = authService.role();
    if (currentRole && allowedRoles.includes(currentRole)) {
      return true;
    }

    const fallbackRoute = authService.homeRouteForRole(currentRole);
    if (fallbackRoute !== '/login') {
      router.navigate([fallbackRoute]);
    } else {
      router.navigate(['/login']);
    }

    return false;
  };
}
