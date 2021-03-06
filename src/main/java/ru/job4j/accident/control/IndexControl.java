package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.repository.AccidentMem;

@Controller
public class IndexControl {

    private final AccidentMem accidentMem;

    public IndexControl(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("accidents", accidentMem.getAllAccidents());
        return "index";
    }
}
