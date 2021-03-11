package com.company;

import java.time.Duration;
import java.time.LocalTime;

public interface Visitable {
    LocalTime getOpeningTime();
    LocalTime getClosingTime();
    default LocalTime defaultOpening(){return LocalTime.of(9, 30);};
    default LocalTime defaultClosing(){return LocalTime.parse("20:00");};

    static Duration getVisitingDuration(Visitable l) {
        Duration d=Duration.between(l.getOpeningTime(),l.getClosingTime());
        return d;
    }
}
