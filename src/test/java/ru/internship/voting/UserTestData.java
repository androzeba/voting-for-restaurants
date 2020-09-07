package ru.internship.voting;

import ru.internship.voting.model.Role;
import ru.internship.voting.model.User;

import java.util.EnumSet;

import static ru.internship.voting.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {

    public static final int USER1_ID = START_SEQ;
    public static final int USER2_ID = START_SEQ + 1;
    public static final int ADMIN_ID = START_SEQ + 2;

    public static final User USER1 = new User(USER1_ID, "User1", "user1@yandex.ru", "password1", EnumSet.of(Role.USER));
    public static final User USER2 = new User(USER2_ID, "User2", "user2@yandex.ru", "password2", EnumSet.of(Role.USER));
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", EnumSet.of(Role.ADMIN));
}