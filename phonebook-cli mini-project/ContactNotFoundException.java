// CUSTOM EXCEPTION
// Definition: A custom exception is a user-defined class that extends Exception (or one of its
//   subclasses), allowing you to create domain-specific error types with meaningful names.
// Why: Instead of throwing a generic Exception("not found"), you throw ContactNotFoundException
//   so the caller knows exactly what went wrong — and can handle it specifically.
//
// Two categories of exceptions in Java:
//   Checked   → extends Exception — the compiler FORCES the caller to handle or declare it.
//               e.g. ContactNotFoundException extends Exception → search() must declare `throws ContactNotFoundException`
//   Unchecked → extends RuntimeException — no compiler enforcement; used for programming errors.
//               e.g. NullPointerException, ArrayIndexOutOfBoundsException
//
// This class is CHECKED — so any method that calls search() or delete() must either:
//   a) catch it with try/catch, OR
//   b) declare throws ContactNotFoundException in its own signature (passing responsibility up)
public class ContactNotFoundException extends Exception {

    // Calls the parent Exception constructor with a message.
    // Definition: super(message) passes the error description up to Exception,
    //   which stores it so getMessage() can return it later.
    // e.g. throw new ContactNotFoundException("Alice") → getMessage() returns "Contact not found: Alice"
    public ContactNotFoundException(String name) {
        super("Contact not found: " + name);
    }
}
