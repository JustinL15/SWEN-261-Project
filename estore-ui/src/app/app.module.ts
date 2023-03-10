import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductsComponent } from './products/products.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { InventoryComponent } from './inventory/inventory.component';
import { EditProductDetailComponent } from './edit-product-detail/edit-product-detail.component';
import { OrderViewComponent } from './order-view/order-view.component';
import { UserLoginComponent } from './userlogin/userlogin.component';


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    ProductsComponent,
    ProductDetailComponent,
    InventoryComponent,
    EditProductDetailComponent,
    OrderViewComponent,
    UserLoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
