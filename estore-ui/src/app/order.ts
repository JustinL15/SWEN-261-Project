import { Product } from "./product";

export interface Order {
    id: number;
    totalPrice: number;
    products: Array<Product>;
    dateTime: Date;
}