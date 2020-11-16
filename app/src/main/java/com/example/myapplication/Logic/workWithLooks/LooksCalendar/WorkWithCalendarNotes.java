package com.example.myapplication.Logic.workWithLooks.LooksCalendar;

import java.util.ArrayList;
import java.util.Date;

public class WorkWithCalendarNotes {

    private ArrayList<CalendarNote> calendarNotes = new ArrayList<>(); // записи в календаре
    //причем запись необязательно должна содержать лук или погоду, но дата должна быть всегда

    public WorkWithCalendarNotes(ArrayList<CalendarNote> calendarNotes) {
        this.calendarNotes = calendarNotes;
    }

    /**
     * добавление записи в календарь (список)
     * @param calendarNote - одна запись в календаре
     * */
    public void addNoteToCalendar(CalendarNote calendarNote){ //note- одна запись
        calendarNotes.add(calendarNote); //добавили
    }

    /**
     * удаление записи
     * @param id - индекс записи в списке
     * */
    public void removeNote(int id){
        calendarNotes.remove(id);
    }

    /**
     * Проверяет, есть ли эта дата в списке (есть ли лук на этот день)
     * @param date - дата для проверки
     * @return true - ДА, лук на этот день есть
     * @return false - НЕТ, лука на этот день нет
     * */
    public boolean checkDay(Date date){
        for (CalendarNote note:
             calendarNotes) {
            if (note.getDate() == date)
                return true;
        }
        return false;
    }


    public ArrayList<CalendarNote> getCalendarNotes() {
        return calendarNotes;
    }

    public void setCalendarNotes(ArrayList<CalendarNote> calendarNotes) {
        this.calendarNotes = calendarNotes;
    }
}
