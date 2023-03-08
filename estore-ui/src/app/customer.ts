import { Order } from "./order";

export interface Customer {
    id: number;
    username: string;
    name: string;
    cartId: number;
    orders: Array<Order>;
    isAdmin: boolean;
    passwordHash: string;
    password: string;
}