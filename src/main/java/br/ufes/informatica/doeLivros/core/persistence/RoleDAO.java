package br.ufes.informatica.doeLivros.core.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.doeLivros.core.domain.Permission;
import br.ufes.informatica.doeLivros.core.domain.Role;

/** TODO: generated by FrameWeb. Should be documented. */
@Local
public interface RoleDAO extends BaseDAO<Role> {

	/** TODO: generated by FrameWeb. Should be documented. */
	public List<Role> getRoleByPermission(Permission permission);

}