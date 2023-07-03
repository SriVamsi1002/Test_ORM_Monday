package com.pennant.prodmtr.service.Impl;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pennant.prodmtr.Dao.Interface.SprintDao;
import com.pennant.prodmtr.model.Dto.ModuleDTO;
import com.pennant.prodmtr.model.Dto.UserDto;
import com.pennant.prodmtr.model.Entity.FunctionalUnit;
import com.pennant.prodmtr.model.Entity.Sprint;
import com.pennant.prodmtr.model.Entity.SprintResource;
import com.pennant.prodmtr.model.Entity.SprintTasks;
import com.pennant.prodmtr.model.Entity.Task;
import com.pennant.prodmtr.service.Interface.SprintService;

@Component
@Transactional
@Service
public class SprintServiceImpl implements SprintService {
	private final SprintDao sprintDao;

	@Autowired
	public SprintServiceImpl(SprintDao sprintDao) {
		this.sprintDao = sprintDao;
	}

	@Override
	public List<Sprint> getBacklogs()
			throws IllegalArgumentException, EntityNotFoundException, TransactionRequiredException,
			QueryTimeoutException, PessimisticLockException, LockTimeoutException, PersistenceException {
		return sprintDao.getBaskLogs();
	}

	@Override
	public Sprint getSprintDetails(int sprintId)
			throws IllegalArgumentException, EntityNotFoundException, TransactionRequiredException,
			QueryTimeoutException, PessimisticLockException, LockTimeoutException, PersistenceException {
		return sprintDao.getSprintDetails(sprintId);
	}

	@Override
	public List<Task> getTasks(int modlId)
			throws IllegalArgumentException, EntityNotFoundException, TransactionRequiredException,
			QueryTimeoutException, PersistenceException, NoResultException, NonUniqueResultException {
		return sprintDao.getTasks(modlId);
	}

	@Override
	public List<Sprint> getAllSprints() throws IllegalArgumentException, TransactionRequiredException,
			QueryTimeoutException, PersistenceException, NoResultException, NonUniqueResultException {
		return sprintDao.getAllSprints();
	}

	@Override
	public List<SprintTasks> getAllTasksBySprintId(Sprint sprintId)
			throws IllegalArgumentException, TransactionRequiredException, QueryTimeoutException, PersistenceException,
			NoResultException, NonUniqueResultException {
		return sprintDao.getAllTasksBySprintId(sprintId);
	}

	@Override
	public Sprint storeSprint(Sprint sprint)
			throws IllegalArgumentException, TransactionRequiredException, EntityExistsException, PersistenceException {
		return sprintDao.storeSprint(sprint);
	}

	@Override
	public List<ModuleDTO> getSprintModulesByProjectId(int projectId)
			throws IllegalArgumentException, QueryTimeoutException, PersistenceException, NoResultException {
		// List<ModuleDTO> modules = sprintDAO.getSprintModulesByProjectId(projectId);

		return sprintDao.getSprintModulesByProjectId(projectId);
	}

	@Override
	public List<FunctionalUnit> getFunctionalUnitsByModId(int modlId, int projid)
			throws IllegalArgumentException, QueryTimeoutException, PersistenceException {
		return sprintDao.getFunctionalUnitsByModId(modlId, projid);
	}

	public void storeSprintResource(SprintResource src) {
		sprintDao.storeSprintResource(src);
	}

	public void storeSprintTasks(SprintTasks sprintTask) {
		// TODO Auto-generated method stub
		sprintDao.storeSprintTasks(sprintTask);

	}

	@Override
	public Task storeTask(Task task) {
		return sprintDao.storeTask(task);
	}

	@Override
	public List<UserDto> getAllResources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sprint> getSprintsByProjId(int projId) {
		return sprintDao.getSprintByProjId(projId);

	}

	public void updateFunctionalstatus(int funit) {
		sprintDao.updateFunctionalstatus(funit);
	}

}
