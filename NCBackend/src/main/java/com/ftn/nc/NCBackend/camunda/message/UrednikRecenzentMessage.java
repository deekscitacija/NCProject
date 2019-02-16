package com.ftn.nc.NCBackend.camunda.message;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.helpClasses.MailSenderService;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.NaucnaOblast;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.model.Urednik;
import com.ftn.nc.NCBackend.web.repository.CasopisRepository;
import com.ftn.nc.NCBackend.web.repository.KorisnikRepository;
import com.ftn.nc.NCBackend.web.repository.RevizijaRadaRepository;

@Service
public class UrednikRecenzentMessage implements JavaDelegate {
	
	@Autowired
	private CasopisRepository casopisRepository;
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	private RevizijaRadaRepository revizijaRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private Environment environment;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long casopisId = Long.parseLong((String) execution.getVariable("casopis"));
		Long revizijaId = Long.parseLong((String) execution.getVariable("revizijaId"));
		
		Casopis casopis = casopisRepository.getOne(casopisId);
		RevizijaRada revizija = revizijaRepository.getOne(revizijaId);
		
		if(casopis == null || revizija == null) {
			throw new BpmnError("greska_nepostojeci_entitet");
		}
		
		if(casopis.getUrednik() == null) {
			throw new BpmnError("greska_nepostojeci_entitet");
		}
		
		Urednik urednik = nadjiUrednikaIzNaucneOblasti(casopis, revizija.getNaucnaOblast());
		
		if(urednik == null) {
			urednik = casopis.getUrednik();
		}
		
		Korisnik korisnik = korisnikRepository.getOne(urednik.getId());
		
		execution.setVariable("urednikRecenzent", urednik.getId().toString());
		
		String poruka = "";

		poruka += "<html>"+
				  "<body>"+
				  "<div>\r\n" + 
				  "<h3>Prijavljan novi rad na recenziju</h3>"+
				  "<p>Postovani, izabrani ste da u casopisu \""+casopis.getNaziv()+"\" budete zaduzeni za upravljanje recenzijom novog rada. </p>"+
				  "<p>Detalje mozete videti u Vasoj listi zadataka.</p>"+
				  "</div>"+
				  "</body>"+
				  "</html>";
		
		try {
			MailSenderService.sendEmail(mailSender, environment, "NCMjok, nova recenzija u casopisu \""+casopis.getNaziv()+"\"", poruka, korisnik.getEmail());
		}catch(Exception e) {
			System.out.println("* greska_slanje_poruke *");
		}

	}
	
	private Urednik nadjiUrednikaIzNaucneOblasti(Casopis casopis, NaucnaOblast naucnaOblast) {
		
		for(Urednik urednik : casopis.getUredjivackiOdbor()) {
			for(NaucnaOblast tempNaucnaOblast : urednik.getNaucneOblasti()) {
				if(tempNaucnaOblast.getKod().equals(naucnaOblast.getKod())) {
					return urednik;
				}
			}
		}

		return null;
	}

}
