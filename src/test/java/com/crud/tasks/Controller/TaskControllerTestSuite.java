package com.crud.tasks.Controller;

import com.crud.tasks.controller.TaskController;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskControllerTestSuite {
    @Autowired
    TaskController taskController;

    @Transactional
    @Test
    public void GetAllTasksTest() {
        //Given
        TaskDto newTask = new TaskDto(1,"Test_task","This task is for testing");
        taskController.createTask(newTask);
        TaskDto newTask2 = new TaskDto(2,"Test_task2","Another testing task");
        taskController.createTask(newTask2);

        List<TaskDto> tasks = new ArrayList<>();

                //When
        tasks = taskController.getTasks();
        System.out.println("List size: " + tasks.size());
        for(TaskDto task : tasks) {
            System.out.println("Task id: " + task.getId() + ", title: " + task.getTitle() + ", content: " + task.getContent());
        }

        //Then
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertEquals("Another testing task", tasks.get(1).getContent());
    }

    @Transactional
    @Test
    public void GetAllTasksShouldReturnEmptyList() {
        //Given
        List<TaskDto> tasks = new ArrayList<>();

        //When
        tasks = taskController.getTasks();
        System.out.println("List size: " + tasks.size());
        for(TaskDto task : tasks) {
            System.out.println("Task id: " + task.getId() + ", title: " + task.getTitle() + ", content: " + task.getContent());
        }

        //Then
        assertNotNull(tasks);
        assertEquals(0, tasks.size());
    }

    @Transactional
    @Test
    public void deleteTasksTest() {
        //Given
        TaskDto newTask = new TaskDto(1,"Test_task","This task is for testing");
        taskController.createTask(newTask);
        TaskDto newTask2 = new TaskDto(2,"Test_task2","Another testing task");
        taskController.createTask(newTask2);

        List<TaskDto> tasks = new ArrayList<>();

        //When
        tasks = taskController.getTasks();
        System.out.println("List size: " + tasks.size());
        long taskId;
        for(TaskDto task : tasks) {
            taskId = task.getId();
            System.out.println("Task id: " + taskId + ", title: " + task.getTitle() + ", content: " + task.getContent());
            taskController.deleteTask(taskId);
            System.out.println("Task " + taskId + " deleted!");
        }
        tasks = taskController.getTasks();
        System.out.println("List size: " + tasks.size());

        //Then
        assertNotNull(tasks);
        assertEquals(0, tasks.size());
    }

    @Transactional
    @Test
    public void deleteNotExistingTaskTest() {
        //Given
        TaskDto newTask = new TaskDto(1,"Test_task","This task is for testing");
        taskController.createTask(newTask);
        TaskDto newTask2 = new TaskDto(2,"Test_task2","Another testing task");
        taskController.createTask(newTask2);

        List<TaskDto> tasks = new ArrayList<>();

        //When
        tasks = taskController.getTasks();
        boolean isException = false;
        System.out.println("List size: " + tasks.size());
        List<Long> taskIds = new ArrayList<>();
        long taskId = 0;
        for(TaskDto task : tasks) {
            taskIds.add(task.getId());
            System.out.println("Task id: " + task.getId() + ", title: " + task.getTitle() + ", content: " + task.getContent());
        }
        while(taskIds.contains(taskId)){
            taskId++;
        }
        try{
            taskController.deleteTask(taskId);
        } catch (EmptyResultDataAccessException e) {
            isException = true;
        }

        //Then
        assertNotNull(tasks);
        assertTrue(isException);
    }
}
