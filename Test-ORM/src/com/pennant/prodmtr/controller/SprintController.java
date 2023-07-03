package com.pennant.prodmtr.controller;

import java.text.ParseException;
import java.util.ArrayList;
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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pennant.prodmtr.model.Dto.FunctionalUnitdto;
import com.pennant.prodmtr.model.Dto.ModuleDTO;
import com.pennant.prodmtr.model.Dto.ProjectDto;
import com.pennant.prodmtr.model.Dto.TaskDto;
import com.pennant.prodmtr.model.Dto.UserDto;
import com.pennant.prodmtr.model.Entity.FunctionalUnit;
import com.pennant.prodmtr.model.Entity.Sprint;
import com.pennant.prodmtr.model.Entity.SprintResource;
import com.pennant.prodmtr.model.Entity.SprintTasks;
import com.pennant.prodmtr.model.Entity.Task;
import com.pennant.prodmtr.model.Input.SprintInput;
import com.pennant.prodmtr.model.Input.SprintResourceInput;
import com.pennant.prodmtr.model.Input.SprintTasksInput;
import com.pennant.prodmtr.model.Input.TaskInput;
import com.pennant.prodmtr.model.view.FunctionalTask;
import com.pennant.prodmtr.service.Interface.ModuleService;
import com.pennant.prodmtr.service.Interface.ProjectService;
import com.pennant.prodmtr.service.Interface.ResourceService;
import com.pennant.prodmtr.service.Interface.SprintService;
import com.pennant.prodmtr.service.Interface.TaskService;

@Controller
public class SprintController {

	SprintService sprintService;
	ProjectService projectService;
	ModuleService moduleService;
	TaskService taskService;
	ResourceService resourceService;
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	public SprintController(SprintService sprintService, ProjectService projectService, ModuleService moduleService,
			TaskService taskService, ResourceService resourceService) {
		super();
		this.sprintService = sprintService;
		this.projectService = projectService;
		this.moduleService = moduleService;
		this.taskService = taskService;
		this.resourceService = resourceService;

	}

	@RequestMapping(value = "/ShowFunctionalUnits", method = RequestMethod.POST)
	public String createTask(@Validated SprintInput sprintInput,
			@ModelAttribute SprintResourceInput SprintResourceInput, Model model, HttpSession session)
			throws ParseException, IllegalArgumentException, TransactionRequiredException, EntityExistsException,
			PersistenceException {
		Sprint s = sprintService.storeSprint(sprintInput.toEntity());
		SprintResource sr = SprintResourceInput.toEntity();
		int sprintid = s.getSprintId();
		session.setAttribute("sprintid", sprintid);
		sr.setSprintId(s.getSprintId());
		sprintService.storeSprintResource(sr);

		List<FunctionalUnit> flist = sprintService.getFunctionalUnitsByModId(sprintInput.getModuleId(),
				sprintInput.getProjectId());
		List<FunctionalUnitdto> funlistDto = new ArrayList<>();

		System.out.println("Before" + flist);

		for (FunctionalUnit functionalUnit : flist) {
			FunctionalUnitdto funUnitDto = FunctionalUnitdto.fromEntity(functionalUnit);
			funlistDto.add(funUnitDto);
		}
		System.out.println("After" + funlistDto);

		model.addAttribute("funlist", funlistDto);
		model.addAttribute("pro_id", sprintInput.getProjectId());
		return "ShowFunctionalUnits";
	}

	@RequestMapping(value = "/ShowFunUnits", method = RequestMethod.GET)
	public String getFunctionalUnitIntoSprint(@RequestParam("modlid") int modlid, @RequestParam("projid") int projid,
			Model model) {

		System.out.println("moduleid=" + modlid + "project ID " + projid);
		List<FunctionalUnit> flist = sprintService.getFunctionalUnitsByModId(modlid, projid);
		System.out.println(flist);
		List<FunctionalUnitdto> funlistDto = new ArrayList<>();

		for (FunctionalUnit functionalUnit : flist) {
			FunctionalUnitdto funUnitDto = FunctionalUnitdto.fromEntity(functionalUnit);
			funlistDto.add(funUnitDto);
		}

		model.addAttribute("funlist", funlistDto);
		return "ShowFunctionalUnits";
	}

	@RequestMapping(value = "/sprint", method = RequestMethod.GET)
	public String sprint(Model model) throws IllegalArgumentException, TransactionRequiredException,
			QueryTimeoutException, PersistenceException, NoResultException, NonUniqueResultException {
		List<Sprint> allSprints = sprintService.getAllSprints();
		model.addAttribute("allSprints", allSprints);
		return "sprint_home";
	}

