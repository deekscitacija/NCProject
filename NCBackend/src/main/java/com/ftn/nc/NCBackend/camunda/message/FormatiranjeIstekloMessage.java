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
import com.ftn.nc.NCBackend.web.repository.CasopisRepository;
import com.ftn.nc.NCBackend.web.repository.KorisnikRepository;

@Service
public class FormatiranjeIstekloMessage implements JavaDelegate {
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	private CasopisRepository casopisRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private Environment environment;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("\n*** FORMATIRANJE ISTEKLO ***");
		Long korisnikId = Long.parseLong((String) execution.getVariable("autorRada"));
		Korisnik autor = korisnikRepository.findById(korisnikId).get();
		
		Long casopisId = Long.parseLong((String) execution.getVariable("casopis"));
		Casopis casopis = casopisRepository.findById(casopisId).get();

		String porukaAutor = "";

		porukaAutor += "<html>"+
				  "<body>"+
				  "<div>\r\n" + 
				  "<h3>Objava rada odbijena</h3>"+
				  "<p>Postovani "+autor.getIme()+" "+autor.getPrezime()+", Vas zahtev za objavu rada u casopisu \""+casopis.getNaziv()+"\" je odbijen usled <b>isteka vremena</b> za ispravku formatiranja. </p>"+
				  "</div>"+
				  "</body>"+
				  "</html>";
		
		try {
			MailSenderService.sendEmail(mailSender, environment, "NCMjok, Objava rada, formatiranje, vreme isteklo", porukaAutor, autor.getEmail());
		}catch(Exception e) {
			System.out.println("* greska_slanje_poruke *");
		}
	}

}
