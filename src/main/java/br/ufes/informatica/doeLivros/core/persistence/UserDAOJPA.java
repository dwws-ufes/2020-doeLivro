package br.ufes.informatica.doeLivros.core.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.doeLivros.core.domain.Role;
import br.ufes.informatica.doeLivros.core.domain.User;

/** TODO: generated by FrameWeb. Should be documented. */
@Stateless
public class UserDAOJPA extends BaseJPADAO<User> implements UserDAO {
	/** Serialization id (using default value, change if necessary). */
	private static final long serialVersionUID = 1L;

	/** TODO: generated by FrameWeb. Should be documented. */
	@PersistenceContext
	private EntityManager entityManager;

	/** TODO: generated by FrameWeb. Should be documented. */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** TODO: generated by FrameWeb. Should be documented. */
	@Override
	public User getUserByEmail(String userEmail) {
		// FIXME: auto-generated method stub
		return null;
	}

	/** TODO: generated by FrameWeb. Should be documented. */
	@Override
	public List<User> getUserList() {
		// FIXME: auto-generated method stub
		return null;
	}

	/** TODO: generated by FrameWeb. Should be documented. */
	@Override
	public List<User> getUserByRole(Role role) {
		// FIXME: auto-generated method stub
		return null;
	}

}