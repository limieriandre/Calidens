package br.com.limieri.calidens.prototype01.controller;

import br.com.limieri.calidens.prototype01.entity.Escola;
import br.com.limieri.calidens.prototype01.entity.Paciente;
import br.com.limieri.calidens.prototype01.repository.EscolaRepo;
import br.com.limieri.calidens.prototype01.repository.PacienteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.time.LocalDate;


@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    public long id_escola;

    final PacienteRepo pacienteRepo;

    final EscolaRepo escolarepo;

    @Autowired
    public PacienteController(PacienteRepo pacienteRepo, EscolaRepo escolarepo) {
        this.pacienteRepo = pacienteRepo;
        this.escolarepo = escolarepo;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView doIndex(){
        ModelAndView mv = new ModelAndView("viewPaciente_Escola");
        mv.addObject("pacientes",pacienteRepo.findAllByOrderByNomeAsc());
        mv.addObject("escolas",escolarepo.findAllByOrderByNomeAsc());


        return mv;
    }

//    @RequestMapping(value = "/view", method = RequestMethod.GET)
//    public String doIndex(Model model, @RequestParam(defaultValue = "0") int page){
//        model.addAttribute("data", pacienteRepo.findAll( PageRequest.of(page,10)));
//
//        return "view";
//    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ModelAndView doIndexPost(@RequestParam("buscar") String buscar){
        ModelAndView mv = new ModelAndView("viewPaciente");

        if(id_escola == 0){
            mv.addObject("pacientes",pacienteRepo.findAllByNomeContaining(buscar));
        }
        else{
            mv.addObject("pacientes",pacienteRepo.findAllByEscola_IdAndNomeContaining(id_escola,buscar));
        }


        return mv;
    }

    @RequestMapping(value = "/pacientes_view", method = RequestMethod.POST)
    public ModelAndView doEscolaPacientes(@RequestParam("idescola") Long idEscola){
        ModelAndView mv = new ModelAndView("viewPaciente");

        id_escola = idEscola;
        if(idEscola == 0){
            mv.addObject("pacientes",pacienteRepo.findAllByOrderByNomeAsc());
        }
        else {
            mv.addObject("pacientes",pacienteRepo.findAllByEscola_IdOrderByNomeAsc(idEscola));
        }

        return mv;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView doSave(@RequestParam("nome") String nome,
                               @RequestParam("dtnascimento") String dtnascimento,
                               @RequestParam("idescola") Long idEscola,
                               @RequestParam("sexo") char sexo,
                               @RequestParam("etnia") String etnia) {
        ModelAndView mv = new ModelAndView("redirect:/pacientes/view");

        Paciente paciente = new Paciente();

        Date data = Date.valueOf(dtnascimento);

        LocalDate date = LocalDate.parse(dtnascimento);

        Escola escola = escolarepo.findById(idEscola).orElse(null);

        // Criando novo paciente
        paciente.setNome(nome);
        paciente.setEscola(escola);
        paciente.setDtNascimento(date);
        paciente.setSexo(sexo);
        paciente.setEtnia(etnia);

        pacienteRepo.save(paciente);
        return mv;
    }

    @RequestMapping("/add")
    public ModelAndView doAdd(){
        ModelAndView mv = new ModelAndView("addPaciente");
        mv.addObject("escolas",escolarepo.findAll());


        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView doViewEscola(@PathVariable("id") String id){
        Long idint = Long.parseLong(id);
        ModelAndView mv = new ModelAndView("consultaPaciente");
        mv.addObject("paciente",pacienteRepo.findById(idint).orElse(null));

        return mv;
    }

    @GetMapping(path="/delete/{id}")
    public ModelAndView doDelete(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView("redirect:/pacientes/view");
        pacienteRepo.deleteById(id);

        return mv;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView doViewPaciente(@PathVariable("id") String id){
        Long idint = Long.parseLong(id);
        ModelAndView mv = new ModelAndView("editPaciente");

        Paciente paciente = pacienteRepo.findById(idint).orElse(null);


        boolean masculino = false;
        boolean feminino  = false;

        if(paciente.getSexo() == 'M'){
            masculino = true;
        }else{
            feminino = true;
        }


        mv.addObject("paciente",pacienteRepo   .findById(idint).orElse(null));
        mv.addObject("escolas",escolarepo.findAll());
        mv.addObject("escola",paciente.getEscola());
        mv.addObject("dtNascimento",paciente.getDtNascimento().toString());
        mv.addObject("sexo",paciente.getSexo());
        mv.addObject("etnia",paciente.getEtnia());
        mv.addObject("masculino",masculino);
        mv.addObject("feminino",feminino);


        return mv;
    }

    @RequestMapping(value = "/edit")
    public ModelAndView doEdit(@RequestParam("id") Long id,
                               @RequestParam("nome") String nome,
                               @RequestParam("dtnascimento") String dtnascimento,
                               @RequestParam("idescola") Long idEscola,
                               @RequestParam("sexo") char sexo,
                               @RequestParam("etnia") String etnia){
        ModelAndView mv = new ModelAndView("redirect:/pacientes/view");

        Paciente paciente = pacienteRepo.findById(id).orElse(null);
        Escola escola = escolarepo.findById(idEscola).orElse(null);


        LocalDate data = LocalDate.parse(dtnascimento);

        paciente.setNome(nome);
        paciente.setDtNascimento(data);
        paciente.setEtnia(etnia);
        paciente.setSexo(sexo);
        paciente.setEscola(escola);

        pacienteRepo.save(paciente);
        return mv;

    }
}
