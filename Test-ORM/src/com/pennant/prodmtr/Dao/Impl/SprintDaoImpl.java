package com.pennant.prodmtr.Dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pennant.prodmtr.Dao.Interface.SprintDao;
import com.pennant.prodmtr.model.Dto.ModuleDTO;
import com.pennant.prodmtr.model.Dto.UserDto;
import com.pennant.prodmtr.model.Entity.FunctionalUnit;
import com.pennant.prodmtr.model.Entity.Module;
import com.pennant.prodmtr.model.Entity.Sprint;
import com.pennant.prodmtr.model.Entity.SprintResource;
import com.pennant.prodmtr.model.Entity.SprintTasks;
import com.pennant.prodmtr.model.Entity.Task;
import com.pennant.prodmtr.model.Entity.User;

@Repository
@Transactional
public class SprintDaoImpl implements SprintDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Sprint> getBaskLogs() throws IllegalArgumentException, IllegalStateException, PersistenceException,
			TransactionRequiredException, QueryTimeoutException, NoResultException {
		try {
			String query = "SELECT s FROM Sprint s WHERE EXISTS (SELECT 1 FROM Task t WHERE t.module.id = s.moduleId.id AND t.taskCompletedDateTime IS NULL)";
			return entityManager.createQuery(query, Sprint.class).getResultList();
		} catch (IllegalArgumentException e) {
			String errorMessage = "Invalid request. Please provide a valid query parameter.";
			// Handle IllegalArgumentException (add custom message or perform other actions)
			throw new IllegalArgumentException(errorMessage, e);
		} catch (IllegalStateException e) {
			String errorMessage = "Internal error. Please try again later.";
			// Handle IllegalStateException (add custom message or perform other actions)
			throw new IllegalStateException(errorMessage, e);
		} catch (QueryTimeoutException e) {
			String errorMessage = "The execution of the query has exceeded the specified timeout.";
			// Handle QueryTimeoutException (add custom message or perform other actions)
			throw new QueryTimeoutException(errorMessage, e);
		} catch (PersistenceException e) {
			String errorMessage = "An error occurred while retrieving data from the database.";
			// Handle PersistenceException (add custom message or perform other actions)
			throw new PersistenceException(errorMessage, e);
		}
	}

	@Override
	public Sprint getSprintDetails(int sprintId)
			throws IllegalArgumentException, EntityNotFoundException, TransactionRequiredException,
			QueryTimeoutException, PessimisticLockException, LockTimeoutException, PersistenceException {
		try {
			return entityManager.find(Sprint.class, sprintId);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid sprint ID. Please provide a valid ID.");
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException("Sprint not found for the given ID.");
		} catch (TransactionRequiredException e) {
			throw new TransactionRequiredException("Transaction is required to fetch the sprint details.");
		} catch (QueryTimeoutException e) {
			throw new QueryTimeoutException(
					"Fetching sprint details took longer than expected. Please try again later.");
		} catch (PessimisticLockException e) {
			throw new PessimisticLockException("Unable to fetch sprint details due to a lock conflict.");
		} catch (LockTimeoutException e) {
			throw new LockTimeoutException(
					"Fetching sprint details exceeded the lock timeout. Please try again later.");
		} catch (PersistenceException e) {
			throw new PersistenceException("An error occurred while fetching the sprint details.");
		}
	}

	@Override
	public List<Task> getTasks(int modlId)
			throws IllegalArgumentException, EntityNotFoundException, TransactionRequiredException,
			QueryTimeoutException, PersistenceException, NoResultException, NonUniqueResultException {
		try {
			String query = "SELECT t FROM Task t WHERE t.module.id = :modlId";
			return entityManager.createQuery(query, Task.class).setParameter("modlId", modlId).getResultList();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid module ID. Please provide a valid ID.");
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException("Module not found for the given ID.");
		} catch (TransactionRequiredException e) {
			throw new TransactionRequiredException("Unable to fetch tasks. Please try again later.");
		} catch (QueryTimeoutException e) {
			throw new QueryTimeoutException("Fetching tasks took longer than expected. Please try again later.");
		} catch (NoResultException e) {
			throw new NoResultException("No tasks found for the specified module ID.");
		} catch (NonUniqueResultException e) {
			throw new NonUniqueResultException(
					"Multiple tasks found for the specified module ID. Please contact support for assistance.");
		} catch (PersistenceException e) {
			throw new PersistenceException("An error occurred while fetching tasks. Please try again later.");
		}
	}

	@Override
	public List<Sprint> getAllSprints() throws IllegalArgumentException, TransactionRequiredException,
			QueryTimeoutException, PersistenceException, NoResultException, NonUniqueResultException {
		try {
			String query = "SELECT s FROM Sprint s";
			return entityManager.createQuery(query, Sprint.class).getResultList();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid query. Please provide a valid query.", e);
		} catch (TransactionRequiredException e) {
			throw new TransactionRequiredException("Transaction is required to perform the query operation.");
		} catch (QueryTimeoutException e) {
			throw new QueryTimeoutException("The execution of the query has exceeded the specified timeout.", e);
		} catch (NoResultException e) {
			throw new NoResultException("No sprints found.");
		} catch (NonUniqueResultException e) {
			throw new NonUniqueResultException("Multiple sprints found. Please contact support for assistance.");
		} catch (PersistenceException e) {
			throw new PersistenceException("An error occurred while fetching sprints.", e);
		}
	}

	@Override
	public List<SprintTasks> getAllTasksBySprintId(Sprint sprintId)
			throws IllegalArgumentException, TransactionRequiredException, QueryTimeoutException, PersistenceException,
			NoResultException, NonUniqueResultException {
		try {
			String query = "SELECT st FROM SprintTasks st WHERE st.id.sprnId = :sprintId";
			return entityManager.createQuery(query, SprintTasks.class).setParameter("sprintId", sprintId)
					.getResultList();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid sprint ID. Please provide a valid ID.", e);
		} catch (TransactionRequiredException e) {
			throw new TransactionRequiredException("Transaction is required to perform the query operation.");
		} catch (QueryTimeoutException e) {
			throw new QueryTimeoutException("The execution of the query has exceeded the specified timeout.", e);
		} catch (NoResultException e) {
			throw new NoResultException("No tasks found for the specified sprint ID.");
		} catch (NonUniqueResultException e) {
			throw new NonUniqueResultException("Multiple tasks found for the specified sprint ID.");
		} catch (PersistenceException e) {
			throw new PersistenceException("An error occurred while fetching tasks for the sprint.", e);
		}
	}

	@Override
	public Sprint storeSprint(Sprint sprint)
			throws IllegalArgumentException, TransactionRequiredException, EntityExistsException, PersistenceException {
		try {
			if (sprint.getSprintId() == 0) {
				entityManager.persist(sprint); // New entity, use persist
			} else {
				entityManager.merge(sprint); // Existing entity, use merge
			}
			System.out.println("Retrieved from the database: " + sprint);
			return sprint;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid sprint data. Please provide valid data.", e);
		} catch (TransactionRequiredException e) {
			throw new TransactionRequiredException("Transaction is required to perform the store operation.");
		} catch (EntityExistsException e) {
			throw new EntityExistsException("Sprint already exists in the database.", e);
		} catch (PersistenceException e) {
			throw new PersistenceException("An error occurred while storing the sprint.", e);
		}
	}

	@Override
	public List<ModuleDTO> getSprintModulesByProjectId(int projectId)
			throws IllegalArgumentException, QueryTimeoutException, PersistenceException, NoResultException {
		try {
			short pid = (short) projectId;
			String query = "SELECT m FROM com.pennant.prodmtr.model.Entity.Module m WHERE m.moduleProject.projectId = :projectId AND m.moduleId NOT IN (SELECT s.moduleId.id FROM com.pennant.prodmtr.model.Entity.Sprint s)";
			TypedQuery<Module> typedQuery = entityManager.createQuery(query, Module.class);
			typedQuery.setParameter("projectId", pid);

			List<Module> moduleList = typedQuery.getResultList();

			for (Module m : moduleList) {
				System.out.println(m);
			}

			List<ModuleDTO> moduleDTOList = new ArrayList<>();
			for (Module m : moduleList) {
				ModuleDTO md = ModuleDTO.fromEntity(m);
				moduleDTOList.add(md);
			}

			System.out.println(moduleList.get(0) + "  divider  " + moduleDTOList.get(0).getModl_id());
			return moduleDTOList;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid project ID. Please provide a valid ID.", e);
		} catch (QueryTimeoutException e) {
			throw new QueryTimeoutException("The execution of the query has exceeded the specified timeout.", e);
		} catch (NoResultException e) {
			throw new NoResultException("No sprint modules found for the specified project ID.");
		} catch (PersistenceException e) {
			throw new PersistenceException("An error occurred while retrieving sprint modules.", e);
		}
	}

	public List<FunctionalUnit> getFunctionalUnitsByModId(int modlId, int prjid)
			throws IllegalArgumentException, QueryTimeoutException, PersistenceException {
		try {
			short mId = (short) modlId;
			short pId = (short) prjid;
			String funstatus = null;
			String query = "SELECT fu FROM FunctionalUnit fu WHERE fu.id.module.id = :modlId AND fu.projectId.projectId = :prjid AND fu.funStatus is null";

			return entityManager.createQuery(query, FunctionalUnit.class).setParameter("modlId", mId)
					.setParameter("prjid", pId).getResultList();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid module or project ID. Please provide valid IDs.", e);
		} catch (QueryTimeoutException e) {
			throw new QueryTimeoutException("The execution of the query has exceeded the specified timeout.", e);
		} catch (PersistenceException e) {
			throw new PersistenceException("An error occurred while retrieving functional units.", e);
		}
	}

	public Task storeTask(Task task) {
		if (task.getTaskId() == 0) {
			entityManager.persist(task); // New entity, use persist
		} else {
			entityManager.merge(task); // Existing entity, use merge
		}
		return task;
	}

	public List<UserDto> getAllResources() {
		String jpql = "SELECT r FROM User r";
		TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
		List<User> users = query.getResultList();

		List<UserDto> userDtos = users.stream().map(UserDto::fromEntity).collect(Collectors.toList());

		return userDtos;
	}

	public void storeSprintResource(SprintResource src) {
		entityManager.persist(src);
	}

	public void storeSprintTasks(SprintTasks sprintTask) {
		// TODO Auto-generated method stub
		entityManager.persist(sprintTask);

	}

	@Override
	public List<Sprint> getSprintByProjId(int projId) {
		TypedQuery<Sprint> query = entityManager
				.createQuery("SELECT s FROM Sprint s where projectId.projectId = :projId", Sprint.class);
		query.setParameter("projId", (short) projId);
		List<Sprint> sprints = query.getResultList();
		return sprints;
	}

	public void updateFunctionalstatus(int funit) {
		String status = "Task";
		String qry = "UPDATE FunctionalUnit f SET f.funStatus = :status WHERE f.id.funitid = :funit";

		entityManager.createQuery(qry).setParameter("funit", funit).setParameter("status", status).executeUpdate();
	}

}