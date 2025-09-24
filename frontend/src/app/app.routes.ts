import { RouterModule, Routes } from '@angular/router';

import { NgModule } from '@angular/core';
import { HomeComponent } from './home/home';
import { LoginComponent } from './login/login';
import { OrdersComponent } from './orders/orders';
import { RegisterComponent } from './register/register';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'orders', component: OrdersComponent },
  { path: '**', redirectTo: 'login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
