package fiap.com.br.brinquedos_revisao.controller;

import fiap.com.br.brinquedos_revisao.model.Brinquedo;
import fiap.com.br.brinquedos_revisao.exception.ResourceNotFoundException;
import fiap.com.br.brinquedos_revisao.hateoas.BrinquedoModelAssembler;
import fiap.com.br.brinquedos_revisao.repository.BrinquedoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/brinquedos")
@RequiredArgsConstructor
public class BrinquedoController {

    private final BrinquedoRepository brinquedoRepository;
    private final BrinquedoModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<Brinquedo>> listarTodos() {
        List<EntityModel<Brinquedo>> content = brinquedoRepository.findAll()
                .stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(content,
                linkTo(methodOn(BrinquedoController.class).listarTodos()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Brinquedo> buscarPorId(@PathVariable Long id) {
        Brinquedo brinquedo = brinquedoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brinquedo não encontrado com ID: " + id));
        return assembler.toModel(brinquedo);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Brinquedo>> criar(@RequestBody @Valid Brinquedo brinquedo) {
        Brinquedo salvo = brinquedoRepository.save(brinquedo);
        EntityModel<Brinquedo> model = assembler.toModel(salvo);
        URI location = linkTo(methodOn(BrinquedoController.class).buscarPorId(salvo.getId())).toUri();
        return ResponseEntity.created(location).body(model);
    }

    @PutMapping("/{id}")
    public EntityModel<Brinquedo> atualizar(@PathVariable Long id, @RequestBody @Valid Brinquedo brinquedo) {
        Brinquedo existente = brinquedoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brinquedo não encontrado com ID: " + id));

        existente.setNome(brinquedo.getNome());
        existente.setPreco(brinquedo.getPreco());

        Brinquedo atualizado = brinquedoRepository.save(existente);
        return assembler.toModel(atualizado);
    }

    @PatchMapping("/{id}")
    public EntityModel<Brinquedo> atualizarParcial(@PathVariable Long id, @RequestBody Brinquedo patch) {
        Brinquedo existente = brinquedoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brinquedo não encontrado com ID: " + id));

        if (patch.getNome() != null) existente.setNome(patch.getNome());
        if (patch.getPreco() != null) existente.setPreco(patch.getPreco());

        Brinquedo atualizado = brinquedoRepository.save(existente);
        return assembler.toModel(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Brinquedo brinquedo = brinquedoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brinquedo não encontrado com ID: " + id));
        brinquedoRepository.delete(brinquedo);
        return ResponseEntity.noContent().build();
    }
}
