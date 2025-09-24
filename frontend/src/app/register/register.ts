import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrls: ['./register.css'],
})
export class RegisterComponent {
  registerForm: FormGroup;
  submitted = false;
  successMessage = '';
  errorMessage = '';

  constructor(private fb: FormBuilder, private router: Router, private authService: AuthService) {
    this.registerForm = this.fb.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      senha: ['', Validators.required],
    });
  }

  // Voltar para a página inicial
  onBack() {
    this.router.navigate(['/']);
  }

  // Enviar o formulário
  onRegister() {
    this.submitted = true;
    if (this.registerForm.valid) {
      this.authService.register(this.registerForm.value).subscribe({
        next: (res) => {
          this.successMessage = 'Usuário registrado com sucesso!';
          this.errorMessage = '';
          this.registerForm.reset();
          this.submitted = false;
          alert(this.successMessage);
          this.router.navigate(['/']);
        },
        error: (err) => {
          this.errorMessage = 'Erro ao registrar usuário.';
          alert(this.errorMessage);
          this.successMessage = '';
        },
      });
    }
  }
}
