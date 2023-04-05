import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { firstValueFrom } from 'rxjs';
import { Product } from '../product';
import { ProductService } from '../services/product.service';
import { UserService } from '../services/user.service';
import { Cart } from '../cart';
import { CartService } from '../services/cart.service';
import { Review } from '../review';
import { Customer } from '../customer';
import { ReviewService } from '../services/review-service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})

export class ProductDetailComponent implements OnInit {
  product: Product | undefined;
  user: Customer | null | undefined;
  quantity: number = 1;
  errorMessage = "";

  // Save the temporary review
  tempReview: Review = {id: 0, productId: 0, customerId: 0, stars: 0, reviewContent: "", ownerResponse: ""} as Review;

  reviews: Review[] = [];
  categories: string[] = [];

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private reviewService: ReviewService,
    private location: Location,
    private userService: UserService,
    private cartService: CartService
  ) {
    
  }

  ngOnInit(): void {
    this.getProduct();
    this.user = this.userService.getCurrentUser();

    this.getReviews();

  }

  getProduct(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
  }

  getReviews(): void {
    this.reviewService.getReviews()
    .subscribe(reviews => this.reviews = reviews);
  }

  addToCart(): void {
    var id: number | undefined = this.product?.id;
    if (this.user !== null && id !== undefined && this.user !== undefined) {
      if (this.user.cartId === 0) {
        this.cartService.createCart({} as Cart).subscribe(cart => {
          if (this.user !== null && id !== undefined && this.user !== undefined) {
            this.userService.updateCustomer({
              id: this.user.id, username: this.user.username,
              name: this.user.name, cartId: cart.id, orders: this.user.orders,
              isAdmin: this.user.isAdmin, password: this.user.password,purchasedIds: this.user.purchasedIds
            }).subscribe(user => {
              if (id !== undefined) {
                this.addProduct(user.cartId, id);
              }
            });
            this.userService.updateCartId(cart.id);
          }
        });
      } else {
        this.addProduct(this.user.cartId, id);
      }
    }
  }

  addProduct(cartId: number, pid: number): void {
    this.errorMessage = "";
    let inCart = 0;
    this.cartService.getCart(cartId).subscribe(cart => {
      var prod = Object.values(cart.inventory).find(reference => reference.id === pid);
      if (prod !== undefined) {
        inCart = prod.quantity;
      }
      if(this.product !== undefined) {
        this.quantity = + this.quantity;
        if(this.product?.quantity >= (this.quantity + + inCart)) {
          this.cartService.addToCart(cartId, {id: pid, quantity: this.quantity}).subscribe();
        } else {
          this.errorMessage = "Only " + this.product?.quantity + " item(s) are in stock.";
          window.alert(this.errorMessage);
        }
      }
    })
  }

  goBack(): void {
    this.location.back();
  }

  async deleteReview(): Promise<void> {
    if (this.user === null || this.user === undefined) {
      window.alert("You must be logged in to delete a review");
      return;
    }
    // Find the review in question
    let reviewId = -1;
    this.reviews.forEach(review => {
      if (review.customerId === this.user?.id && review.productId === this.product?.id) {
        reviewId = review.id;
      }
    });

    if (reviewId == -1) {
      window.alert("No review to delete");
      return;
    }

    await firstValueFrom(this.reviewService.deleteReview(reviewId));
    this.reviewService.getReviews().subscribe(reviews => this.reviews = reviews);
  }

  async addReview(): Promise<boolean> {
    var id: number | undefined = this.product?.id;

    if (id === undefined) {
      window.alert("A product must be selected");
      return false;
    }

    if (this.user === null || this.user === undefined) {
      window.alert("You must be logged in to add a review");
      return false;
    }

    let customer = await firstValueFrom(this.userService.getCustomer(this.user.id));
    // Now if purchasedIds are undefined then we will create a blank array and update the customer, then reset it
    if (customer.purchasedIds === undefined) {
      customer.purchasedIds = [];
      customer = await firstValueFrom(this.userService.updateCustomer({id: customer.id, username: customer.username,
        name: customer.name, cartId: customer.cartId, orders: customer.orders, isAdmin: customer.isAdmin,
        password: customer.password, purchasedIds: [] as number[]}));
    }

    // Check if a review already exists for this user
    let reviewId = 0;
    this.reviews.forEach(review => {
      if (review.customerId === this.user?.id && review.productId === id) {
        reviewId = review.id;
      }
    });

    if (this.tempReview.stars < 0.5 || this.tempReview.stars > 5) {
      window.alert("Must be between 0.5 and 5 stars");
      return false;
    }

    // Build the review
    let review = {id: reviewId, productId: id, customerId: this.user.id, stars: this.tempReview.stars, reviewContent: this.tempReview.reviewContent, ownerResponse: this.tempReview.ownerResponse} as Review;

    // Loop through the purchased IDs of the customer and see if we match
    let found = false;
    for (const purchasedId of customer.purchasedIds) {
      if (purchasedId === id) {
        if (!found) {
          // Either add a new review or update
          if (reviewId === 0) {
            await firstValueFrom(this.reviewService.addReview(review));
          }
          else {
            await firstValueFrom(this.reviewService.updateReview(review));
          }
          this.reviewService.getReviews().subscribe(reviews => this.reviews = reviews);
        }
        found = true;
      }
      
    }

    if (!found) {
      window.alert("You must have purchased this product to add a review");
    }

    return found;
    
  }

}
