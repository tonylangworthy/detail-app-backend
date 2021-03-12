package com.webbdealer.detailing.shared;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ApiErrorResponse {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private LocalDateTime timestamp;

    private HttpStatus status;

    private int statusCode;

    private String message;

    private List<String> errors;

    public ApiErrorResponse() {}

    public ApiErrorResponse(HttpStatus status, int statusCode, String message, List<String> errors) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorResponse(HttpStatus status, int statusCode, String message, String error) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.errors = Arrays.asList(error);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((errors == null) ? 0 : errors.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + statusCode;
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ApiErrorResponse other = (ApiErrorResponse) obj;
        if (errors == null) {
            if (other.errors != null)
                return false;
        } else if (!errors.equals(other.errors))
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (status != other.status)
            return false;
        if (statusCode != other.statusCode)
            return false;
        if (timestamp == null) {
            if (other.timestamp != null)
                return false;
        } else if (!timestamp.equals(other.timestamp))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ApiErrorResponse [timestamp=" + timestamp + ", status=" + status + ", statusCode=" + statusCode
                + ", message=" + message + ", errors=" + errors + "]";
    }


}
