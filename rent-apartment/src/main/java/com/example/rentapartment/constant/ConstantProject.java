package com.example.rentapartment.constant;

public class ConstantProject {

    /**
     * PathConstant
     */
    public final static String SEARCH_CITY_FROM_TREE = "/results/0/components/city";
    public static final String BASE_URL = "http://localhost:9098/product-send?id=%s";

    /**
     * FieldConstant
     */
    public final static String FIRST_NAME = "firstName";
    public final static String LAST_NAME = "lastName";
    public final static String NUMBER_PASSPORT = "numberPassport";
    public final static String NUMBER_PHONE = "numberPhone";
    public final static String PARENT_CITY = "parentCity";
    public final static String EMAIL = "email";
    public final static String PASSWORD = "password";
    public final static String STREET = "street";
    public final static String CITY = "city";
    public final static String NUMBER_APARTMENT = "numberApartment";
    public final static String NUMBER_HOUSE = "numberHouse";
    public final static String NUMBER_OF_ROOMS = "numberOfRooms";
    public final static String PRICE = "price";


    /**
     * ControllerConstant
     */
    public final static String API = "/api";
    public final static String SAVE_TO_BASE = "/savetobase";
    public final static String REGISTRATION_APARTMENT = "/registration_apartment";
    public final static String GET_INFO_BY_STREET = "/getinfobystreet/{street}";
    public final static String GET_INFO_BY_CITY = "/getinfobycity";
    public final static String CHOICE_APARTMENT = "/choice-apartment";
    public final static String GET_APARTMENT_BY_PRICE_AND_NUMBER_OF_ROOMS = "/price/numberOfRooms";
    public final static String USER_REGISTRATION = "/user-registration";
    public final static String USER_AUTHORIZATION = "/user-authorization";

    /**
     * ExceptionConstant
     */

    public final static String EXCEPTION_AUTH = "Авторизируйтесь!";
    public final static String EXCEPTION_NOT_FOUND_USER = "Пользователь не найден! Зарегистрируйтесь.";
    public final static String EXCEPTION_BAD_PASSWORD = "Неверный пароль!";
    public final static String USER_EXISTS = "Пользователь с таким именем уже существует!";
    public final static String APART_EXISTS ="Квартира с таким адресом уже существует!";


    /**
     * MessageConstant
     */

    public final static String GOOD_AUTH = "Вы успешно авторизированы!";
    public final static String GOOD_REG_USER = "Пользователь успешно зарегистрирован!Вы можете войти в систему.";
    public final static String GOOD_REG_APART="Квартира успешно зарегистрирована!";

}
