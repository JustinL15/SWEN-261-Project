import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ProductsComponent } from './products/products.component';
import { InventoryComponent } from './inventory/inventory.component';
import { EditProductDetailComponent } from './edit-product-detail/edit-product-detail.component';
import { OrderViewComponent } from './order-view/order-view.component';

const routes: Routes = [
  { path: 'home', component: DashboardComponent},
  { path: 'products', component: ProductsComponent},
  { path: 'detail/:id', component: ProductDetailComponent},
  { path: 'manage', component: InventoryComponent},
  { path: 'manage/:id', component: EditProductDetailComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'orders', component: OrderViewComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
