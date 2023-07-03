package com.pennant.prodmtr.service.Interface;

import java.util.List;

import com.pennant.prodmtr.model.Dto.ModuleDTO;
import com.pennant.prodmtr.model.Dto.UserDto;
import com.pennant.prodmtr.model.Entity.FunctionalUnit;
import com.pennant.prodmtr.model.Entity.Sprint;
import com.pennant.prodmtr.model.Entity.SprintResource;
import com.pennant.prodmtr.model.Entity.SprintTasks;
import com.pennant.prodmtr.model.Entity.Task;

public interface SprintService {
	List<Sprint> getBacklogs();

	Sprint getSprintDetails(int sprintId);

	List<Task> getTasks(int modlId);

	List<Sprint> getAllSprints();

	List<SprintTasks> getAllTasksBySprintId(Sprint sprintId);

	Sprint storeSprint(Sprint sprint);

	List<ModuleDTO> getSprintModulesByProjectId(int projectId);

	List<FunctionalUnit> getFunctionalUnitsByModId(int modlId, int projid);

	public void updateFunctionalstatus(int funit);

	public Task storeTask(Task task);

	public List<UserDto> getAllResources();

	public void storeSprintResource(SprintResource src);

	public void storeSprintTasks(SprintTasks sprintTask);

	List<Sprint> getSprintsByProjId(int projectId);
}