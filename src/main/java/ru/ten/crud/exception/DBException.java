package ru.ten.crud.exception;

import java.sql.SQLException;

public class DBException extends SQLException {

    public DBException(String throwable) {
        super(throwable);
    }
}
