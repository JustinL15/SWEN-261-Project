import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Product } from '../product';
import { ProductService } from '../services/product.service';
import { Review } from '../review'
import { ReviewService } from '../services/review-service';

@Component({
  selector: 'app-edit-product-detail',
  templateUrl: './edit-product-detail.component.html',
  styleUrls: ['./edit-product-detail.component.css']
})
export class EditProductDetailComponent implements OnInit {
  product: Product | undefined;
  reviews: Review | undefined;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private reviewService: ReviewService,
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

  /* returns to previous page (should be the inventory page) */
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

  deleteReview(review: Review): void {
    this.reviewService.deleteReview(review.id).subscribe();
    this.goBack();
  }

  commentReview(review: Review): void {
    review.reviewContent = review.reviewContent.trim();
    this.reviewService.addReview(review).subscribe();
    this.goBack();
  }

  getReviews(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.reviewService.getReview(id).subscribe(reviews => this.reviews = reviews);
  }

}
