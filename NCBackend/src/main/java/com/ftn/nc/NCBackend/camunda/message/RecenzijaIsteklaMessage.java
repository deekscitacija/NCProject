package com.ftn.nc.NCBackend.camunda.message;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ftn.nc.NCBackend.helpClasses.MailSenderService;
import com.ftn.nc.NCBackend.web.model.Casopis;
import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.RevizijaRada;
import com.ftn.nc.NCBackend.web.model.RevizijaRadaRecenzent;
import com.ftn.nc.NCBackend.web.repository.CasopisRepository;
import com.ftn.nc.NCBackend.web.repository.KorisnikRepository;
import com.ftn.nc.NCBackend.web.repository.RevizijaRadaRecenzentRepository;
import com.ftn.nc.NCBackend.web.repository.RevizijaRadaRepository;

@Service
public class RecenzijaIsteklaMessage implements JavaDelegate {
	
	@Autowired
	private RevizijaRadaRepository revizijaRadaRepository;
	
	@Autowired
	private RevizijaRadaRecenzentRepository revizijaRadaRecenzentRepository;
	
	@Autowired
	private CasopisRepository casopisRepository;
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private Environment environment;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long casopisId = Long.parseLong((String) execution.getVariable("casopis"));
		Long revizijaId = Long.parseLong((String) execution.getVariable("revizijaId"));
		Long urednikRecenzent = Long.parseLong((String) execution.getVariable("urednikRecenzent"));
		
		Casopis casopis = casopisRepository.getOne(casopisId);
		RevizijaRada revizija = revizijaRadaRepository.getOne(revizijaId);
		
		if(casopis == null || revizija == null) {
			return;
		}
		
		Korisnik urednik = korisnikRepository.getOne(urednikRecenzent);
		
		String poruka = "";

		poruka += "<html>"+
				  "<body>"+
				  "<div>\r\n" + 
				  "<h3>Recenzenti koji nizu obavizi recenziju za objavu rada u casopisu \""+casopis.getNaziv()+"\" : </h3>";
				  
		for(RevizijaRadaRecenzent rrr: revizija.getRecenzentiRevizija()) {
			if(!rrr.isZavrseno()) {
				rrr.setAktuelno(false);
				rrr = revizijaRadaRecenzentRepository.save(rrr);
				Korisnik recenzent = korisnikRepository.getOne(rrr.getRecenzent().getId());
				poruka += "<p>"+recenzent.getIme()+" "+recenzent.getPrezime()+", "+recenzent.getEmail()+".";
			}
		}
		
		poruka += "</div>"+
				  "</body>"+
				  "</html>";

		try {
			MailSenderService.sendEmail(mailSender, environment, "NCMjok, istekla recenzija u casopisu \""+casopis.getNaziv()+"\"", poruka, urednik.getEmail());
		}catch(Exception e) {
			System.out.println("* greska_slanje_poruke *");
		}
	}

}
