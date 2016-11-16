package ru.aviaj.database.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

public class DbException extends Exception {
    public DbException(String message, Exception cause) {
        super(message, cause);
    }

    public String getStackTraceString() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        this.printStackTrace(printWriter);

        return stringWriter.toString();
    }
}
