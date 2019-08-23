package com.falcon.events.web.rest.errors;

public class EventLocationExistException extends BadRequestAlertException {
    public EventLocationExistException(String defaultMessage, String entityName, String errorKey) {
        super(defaultMessage, entityName, errorKey);
    }
}
