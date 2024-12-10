package com.batsworks.matrix.utils;

import com.batsworks.matrix.frame.MatrixCalculator;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log<T> {

    private Class<T> logClass = (Class<T>) MatrixCalculator.class;
    private final Logger log = Logger.getLogger(logClass.getName());

    public Log(Class<T> logClass) {
        this.logClass = logClass;
    }

    public void error(String message) {
        log.log(Level.SEVERE, message);
    }

    public void error(Throwable e) {
        log.log(Level.SEVERE, e.getMessage(), e);
    }

    public void error(String message, Object...params) {
        log.log(Level.SEVERE, message, params);
    }

    public void info(String message) {
        log.log(Level.INFO, message);
    }

    public void info(Throwable e) {
        log.log(Level.INFO, e.getMessage(), e);
    }

    public void info(String message, Object...params) {
        log.log(Level.INFO, message, params);
    }
}
