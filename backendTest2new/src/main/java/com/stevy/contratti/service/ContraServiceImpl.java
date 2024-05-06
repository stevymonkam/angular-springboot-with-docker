package com.stevy.contratti.service;

import com.stevy.contratti.models.Contrat;
import com.stevy.contratti.models.DateAndCalendar;
import com.stevy.contratti.models.ERole;
import com.stevy.contratti.models.FileContrat;
import com.stevy.contratti.repository.ContratRepository;
import com.stevy.contratti.repository.RoleRepository;
import com.stevy.contratti.repository.UserRepository;
import com.stevy.contratti.security.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class ContraServiceImpl implements ContraService {
    private UserRepository appUserRepository;
    private ContratRepository appContratRepository;
    private RoleRepository appRoleRepository;

    public ContraServiceImpl(UserRepository appUserRepository, ContratRepository appContratRepository, RoleRepository appRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.appContratRepository = appContratRepository;
        this.appRoleRepository = appRoleRepository;
    }
    @Override
    public List <Contrat> Segnalisazioni(List<Contrat> l ) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-M-yyyy");
        List < Contrat > lc = new LinkedList < > ();
        int num = 0;
        String[] arr_segnali = new String[10];


        int c1 = 0;
        for (Contrat c: l) {
            String listString = "";
            List < String > segnali = new ArrayList < > ();

            c1++;

            String a1 = c.getData_scadenza();
            if (a1 == null) throw new RuntimeException("Data_scadenza non exists");
            DateAndCalendar obj = new DateAndCalendar();
            Date date = obj.format_data(a1);
            Calendar data_scadenza1 = obj.dateToCalendar(date);
            Calendar data_scadenza2 = obj.dateToCalendar(date); //je defini a chaque fois car si je transforme sa peut changer
            Calendar data_inizio = obj.data_inizio(data_scadenza1, c.getPreavviso(), 60);
            Calendar data_fine = obj.data_fine(data_scadenza2, c.getPreavviso()); // si je met datascadenza1 ici sa aura deja changer donc sa fause le calcul
            Calendar data_oggi = Calendar.getInstance();
            Date t1_inizio = obj.calendarToDate(data_inizio);
            Date t2_fine = obj.calendarToDate(data_fine);
            Date t3_oggi = obj.calendarToDate(data_oggi);

            Calendar data_scadenza_rinovoauto = obj.dateToCalendar(date);
            Date t1_inizio_scadenza4 = obj.calendarToDate(data_scadenza_rinovoauto);
            if ((t1_inizio_scadenza4.before(t3_oggi)) && (c.getRinnovo_automatico().equals("1")) && c.getStato_contratto().equals("vigente")) { //rinnovo_automatico ici a ete gere coe une string en testant le frond si sont type change venir aussi ici changer

                segnali.add("Rinnovo automatico da aggiornare");

            }

            Calendar data_scadenza_scad = obj.dateToCalendar(date);
            Date t1_inizio_scad = obj.calendarToDate(data_scadenza_scad);
            if (t1_inizio_scad.before(t3_oggi) && c.getStato_contratto().equals("vigente")) {

                segnali.add("contrato scaduto");


            }

            if (t1_inizio.before(t3_oggi) && (t3_oggi.before(t2_fine))) {

                segnali.add("Preavviso di scadenza");


            }
            Calendar data_scadenza3 = obj.dateToCalendar(date); // je defini comsa a chaque fois pour eviter d'utiluser une date deja transformer
            Calendar data_inizio_scadenza = obj.data_inizio(data_scadenza3, 0, 60);
            Date t1_inizio_scadenza = obj.calendarToDate(data_inizio_scadenza);
            if (t1_inizio_scadenza.before(t3_oggi)) {

                segnali.add("Contratto in scadenza");


            }

            Set < FileContrat > Setfilecontra2 = c.getFileContrats();
            if (c.getStato_contratto().equals("vigente") && Setfilecontra2.isEmpty()) {
                segnali.add("Documento mancante");

            }

            int size_list = segnali.size();
            for (String s: segnali) {
                size_list--;
                listString += s;
                if (size_list != 0) {
                    listString += "\t" + "e/o" + "\t";
                }

            }

            if (segnali.isEmpty()) {
                c.setSegnalazione(null);
            } else {
                c.setSegnalazione(listString);
                lc.add(c);
            }
            listString = null;
            segnali = null;

        }

        return lc;
    }

    @Override
    public List<Contrat> ListInterrogazione(String data, String data1, String data2) {

          if(data.equals("data_scadenza")){

               return  appContratRepository.findByData_scadenza(data1,data2);
           }
           if(data.equals("data_disdetta")){

            return  appContratRepository.findByData_disdetta(data1,data2);
           }
           if(data.equals("data_validata")){

            return  appContratRepository.findByData_validata(data1,data2);
           }

        return  null;
    }
    @Override
    public Contrat update(Contrat contrat ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl LoggedInUserId = (UserDetailsImpl)authentication.getPrincipal();
        if (AuthorityUtils.authorityListToSet(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        ).contains("ROLE_ADMMINISTRAZION")) {
            Contrat contratadmin =  (Contrat)appContratRepository.findByUserRoleAdmin(ERole.ROLE_ADMMINISTRAZION,contrat.getId());
            if (contratadmin == null) return null;
            contratadmin.setAzienda(contrat.getAzienda());
            contratadmin.setCodice(contrat.getCodice());
            contratadmin.setData_disdetta(contrat.getData_disdetta());
            contratadmin.setData_rinnovo(contrat.getData_rinnovo());
            contratadmin.setData_scadenza(contrat.getData_scadenza());
            contratadmin.setData_validata(contrat.getData_validata());
            contratadmin.setFornitore(contrat.getFornitore());
            contratadmin.setIva(contrat.getIva());
            contratadmin.setLop_cliente(contrat.getLop_cliente());
            contratadmin.setMail_contratto(contrat.getMail_contratto());
            contratadmin.setMail_preavviso(contrat.getMail_preavviso());
            contratadmin.setNote(contrat.getNote());
            contratadmin.setOwner(contrat.getOwner());
            contratadmin.setPeriodo(contrat.getPeriodo());
            contratadmin.setPreavviso(contrat.getPreavviso());
            contratadmin.setRinnovo_automatico(contrat.getRinnovo_automatico());
            contratadmin.setSede(contrat.getSede());
            contratadmin.setSocieta(contrat.getSocieta());
            contratadmin.setStato_contratto(contrat.getStato_contratto());
            contratadmin.setTipo_importo(contrat.getTipo_importo());
            contratadmin.setTipologia_contratto(contrat.getTipologia_contratto());
            //contratadmin.setUser(contrat.getUser());
            appContratRepository.save(contratadmin);
            return contratadmin;
        } else if (AuthorityUtils.authorityListToSet(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        ).contains("ROLE_LOGISTICA")) {
            Contrat contratlogis =  (Contrat)appContratRepository.findByUserRoleLogis(ERole.ROLE_LOGISTICA,contrat.getId());
            if(contratlogis == null) return null;
            contratlogis.setAzienda(contrat.getAzienda());
            contratlogis.setCodice(contrat.getCodice());
            contratlogis.setData_disdetta(contrat.getData_disdetta());
            contratlogis.setData_rinnovo(contrat.getData_rinnovo());
            contratlogis.setData_scadenza(contrat.getData_scadenza());
            contratlogis.setData_validata(contrat.getData_validata());
            contratlogis.setFornitore(contrat.getFornitore());
            contratlogis.setIva(contrat.getIva());
            contratlogis.setLop_cliente(contrat.getLop_cliente());
            contratlogis.setMail_contratto(contrat.getMail_contratto());
            contratlogis.setMail_preavviso(contrat.getMail_preavviso());
            contratlogis.setNote(contrat.getNote());
            contratlogis.setOwner(contrat.getOwner());
            contratlogis.setPeriodo(contrat.getPeriodo());
            contratlogis.setPreavviso(contrat.getPreavviso());
            contratlogis.setRinnovo_automatico(contrat.getRinnovo_automatico());
            contratlogis.setSede(contrat.getSede());
            contratlogis.setSocieta(contrat.getSocieta());
            contratlogis.setStato_contratto(contrat.getStato_contratto());
            contratlogis.setTipo_importo(contrat.getTipo_importo());
            contratlogis.setTipologia_contratto(contrat.getTipologia_contratto());
           //contratlogis.setUser(contrat.getUser());
            appContratRepository.save(contratlogis);
            return contratlogis;
        } else if (AuthorityUtils.authorityListToSet(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        ).contains("ROLE_SUPER_ADMIN")) {
            Contrat contrasuperadmin =  (Contrat)appContratRepository.findContratById(contrat.getId());
            if(contrasuperadmin == null) return null;
            contrasuperadmin.setAzienda(contrat.getAzienda());
            contrasuperadmin.setCodice(contrat.getCodice());
            contrasuperadmin.setData_disdetta(contrat.getData_disdetta());
            contrasuperadmin.setData_rinnovo(contrat.getData_rinnovo());
            contrasuperadmin.setData_scadenza(contrat.getData_scadenza());
            contrasuperadmin.setData_validata(contrat.getData_validata());
            contrasuperadmin.setFornitore(contrat.getFornitore());
            contrasuperadmin.setIva(contrat.getIva());
            contrasuperadmin.setLop_cliente(contrat.getLop_cliente());
            contrasuperadmin.setMail_contratto(contrat.getMail_contratto());
            contrasuperadmin.setMail_preavviso(contrat.getMail_preavviso());
            contrasuperadmin.setNote(contrat.getNote());
            contrasuperadmin.setOwner(contrat.getOwner());
            contrasuperadmin.setPeriodo(contrat.getPeriodo());
            contrasuperadmin.setPreavviso(contrat.getPreavviso());
            contrasuperadmin.setRinnovo_automatico(contrat.getRinnovo_automatico());
            contrasuperadmin.setSede(contrat.getSede());
            contrasuperadmin.setSocieta(contrat.getSocieta());
            contrasuperadmin.setStato_contratto(contrat.getStato_contratto());
            contrasuperadmin.setTipo_importo(contrat.getTipo_importo());
            contrasuperadmin.setTipologia_contratto(contrat.getTipologia_contratto());
            //contratlogis.setUser(contrat.getUser());
            appContratRepository.save(contrasuperadmin);
            return contrasuperadmin;
        } else {
            System.out.println("ROLE_HR");
            Contrat contratExist =  appContratRepository.findBySocietaId(contrat.getSocieta().getId(), contrat.getId());
            if (contratExist == null) return null;
            contratExist.setAzienda(contrat.getAzienda());
            contratExist.setCodice(contrat.getCodice());
            contratExist.setData_disdetta(contrat.getData_disdetta());
            contratExist.setData_rinnovo(contrat.getData_rinnovo());
            contratExist.setData_scadenza(contrat.getData_scadenza());
            contratExist.setData_validata(contrat.getData_validata());
            contratExist.setFornitore(contrat.getFornitore());
            contratExist.setIva(contrat.getIva());
            contratExist.setLop_cliente(contrat.getLop_cliente());
            contratExist.setMail_contratto(contrat.getMail_contratto());
            contratExist.setMail_preavviso(contrat.getMail_preavviso());
            contratExist.setNote(contrat.getNote());
            contratExist.setOwner(contrat.getOwner());
            contratExist.setPeriodo(contrat.getPeriodo());
            contratExist.setPreavviso(contrat.getPreavviso());
            contratExist.setRinnovo_automatico(contrat.getRinnovo_automatico());
            contratExist.setSede(contrat.getSede());
            contratExist.setSocieta(contrat.getSocieta());
            contratExist.setStato_contratto(contrat.getStato_contratto());
            contratExist.setTipo_importo(contrat.getTipo_importo());
            contratExist.setTipologia_contratto(contrat.getTipologia_contratto());
            //contratExist.setUser(contrat.getUser());
            appContratRepository.save(contratExist);
            return contratExist;
        }
    }
    @Override
    public Contrat show(Long id ) {
        if (AuthorityUtils.authorityListToSet(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        ).contains("ROLE_ADMMINISTRAZION")) {
            Contrat contrat =  (Contrat)appContratRepository.findByRole(ERole.ROLE_ADMMINISTRAZION,id);
            if (contrat == null) return null;
            return contrat;
        } else if (AuthorityUtils.authorityListToSet(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        ).contains("ROLE_LOGISTICA")) {
            Contrat contrat =  (Contrat)appContratRepository.findByUserRoleLogis(ERole.ROLE_LOGISTICA,id);
            if (contrat == null) return null;
            return contrat;
        } else if (AuthorityUtils.authorityListToSet(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
        ).contains("ROLE_HR")) {
            Contrat contrat =  (Contrat)appContratRepository.findByUserRoleLogis(ERole.ROLE_HR,id);
            if (contrat == null) return null;
            return contrat;
        } else {
            Contrat contrat =  (Contrat)appContratRepository.findByUserRoleLogis(ERole.ROLE_GESTIONE,id);
            if (contrat == null) return null;
            return contrat;
        }
    }
}