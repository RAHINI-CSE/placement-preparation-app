package com.example.placementprep.controller;

import com.example.placementprep.model.Topic;
import com.example.placementprep.model.Quiz;
import com.example.placementprep.model.User;
import com.example.placementprep.model.QuizResult;
import com.example.placementprep.repository.TopicRepository;
import com.example.placementprep.repository.QuizRepository;
import com.example.placementprep.repository.UserRepository;
import com.example.placementprep.repository.QuizResultRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizResultRepository quizResultRepository;

    // 1. Admin Dashboard
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    // 2. Manage Topics
    @GetMapping("/topics")
    public String viewTopics(Model model) {
        model.addAttribute("topics", topicRepository.findAll());
        return "admin/manage-topics";
    }

    @GetMapping("/topics/new")
    public String showTopicForm(Model model) {
        model.addAttribute("topic", new Topic());
        return "admin/topic-form";
    }

    @PostMapping("/topics")
    public String saveTopic(@ModelAttribute Topic topic) {
        topicRepository.save(topic);
        return "redirect:/admin/topics";
    }

    @GetMapping("/topics/delete/{id}")
    public String deleteTopic(@PathVariable Long id) {
        topicRepository.deleteById(id);
        return "redirect:/admin/topics";
    }

    // 3. Manage Quizzes
    @GetMapping("/quizzes")
    public String viewQuizzes(Model model) {
        model.addAttribute("quizzes", quizRepository.findAll());
        return "admin/manage-quizzes";
    }

    @GetMapping("/quizzes/new")
    public String showQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("topics", topicRepository.findAll());
        return "admin/quiz-form";
    }

    @PostMapping("/quizzes")
    public String saveQuiz(@ModelAttribute Quiz quiz) {
        quizRepository.save(quiz);
        return "redirect:/admin/quizzes";
    }

    @GetMapping("/quizzes/delete/{id}")
    public String deleteQuiz(@PathVariable Long id) {
        quizRepository.deleteById(id);
        return "redirect:/admin/quizzes";
    }

    // 4. Manage Students
    @GetMapping("/students")
    public String manageStudents(Model model) {
        List<User> students = userRepository.findByRole("ROLE_STUDENT");
        model.addAttribute("students", students);
        return "admin/students";
    }

    // 5. View Progress
    @GetMapping("/progress")
    public String viewProgress(Model model) {
        List<QuizResult> results = quizResultRepository.findAll();
        model.addAttribute("results", results);
        return "admin/progress";
    }

    // 6. Upload Resources (Dummy for now)
    @GetMapping("/resources")
    public String showResourcesPage() {
        return "admin/resources";
    }

    @PostMapping("/resources/upload")
    public String uploadResource(@RequestParam String title, @RequestParam String link, @RequestParam String category) {
        // Save logic here if Resource model and repository are added
        return "redirect:/admin/resources";
    }
    
    @GetMapping("/students/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("student", user);
        return "admin/edit-student";
    }

    @PostMapping("/students/update")
    public String updateStudent(@ModelAttribute User student) {
        userRepository.save(student);
        return "redirect:/admin/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/students";
    }

}
