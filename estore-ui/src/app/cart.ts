import { ProductReference } from "./product-reference";

export interface Cart {
    inventory: Record<number, ProductReference>;
  }