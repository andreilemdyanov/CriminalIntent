package com.work.criminalintent;

import java.util.Date;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Crime {
    @Getter
    private UUID id;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private Date date;
    @Getter
    @Setter
    private boolean solved;
    public Crime() {
        id = UUID.randomUUID();
        date = new Date();
    }
}
