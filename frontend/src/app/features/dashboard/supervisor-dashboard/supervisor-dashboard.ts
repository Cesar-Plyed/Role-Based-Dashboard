import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  imports: [CommonModule],
  selector: 'app-supervisor-dashboard',
  styleUrl: './supervisor-dashboard.css',
  templateUrl: './supervisor-dashboard.html',
})
export class SupervisorDashboard {
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
}
