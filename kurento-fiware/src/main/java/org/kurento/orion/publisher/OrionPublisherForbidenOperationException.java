package org.kurento.orion.publisher;

import org.kurento.orion.connector.OrionConnectorException;

public class OrionPublisherForbidenOperationException extends OrionConnectorException {

  private static final long serialVersionUID = 8650895016643175086L;

  /**
   * default constructor.
   */
  public OrionPublisherForbidenOperationException() {
	// Default constructor
  }

  /**
   * Constructs a new runtime exception with the specified detail message. The
   * cause is not initialised, and may subsequently be initialised by a call to
   * initCause.
   *
   * @param msg
   *          the detail message. The detail message is saved for later retrieval
   *          by the {@link #getMessage()} method.
   */
  public OrionPublisherForbidenOperationException(final String msg) {
	super(msg);
  }

  /**
   *
   * @param msg
   *          the detail message. The detail message is saved for later retrieval
   *          by the {@link #getMessage()} method.
   * @param throwable
   *          the cause (which is saved for later retrieval by the
   *          {@link #getCause()} method). (A null value is permitted, and
   *          indicates that the cause is nonexistent or unknown.)
   */
  public OrionPublisherForbidenOperationException(final String msg, final Throwable throwable) {
	super(msg, throwable);
  }

  /**
   *
   * @param cause
   *          the cause (which is saved for later retrieval by the
   *          {@link #getCause()} method). (A null value is permitted, and
   *          indicates that the cause is nonexistent or unknown.)
   */
  public OrionPublisherForbidenOperationException(final Throwable cause) {
	super(cause);

  }
}
