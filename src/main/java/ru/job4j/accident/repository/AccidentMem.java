package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger idx = new AtomicInteger(4);
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();

    private AccidentMem() {
        AccidentType one = AccidentType.of(1, "Две машины");
        AccidentType two = AccidentType.of(2, "Машина и человек");
        AccidentType three = AccidentType.of(3, "Машина и велосипед");
        types.put(one.getId(), one);
        types.put(two.getId(), two);
        types.put(three.getId(), three);
        Accident first = Accident.of("First", "Поцарапан бампер", "Minsk", one);
        first.setId(1);
        Accident second = Accident.of("Second", "Поцарапано крыло", "Gomel", two);
        second.setId(2);
        Accident third = Accident.of("Third", "Разбито стекло", "Grodno", three);
        third.setId(3);
        accidents.put(1, first);
        accidents.put(2, second);
        accidents.put(3, third);
    }

    public Object getAllAccidents() {
        return accidents.values();
    }

    public void create(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(idx.getAndIncrement());
        }
        accidents.put(accident.getId(), accident);
    }

    public Accident findById(int id) {
        return accidents.get(id);
    }

    public Object getAllTypes() {
        return types.values();
    }

    public AccidentType findTypeById(int id) {
        return types.get(id);
    }
}
