package ru.internship.voting.model;

import java.time.LocalDateTime;

public class Vote extends AbstractBaseEntity {

    private LocalDateTime dateTime;

    private User user;

    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(LocalDateTime dateTime) {
        this(null, dateTime);
    }

    public Vote(Integer id, LocalDateTime dateTime) {
        super(id);
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                '}';
    }
}