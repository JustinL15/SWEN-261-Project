import { Product } from './product';

export interface Order {
    id: number;
    totalPrice: number;
    products: Product[];
    complete: boolean;
    time: number[];
  }
