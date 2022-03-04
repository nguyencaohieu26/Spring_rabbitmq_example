package com.example.inventory.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationInventoryException {
    private String defaultMessage;
    private String objectName;
    private String field;
    private Object rejectedValue;

    public static final class ValidationInventoryExceptionBuilder {
        private String defaultMessage;
        private String objectName;
        private String field;
        private Object rejectedValue;

        private ValidationInventoryExceptionBuilder() {
        }

        public static ValidationInventoryExceptionBuilder aValidationInventoryException() {
            return new ValidationInventoryExceptionBuilder();
        }

        public ValidationInventoryExceptionBuilder withDefaultMessage(String defaultMessage) {
            this.defaultMessage = defaultMessage;
            return this;
        }

        public ValidationInventoryExceptionBuilder withObjectName(String objectName) {
            this.objectName = objectName;
            return this;
        }

        public ValidationInventoryExceptionBuilder withField(String field) {
            this.field = field;
            return this;
        }

        public ValidationInventoryExceptionBuilder withRejectedValue(Object rejectedValue) {
            this.rejectedValue = rejectedValue;
            return this;
        }

        public ValidationInventoryException build() {
            ValidationInventoryException validationInventoryException = new ValidationInventoryException();
            validationInventoryException.setDefaultMessage(defaultMessage);
            validationInventoryException.setObjectName(objectName);
            validationInventoryException.setField(field);
            validationInventoryException.setRejectedValue(rejectedValue);
            return validationInventoryException;
        }
    }
}
