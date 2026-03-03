package service;

import model.Contact;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactService {

    private List<Contact> contacts;

    public ContactService() {
        contacts = FileUtil.load();  // Load from file
    }

    // ==========================
    // ADD CONTACT
    // ==========================
    public void addContact(Contact contact) {
        contacts.add(contact);
        FileUtil.save(contacts);
    }

    // ==========================
    // DELETE CONTACT
    // ==========================
    public void deleteContact(int index) {
        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            FileUtil.save(contacts);
        }
    }

    // ==========================
    // UPDATE CONTACT
    // ==========================
    public void updateContact(int index, Contact updatedContact) {
        if (index >= 0 && index < contacts.size()) {
            contacts.set(index, updatedContact);
            FileUtil.save(contacts);
        }
    }

    // ==========================
    // GET ALL CONTACTS
    // ==========================
    public List<Contact> getAllContacts() {
        return contacts;
    }

    // ==========================
    // SEARCH CONTACT
    // ==========================
    public List<Contact> searchContact(String keyword) {
        return contacts.stream()
                .filter(c ->
                        c.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        c.getPhone().contains(keyword) ||
                        c.getEmail().toLowerCase().contains(keyword.toLowerCase())
                )
                .collect(Collectors.toList());
    }
}