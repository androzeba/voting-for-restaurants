package ru.internship.voting.model;

import java.time.LocalDate;

public class Dish extends AbstractNamedEntity {

    private LocalDate date;

    private Integer price;

    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(String name, LocalDate date, Integer price) {
        this(null, name, date, price);
    }

    public Dish(Integer id, String name, LocalDate date, Integer price) {
        super(id, name);
        this.date = date;
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}