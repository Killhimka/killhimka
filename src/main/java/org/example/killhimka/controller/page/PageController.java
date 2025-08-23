package org.example.killhimka.controller.page;

import lombok.RequiredArgsConstructor;
import org.example.killhimka.dto.employee.EmployeeDto;
import org.example.killhimka.service.employee.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final EmployeeService employeeService; // Добавь поле

    // ... (другие методы, как были)

    // Страница со списком сотрудников - теперь доступна по /employees
    @GetMapping("/employees")
    public String showEmployeeList(Model model) { // Добавь Model model
        // Получаем список сотрудников из сервиса
        List<EmployeeDto> employees = employeeService.getAllEmployees(); // Предполагается, что есть такой метод
        // Добавляем список в модель, чтобы Thymeleaf мог его использовать
        model.addAttribute("employees", employees);
        return "list"; // templates/list.html
    }

    // Главная страница сайта (до входа) - вход и регистрация
    @GetMapping("/")
    public String showHomePage() {
        return "index"; // templates/index.html
    }

    // Страница регистрации
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // templates/register.html
    }

    // Страница входа
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // templates/login.html
    }

    // Главная страница после входа (Dashboard)
    // Принимает имя пользователя из параметра запроса (имитация успешного входа)
    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam(name = "username", required = false, defaultValue = "Guest") String username, Model model) {
        model.addAttribute("username", username);
        return "dashboard"; // templates/dashboard.html
    }

    /*// Страница со списком сотрудников
    @GetMapping("/employees")
    public String showEmployeeList() {
        return "list"; // templates/list.html
    }*/

    // Страница добавления сотрудника
    @GetMapping("/employees/add-form")
    public String showAddEmployeeForm() {
        return "add-employee"; // templates/add-employee.html
    }

    @GetMapping("/employees/edit-form/{id}") // <-- ИЗМЕНИЛ URL
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        model.addAttribute("employeeId", id);
        return "edit-employee"; // templates/edit-employee.html
    }
}