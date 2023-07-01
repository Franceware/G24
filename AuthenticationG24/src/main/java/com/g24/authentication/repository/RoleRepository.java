package com.g24.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.g24.authentication.entity.Role;

/* RoleRepository estende l'interfaccia di repository JPA.
 * JpaRepository permette di effettuare diverse operazioni, quali Create, Read, Update e Delete su di un'entità JPA. 
 * In particolare, in questo caso accetta due parametri, l'entità Role e il tipo di chiave primaria dell'entità, Long.
 */

public interface RoleRepository extends JpaRepository<Role, Long> 
{
	/* Metodo findByName  - Utilizza la funzionalità di creazione di query del metodo di Spring Data JPA per generare 
	 *                      query di ricerca di un ruolo in base al nome.
	 *                      Il metodo accetta un argomento, il ruolo, e restituisce un oggetto role o null, nel caso 
	 *                      non venga trovata una corrispondenza.
	 */
	Role findByName(String name);
}
