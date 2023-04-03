import { Customer } from './customer';
import { Product } from './product';

export interface Review {
    id: number;
    productId: number;
    customerUser: Customer;
    stars: number;
    reviewContent: string;
    dateTime: Date | null;
    username: String;
    purchased: Array<number>;
}