package com.example.springauth.event;

import com.example.springauth.user.UserService;
import com.example.springauth.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
public class EventController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserService userService;

    @GetMapping("/order")
    public String eventForm(Model model) {
        model.addAttribute("event", new Event());
        return "order";
    }

    @PostMapping("/order")
    public String eventSubmit(@ModelAttribute Event event) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails user = userService.loadUserByUsername(authentication.getName());
            event.setCustomer((User) user);
            eventRepository.save(event);
        }
        return "redirect:/events";
    }

    @GetMapping("/events")
    public String eventOverview(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) userService.loadUserByUsername(authentication.getName());
            model.addAttribute("events", user.getEvents());
            return "events";
        }
        return null;
    }

}
