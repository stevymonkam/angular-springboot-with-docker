package com.stevy.contratti.models;

import com.stevy.contratti.repository.SocietaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAndCalendar {

    private Societa societa;
    private Role role;

    @Autowired
    SocietaRepository societaRepository;

    public Societa getSocieta() {
        return societa;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setSocieta(Societa societa) {
        this.societa = societa;
    }

    public Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }
    public Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }

    public Calendar data_inizio(Calendar data_scadenza, int preavviso, int mese) {

        int number = (-preavviso - mese);
        data_scadenza.add(Calendar.DATE, number);

        return data_scadenza;

    }
    public Calendar data_fine(Calendar data_scadenza, int preavviso) {
        int number = (-preavviso);

        data_scadenza.add(Calendar.DATE, number);
        return data_scadenza;
    }
    public Date format_data(String data_scadenza) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String[] result = data_scadenza.split("-");

        if (result.length != 3) {
            Date date1 = sdf.parse(data_scadenza);
            return date1;
        }
        if (result.length == 3 && result.length != 1) {

            int longeur1 = result[0].length();
            int longeur2 = result[2].length();
            System.out.println("voici l1" + "/" + longeur1);
            System.out.println("voici l2" + "/" + longeur2);
            if (longeur1 > longeur2) {
                String t = result[2] + '/' + result[1] + '/' + result[0];
                Date date = sdf.parse(t);
                return date;
            }
            if (longeur1 < longeur2) {
                String t = result[0] + '/' + result[1] + '/' + result[2];
                Date date = sdf.parse(t);
                return date;

            }

        }
        return null;

    }

}