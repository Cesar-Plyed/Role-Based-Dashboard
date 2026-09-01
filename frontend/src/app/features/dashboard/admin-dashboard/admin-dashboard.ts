import { Component } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-admin-dashboard',
  styleUrl: './admin-dashboard.css',
  templateUrl: './admin-dashboard.html',
})
export class AdminDashboard {
  usuarios = [
    {
      id: 1,
      username: 'admin',
      role: 'ADMIN',
    },
    {
      id: 2,
      username: 'alice',
      role: 'USER',
    },
    {
      id: 3,
      username: 'bob',
      role: 'USER',
    },
    {
      id: 4,
      username: 'Pablito 123',
      role: 'ADMIN',
    },
    {
      id: 5,
      username: 'Pablito supervisor',
      role: 'SUPERVISOR',
    },
  ];

  poducts = [{ name: 'Camiseta', price: 19.99, stock: 100 }];
}
