package com.falcon.events.web.rest.errors;

public class EventAlreadyExistException extends BadRequestAlertException {
    public EventAlreadyExistException(String defaultMessage, String entityName, String errorKey) {
        super(defaultMessage, entityName, errorKey);
    }
}
