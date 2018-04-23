package com.afua.lostandfounddemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MainController {
    @Autowired
    PetRepository petRepo;

    @RequestMapping("/")
    public String listItems(Model model)
    {
        model.addAttribute("petList",petRepo.findAll());
        return "index";
    }

    @RequestMapping("/add")
    public String addPet(Model model)
    {
        model.addAttribute("aPet",new LostPet());
        return "addpet";
    }

    @RequestMapping("/update")
    public String addPet(HttpServletRequest request, Model model)
    {

        long petID = new Long(request.getParameter("id"));
        model.addAttribute("aPet",petRepo.findById(petID).get());
        return "addpet";
    }

    @RequestMapping("/save")
    public String savingPet(@Valid @ModelAttribute("aPet") LostPet thePet, BindingResult result)
    {

        if(result.hasErrors())
        {
            return "addpet";
        }
        petRepo.save(thePet);
        System.out.println("Saving pet");
        return "redirect:/";
    }

    @RequestMapping("/showlost")
    public String showLostPets(Model model)
    {

        model.addAttribute("petList",petRepo.findAllByLost(true));
        return "index";
    }

    @PostMapping("/toggle")
    public String togglePetStatus(HttpServletRequest request)
    {
        long petID = new Long(request.getParameter("id"));
        LostPet thisPet = petRepo.findById(petID).get();
        thisPet.setLost(!thisPet.isLost());
        petRepo.save(thisPet);

        return "redirect:/";
    }

}
