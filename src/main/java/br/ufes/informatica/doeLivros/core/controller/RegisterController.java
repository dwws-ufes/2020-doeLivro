package br.ufes.informatica.doeLivros.core.controller;

import javax.ejb.EJB;
import javax.enterprise.inject.Model;

import br.ufes.inf.nemo.jbutler.ejb.controller.JSFController;
import br.ufes.informatica.doeLivros.core.application.RegisterService;
import br.ufes.informatica.doeLivros.core.domain.User;

/** TODO: generated by FrameWeb. Should be documented. */
@Model
public class RegisterController extends JSFController {
	/** Serialization id (using default value, change if necessary). */
	private static final long serialVersionUID = 1L;

	/** TODO: generated by FrameWeb. Should be documented. */
	@EJB
	private RegisterService registerService;

	/** TODO: generated by FrameWeb. Should be documented. */
	private User user;

	/** TODO: generated by FrameWeb. Should be documented. */
	private String confirmPassword;

	/** TODO: generated by FrameWeb. Should be documented. */
	public String register() {
		// FIXME: auto-generated method stub
		return null;
	}

	/** Getter for user. */
	public User getUser() {
		return user;
	}

	/** Setter for user. */
	public void setUser(User user) {
		this.user = user;
	}

	/** Getter for confirmPassword. */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/** Setter for confirmPassword. */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}