package com.herokuapp.erpmesbackend.erpmesbackend.tasks;

import com.herokuapp.erpmesbackend.erpmesbackend.exceptions.NotFoundException;
import com.herokuapp.erpmesbackend.erpmesbackend.staff.employees.Employee;
import com.herokuapp.erpmesbackend.erpmesbackend.staff.employees.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/tasks")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskRepository.findAll());
    }

    @GetMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task getOneTask(@PathVariable("id") Long id) {
        checkIfTaskExists(id);
        return taskRepository.findById(id).get();
    }

    @GetMapping(value = "/tasks", params = {"assigneeId"})
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasksByAssignee(@RequestParam("assigneeId") Long assigneeId) {
        checkIfAssigneeExists(assigneeId);
        return new ArrayList<>(taskRepository.findByAssigneeId(assigneeId));
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public Task addNewTask(@RequestBody TaskRequest taskRequest) {
        String name = taskRequest.getName();
        Category category = taskRequest.getCategory();

        checkIfAssigneeExists(taskRequest.getAssigneeId());
        Employee assignee = employeeRepository.findById(taskRequest.getAssigneeId()).get();

        List<Task> precedingTasks = new ArrayList<>();

        if (taskRequest.getPrecedingTaskIds() != null)
            taskRequest.getPrecedingTaskIds().forEach(id -> precedingTasks.add(taskRepository.findById(id).get()));

        String details = taskRequest.getDetails();
        int estimatedTimeInMinutes = taskRequest.getEstimatedTimeInMinutes();
        LocalDateTime deadline = taskRequest.getDeadline();

        Task task = new Task(name, category, assignee, precedingTasks, details, estimatedTimeInMinutes, deadline);
        taskRepository.save(task);
        return task;
    }

    @DeleteMapping("/tasks/{id}")
    public HttpStatus removeTask(@PathVariable("id") Long id) {
        checkIfTaskExists(id);
        taskRepository.delete(taskRepository.findById(id).get());
        return HttpStatus.OK;
    }

    private void checkIfTaskExists(Long id) {
        if (!taskRepository.findById(id).isPresent())
            throw new NotFoundException("Such task doesn't exist!");
    }

    private void checkIfAssigneeExists(Long id) {
        if (!employeeRepository.findById(id).isPresent())
            throw new NotFoundException("Chosen assignee doesn't exist!");
    }
}