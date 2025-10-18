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
        model.addAttribute("appUser", new AppUser());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid AppUser appUser, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            return "signup";
        }
        if (userRepository.existsByUsername(appUser.getUsername())) {
            ra.addFlashAttribute("error", "Usuário já existe");
            return "redirect:/signup";
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRole(Role.USER);
        appUser.setEnabled(true);
        userRepository.save(appUser);
        ra.addFlashAttribute("success", "Usuário criado com sucesso. Faça login.");
        return "redirect:/login";
    }
}
