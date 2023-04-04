import { Order } from "./order";

export interface Customer {
    id: number;
    username: string;
    name: string;
    cartId: number;
    orders: Array<number>;
    isAdmin: boolean;
    password: string;
}