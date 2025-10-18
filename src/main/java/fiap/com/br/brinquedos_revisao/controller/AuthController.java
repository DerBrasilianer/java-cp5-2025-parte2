package fiap.com.br.brinquedos_revisao.controller;

import fiap.com.br.brinquedos_revisao.model.AppUser;
import fiap.com.br.brinquedos_revisao.enums.Role;
import fiap.com.br.brinquedos_revisao.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("appUser", new AppUser());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam Role role,
                         RedirectAttributes ra) {

        System.out.println("=== POST /signup RECEBIDO ===");
        System.out.println("Username: " + username);
        System.out.println("Role: " + role);

        if (userRepository.existsByUsername(username)) {
            System.out.println("=== USUÁRIO JÁ EXISTE ===");
            ra.addFlashAttribute("error", "Usuário já existe");
            return "redirect:/signup";
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setRole(role);
        appUser.setEnabled(true);

        userRepository.save(appUser);
        System.out.println("=== USUÁRIO CRIADO COM SUCESSO ===");
        ra.addFlashAttribute("success", "Usuário criado com sucesso. Faça login.");
        return "redirect:/login";
    }
}