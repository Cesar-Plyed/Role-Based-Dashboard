import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  ReactiveFormsModule,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UserRole } from '@core/auth/models/user.model';
import { AuthService } from '@core/auth/services/auth.service';

function passwordMatch(control: AbstractControl): ValidationErrors | null {
  const password = control.get('password')?.value;
  const confirm = control.get('confirmPassword')?.value;

  return password && confirm && password !== confirm ? { mismatch: true } : null;
}

@Component({
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  selector: 'app-register',
  styleUrl: './register.css',
  templateUrl: './register.html',
})
export class Register {
  private fb = inject(FormBuilder);
  readonly loading = signal(false);
  readonly errorMsg = signal<string | null>(null);

  form = this.fb.group(
    {
      username: ['', [Validators.required, Validators.minLength(8)]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      role: this.fb.control<UserRole>('USER', { nonNullable: true }),
      confirmPassword: ['', Validators.required],
    },
    { validators: passwordMatch },
  );

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.errorMsg.set(null);

    const { username, password, role } = this.form.getRawValue();

    this.authService.register({ username: username!, password: password!, role: role! }).subscribe({
      next: (res) => {
        this.loading.set(false);
        const routeRole = res?.role ?? this.authService.role() ?? null;
        this.router.navigate([this.authService.homeRouteForRole(routeRole)]);
      },
      error: (err) => {
        this.loading.set(false);
        this.errorMsg.set(
          err?.error?.message ?? 'No se ha podido crear tu cuenta. Intentalo de nuevo luego',
        );
      },
    });
  }
}
