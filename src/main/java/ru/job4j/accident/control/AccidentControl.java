package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.AccidentTypeRepository;
import ru.job4j.accident.repository.RuleRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AccidentControl {
    private final AccidentRepository accRep;
    private final AccidentTypeRepository aTypeRep;
    private final RuleRepository ruleRep;

    public AccidentControl(AccidentRepository accRep,
                           AccidentTypeRepository aTypeRep,
                           RuleRepository ruleRep) {
        this.accRep = accRep;
        this.aTypeRep = aTypeRep;
        this.ruleRep = ruleRep;
        AccidentType at1 = AccidentType.of(1, "две машины");
        AccidentType at2 = AccidentType.of(2, "машина и человек");
        AccidentType at3 = AccidentType.of(3, "машина и велосипед");
        aTypeRep.save(at1);
        aTypeRep.save(at2);
        aTypeRep.save(at3);
        Rule r1 = Rule.of(1, "статья 1");
        Rule r2 = Rule.of(2, "статья 2");
        Rule r3 = Rule.of(3, "статья 3");
        ruleRep.save(r1);
        ruleRep.save(r2);
        ruleRep.save(r3);
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<AccidentType> types = new ArrayList<>();
        List<Rule> rules = new ArrayList<>();
        aTypeRep.findAll().forEach(types::add);
        ruleRep.findAll().forEach(rules::add);
        model.addAttribute("types", types);
        model.addAttribute("allRules", rules);
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        for (String id : ids) {
            accident.addRule(ruleRep.findById(Integer.parseInt(id)).get());
        }
        accRep.save(accident);
            return "redirect:/";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("types", aTypeRep.findAll());
        model.addAttribute("allRules", ruleRep.findAllRules());
        model.addAttribute("accident", accRep.findById(id).get());
        model.addAttribute("accRules", accRep.accident(id).getRules());
        return "accident/update";
    }
}
