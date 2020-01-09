package com.example.tacocloud.web;

import com.example.tacocloud.model.Ingredient;
import com.example.tacocloud.model.Ingredient.Type;
import com.example.tacocloud.model.Order;
import com.example.tacocloud.model.Taco;
import com.example.tacocloud.repository.IngredientRepository;
import com.example.tacocloud.repository.TacoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Controller
@RequestMapping("/design")
@AllArgsConstructor
@SessionAttributes("order")
public class DesignTacoController {

    final IngredientRepository ingredientRepo;
    final TacoRepository tacoRepo;

    @ModelAttribute(binding = false, name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute(value = "ingredients")
    public Map<String, List<Ingredient>> ingredients(Model model) {

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);

        return Arrays.stream(Type.values()).collect(toMap(
                type -> type.toString().toLowerCase(),
                type -> filterByType(ingredients, type)
        ));
    }

    @GetMapping
    public String showDesignForm(Model model) {
        return "newTaco";
    }

    @PostMapping
    public String design(@Valid Taco taco, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "newTaco";
        }
        tacoRepo.save(taco);
        order.addTaco(taco);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(toList());
    }
}