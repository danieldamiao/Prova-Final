package br.edu.unifio.prova2bim.bean;


import br.edu.unifio.prova2bim.domain.Desenvolvedor;
import br.edu.unifio.prova2bim.domain.Genero;
import br.edu.unifio.prova2bim.domain.Jogo;
import br.edu.unifio.prova2bim.repository.DesenvolvedorRepository;
import br.edu.unifio.prova2bim.repository.GeneroRepository;
import br.edu.unifio.prova2bim.repository.JogoRepository;
import lombok.Data;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.faces.view.ViewScoped;
import java.util.List;

@Data
@Component
@ViewScoped
public class JogoBean {
    private Jogo jogo;
    private List<Jogo> jogos;
    private List<Genero> generos;
    private List<Desenvolvedor> desenvolvedores;

    @Autowired
    JogoRepository jogoRepository;

    @Autowired
    GeneroRepository generoRepository;

    @Autowired
    DesenvolvedorRepository desenvolvedorRepository;


    public void novo(){
        jogo =  new Jogo();
        generos = generoRepository.findAll(Sort.by(Sort.Direction.ASC, "nomeGenero"));
        desenvolvedores = desenvolvedorRepository.findAll(Sort.by(Sort.Direction.ASC, "nomeDev"));
        jogos = jogoRepository.findAll(Sort.by(Sort.Direction.ASC, "tituloJogo"));
    }

    public void Listar(){
        jogos = jogoRepository .findAll(Sort.by(Sort.Direction.ASC, "codigoJogo"));
    }

    public void salvar(){
        try {
            jogoRepository.save(jogo);
            Messages.addGlobalInfo("Sucesso");
        }
        catch (DataIntegrityViolationException exception){
            Messages.addGlobalError("Desenvolvedor Duplicado");
        }
    }
    public void excluir(){
        try {
            jogoRepository.deleteById(jogo.getCodigoJogo());
            Messages.addFlashGlobalInfo("Jogo removido com Sucesso");

            Faces.navigate("jogo-listar.xhtml?faces-redirect=true");
        } catch (DataIntegrityViolationException exception) {
            Messages.addGlobalError("Jogo vinculado com outros registros");
        }
    }
    public void selecionarEdicao(Jogo jogo){
        Faces.setFlashAttribute("jogo", jogo);
        Faces.navigate("jogo-editar.xhtml?faces-redirect=true");
    }

    public void selecionarExclusao(Jogo jogo){
        Faces.setFlashAttribute("jogo", jogo);
        Faces.navigate("jogo-excluir.xhtml?faces-redirect=true");
    }

    public void carregarEdicao(){
        jogo = Faces.getFlashAttribute("jogo");

        if (jogo == null){
            Faces.navigate("jogo-listar.xhtml?faces-redirect=true");
        }
    }
}
