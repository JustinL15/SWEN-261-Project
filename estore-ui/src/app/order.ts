export interface Product {
    id: number;
    name: string;
    price: number;
    quantity: number;
    description: string;
}

export interface Order {
    id: number;
    totalPrice: number;
    products: Array<Product>;
    dateTime: Date;
}