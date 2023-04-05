import { Component, OnInit } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { ProductService } from '../services/product.service';
import { ReviewService } from '../services/review-service';
import { Review } from '../review';

@Component({
  selector: 'app-review-manage',
  templateUrl: './review-manage.component.html',
  styleUrls: ['./review-manage.component.css']
})
export class ReviewManageComponent implements OnInit {
  productReview: {productName: string, review: Review}[] = [];

  constructor(
    private reviewService: ReviewService,
    private productService: ProductService,
  ) {
    
  }

  ngOnInit(): void {
    this.getReviews();
  }

  async getReviews(): Promise<void> {
    let reviews = await firstValueFrom(this.reviewService.getReviews());

    for (const review of reviews) {
      let name = (await firstValueFrom(this.productService.getProduct(review.productId))).name;
      this.productReview.push({productName: name, review: review});
    }
    
  }

  saveResponse(review: Review, newOwnerResponse: string): void {
    review.ownerResponse = newOwnerResponse;
    this.reviewService.updateReview(review).subscribe();
  }

}
