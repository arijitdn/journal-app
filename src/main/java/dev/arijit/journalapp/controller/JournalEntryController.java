package dev.arijit.journalapp.controller;

import dev.arijit.journalapp.entity.JournalEntry;
import dev.arijit.journalapp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(journalEntry);
        return journalEntry;
    }

    @GetMapping("/id/{id}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId id) {
        return journalEntryService.findById(id).orElse(null);
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        JournalEntry foundEntry = journalEntryService.findById(id).orElse(null);

        if(foundEntry != null) {
            foundEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : foundEntry.getTitle());
            foundEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : foundEntry.getContent());
        }

        journalEntryService.saveEntry(foundEntry);
        return newEntry;
    }

    @DeleteMapping("/id/{id}")
    public boolean deleteJournalById(@PathVariable ObjectId id) {
        journalEntryService.deleteById(id);
        return true;
    }
}
