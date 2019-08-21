package com.falcon.events.web.rest.errors;

public class AttendanceAlreadyAddedException extends BadRequestAlertException {
    public AttendanceAlreadyAddedException(String defaultMessage, String entityName, String errorKey) {
        super(defaultMessage, entityName, errorKey);
    }
}
