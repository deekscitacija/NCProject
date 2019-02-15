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
import com.ftn.nc.NCBackend.web.repository.CasopisRepository;
import com.ftn.nc.NCBackend.web.repository.KorisnikRepository;

@Service
public class PrijavaNovogRadaMessage implements JavaDelegate{
	
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

		System.out.println("*** Obavestavanje autora i urednika ***");
		
		Long korisnikId = Long.parseLong((String) execution.getVariable("autorRada"));
		Korisnik autor = korisnikRepository.findById(korisnikId).get();
		
		if(autor == null) {
			throw new BpmnError("greska_nepostojeci_entitet");
		}
		
		Long casopisId = Long.parseLong((String) execution.getVariable("casopis"));
		Casopis casopis = casopisRepository.findById(casopisId).get();
		
		if(casopis == null) {
			throw new BpmnError("greska_nepostojeci_entitet");
		}
		
		Korisnik urednik = korisnikRepository.findById(casopis.getUrednik().getId()).get();
		
		// *** Poruka, autor ***//
		String porukaAutor = "";

		porukaAutor += "<html>"+
				  "<body>"+
				  "<div>\r\n" + 
				  "<h3>Objava vaseg rada je zabelezena </h3>"+
				  "<p>Postovani "+autor.getIme()+" "+autor.getPrezime()+", Vas zahtev za objavu rada u casopis \""+casopis.getNaziv()+"\" je uspesno zabelezena. "+
				  "Bicete obavesteni o odluci odgovornog urednika. </p>"+
				  "</div>"+
				  "</body>"+
				  "</html>";
		
			
		// *** Poruka, urednik ***//
		String porukaUrednik = "";

		porukaUrednik += "<html>"+
				  "<body>"+
				  "<div>\r\n" + 
				  "<h3>Prijavljen je novi rad u casopis"+casopis.getNaziv()+"</h3>"+
				  "<p>Postovani "+urednik.getIme()+" "+urednik.getPrezime()+", novi rad u Vasem casopisu ceka odobrenje za recenziju."+
				  "Detalje o prijavi mozete videti u listi zadataka na vasoj korisnickoj stranici. </p>"+
				  "</div>"+
				  "</body>"+
				  "</html>";
		
		try {
			MailSenderService.sendEmail(mailSender, environment, "NCMjok, Obavestenje o objavi rada", porukaAutor, autor.getEmail());
			MailSenderService.sendEmail(mailSender, environment, "NCMjok, Nova objava za casopis "+casopis.getNaziv(), porukaUrednik, urednik.getEmail());
		}catch(Exception e) {
			System.out.println("* greska_slanje_poruke *");
			throw new BpmnError("greska_slanje_poruke");
		}
	}
	
}
