package com.kns.tenquest.controller;


import org.springframework.ui.Model;
import com.kns.tenquest.entity.Category;
import com.kns.tenquest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    //카테고리 확인하기 : GET  : 데이터 가져와서 뿌려주기
    @RequestMapping(value="/view/category",method= RequestMethod.GET)
    public String categoryView(Model model){
        // Temporarily implemented. Just for test.
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        return "category_view";
    }

    //카테고리 추가하기 : POST  : 데이터 추가 : 개발자만 가능

    //카테고리 수정하기 : UPDATE : 데이터 수정: 개발자만 가능

    //카테고리 삭제하기 : DELETE : 데이터 삭제: 개발자만 가능

}
