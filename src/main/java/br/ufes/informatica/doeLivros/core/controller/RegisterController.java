package br.ufes.informatica.doeLivros.core.controller;

import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import br.ufes.inf.nemo.jbutler.ejb.controller.JSFController;
import br.ufes.informatica.doeLivros.core.application.RegisterService;
import br.ufes.informatica.doeLivros.core.domain.User;
import br.ufes.informatica.doeLivros.core.exceptions.EmailInUseException;
import br.ufes.informatica.doeLivros.people.domain.Address;

/** TODO: generated by FrameWeb. Should be documented. */
@Model
public class RegisterController extends JSFController {
	/** Serialization id (using default value, change if necessary). */
	private static final long serialVersionUID = 1L;

	/** TODO: generated by FrameWeb. Should be documented. */
	@EJB
	private RegisterService registerService;

	@Inject
	private AuthenticateController authenticateController;

	/** TODO: generated by FrameWeb. Should be documented. */
	private User user = new User();

	/** TODO: generated by FrameWeb. Should be documented. */
	private String confirmPassword;
	
	private String newPassword;

	public String getNewPassword() {
		return newPassword;
	}
	public String getOldPassword() {
		return oldPassword;
	}

	private String oldPassword;
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	private Address bkpAddress;

	public void checkAdminCreated() {
		registerService.checkAdminCreated();
	}
	
	// Função do controlador responsável pelo registro de usuário. 
	// Irá se comunicar com o serviço para cadastrar o usuário no banco de dados
	// Mas fará algumas checagens antes: senha digitada 2x corretamente e se o 
	// endereço de email já está sendo utilizado.
	public String register() {
		
		if (confirmPassword.matches(".*\\s+.*") || user.getPassword().matches(".*\\s+.*") ) {
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgsCore");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("signupForm.passwordBlank"), ""));
			return null;
		}
		
		if (confirmPassword.equals(user.getPassword())) {
			try {
				registerService.register(user);
			} catch (EmailInUseException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgsCore");
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("signupForm.emailError"), ""));
				return null;
			}

		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgsCore");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("signupForm.passwordMismatch"), ""));
			return null;
		}
		this.authenticateController.setUser(user);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("user", this.user);
		return "/index.xhtml?faces-redirect=true";
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

	// Função do controlador responsável por atualizar o usuário no banco de dados.
	// Chamada na página de atualização de dados do usuário.
	public String updateUser() {
		User user = this.registerService.updateUser(this.authenticateController.getUser());
		this.authenticateController.setUser(user);
		return "updateUser.xhtml?faces-redirect=true";
	}

	// Função que checa se o usuário tem um endereço registrado. Caso não tenha
	// Irá criar um objeto endereço que deverá ser preenchido na página correspondente
	public void checkAddress() {
		bkpAddress = this.authenticateController.getUser().getTarget();
		if (this.authenticateController.getUser().getTarget() == null)
			this.authenticateController.getUser().setTarget(new Address());
	}

	// Função que cancela a atualização de endereço do usuário
	public String cancelUpdateAddress() {
		this.authenticateController.getUser().setTarget(bkpAddress);
		return "updateUser.xhtml?faces-redirect=true";
	}

	// Função que cancela a atualização de dados do usuário.
	public String cancelUpdate() {
		return "/index.xhtml?faces-redirect=true";
	}
	
	//Função de atualiação de senha. Será chamada no formulário responsável por essa funcionalidade.
	public String updatePassword() {
		if (this.authenticateController.getUser().getPassword().compareTo(this.oldPassword) == 0) {
			if (this.newPassword.compareTo(this.confirmPassword) == 0) {
				this.authenticateController.getUser().setPassword(this.newPassword);
				return this.updateUser();
			}
			else {
				FacesContext context = FacesContext.getCurrentInstance();
				ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgsCore");
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("user.updatePassPage.mismatch"), ""));
				return null;
			}
		}
		else {
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgsCore");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("user.updatePassPage.error"), ""));
			return null;
		}
	}
	
	// Função que cancela a mudança de senha do usuário. Será chamada no formulário responsável por essa funcionalidade.
	public String cancelUpdatePassword() {
		this.confirmPassword = "";
		this.newPassword = "";
		this.oldPassword = "";
		return "updateUser.xhtml?faces-redirect=true";
	}

}