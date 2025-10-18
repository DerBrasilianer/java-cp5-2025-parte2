package fiap.com.br.brinquedos_revisao.controller;

import fiap.com.br.brinquedos_revisao.model.AppUser;
import fiap.com.br.brinquedos_revisao.enums.Role;
import fiap.com.br.brinquedos_revisao.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
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
        System.out.println("=== GET /signup ACESSADO ===");
        model.addAttribute("appUser", new AppUser());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute AppUser appUser, BindingResult br, RedirectAttributes ra) {
        System.out.println("=== POST /signup RECEBIDO ===");
        System.out.println("Username: " + appUser.getUsername());

        // Validações manuais
        if (appUser.getUsername() == null || appUser.getUsername().trim().isEmpty()) {
            br.rejectValue("username", "NotEmpty", "Usuário é obrigatório");
        }
        if (appUser.getPassword() == null || appUser.getPassword().trim().isEmpty()) {
            br.rejectValue("password", "NotEmpty", "Senha é obrigatória");
        }

        if (br.hasErrors()) {
            System.out.println("=== ERROS DE VALIDAÇÃO ===");
            return "signup";
        }

        if (userRepository.existsByUsername(appUser.getUsername())) {
            System.out.println("=== USUÁRIO JÁ EXISTE ===");
            ra.addFlashAttribute("error", "Usuário já existe");
            return "redirect:/signup";
        }

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRole(Role.USER);
        appUser.setEnabled(true);
        userRepository.save(appUser);
        System.out.println("=== USUÁRIO CRIADO COM SUCESSO ===");
        ra.addFlashAttribute("success", "Usuário criado com sucesso. Faça login.");
        return "redirect:/login";
    }

}
