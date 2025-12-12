![Banking System Banner](https://i.imgur.com/8J0xK9m.png)


# 🏦✨ JAVA BANKING MANAGEMENT SYSTEM

# 🚀 Overview
A modern, fully functional **Banking System** built using **Java OOP + Swing UI + File Serialization**.

Supports **User & Admin roles**, secure transactions, file-based persistent storage, and a clean, intuitive GUI.

---

# 🔥 Features

## 🧑‍💼 User Features
- 💵 Deposit money  
- 💸 Withdraw money  
- 🔁 Transfer money (with balance validation)  
- 📊 Check account balance  
- 📜 View transaction history  
- 💾 Auto-save using serialization  

---

## 🛡️ Admin Features
- 👁️ View all users  
- 📂 View all transactions  
- ❌ Delete any user  
- 🔍 Inspect logs  

---



# 🧱 Core Classes

## 👤 Person
- Base class for all identities  
- Stores name, email, password, and ID  

## 👤 User
- Extends Person  
- Owns an Account  
- Handles deposits, withdrawals, transfers, and transaction records  

## 🗝️ Admin
- Extends Person  
- Can view/delete users and view all transactions  

## 💳 Account
- Stores balance  
- Handles add/remove funds  

## 📄 Transaction
- Stores transaction type, amount, time, and summary  

---

# 🖥️ User Interface
- Built using **Java Swing**  
- Clean, easy-to-navigate dashboard  
- Admin and User views separated  
- Input validation and error handling  

---

# 💾 Data Storage
- Fully implemented using **Java Serialization**  
- Automatically saves:
  - User accounts  
  - Balances  
  - Transaction history  
  - Deleted/updated data  

---

# 📦 Project Structure
/bankingSystem
│
├── Person.java
├── User.java
├── Admin.java
├── Account.java
├── Transaction.java
│
├── gui/
│ ├── LoginUI.java
│ ├── UserDashboard.java
│ ├── AdminDashboard.java
│ ├── TransferUI.java
│ └── ...
│
├── data/
│ └── users.ser
│
└── Main.java


---

# 🏁 How to Run
1. Clone the repo  
2. Open in any Java IDE  
3. Run `Main.java`  
4. App launches with Login UI  

---

# 🏗️ Tech Stack
- ☕ Java  
- 🎨 Swing  
- 💾 Serialization  
- 🧱 OOP  

---

# 🙌 Contributors
- **Nafis Kamal** (Developer)

---

# ⭐ If you like this project
Give it a **star** on GitHub! ⭐  
