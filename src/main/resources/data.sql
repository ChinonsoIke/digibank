-- Insert a customer
INSERT INTO customer (id, first_name, last_name, email, password) VALUES (1, 'John', 'Doe', 'john.doe@example.com', 'password');

-- Insert an account for the customer
INSERT INTO account (id, number, type, balance, created_date, customer_id) VALUES (1, '1234567890', 0, 1000.00, '2024-01-01', 1);

-- Insert transactions for the account
INSERT INTO transaction (id, type, description, created_at, account_id) VALUES
  (1, 0, 'Initial Deposit', '2024-01-02T10:00:00', 1),
  (2, 1, 'ATM Withdrawal', '2024-01-03T12:00:00', 1),
  (3, 0, 'Salary Credit', '2024-01-10T09:00:00', 1); 