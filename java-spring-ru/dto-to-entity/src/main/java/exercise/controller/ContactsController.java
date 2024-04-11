package exercise.controller;

import exercise.dto.ContactCreateDTO;
import exercise.dto.ContactDTO;
import exercise.model.Contact;
import exercise.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactsController {

    private final ContactRepository contactRepository;

    // BEGIN
    @PostMapping
    public ResponseEntity<ContactDTO> create(@RequestBody ContactCreateDTO contactCreateDTO) {
        var contact = contactRepository.save(toContact(contactCreateDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(toContactDTO(contact));
    }
    // END

    private Contact toContact(ContactCreateDTO contactCreateDTO) {
        Contact contact = new Contact();

        contact.setPhone(contactCreateDTO.getPhone());
        contact.setFirstName(contactCreateDTO.getFirstName());
        contact.setLastName(contactCreateDTO.getLastName());

        return contact;
    }

    private ContactDTO toContactDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();

        contactDTO.setId(contact.getId());
        contactDTO.setPhone(contact.getPhone());
        contactDTO.setFirstName(contact.getFirstName());
        contactDTO.setLastName(contact.getLastName());
        contactDTO.setCreatedAt(contact.getCreatedAt());
        contactDTO.setUpdatedAt(contact.getUpdatedAt());

        return contactDTO;
    }
}
