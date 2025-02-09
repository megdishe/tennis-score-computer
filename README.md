# 🎾 Tennis Score Computer

## 📌 Overview
**Tennis Score Computer** is a **Java-based** console application that implements **tennis game scoring** based on official rules.  
It supports **standard game progression**, including **Deuce, Advantage, and Game Winning Conditions**.

### **This project was developed following:**  
✅ **BDD (Behavior-Driven Development)** using **Cucumber**  
✅ **TDD (Test-Driven Development)** with **JUnit & AssertJ**  
✅ **Hexagonal Architecture** for **clean separation of concerns**  

---

## 🚀 Features  
✔ **Standard Tennis Scoring computing**  
✔ **Handles Deuce & Multiple Advantage Transitions**  
✔ **Supports replaying multiple game rounds**  

---

## 📥 Installation, Setup & Testing  

### **🔧 Prerequisites**  
- **Java 21**  
- **Maven 3+**  

### **🚀 Running the Application & Tests**  
```sh
mvn clean install
mvn exec:java -Dexec.mainClass="com.tennis.TennisScoreComputerApp"
mvn test                     # Run Unit & BDD Tests (Cucumber) & arch Tests
