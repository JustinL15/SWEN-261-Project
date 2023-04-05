import { Order } from "./order";

export interface Customer {
    id: number;
    username: string;
    name: string;
    cartId: number;
    orders: Array<Order>;
    isAdmin: boolean;
    password: string;
    purchasedIds: Array<number>;
}