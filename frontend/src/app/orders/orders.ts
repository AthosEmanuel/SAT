import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { TableComponent } from '../components/table/table';
import { PedidoService } from '../service/ordes.service';

@Component({
  selector: 'app-orders',
  imports: [TableComponent],
  templateUrl: './orders.html',
  styleUrl: './orders.css',
})
export class OrdersComponent implements OnInit {
  columns = ['id', 'produtoNome', 'produtoDescricao', 'valor', 'dataCriacao'];
  data: any[] = [];

  constructor(private router: Router, private pedidoService: PedidoService) {}

  ngOnInit(): void {
    this.carregarPedidos();
  }

  carregarPedidos(): void {
    this.pedidoService.listarPedidos().subscribe({
      next: (res) => {
        this.data = res.map((produto: any) => ({
          ...produto,
          dataCriacao: new Date(produto.dataCriacao).toLocaleDateString('pt-BR'),
        }));
      },
      error: (err) => {
        console.error('Erro ao carregar produtos:', err);
      },
    });
  }

  onBack() {
    this.router.navigate(['/home']);
  }
}
