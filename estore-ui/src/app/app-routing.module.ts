import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ProductsComponent } from './products/products.component';
import { OrderViewComponent } from './order-view/order-view.component';

const routes: Routes = [
  { path: 'home', component: DashboardComponent},
  { path: 'products', component: ProductsComponent},
  { path: 'detail/:id', component: ProductDetailComponent},
  { path: 'orders', component: OrderViewComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
