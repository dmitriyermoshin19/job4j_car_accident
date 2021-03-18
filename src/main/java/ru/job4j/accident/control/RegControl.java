package ru.job4j.accident.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.Authority;
import ru.job4j.accident.model.User;
import ru.job4j.accident.repository.AuthorityRepository;
import ru.job4j.accident.repository.UserRepository;

@Controller
public class RegControl {

    private final PasswordEncoder encoder;

    private final UserRepository users;

    private final AuthorityRepository authorities;

    @Autowired
    public RegControl(PasswordEncoder encoder, UserRepository users, AuthorityRepository authorities) {
        this.encoder = encoder;
        this.users = users;
        this.authorities = authorities;
        Authority a1 = new Authority("ROLE_USER");
        Authority a2 = new Authority("ROLE_ADMIN");
        if (authorities.findByAuthority("ROLE_USER") == null) {
            authorities.save(a1);
            authorities.save(a2);
            User admin = new User("$2a$10$kVNH1J7DegNjZJ1MWwsabeu2ucViST6NLIKY8wHQmD85HpafH1vL6",
                    "root", a2, true);
            users.save(admin);
        }
    }

    @PostMapping("/reg")
    public String save(@ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        users.save(user);
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String reg(@ModelAttribute Accident accident) {
        return "reg";
    }
}
