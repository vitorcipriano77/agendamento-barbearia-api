package com.cipriano.agendamento.exception;

public class ConflitoHorarioException extends RuntimeException {
    public ConflitoHorarioException(String message) {
        super(message);
    }
}