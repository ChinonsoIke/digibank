package proj.entities;

import jakarta.persistence.*;
import proj.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private AccountType type;
    private double balance;

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Account(){}

    public Account(String number, AccountType type, LocalDate createdDate, Customer customer) {
        this.number = number;
        this.type = type;
        this.createdDate = createdDate;
        this.customer = customer;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    private LocalDate createdDate;

    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    private List<Transaction> transactions;
}
