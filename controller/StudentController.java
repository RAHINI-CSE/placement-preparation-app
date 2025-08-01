package com.example.placementprep.controller;

import com.example.placementprep.model.Topic;
import com.example.placementprep.model.User;
import com.example.placementprep.model.QuizResult;
import com.example.placementprep.model.Progress;
import com.example.placementprep.repository.TopicRepository;
import com.example.placementprep.repository.UserRepository;
import com.example.placementprep.repository.QuizResultRepository;
import com.example.placementprep.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuizResultRepository quizResultRepository;

    @Autowired
    private ProgressRepository progressRepository;
    
    @Autowired 
    private PasswordEncoder passwordEncoder;

    // 1. Dashboard
    @GetMapping("/dashboard")
    public String studentDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User student = userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("student", student);
        return "student/dashboard";
    }

    // 2. Learning Path
    @GetMapping("/learning-path")
    public String learningPath(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User student = userRepository.findByEmail(userDetails.getUsername());
        List<Topic> topics = topicRepository.findByArea(student.getInterestArea());
        model.addAttribute("topics", topics);
        model.addAttribute("interest", student.getInterestArea());
        return "student/learning-path";
    }

    // 3. Quizzes 
    @GetMapping("/quizzes")
    public String quizzes(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User student = userRepository.findByEmail(userDetails.getUsername());

        if (student == null || student.getInterestArea() == null) {
            model.addAttribute("error", "User or interest area not found.");
            return "error"; // Show a generic error page
        }

        List<Topic> topics = topicRepository.findByArea(student.getInterestArea());
        model.addAttribute("topics", topics);
        return "student/quizzes"; // ✅ FIXED: new file to display available topics
    }


    // 4. Progress
    @GetMapping("/progress")
    public String progress(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User student = userRepository.findByEmail(userDetails.getUsername());
        List<QuizResult> results = quizResultRepository.findByUserId(student.getId());
        List<Progress> progresses = progressRepository.findByUserId(student.getId());

        model.addAttribute("results", results);
        model.addAttribute("progresses", progresses);
        return "student/progress";
    }

    // 5. Resources
    @GetMapping("/resources")
    public String resources(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User student = userRepository.findByEmail(userDetails.getUsername());
        List<Topic> topics = topicRepository.findByArea(student.getInterestArea());
        model.addAttribute("topics", topics);
        return "student/resources";
    }

    // 6. Placement Drives 
    @GetMapping("/drives")
    public String drives(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // You can later fetch drives from a table or static list
        return "student/drives"; // Create this HTML page
    }

    // 7. Profile 
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User student = userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("student", student);
        return "student/profile"; // Create this HTML page
    }
    
 // Update profile
    @PostMapping("/profile/update")
    public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                @ModelAttribute("student") User updatedUser,
                                @RequestParam(required = false) String password) {
        User student = userRepository.findByEmail(userDetails.getUsername());
        
        student.setName(updatedUser.getName());
        student.setInterestArea(updatedUser.getInterestArea());

        if (password != null && !password.isEmpty()) {
            student.setPassword(passwordEncoder.encode(password)); // encode only if updated
        }

        userRepository.save(student);
        return "redirect:/student/profile?success";
    }

}
