package br.ufes.informatica.doeLivros.core.application;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.TextUtils;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.doeLivros.core.domain.Role;
import br.ufes.informatica.doeLivros.core.domain.User;
import br.ufes.informatica.doeLivros.core.exceptions.EmailInUseException;
import br.ufes.informatica.doeLivros.core.exceptions.EncodeFailedException;
import br.ufes.informatica.doeLivros.core.persistence.RoleDAO;
import br.ufes.informatica.doeLivros.core.persistence.UserDAO;
import br.ufes.informatica.doeLivros.people.persistence.AddressDAO;

/** TODO: generated by FrameWeb. Should be documented. */
@Stateless
public class RegisterServiceBean implements RegisterService {
	/** Serialization id (using default value, change if necessary). */
	private static final long serialVersionUID = 1L;

	/** TODO: generated by FrameWeb. Should be documented. */
	@EJB
	private UserDAO userDAO;
	
	@EJB
	private AddressDAO addressDAO;
	
	@EJB
	private RoleDAO roleDAO;
	
	// Função que checa se já existe um admin no sistema. Caso contrário, cria um usuário
	// admin padrão.
	@Override
	public void checkAdminCreated() {
		
		 try {
			 	roleDAO.retrieveByName(Role.ADMIN_ROLE_NAME); 
		    } catch (PersistentObjectNotFoundException e) {
		      // This is the expected outcome. Just log that everything is OK.
		    	Role role = new Role();
		    	role.setName(Role.ADMIN_ROLE_NAME);
		    	roleDAO.save(role);
		    	User admin = new User();
				Date data = new Date();
//				Address target = new Address();
		    	admin.setFirstName("Doe");
		    	admin.setLastName("Livro");
		    	admin.setBirthDate(data);
		    	admin.setRegistrationDate(data);
		    	admin.setEmail("admin@doelivro.com");
		    	admin.setPassword("admin");
//		    	admin.setTarget(target);
		    	Set<Role> list = new HashSet<Role>();
				list.add(role);
				admin.setRolesList(list);
				userDAO.save(admin);
//				addressDAO.save(target);
		    } catch (MultiplePersistentObjectsFoundException e) {
		      // This is a severe problem: the unique constraint has already been violated.
		      throw new EJBException(e);
		    }
	}

	// Função responsável pelo registro de um usuário no sistema. Caso não existe a role
	// de usuário comum, a função irá criar. Todo usuário criado terá a role "User" por padrão.
	// Com exceção do Administrador.
	@Override
	public void register(User user) throws EmailInUseException, EncodeFailedException {
		// FIXME: auto-generated method stub
		 try {
			 userDAO.getUserByEmail(user.getEmail());
		      throw new EmailInUseException(user.getEmail());
		    } catch (PersistentObjectNotFoundException e) {
		      // This is the expected outcome. Just log that everything is OK.
		    } catch (MultiplePersistentObjectsFoundException e) {
		      // This is a severe problem: the unique constraint has already been violated.
		      throw new EJBException(e);
		    }

		 Role role;
		 try {
			 	role = roleDAO.retrieveByName(Role.USER_ROLE_NAME); 
		    } catch (PersistentObjectNotFoundException e) {
		      // This is the expected outcome. Just log that everything is OK.
		    	role = new Role();
		    	role.setName(Role.USER_ROLE_NAME);
		    	roleDAO.save(role);
		    } catch (MultiplePersistentObjectsFoundException e) {
		      // This is a severe problem: the unique constraint has already been violated.
		      throw new EJBException(e);
		    }
		 
		 try {
		Set<Role> list = new HashSet<Role>();
		list.add(role);
		user.setRolesList(list);
		Date data = new Date();
		user.setRegistrationDate(data);
		user.setPassword(TextUtils.produceBase64EncodedMd5Hash(user.getPassword()));
		userDAO.save(user);
		if (user.getTarget() != null)
			addressDAO.save(user.getTarget());
		 }  catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			 throw new EncodeFailedException(e);
		 }
	}

	// Função responsável por atualizar o usuário com as novas informações fornecidas. 
	// É chamada na página de atualizar dados do usuário. 
	@Override
	public User updateUser(User user) {
//		System.out.println("1"+user);
		if (user.getTarget() != null) {
			this.addressDAO.save(user.getTarget());
		}
		user = this.userDAO.merge(user);
		return user;
//		return false;
	}
}