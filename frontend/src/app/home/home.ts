import { Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TableComponent } from '../components/table/table';
import { PedidoService } from '../service/ordes.service';
import { ProductService } from '../service/product.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, TableComponent],
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
})
export class HomeComponent implements OnInit {
  columns = ['id', 'nome', 'descricao', 'preco', 'dataCriacao', 'comprar'];
  data: any[] = [];

  constructor(
    private productService: ProductService,
    private pedidoService: PedidoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarProdutos();
  }

  carregarProdutos(): void {
    this.productService.getAllProducts().subscribe({
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

  comprarProduto(produto: any) {
    // Pergunta ao usuário se quer confirmar a compra
    const confirmado = confirm(
      `Deseja confirmar a compra do produto "${produto.nome}" por R$ ${produto.preco}?`
    );

    if (!confirmado) {
      return;
    }

    const pedido = {
      produtoId: produto.id,
      valor: produto.preco,
    };

    this.pedidoService.criarPedido(pedido).subscribe({
      next: (res: any) => {
        alert('Pedido criado com sucesso!');
      },
      error: (err: any) => {
        console.error('Erro ao criar pedido:', err);
        alert('Erro ao criar pedido');
      },
    });
  }

  onMyOrders() {
    this.router.navigate(['/orders']);
  }
}
