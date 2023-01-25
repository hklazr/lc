package com.project.lovecalculator.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.lovecalculator.model.Person;
import com.project.lovecalculator.service.LoveService;

import jakarta.validation.Valid;




// @Controller
// public class LoveController {

    // @Autowired
    // Person person;

    // public String index() {
    //     return "index";
    // }
    // @Autowired
    // LoveService loveService;

    // @GetMapping (path="/form")
    // public String form(Model model){
        
    //     model.addAttribute("person", new Person());
    //     return "form";
    // }

    //  //after user keys in details in /form and save, form.html will check for errors
    //  @PostMapping("/person")
    //  public String checkPersonInfo(@Valid Person person, BindingResult bindingResult, Model model) {
        
    //     // System.out.println(bindingResult);
    //     // System.out.println("bindingResult.hasError() ? " + bindingResult.hasErrors());

    //     // bindingResult.addError(new FieldError("test", "test", "test"));
    //      if (bindingResult.hasErrors()) {
    //         //  System.out.println("has error");
    //          model.addAttribute("person", person);
    //          return "form";
    //      } else {
    //         // System.out.println("no error"); 
    //         model.addAttribute("person", person);
    //          return "result";
    //      }
    //  }
    // }

@Controller
public class LoveController {
    
    @Autowired
    LoveService loveService;

    //from index, once press lets go, will return to form
    @GetMapping(path="/form")
    public String form(Model model){
        model.addAttribute("person", new Person());
        return "form";
    }

    // from form it goes into here to be calculated then it goes to person to generate through the GS
    @PostMapping("/result")
    public String calcCompatibility(@ModelAttribute Person person, Model model) throws IOException {
        // Person p = new Person();
        System.out.println("one");
        System.out.println(person.getFname());
        System.out.println(person.getSname());
        Optional<Person> r = this.loveService.calcCompatibility(person.getFname(), person.getSname());
        //set person
        System.out.println("two");
        model.addAttribute("result", r.get());
        return "result"; // info is passed to result
    }

//this compiles all the compatibility and puts into a list in list html from redis db
    @GetMapping(path = "/list")
    public String getAllRecords(Model model) throws IOException {
        Person[] mArr = loveService.getAllMatchMaking();
        model.addAttribute("arr", mArr);
        return "list";
}
}

    
