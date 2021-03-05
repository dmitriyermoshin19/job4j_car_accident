package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccidentMem {

    private final Map<Integer, Accident> accidents = new HashMap<>();

    private AccidentMem() {
        Accident first = Accident.of("First", "Поцарапан бампер", "Minsk");
        first.setId(1);
        Accident second = Accident.of("Second", "Поцарапано крыло", "Gomel");
        second.setId(2);
        Accident third = Accident.of("Third", "Разбито стекло", "Grodno");
        third.setId(3);
        accidents.put(1, first);
        accidents.put(2, second);
        accidents.put(3, third);
    }

    public List<Accident> getAllAccidents() {
        return new ArrayList<>(accidents.values());
    }
}
