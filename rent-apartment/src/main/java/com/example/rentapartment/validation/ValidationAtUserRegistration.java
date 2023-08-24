package com.example.rentapartment.validation;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Data
public class ValidationAtUserRegistration {

   private String value;
   private String name;
   private  ArrayList<String> list = new ArrayList<>();

    /**
     * Метод проверки на 0
     */
    public String checkingForZero(String name, String value) {
        this.value = value;
        this.name = name;
        if (value == null) {
            list.add("Поле " + name + " не может быть пустым!");
        }
        return value;
    }

    /**
     * Метод проверки на допустимые символы
     */
    public void checkNameClient(String name, String value) {
        if (value.matches(".*[^А-Яа-яЁё].*") == true) {
            list.add("Поле " + name + " содержит недопустимые символы!");
        }
    }

    /**
     * Метод проверки на допустимые символы в роле numberPhone
     */
    public void checkNumberPhone(String value) {
        if (value.matches("^\\d{10}$") == false) {
            list.add(" Поле numberPhone заполнено некорректно, " +
                    "в нем должно быть 10 цифр, без 8(+7)");
        }

    }

    /**
     * Метод проверки на допустимое количество символов  в поле numberPassport
     */
    public void checkNumberPassport(String value) {
        if (value.matches("(\\d{4}\\s\\d{6})") == false) {
            list.add(" Поле numberPassport заполнено некорректно, " +
                    "в нем должно быть 10 цифр, после 4й цифры -пробел");
        }

    }
    /**
     * Метод проверки на допустимые символы в поле email
     */
    public void checkEmail(String value) {
        if (value.matches("^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(\\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@([a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*(aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])$") == false) {
            list.add("Поле email заполнено некорректно");
        }
    }
    /**
     * Метод проверки на допустимые символы в поле password
     */
    public void checkPassword(String value){
        if(value.matches("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")==false){
            list.add("Поле password заполнено некорректно, " +"поле должно содержать не менее 8 символов, содержать только латинские буквы, цифры и спецсимволы");
        }
    }


}
