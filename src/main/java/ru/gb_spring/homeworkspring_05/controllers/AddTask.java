package ru.gb_spring.homeworkspring_05.controllers;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb_spring.homeworkspring_05.services.TaskServices;

/**
 * Обработка запросов добавления задачи
 * GET - "/addtask" - вывод HTML формы
 * POST - "/addtask - обработка формы
 */
@Controller
@Log
public class AddTask {
    @Autowired
    TaskServices ts;

    /**
     * Вывод формы для ввода заголовка и описания задачи
     *
     * @return - вывод страницы с формой ввода задачи
     */

    private final Counter addNoteCounter = Metrics.counter("add_task_count");

    @GetMapping("/addtask")
    public String addTaskForm() {
        addNoteCounter.increment();
        return "addtask";
    }

    /**
     * Обработчик POST запроса на создание задачи
     *
     * @param description - описание задачи
     * @return - переход на стартовую страницу
     */
    @PostMapping("/addtask")
    public String addTaskAction(
            @RequestParam String description,
            Model model) {
        ts.saveTask(ts.addTask(description));
        model.addAttribute("tasks", ts.getAllTask());
        return "index";
    }
}
