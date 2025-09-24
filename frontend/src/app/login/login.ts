import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class LoginComponent {
  loginForm: FormGroup;
  submitted = false;
  successMessage = '';
  errorMessage = '';

  constructor(private fb: FormBuilder, private router: Router, private authService: AuthService) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      senha: ['', Validators.required],
    });
  }

  onLogin() {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: (res: any) => {
          this.successMessage = 'Login realiazado com sucesso!';
          this.errorMessage = '';
          this.loginForm.reset();
          this.submitted = false;
          sessionStorage.setItem('token', res.token);
          this.router.navigate(['/home']);
        },
        error: (err: any) => {
          this.errorMessage = 'Erro ao fazer login usuário.';
          alert(this.errorMessage);
          this.successMessage = '';
        },
      });
    } else {
      console.log('Form inválido!');
    }
  }

  onRegister() {
    this.router.navigate(['/register']);
  }
}
