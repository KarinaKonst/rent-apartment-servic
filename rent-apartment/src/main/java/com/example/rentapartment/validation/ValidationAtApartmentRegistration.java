package com.example.rentapartment.validation;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Data
public class ValidationAtApartmentRegistration {

    private String value;
    private String name;
    private ArrayList<String> list = new ArrayList<>();

    /**
     * Метод проверки на 0
     */
    public String checkingForZero(String name, String value) {
        this.value = value;
        this.name = name;
        if (value==null) {
            list.add("Поле " + name + " не может быть пустым!" );
        }
        return value;
    }

    /**
     * Метод проверки на допустимые символы
     */
    public void checkStringFields(String name, String value) {
        if (value.matches(".*[^А-Яа-яЁё].*") == true) {
            list.add("Поле " + name + " содержит недопустимые символы!");
        }
    }
    /**
     * Метод проверки на допустимые значения
     */

    public void checkingForIntegers(String name,String value){
        if(value.matches(".*\\d.*")==false){
            list.add("Поле " + name + " должно содержать только числа!" );

        }
    }
}
