Ecommerce system implementation
This project implements a basic object-oriented e-commerce system that supports product management, customer accounts, and a simple shopping experience.

System Features
	•	Products
	•	Base Product: Has ID, name, price, and stock.
	•	Shippable Product: Includes a shipping address.
	•	Expirable Product: Includes an expiry date.
	•	Shippable & Expirable Product: Combines both features.
	•	Customers
	•	Can be created with a name and account balance.
	•	Can add products to their cart and checkout.
	•	Shopping Cart
	•	Allows adding/removing products by ID.
	•	Calculates total cost.
	•	Validates stock, expiry, and balance during checkout.

Exceptions

Custom exceptions are thrown in specific cases:
	•	InsufficientBalanceException
	•	ProductOutOfStockException
	•	ProductExpiredException
	•	EmptyCartException

Testing

Includes detailed test cases for:
	•	All product types
	•	Poor and rich customers
	•	Buying expired or out-of-stock products
	•	Empty cart scenarios
Test Cases:
![Screenshot (473)](https://github.com/user-attachments/assets/b27ee0cc-ed8e-417e-8218-a4f2ac9fa091)
![Screenshot (474)](https://github.com/user-attachments/assets/417a6d67-200e-4128-958a-1b6f3d24d73c)
![Screenshot (475)](https://github.com/user-attachments/assets/5dd45e00-1a52-4165-8ee8-27f0887e78d8)
