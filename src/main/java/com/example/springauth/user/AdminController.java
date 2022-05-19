package com.example.springauth.user;

import com.example.springauth.event.Event;
import com.example.springauth.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AdminController {
    @Autowired
    public EventRepository eventRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String eventList(Model model) {
        model.addAttribute("allEvents", eventRepository.findAll());
        return "adminEvents";
    }


    @GetMapping("/admin/edit/{eventID}")
    public String eventEditForm(@PathVariable Long eventID, Model model) {
        Event event = eventRepository.getById(eventID);
        model.addAttribute("event", event);
        return "eventEdit";
    }

    @PostMapping("/admin/edit/{eventID}")
    public String eventEdit(@ModelAttribute Event eventFromForm, @PathVariable Long eventID) {
        Event event = eventRepository.getById(eventID);
        event.setStatus(eventFromForm.getStatus());
        eventRepository.save(event);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{eventID}")
    public String eventDelete(@PathVariable Long eventID) {
        eventRepository.deleteById(eventID);
        return "redirect:/admin";
    }
}