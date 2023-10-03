package com.example.interiorclient.exception;

import lombok.Getter;

@Getter
public class NotFoundBookingHistoryException extends  RuntimeException{
    private final String EXCEPTION_DESCRIPTION ="Отсутствует запись бронирования";
}
