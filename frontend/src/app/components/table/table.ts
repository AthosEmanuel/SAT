import { Component, EventEmitter, Input, Output } from '@angular/core';

import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './table.component.html',
  styleUrls: ['./table.css'],
})
export class TableComponent {
  @Input() columns: string[] = [];
  @Input() data: any[] = [];

  @Output() comprar = new EventEmitter<any>();

  onComprar(row: any) {
    this.comprar.emit(row);
  }
}
