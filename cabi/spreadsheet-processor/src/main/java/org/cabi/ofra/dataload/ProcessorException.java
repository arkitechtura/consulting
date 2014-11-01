package org.cabi.ofra.dataload;

/**
 * (c) 2014, Eduardo Quirós-Campos
 */
public class ProcessorException extends Exception {
  public ProcessorException(String message) {
    super(message);
  }

  public ProcessorException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProcessorException(Throwable cause) {
    super(cause);
  }
}
