package by.kolesnik.aston_2_homework4.constant;

public class ValidationConstant {

    public static final String NAME_REGEX = "[a-zA-Zа-яА-ЯёЁ\\s-]+";
    public static final String NAME_FORMAT_MESSAGE = "Only letters, spaces, and hyphens";
    public static final String NAME_IS_BLANK_MESSAGE = "Name is required";

    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String EMAIL_FORMAT_MESSAGE = "Invalid email format";
    public static final String EMAIL_IS_BLANK_MESSAGE = "Email is required";

    public static final String AGE_IS_BLANK_MESSAGE = "Age is required";
    public static final String AGE_IS_NEGATIVE_MESSAGE = "Age cannot be negative";
    public static final String AGE_IS_EXCESSIVE_MESSAGE = "Age must be less than 150";
}
