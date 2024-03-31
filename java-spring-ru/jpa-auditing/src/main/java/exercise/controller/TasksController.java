package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Task;
import exercise.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TasksController {


    private final TaskRepository taskRepository;

    @GetMapping(path = "")
    public List<Task> index() {
        return taskRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Task show(@PathVariable long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
    }

    // BEGIN
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                           @RequestBody Task task) {
        val existingTask = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Задачи с id " + id + " не существует")
        );

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());

        return ResponseEntity.ok().body(taskRepository.save(existingTask));
    }
    // END

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        taskRepository.deleteById(id);
    }
}
