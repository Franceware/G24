package com.example.demo.entity;

/*JPA -> Java Persistence API
  Specifica Java per la gestione dei dati relazionali nelle applicazioni Java Enterprise */
import jakarta.persistence.*;

/* Lombok
 * E' una libreria Java che si collega automaticamente all'editor e crea strumenti.
 * In questo caso, permette di generare automaticaticamente i metodi getter e setter per
 * tutti i campi della classe, un costruttore senza argomenti per la classe, un costruttore
 * che accetta tutti gli argomenti per i vari campi della classe.
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/* Definisce la classe User come un'entità JPA. In altri termini, la classe Users viene
 * mappata a una tabella del database.
 * Table permette di specificare il nome della tabella del database a cui è mappata l'entità.
 */
@Entity
@Table(name = "users")

public class User
{
	//private static final long serialVersionUID = 1L;
	
	//Id - Specifica come chiave primaria il campo id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Column - Specifica, in questo caso, i campi nome email e password non possono essere nulli.
	//         Inoltre, il campo email deve essere univoco nella tabella del database.
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	
	@Column
	private String degreeCourse;
	
	@Column
	private String university;
	
	//Specifica la relazione tra le due entità Utente e Ruolo - Molti a molti 
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	//Specifica come implementare il join
	@JoinTable(
			name = "users_roles",
			joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName="ID")},
			inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
	private List<Role> roles = new ArrayList<>();
}