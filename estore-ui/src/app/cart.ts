import { ProductReference } from "./product-reference";

export interface Cart {
    id: number;
    inventory: Record<number, ProductReference>;
  }