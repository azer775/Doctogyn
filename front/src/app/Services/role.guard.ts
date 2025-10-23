import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from './token.service';
import { Role } from '../Models/enums';

export const roleGuard: CanActivateFn = (route, state) => {
  const tokenService = inject(TokenService);
  const router = inject(Router);

  // Check if user is authenticated first
  if (!tokenService.isTokenValid()) {
    console.warn('User not authenticated, redirecting to login');
    router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }

  // Get user roles
  const userRoles = tokenService.userRoles;
  const userRole = userRoles && userRoles.length > 0 ? userRoles[0] : '';

  // Get required roles from route data
  const requiredRoles = route.data['roles'] as string[];

  // If no roles specified, allow access
  if (!requiredRoles || requiredRoles.length === 0) {
    return true;
  }

  // Check if user has one of the required roles
  const hasAccess = requiredRoles.some(role => 
    userRole === role || userRole === Role[role as keyof typeof Role]
  );

  if (hasAccess) {
    return true;
  }

  // User doesn't have required role, redirect to default page
  console.warn(`User role ${userRole} does not have access to ${state.url}`);
  router.navigate(['/dashboard/scheduler2']); // Redirect to appointments (accessible by all)
  return false;
};
