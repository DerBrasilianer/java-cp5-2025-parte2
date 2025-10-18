package fiap.com.br.brinquedos_revisao.controller;

import fiap.com.br.brinquedos_revisao.model.Brinquedo;
import fiap.com.br.brinquedos_revisao.exception.ResourceNotFoundException;
import fiap.com.br.brinquedos_revisao.repository.BrinquedoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/brinquedos")
@RequiredArgsConstructor
public class BrinquedoMvcController {

    private final BrinquedoRepository brinquedoRepository;

    @GetMapping("/web")
    public String listarWeb(Model model) {
        List<Brinquedo> lista = brinquedoRepository.findAll();
        model.addAttribute("brinquedos", lista);
        return "brinquedos/list";
    }

    @GetMapping("/web/new")
    public String novoForm(Model model) {
        model.addAttribute("brinquedo", new Brinquedo());
        model.addAttribute("actionUrl", "/brinquedos/web");
        model.addAttribute("method", "post");
        return "brinquedos/form";
    }

    @PostMapping("/web")
    public String criarWeb(@Valid @ModelAttribute Brinquedo brinquedo, BindingResult br, RedirectAttributes ra, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("actionUrl", "/brinquedos/web");
            return "brinquedos/form";
        }
        brinquedoRepository.save(brinquedo);
        ra.addFlashAttribute("success", "Brinquedo criado com sucesso");
        return "redirect:/brinquedos/web";
    }

    @GetMapping("/web/{id}")
    public String verDetalhes(@PathVariable Long id, Model model) {
        Brinquedo b = brinquedoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brinquedo não encontrado com ID: " + id));
        model.addAttribute("brinquedo", b);
        return "brinquedos/details";
    }

    @GetMapping("/web/{id}/edit")
    public String editarForm(@PathVariable Long id, Model model) {
        Brinquedo b = brinquedoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brinquedo não encontrado com ID: " + id));
        model.addAttribute("brinquedo", b);
        model.addAttribute("actionUrl", "/brinquedos/" + id + "/update");
        return "brinquedos/form";
    }

    @PostMapping("/{id}/update")
    public String atualizarWeb(@PathVariable Long id, @Valid @ModelAttribute Brinquedo brinquedo, BindingResult br, RedirectAttributes ra, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("actionUrl", "/brinquedos/" + id + "/update");
            return "brinquedos/form";
        }

        Brinquedo existente = brinquedoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brinquedo não encontrado com ID: " + id));

        existente.setNome(brinquedo.getNome());
        existente.setPreco(brinquedo.getPreco());

        brinquedoRepository.save(existente);
        ra.addFlashAttribute("success", "Brinquedo atualizado");
        return "redirect:/brinquedos/web";
    }

    @PostMapping("/{id}/delete")
    public String deletarWeb(@PathVariable Long id, RedirectAttributes ra) {
        Brinquedo b = brinquedoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brinquedo não encontrado com ID: " + id));
        brinquedoRepository.delete(b);
        ra.addFlashAttribute("success", "Brinquedo deletado");
        return "redirect:/brinquedos/web";
    }
}
