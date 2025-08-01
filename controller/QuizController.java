package com.example.placementprep.controller;

import com.example.placementprep.model.Quiz;
import com.example.placementprep.model.QuizResult;
import com.example.placementprep.model.Topic;
import com.example.placementprep.model.User;
import com.example.placementprep.repository.QuizRepository;
import com.example.placementprep.repository.QuizResultRepository;
import com.example.placementprep.repository.TopicRepository;
import com.example.placementprep.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizResultRepository quizResultRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    // Show quiz questions for a topic
    @GetMapping("/quiz/{topicId}")
    public String showQuiz(@PathVariable Long topicId, Model model) {
        List<Quiz> questions = quizRepository.findByTopicId(topicId);

        if (questions.isEmpty()) {
            model.addAttribute("error", "No questions available for this topic.");
            return "student/quiz";
        }

        model.addAttribute("questions", questions);
        model.addAttribute("topicId", topicId);
        return "student/quiz";
    }

    // Submit quiz and calculate score
    @PostMapping("/quiz/{topicId}/submit")
    public String submitQuiz(@PathVariable Long topicId,
                             @RequestParam MultiValueMap<String, String> formData,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model) {

        List<Quiz> questions = quizRepository.findByTopicId(topicId);
        int score = 0;

        for (Quiz question : questions) {
            String userAnswer = formData.getFirst("q" + question.getId());
            if (userAnswer != null && userAnswer.equals(question.getCorrectOption())) {
                score++;
            }
        }

        // Find user
        User user = userRepository.findByEmail(userDetails.getUsername());
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "student/quiz-result";
        }

        // Find topic
        Optional<Topic> topicOpt = topicRepository.findById(topicId);
        if (!topicOpt.isPresent()) {
            model.addAttribute("error", "Topic not found.");
            return "student/quiz-result";
        }

        // Save result
        QuizResult result = new QuizResult();
        result.setUser(user);
        result.setTopic(topicOpt.get());
        result.setScore(score);
        result.setTotalQuestions(questions.size());
        quizResultRepository.save(result);

        model.addAttribute("score", score);
        model.addAttribute("total", questions.size());
        model.addAttribute("result", result);
        return "student/quiz-result";
    }
}
