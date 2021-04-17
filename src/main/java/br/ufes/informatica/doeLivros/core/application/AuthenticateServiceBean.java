package br.ufes.informatica.doeLivros.core.application;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.TextUtils;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.doeLivros.core.domain.User;
import br.ufes.informatica.doeLivros.core.exceptions.EncodeFailedException;
import br.ufes.informatica.doeLivros.core.persistence.UserDAO;

/** TODO: generated by FrameWeb. Should be documented. */
@Stateless
public class AuthenticateServiceBean implements AuthenticateService {
	/** Serialization id (using default value, change if necessary). */
	private static final long serialVersionUID = 1L;

	/** TODO: generated by FrameWeb. Should be documented. */
	@EJB
	private UserDAO userDAO;

	// Método de autenticação. Ele recupera o usuário de acordo com o email fornecido
	// E confere a senha. Se o usuário não existir ou a senha estiver errada, retorna nulo. 
	// Caso contrário, retorna o usuário associado ao e-mail.
	@Override
	public User authenticate(String email, String password) throws EncodeFailedException {
		try {
			 User user = this.userDAO.getUserByEmail(email);
			 //if (password.compareTo(user.getPassword()) == 0) { TextUtils.produceBase64EncodedMd5Hash(password)
			if (TextUtils.produceBase64EncodedMd5Hash(password).compareTo(user.getPassword()) == 0) {
				 return user;
			 }
			 else
				 return null;
		} catch (PersistentObjectNotFoundException e) {
			return null;
		} catch (MultiplePersistentObjectsFoundException e) {
			return null;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
		 throw new EncodeFailedException(e);
		}
	}
	

}