package br.com.limieri.calidens.prototype01.controller;

import br.com.limieri.calidens.prototype01.entity.Consulta;
import br.com.limieri.calidens.prototype01.entity.Paciente;
import br.com.limieri.calidens.prototype01.repository.ConsultaRepo;
import br.com.limieri.calidens.prototype01.repository.PacienteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    final PacienteRepo pacienteRepo;

    final ConsultaRepo consultaRepo;

    @Autowired
    public ConsultaController(PacienteRepo pacienteRepo, ConsultaRepo consultaRepo) {
        this.pacienteRepo = pacienteRepo;
        this.consultaRepo = consultaRepo;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView doIndex(){
        ModelAndView mv = new ModelAndView("viewConsulta");
        mv.addObject("consultas",consultaRepo.findAllByOrderByDataDesc());


        return mv;
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ModelAndView doIndexPost(@RequestParam("buscar") String buscar){
        ModelAndView mv = new ModelAndView("viewConsulta");

        //mv.addObject("consultas",consultaRepo.findAllByNomePacienteContaining(buscar));
        mv.addObject("consultas",consultaRepo.findAllByPacienteNomeContaining(buscar));

        return mv;
    }

    @GetMapping(path="/delete/{id}")
    public ModelAndView doDelete(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView("redirect:/consultas/view");
        consultaRepo.deleteById(id);

        return mv;
    }

    @RequestMapping("/add")
    public ModelAndView doAdd(){
        ModelAndView mv = new ModelAndView("addConsulta");
        mv.addObject("pacientes",pacienteRepo.findAllByOrderByNomeAsc());


        return mv;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView doSave(@RequestParam("data") String dataConsulta,
                               @RequestParam("carie") String carie,
                               @RequestParam("restauracao") String restauracao,
                               @RequestParam("idpaciente") Long idPaciente){
        ModelAndView mv = new ModelAndView("redirect:/consultas/view");

        Consulta consulta = new Consulta();

        Date data = Date.valueOf(dataConsulta);

        //LocalDate date = LocalDate.parse(dtnascimento);

        boolean caries = Boolean.parseBoolean(carie);
        boolean restauracoes = Boolean.parseBoolean(restauracao);

        Paciente paciente = pacienteRepo.findById(idPaciente).orElse(null);

        // Criando novo paciente
        consulta.setCarie(caries);
        consulta.setRestauracao(restauracoes);
        consulta.setData(data);
        consulta.setPaciente(paciente);

        consultaRepo.save(consulta);
        return mv;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView doViewPaciente(@PathVariable("id") String id){
        Long idint = Long.parseLong(id);
        ModelAndView mv = new ModelAndView("editConsulta");

        Consulta consulta = consultaRepo.findById(idint).orElse(null);

        String datax = consulta.getData().toString();
        String data  = datax.substring(0,10);

        boolean caries = false;
        boolean restauracoes  = false;

        if(consulta.getCarie()){
            caries = true;
        }

        if(consulta.getRestauracao()){
            restauracoes = true;
        }

        mv.addObject("consulta",consulta);
        mv.addObject("pacientes",pacienteRepo.findAll());
        mv.addObject("paciente",consulta.getPaciente());
        mv.addObject("data",data);
        mv.addObject("caries",caries);
        mv.addObject("restauracoes",restauracoes);

        return mv;
    }

    @RequestMapping(value = "/edit")
    public ModelAndView doEdit(@RequestParam("id") Long id,
                               @RequestParam("data") String data,
                               @RequestParam("idpaciente") Long idPaciente,
                               @RequestParam("carie") String carie,
                               @RequestParam("restauracao") String restauracao){
        ModelAndView mv = new ModelAndView("redirect:/consultas/view");

        Consulta consulta = consultaRepo.findById(id).orElse(null);
        Paciente paciente = pacienteRepo.findById(idPaciente).orElse(null);


        boolean caries = Boolean.parseBoolean(carie);
        boolean restauracoes = Boolean.parseBoolean(restauracao);

        //LocalDate datax = LocalDate.parse(data);
        Date datax = Date.valueOf(data);

        consulta.setPaciente(paciente);
        consulta.setData(datax);
        consulta.setRestauracao(restauracoes);
        consulta.setCarie(caries);


        consultaRepo.save(consulta);
        return mv;

    }
}
