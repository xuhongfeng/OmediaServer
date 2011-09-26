package org.tsinghua.omedia.exception;

import java.io.IOException;

public class DbException extends IOException {

    private static final long serialVersionUID = -3371341427836211946L;

    public DbException() {
        super();
    }

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbException(String message) {
        super(message);
    }

    public DbException(Throwable cause) {
        super(cause);
    }

    
}
