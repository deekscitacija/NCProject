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
public class IzmeneRecenzijaMessage implements JavaDelegate {
	
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
		
		Long korisnikId = Long.parseLong((String) execution.getVariable("autorRada"));
		Korisnik autor = korisnikRepository.findById(korisnikId).get();
		
		Long casopisId = Long.parseLong((String) execution.getVariable("casopis"));
		Casopis casopis = casopisRepository.findById(casopisId).get();
		
		String statusRecenzije = (String) execution.getVariable("revizijaStatus");
		
		String porukaAutor = "";
		
		if(statusRecenzije.equals("M")) {
			porukaAutor += "<html>"+
						   "<body>"+
						    "<div>\r\n" + 
						    "<h3>Objava rada prihvacena uz doradu</h3>"+
						    "<p>Postovani "+autor.getIme()+" "+autor.getPrezime()+", Vas zahtev za objavu rada u casopis \""+casopis.getNaziv()+"\" je prihvacen, ali uz neophodnu doradu. </p>"+
						    "<p>Nakon izvrsene recenzije, Vas rad je <b>prihvacen uz manju doradu</b> od strane recenzenata. Neophpdno je dostaviti novu verziju rada. Detalje mozete pronaci na Vasoj korisnickoj stranici.</p>"+
						    "</div>"+
						    "</body>"+
						    "</html>";
		}else {
			porukaAutor += "<html>"+
						   "<body>"+
						   "<div>\r\n" + 
						   "<h3>Objava rada uslovno prihvacena</h3>"+
						   "<p>Postovani "+autor.getIme()+" "+autor.getPrezime()+", Vas zahtev za objavu rada u casopis \""+casopis.getNaziv()+"\" je prihvacen, ali uz neophodnu vecu doradu. </p>"+
						   "<p>Nakon izvrsene recenzije, Vas rad je <b>uslovno prihvacen</b> od strane recenzenata. Neophpdno je dostaviti novu verziju rada. Detalje mozete pronaci na Vasoj korisnickoj stranici.</p>"+
						   "</div>"+
						   "</body>"+
						   "</html>";
		}

		
		try {
			MailSenderService.sendEmail(mailSender, environment, "NCMjok, Objava rada, ishod recenzije", porukaAutor, autor.getEmail());
		}catch(Exception e) {
			System.out.println("* greska_slanje_poruke *");
		}
		

	}

}
