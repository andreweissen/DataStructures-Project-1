/**
 * DivideByZero.java - User-defined checked exception
 * Begun 10/31/17
 * @author Andrew Eissen
 */

//package infixexpressions;

class DivideByZero extends Exception {

    // Default constructor
    public DivideByZero() {
        super();
    }

    /**
     * Parameterized constructor
     * @param message
     */
    public DivideByZero(String message) {
       super(message);
    }

    /**
     * Parameterized constructor
     * @param message
     * @param cause
     */
    public DivideByZero(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Parameterized constructor
     * @param cause
     */
    public DivideByZero(Throwable cause) {
        super(cause);
    }
}