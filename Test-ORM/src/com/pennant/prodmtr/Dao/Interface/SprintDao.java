package com.pennant.prodmtr.Dao.Interface;

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

import com.pennant.prodmtr.model.Dto.ModuleDTO;
import com.pennant.prodmtr.model.Dto.UserDto;
import com.pennant.prodmtr.model.Entity.FunctionalUnit;
import com.pennant.prodmtr.model.Entity.Sprint;
import com.pennant.prodmtr.model.Entity.SprintResource;
import com.pennant.prodmtr.model.Entity.SprintTasks;
import com.pennant.prodmtr.model.Entity.Task;

public interface SprintDao {
	List<Sprint> getBaskLogs() throws IllegalArgumentException, EntityNotFoundException, TransactionRequiredException,
			QueryTimeoutException, PessimisticLockException, LockTimeoutException, PersistenceException;

	Sprint getSprintDetails(int sprintId)
			throws IllegalArgumentException, EntityNotFoundException, TransactionRequiredException,
			QueryTimeoutException, PessimisticLockException, LockTimeoutException, PersistenceException;

	List<Task> getTasks(int modlId)
			throws IllegalArgumentException, EntityNotFoundException, TransactionRequiredException,
			QueryTimeoutException, PersistenceException, NoResultException, NonUniqueResultException;

	List<Sprint> getAllSprints() throws IllegalArgumentException, TransactionRequiredException, QueryTimeoutException,
			PersistenceException, NoResultException, NonUniqueResultException;

	List<SprintTasks> getAllTasksBySprintId(Sprint sprintId)
			throws IllegalArgumentException, TransactionRequiredException, QueryTimeoutException, PersistenceException,
			NoResultException, NonUniqueResultException;

	Sprint storeSprint(Sprint sprint)
			throws IllegalArgumentException, TransactionRequiredException, EntityExistsException, PersistenceException;

	List<ModuleDTO> getSprintModulesByProjectId(int projectId)
			throws IllegalArgumentException, QueryTimeoutException, PersistenceException, NoResultException;

	List<FunctionalUnit> getFunctionalUnitsByModId(int modl_id, int projid)
			throws IllegalArgumentException, QueryTimeoutException, PersistenceException;

	public Task storeTask(Task task);

	public List<UserDto> getAllResources();

	// Add other methods as needed
	public void storeSprintResource(SprintResource src);

	public void storeSprintTasks(SprintTasks sprintTask);

	List<Sprint> getSprintByProjId(int projId);

	public void updateFunctionalstatus(int funit);
}