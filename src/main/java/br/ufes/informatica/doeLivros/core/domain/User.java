package br.ufes.informatica.doeLivros.core.domain;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.informatica.doeLivros.people.domain.Person;

/** TODO: generated by FrameWeb. Should be documented. */
@Entity
@Table(name= "Person")
@DiscriminatorValue("1")
public class User extends Person { //implements Comparable<User> { 
	// Bug do frameweb? User deveria estender Person, de acordo com o modelo, 
	// mas não foi representado no código gerado. Além disso, não é possível
	// estender classes com dois Comparables, e por isso comentei aqui
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** TODO: generated by FrameWeb. Should be documented. false */

	private String nickname;

	/** TODO: generated by FrameWeb. Should be documented. false */
	@NotNull
	@Size(max = 150)
	private String email;

	/** TODO: generated by FrameWeb. Should be documented. false */
	@NotNull
	@Size(max = 10)
	private String password;

	/** TODO: generated by FrameWeb. Should be documented. */
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<Role> rolesList;

	/** Getter for nickname. */
	public String getNickname() {
		return nickname;
	}

	/** Setter for nickname. */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/** Getter for email. */
	public String getEmail() {
		return email;
	}

	/** Setter for email. */
	public void setEmail(String email) {
		this.email = email;
	}

	/** Getter for password. */
	public String getPassword() {
		return password;
	}

	/** Setter for password. */
	public void setPassword(String password) {
		this.password = password;
	}

	/** Getter for rolesList. */
	public Set<Role> getRolesList() {
		return rolesList;
	}

	/** Setter for rolesList. */
	public void setRolesList(Set<Role> rolesList) {
		this.rolesList = rolesList;
	}

//	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
//	@Override
//	public int compareTo(User o) {
//		// FIXME: auto-generated method stub
//		return super.compareTo(o);
//	}
}