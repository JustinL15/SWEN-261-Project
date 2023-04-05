import { Order } from "./order";
import { Product } from "./product";

export interface Customer {
    id: number;
    username: string;
    name: string;
    cartId: number;
    orders: Array<number>;
    starred: Array<Product>;
    isAdmin: boolean;
    password: string;
}