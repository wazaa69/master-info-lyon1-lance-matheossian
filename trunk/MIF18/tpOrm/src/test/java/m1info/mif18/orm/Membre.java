package m1info.mif18.orm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Membre {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_gen")
	@SequenceGenerator(name="seq_gen",sequenceName="SEQ_MEMBRE",allocationSize=1)
	private Long id;
	private String nom;
	private String email;
	private long nb_interventions = 0L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getNb_interventions() {
		return nb_interventions;
	}

	public void setNb_interventions(long nbInterventions) {
		nb_interventions = nbInterventions;
	}

}