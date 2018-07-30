package com.crud.tasks.Controller;

import com.crud.tasks.controller.TaskController;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc
public class TaskControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchAllTasks() throws Exception {
        //Given
        Task newTask = new Task(1L,"Test_task","This task is for testing");
        Task newTask2 = new Task(2L,"Test_task2","Another testing task");

        List<Task> tasks = new ArrayList<>();
        tasks.add(newTask);
        tasks.add(newTask2);

        TaskDto newTaskDto = new TaskDto(1L,"Test_task","This task is for testing");
        TaskDto newTaskDto2 = new TaskDto(2L,"Test_task2","Another testing task");

        List<TaskDto> tasksDto = new ArrayList<>();
        tasksDto.add(newTaskDto);
        tasksDto.add(newTaskDto2);

        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(tasksDto);
        when(service.getAllTasks()).thenReturn(tasks);

        //When & Then
        mockMvc.perform(get("/v1/tasks").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test_task")))
                .andExpect(jsonPath("$[0].content", is("This task is for testing")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Test_task2")))
                .andExpect(jsonPath("$[1].content", is("Another testing task")))
        ;
    }

    @Test
    public void shouldFetchTask() throws Exception {
        //Given
        Task newTask = new Task(1L,"Test_task","This task is for testing");

        TaskDto newTaskDto = new TaskDto(1L,"Test_task","This task is for testing");

        when(taskMapper.mapToTaskDto(newTask)).thenReturn(newTaskDto);
        when(taskMapper.mapToTask(newTaskDto)).thenReturn(newTask);
        when(service.getTask(1L)).thenReturn(Optional.of(newTask));

        //When & Then
        mockMvc.perform(get("/v1/tasks/1")
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.title", is("Test_task")))
            .andExpect(jsonPath("$.content", is("This task is for testing")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        Task newTask = new Task(1L,"Test_task","This task is for testing");

        TaskDto newTaskDto = new TaskDto(1L,"Test_task","This task is for testing");

        when(taskMapper.mapToTaskDto(newTask)).thenReturn(newTaskDto);
        when(taskMapper.mapToTask(newTaskDto)).thenReturn(newTask);
        when(service.saveTask(newTask)).thenReturn(newTask);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(newTask);

        //When & Then
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        Task newTask = new Task(1L,"Test_task","This task is for testing");

        TaskDto newTaskDto = new TaskDto(1L,"Test_task","This task is for testing");

        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(newTaskDto);
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(newTask);
        when(service.saveTask(newTask)).thenReturn(newTask);
        when(service.getTask(1L)).thenReturn(Optional.of(newTask));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(newTaskDto);

        //When & Then
        mockMvc.perform(put("/v1/tasks")
                .contentType(APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test_task")))
                .andExpect(jsonPath("$.content", is("This task is for testing")));

        mockMvc.perform(get("/v1/tasks/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test_task")))
                .andExpect(jsonPath("$.content", is("This task is for testing")));

        mockMvc.perform(delete("/v1/tasks/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        Assert.assertEquals(0, service.getAllTasks().size());


    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        Task newTask = new Task(1L,"Test_task","This task is for testing");

        TaskDto newTaskDto = new TaskDto(1L,"Test_task","This task is for testing");

        when(taskMapper.mapToTaskDto(newTask)).thenReturn(newTaskDto);
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(newTask);
        when(service.saveTask(newTask)).thenReturn(newTask);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(newTaskDto);

        //When & Then
        mockMvc.perform(put("/v1/tasks")
                .contentType(APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test_task")))
                .andExpect(jsonPath("$.content", is("This task is for testing")));
    }
}
