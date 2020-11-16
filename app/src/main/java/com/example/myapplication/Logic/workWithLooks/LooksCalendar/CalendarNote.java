package com.example.myapplication.Logic.workWithLooks.LooksCalendar;

import com.example.myapplication.Logic.workWithLooks.Look;

import java.util.ArrayList;
import java.util.Date;

public class CalendarNote {
    //класс для хранения ВСЕХ записей в календаре
    private Look look; // лук
    private Date date; // дата

    public CalendarNote(Look look, Date date) {
        this.look = look;
        this.date = date;
    }


    public Look getLook() {
        return look;
    }

    public void setLook(Look look) {
        this.look = look;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
