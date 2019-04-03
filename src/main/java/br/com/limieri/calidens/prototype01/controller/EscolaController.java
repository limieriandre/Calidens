package br.com.limieri.calidens.prototype01.controller;

import br.com.limieri.calidens.prototype01.entity.Escola;
import br.com.limieri.calidens.prototype01.repository.EscolaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/escolas")
public class EscolaController
{
    final EscolaRepo escolaRepo;

    @Autowired
    public EscolaController(EscolaRepo escolaRepo) {
        this.escolaRepo = escolaRepo;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView doView(){
        ModelAndView mv = new ModelAndView("viewEscola");
        mv.addObject("escolas",escolaRepo.findAll());


        return mv;
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ModelAndView doIndexPost(@RequestParam("buscar") String buscar){
        ModelAndView mv = new ModelAndView("viewEscola");

        mv.addObject("escolas",escolaRepo.findAllByNomeContaining(buscar));


        return mv;
    }

    @RequestMapping("/add")
    public ModelAndView doAdd(){
        ModelAndView mv = new ModelAndView("addEscola");


        return mv;
    }

    @RequestMapping("/save")
    public ModelAndView doSave(@Valid @RequestParam("nome") String nome, @RequestParam("endereco") String endereco){
        Escola escola = new Escola();

        escola.setNome(nome);
        escola.setEndereco(endereco);

        escolaRepo.save(escola);

        ModelAndView mv = new ModelAndView("redirect:/escolas/view");


        return mv;
    }

    @RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
    public ModelAndView doDelete(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView("redirect:/escolas/view");
        escolaRepo.deleteById(id);

        return mv;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView doEditEscola(@PathVariable("id") String id){
        Long idint = Long.parseLong(id);
        ModelAndView mv = new ModelAndView("editEscola");
        mv.addObject("escolas",escolaRepo.findById(idint).orElse(null));

        return mv;
    }

    @RequestMapping(value = "/edit")
    public ModelAndView doEdit(@RequestParam("id") Long id, @RequestParam("nome") String nome, @RequestParam("endereco") String endereco){
        ModelAndView mv = new ModelAndView("redirect:/escolas/view");

        Escola escola = escolaRepo.findById(id).orElse(null);

        escola.setNome(nome);
        escola.setEndereco(endereco);

        escolaRepo.save(escola);

        return mv;

    }

}
