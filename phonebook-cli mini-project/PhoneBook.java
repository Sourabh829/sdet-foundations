import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// COLLECTIONS PREVIEW
// Definition: The Java Collections Framework is a set of classes and interfaces for storing
//   and manipulating groups of objects. Each type has a different structure and use case.
//
//   HashMap<K, V>  → stores key-value pairs; keys are unique; O(1) average lookup by key.
//                    e.g. contacts maps "Alice" → "9876543210"; searching "Alice" is instant.
//   ArrayList<E>   → ordered, resizable list; allows duplicates; index-based access.
//                    e.g. listAllContacts() returns an ArrayList<String> of all "Name: Phone" lines.
//   HashSet<E>     → unordered collection; NO duplicates allowed; O(1) average lookup.
//                    e.g. getUniqueAreaCodes() returns a HashSet<String> so "9876" appears only once
//                    even if ten contacts share that prefix.
//
// All three are generic (use <>) — the type in <> tells Java what objects the collection holds.
public class PhoneBook {

    // HASHMAP — the core data structure of this app.
    // Definition: HashMap<K, V> stores entries as key→value pairs in a hash table.
    //   Key must be unique — adding the same key twice overwrites the old value.
    //   K = String (contact name), V = String (phone number).
    // e.g. contacts.put("Alice", "9876543210") stores one entry;
    //      contacts.get("Alice") returns "9876543210" in O(1) time.
    private HashMap<String, String> contacts;

    public PhoneBook() {
        // HashMap starts empty; grows automatically as entries are added.
        contacts = new HashMap<>();
    }

    // ADD — puts a new key-value pair into the HashMap.
    // Definition: put(key, value) inserts or REPLACES the entry for that key.
    //   If "Alice" already exists, the old number is silently overwritten.
    // e.g. add("Alice", "9876543210") → contacts now has {"Alice": "9876543210"}
    public void add(String name, String phone) {
        contacts.put(name, phone);
        System.out.println("Saved: " + name + " → " + phone);
    }

    // SEARCH — retrieves a value by key and throws a custom exception if absent.
    // `throws ContactNotFoundException` — CHECKED exception declaration.
    // Definition: `throws` in the method signature tells the compiler (and the caller)
    //   that this method might throw that exception. The caller MUST handle it.
    //   Without this declaration, Java would refuse to compile the throw statement below.
    // e.g. search("Bob") when Bob is absent → throws ContactNotFoundException("Bob")
    //      → getMessage() returns "Contact not found: Bob"
    public String search(String name) throws ContactNotFoundException {
        // containsKey() checks if the HashMap has an entry for this key.
        // e.g. contacts.containsKey("Alice") returns true if Alice was added.
        if (!contacts.containsKey(name)) {
            // CUSTOM EXCEPTION in use: we throw our specific type, not a generic Exception.
            // The caller in Main.java catches ContactNotFoundException by name.
            throw new ContactNotFoundException(name);
        }
        return contacts.get(name);  // get(key) returns the mapped value
    }

    // DELETE — removes an entry by key, throws if not found.
    // Same `throws` pattern as search() — both can fail for the same reason.
    // e.g. delete("Alice") → contacts.remove("Alice") wipes the entry;
    //      delete("Ghost") → throws ContactNotFoundException("Ghost")
    public void delete(String name) throws ContactNotFoundException {
        if (!contacts.containsKey(name)) {
            throw new ContactNotFoundException(name);
        }
        contacts.remove(name);  // remove(key) deletes the key-value pair entirely
        System.out.println("Deleted: " + name);
    }

    // ARRAYLIST — ordered list of all contacts as formatted strings.
    // Definition: ArrayList<E> is a resizable array. Unlike a plain array, it grows
    //   automatically. Elements maintain insertion order and can be accessed by index.
    //   Allows duplicates (unlike HashSet).
    // e.g. listAllContacts() returns ["Alice: 9876543210", "Bob: 1234567890"]
    //      in whatever order HashMap stored them (HashMap is unordered).
    public ArrayList<String> listAllContacts() {
        ArrayList<String> list = new ArrayList<>();
        // entrySet() gives all key-value pairs as a Set of Map.Entry objects.
        for (HashMap.Entry<String, String> entry : contacts.entrySet()) {
            list.add(entry.getKey() + ": " + entry.getValue());
        }
        return list;
    }

    // HASHSET — unique area codes (first 4 digits) extracted from all phone numbers.
    // Definition: HashSet<E> stores only unique elements. Adding a duplicate is silently ignored.
    //   Has no guaranteed order. Useful when you only care about distinct values.
    // e.g. if Alice="9876-...", Bob="9876-...", Carol="1234-..." →
    //      HashSet contains {"9876", "1234"} — the duplicate "9876" appears only once.
    public HashSet<String> getUniqueAreaCodes() {
        HashSet<String> areaCodes = new HashSet<>();
        for (String phone : contacts.values()) {  // values() returns all phone numbers
            if (phone.length() >= 4) {
                areaCodes.add(phone.substring(0, 4));  // duplicate area codes are silently dropped
            }
        }
        return areaCodes;
    }

    public int size() {
        return contacts.size();  // size() returns the number of key-value pairs currently stored
    }
}
