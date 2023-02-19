package com.kns.tenquest.controller;


import com.kns.tenquest.entity.Question;
import org.springframework.ui.Model;
import com.kns.tenquest.service.QuestionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionServices questionServices;


    @RequestMapping(value = "/view/question", method = RequestMethod.GET)
    public String questionView(Model model){

        List<Question> questionList = questionServices.getAllQuestions();
        model.addAttribute("questionList", questionList);
        return "question_view";

    }
}
