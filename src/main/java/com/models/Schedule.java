package com.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Schedule {
    public List<String> session1 = Arrays.asList("", "", "", "", "", "", "");
    public List<String> session2 = Arrays.asList("", "", "", "", "", "", "");
    public List<String> session3 = Arrays.asList("", "", "", "", "", "", "");
    public List<String> session4 = Arrays.asList("", "", "", "", "", "", "");

    public Schedule() {

    }

    public Schedule(List<String> session1, List<String> session2, List<String> session3, List<String> session4) {
        this.session1 = session1;
        this.session2 = session2;
        this.session3 = session3;
        this.session4 = session4;
    }
}