	@RequestMapping(value = "/sprint_details", method = RequestMethod.GET)
	public String getSprintDetails(Model model, @RequestParam int sprintId, HttpSession session)
			throws IllegalArgumentException, TransactionRequiredException, QueryTimeoutException, PersistenceException,
			NoResultException, NonUniqueResultException {
		Sprint sprint = sprintService.getSprintDetails(sprintId);
		model.addAttribute("sprint", sprint);
		Sprint s = new Sprint();
		s.setSprintId(sprintId);
		int sprintid = s.getSprintId();
		session.setAttribute("sprintid", sprintid);
		List<SprintTasks> tasksByIdSprints = sprintService.getAllTasksBySprintId(s);
		model.addAttribute("tasksByIdSprints", tasksByIdSprints);
		return "sprint_details";
	}

	@RequestMapping(value = "/add_sprint", method = RequestMethod.GET)
	public String addSprint(Model model) {
		List<ProjectDto> pl = projectService.getAllProjects();
		model.addAttribute("projects", pl);

		List<UserDto> lu = resourceService.getAllResources();

		model.addAttribute("users", lu);
		System.out.println("lu" + lu);
		return "add_sprint";
	}

	@RequestMapping(value = "/FunctionalUnit", method = RequestMethod.GET)
	public String addSprint() {

		return "FunctionalUnit";
	}

	@RequestMapping(value = "/SubTaskdetails", method = RequestMethod.GET)
	public String SubtaskDetails() {
		// System.out.println("Subtask Details requested");
		return "SubtaskDetails";
	}

	@RequestMapping(value = "/CreateSubTask", method = RequestMethod.GET)
	public String CreateSubtask() {

		return "CreateSubtask";
	}

	// TODO
	@RequestMapping(value = "/backlogs", method = RequestMethod.GET)
	public String pastdue(Model model)
			throws IllegalArgumentException, EntityNotFoundException, TransactionRequiredException,
			QueryTimeoutException, PessimisticLockException, LockTimeoutException, PersistenceException {
		ArrayList<Sprint> SprintList = (ArrayList<Sprint>) sprintService.getBacklogs();

		model.addAttribute("sprintList", SprintList);
		return "backlog";
	}

	@RequestMapping(value = "/BacklogTasks", method = RequestMethod.GET)
	public String getBacklogTasks(Model model, @RequestParam("sprnModlId") int sprnModlId,
			@RequestParam("sprnId") int sprnId)
			throws IllegalArgumentException, EntityNotFoundException, TransactionRequiredException,
			QueryTimeoutException, PersistenceException, NoResultException, NonUniqueResultException {

		Sprint sprint = sprintService.getSprintDetails(sprnId);
		List<Task> taskList = sprintService.getTasks(sprnModlId);
		model.addAttribute("sprint", sprint);
		model.addAttribute("taskList", taskList);
		return "BacklogTasks";
	}

	@ResponseBody
	@RequestMapping(value = "/getModuleById", method = RequestMethod.POST, produces = "application/json")
	public String getModuleById(@RequestParam("projectId") int projectId)
			throws IllegalArgumentException, QueryTimeoutException, PersistenceException, NoResultException {
		List<ModuleDTO> moduleList = sprintService.getSprintModulesByProjectId(projectId);
		System.out.println("module list is " + moduleList.get(0).getModl_name());
		Gson gson = new Gson();
		String json = gson.toJson(moduleList);
		System.out.println("data in json formate" + json);
		return json;
	}

	@RequestMapping(value = "/Task", method = RequestMethod.POST)
	public String createTask(@ModelAttribute FunctionalTask ft, Model model) {
		model.addAttribute("funtask", ft);
		List<UserDto> lu = resourceService.getAllResources();
		model.addAttribute("users", lu);
		List<TaskDto> tasks = taskService.getAllTasks();
		model.addAttribute("tasks", tasks);
		return "Task";
	}

	@RequestMapping(value = "/TaskAdded", method = RequestMethod.POST)
	public String TaskAdded(@ModelAttribute TaskInput taskInput, SprintTasksInput sprintTasksInput,

			Model model, HttpSession session) {

		// System.out.println(":error int the TaskAdded controller");
		System.out.println(":error int the TaskAdded controller" + taskInput.toEntity().getTaskName());
		int sprintid = (int) session.getAttribute("sprintid");
		Task t = sprintService.storeTask(taskInput.toEntity());
		sprintTasksInput.setSprintId(sprintid);
		sprintTasksInput.setTaskId(t.getTaskId());

		sprintTasksInput.setUserId(t.getTaskSupervisor().getUserId());
		SprintTasks st;
		st = sprintTasksInput.toEntity();
		System.out.println(sprintTasksInput);
		sprintService.updateFunctionalstatus(taskInput.getFunid());
		sprintService.storeSprintTasks(st);

		return "TaskAdded";
	}

	@RequestMapping(value = "/sprintDetailsByProjId", method = RequestMethod.GET)
	public String getSprintDetailsByProjId(@RequestParam("projectId") int projectId, Model model) {

		List<Sprint> sprintsByProjId = sprintService.getSprintsByProjId(projectId);

		model.addAttribute("sprintsByProjId", sprintsByProjId);
		return "sprintsByProjId";
	}
}
