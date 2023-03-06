import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { InventoryComponent } from '../inventory/inventory.component';

@Component({
  selector: 'app-edit-product-detail',
  templateUrl: './edit-product-detail.component.html',
  styleUrls: ['./edit-product-detail.component.css']
})
export class EditProductDetailComponent {
  product: Product | undefined;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.getProduct();
  }

  getProduct(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.productService.getProduct(id).subscribe(product => this.product = product);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.product) {
      this.productService.updateProduct(this.product).subscribe(() => this.goBack());
    }
  }

  delete(product: Product): void {
    this.productService.deleteProduct(product.id).subscribe();
    this.goBack();
  }

}
