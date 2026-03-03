package service;

import java.util.*;
import model.Contact;
import util.FileUtil;

public class ContactService {
    private List<Contact> contacts;

    public ContactService() {
        contacts = FileUtil.load();
    }

    public void addContact(Contact c) {
        if (c != null) {
            contacts.add(c);
            Collections.sort(contacts);
            FileUtil.save(contacts);
        }
    }

    public void updateContact(int index, Contact c) {
        if (index >= 0 && index < contacts.size() && c != null) {
            contacts.set(index, c);
            Collections.sort(contacts);
            FileUtil.save(contacts);
        }
    }

    public void deleteContact(int index) {
        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            FileUtil.save(contacts);
        }
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public List<Contact> searchContacts(String query) {
        List<Contact> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase().trim();
        
        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(lowerQuery) ||
                contact.getPhone().contains(query) ||
                contact.getEmail().toLowerCase().contains(lowerQuery) ||
                contact.getCompany().toLowerCase().contains(lowerQuery)) {
                results.add(contact);
            }
        }
        return results;
    }

    public int getContactIndex(Contact contact) {
        return contacts.indexOf(contact);
    }
}
