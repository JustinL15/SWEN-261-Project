import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ProductsComponent } from './products/products.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { InventoryComponent } from './inventory/inventory.component';
import { EditProductDetailComponent } from './edit-product-detail/edit-product-detail.component';
import { OrderViewComponent } from './order-view/order-view.component';
import { UserLoginComponent } from './userlogin/userlogin.component';
import { ReviewManageComponent } from './review-manage/review-manage.component';
import { AuthGuard } from './auth.guard';
import { LoginGuard } from './login.guard';
import { OrderDetailComponent } from './order-detail/order-detail.component';

const routes: Routes = [
  { path: 'home', component: DashboardComponent},
  { path: 'products', component: ProductsComponent},
  { path: 'order/:id', component: OrderDetailComponent, canActivate: [LoginGuard]},
  { path: 'cart', component: ShoppingCartComponent, canActivate: [LoginGuard]},
  { path: 'detail/:id', component: ProductDetailComponent},
  { path: 'manage', component: InventoryComponent, canActivate: [AuthGuard]},
  { path: 'manage/:id', component: EditProductDetailComponent, canActivate: [AuthGuard]},
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'orders', component: OrderViewComponent},
  { path: 'login', component: UserLoginComponent, canActivate: [LoginGuard]},
  { path: 'reviewManage', component: ReviewManageComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
