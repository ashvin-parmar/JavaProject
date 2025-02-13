package com.invoiceapp.service;

import com.invoiceapp.model.Customer;
import java.util.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CustomerService {
    private static final String DATA_FILE = "customers.json";
    private List<Customer> customers;
    private final Gson gson;

    public CustomerService() {
        this.gson = new Gson();
        this.customers = loadCustomers();
    }

    public void saveCustomer(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(UUID.randomUUID().toString());
        }
        
        Optional<Customer> existing = customers.stream()
            .filter(c -> c.getId().equals(customer.getId()))
            .findFirst();
            
        if (existing.isPresent()) {
            customers.remove(existing.get());
        }
        
        customers.add(customer);
        saveToFile();
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    public Optional<Customer> getCustomerById(String id) {
        return customers.stream()
            .filter(c -> c.getId().equals(id))
            .findFirst();
    }

    private List<Customer> loadCustomers() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, new TypeToken<List<Customer>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(DATA_FILE)) {
            gson.toJson(customers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

