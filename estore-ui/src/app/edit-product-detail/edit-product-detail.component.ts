import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Product } from '../product';
import { ProductService } from '../product.service';

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

  /* displays the product on init */
  ngOnInit(): void {
    this.getProduct();
  }

  /* gets the product with the id */
  getProduct(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.productService.getProduct(id).subscribe(product => this.product = product);
  }

  /* returns to previous paige (should be the inventory paige) */
  goBack(): void {
    this.location.back();
  }

  /* saves the product changes */
  save(): void {
    if (this.product) {
      this.productService.updateProduct(this.product).subscribe(() => this.goBack());
    }
  }

  /* removes the product from inventory */
  delete(product: Product): void {
    this.productService.deleteProduct(product.id).subscribe();
    this.goBack();
  }

}
