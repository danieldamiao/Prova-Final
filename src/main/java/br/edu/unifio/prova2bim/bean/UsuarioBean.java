package br.edu.unifio.prova2bim.bean;

import br.edu.unifio.prova2bim.domain.Usuario;
import br.edu.unifio.prova2bim.repository.UsuarioRepository;
import lombok.Data;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.faces.view.ViewScoped;
import java.util.List;

@Component
@ViewScoped
@Data

public class UsuarioBean {
    private Usuario usuario;
    private List<Usuario> usuarios;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void novo(){
        usuario = new Usuario();
    }

    public void salvar(){
        try{
            usuarioRepository.save(usuario);
            Messages.addFlashGlobalInfo("Salvo!");
            Faces.navigate("usuario-listagem.xhtml?faces-redirect=true");

        }catch (DataIntegrityViolationException exception){
            Messages.addGlobalError("Usuário já existe");
        }
    }

    public void listar(){
        usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "codigo"));
    }

    public void selectEdicao(Usuario usuario){
        Faces.setFlashAttribute("usuario", usuario);
        Faces.navigate("usuario-edicao.xhtml?faces-redirect=true");
    }

    public void selectExclusao(Usuario usuario){
        Faces.setFlashAttribute("usuario", usuario);
        Faces.navigate("usuario-exclusao.xhtml?faces-redirect=true");
    }

    public void carregar(){
        usuario = Faces.getFlashAttribute("usuario");

        if(usuario == null){
            Faces.navigate("usuario-listagem.xhtml?faces-redirect=true");
        }
    }

    public void excluir(){
        try{
            usuarioRepository.deleteById(usuario.getCodigo());
            Messages.addFlashGlobalInfo("Usuário removido com sucesso!");
            Faces.navigate("usuario-listagem.xhtml?faces-redirect=true");
        }catch (DataIntegrityViolationException exception){
            Messages.addGlobalError("Usuário não pôde ser removido");
        }
    }
}
