package com.codeup.blogapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

@Controller
public class DiceController {
    @GetMapping("/roll-dice")
    public String homePage(){
        return "roll-dice";
    }

    public long rollDice(){
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    @GetMapping("/roll-dice/{guess}")
    public String guessDiceroll(@PathVariable String guess, Model model){
        long diceRolled = rollDice();
        String guessResult = "";
        if (diceRolled == Long.parseLong(guess)){
            guessResult += "You guessed right";
        } else {
            guessResult += "Try again";
        }

        String guessDisplay = "You guessed: " + guess;
        String diceRolledDisplayed = "Dice roll is: " + diceRolled;

        model.addAttribute("guess", guessDisplay);
        model.addAttribute("diceRolled", diceRolledDisplayed);
        model.addAttribute("guessResult", guessResult);
        return "roll-dice";
    }
}
