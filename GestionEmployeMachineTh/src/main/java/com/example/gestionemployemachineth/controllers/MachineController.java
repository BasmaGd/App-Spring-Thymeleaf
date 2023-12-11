package com.example.gestionemployemachineth.controllers;

import com.example.gestionemployemachineth.entities.Machine;
import com.example.gestionemployemachineth.repositories.MachineRepositry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.Year;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MachineController {

        @Autowired
        private MachineRepositry machineRepository;
        @GetMapping("/machine")
        public String showSignUpForm(Machine machine) {
                return "add-machine";
        }

        @PostMapping("/addmachine")
        public String addMachine(Machine machine, BindingResult result, Model model) {
                if (result.hasErrors()) {
                        return "add-machine";
                }
                machineRepository.save(machine);
                model.addAttribute("machines", machineRepository.findAll());
                return "indexM";
        }


        @GetMapping("/edit/machine/{id}")
        public String showUpdateForm(@PathVariable("id") Long id, Model model) {
                Machine machine = machineRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid machine Id:" + id));
                model.addAttribute("machine", machine);
                return "update-machine";
        }

        @PostMapping("/update/machine/{id}")
        public String updateMachine(@PathVariable("id") Long id, Machine machine, BindingResult result, Model model) {
                if (result.hasErrors()) {
                        machine.setId(id);
                        return "update-machine";
                }

                machineRepository.save(machine);
                model.addAttribute("machines", machineRepository.findAll());
                return "indexM";
        }

        @GetMapping("/delete/machine/{id}")
        public String deleteMachine(@PathVariable("id") Long id, Model model) {
                Machine machine = machineRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Machine Id:" + id));
                machineRepository.delete(machine);
                model.addAttribute("machines", machineRepository.findAll());
                return "indexM";
        }
        
        @GetMapping("/graph")
        public String showGraph(Model model) {
            Iterable<Machine> machines = machineRepository.findAll();
            
         // Extraction des années d'achat et comptage du nombre de machines par année
            Map<Integer, Long> machinesByYear = new HashMap<>();
            machines.forEach(machine -> {
                java.util.Date utilDate = new java.util.Date(machine.getDateAchat().getTime());
                int year = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
                machinesByYear.merge(year, 1L, Long::sum);
            });


            // Création des listes pour les données du graphe
            List<String> years = machinesByYear.keySet().stream().map(String::valueOf).collect(Collectors.toList());
            List<Long> machineCounts = machinesByYear.values().stream().collect(Collectors.toList());

            // Ajout des données au modèle pour l'affichage dans la vue
            model.addAttribute("years", years);
            model.addAttribute("machineCounts", machineCounts);

            return "graphique";
        }
        
}