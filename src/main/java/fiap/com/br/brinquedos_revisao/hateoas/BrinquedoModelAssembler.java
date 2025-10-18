package fiap.com.br.brinquedos_revisao.hateoas;

import fiap.com.br.brinquedos_revisao.controller.BrinquedoController;
import fiap.com.br.brinquedos_revisao.model.Brinquedo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BrinquedoModelAssembler implements RepresentationModelAssembler<Brinquedo, EntityModel<Brinquedo>> {

    @Override
    public EntityModel<Brinquedo> toModel(Brinquedo entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(BrinquedoController.class).buscarPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(BrinquedoController.class).listarTodos()).withRel("brinquedos"),
                linkTo(methodOn(BrinquedoController.class).atualizar(entity.getId(), entity)).withRel("update"),
                linkTo(methodOn(BrinquedoController.class).atualizarParcial(entity.getId(), entity)).withRel("patch"),
                linkTo(methodOn(BrinquedoController.class).deletar(entity.getId())).withRel("delete")
        );
    }
}
