package br.com.limieri.calidens.prototype01.controller;

import br.com.limieri.calidens.prototype01.entity.Consulta;
import br.com.limieri.calidens.prototype01.entity.Paciente;
import br.com.limieri.calidens.prototype01.repository.ConsultaRepo;
import br.com.limieri.calidens.prototype01.repository.EscolaRepo;
import br.com.limieri.calidens.prototype01.repository.PacienteRepo;
import br.com.limieri.calidens.prototype01.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class GraficoController {

    final UsuarioRepo usuarioRepo;
    final PacienteRepo pacienterepo;
    final ConsultaRepo consultaRepo;
    final EscolaRepo   escolaRepo;

    @Autowired
    public GraficoController(UsuarioRepo usuarioRepo, PacienteRepo pacienterepo, ConsultaRepo consultaRepo, EscolaRepo escolaRepo) {
        this.usuarioRepo = usuarioRepo;
        this.pacienterepo = pacienterepo;
        this.consultaRepo = consultaRepo;
        this.escolaRepo   = escolaRepo;
    }

    @RequestMapping("/graficos")
    private ModelAndView graficos(){
        ModelAndView mv = new ModelAndView("graficos");

        mv.addObject("escolas",escolaRepo.findAll());

        return mv;
    }

    //Cáries x Total
    @RequestMapping("/graph1")
    public ModelAndView doGraph1(){
        ModelAndView mv = new ModelAndView("graph1");

        List<Consulta> comCaries = consultaRepo.findAllByCarieTrue();
        List<Consulta> semCaries = consultaRepo.findAllByCarieFalse();
        List<Consulta> total     = consultaRepo.findAll();

        mv.addObject("total",total.size());
        mv.addObject("comCaries",comCaries.size());
        mv.addObject("semCaries",semCaries.size());

        return mv;
    }

    //Homens x Mulheres
    @RequestMapping("/graph2")
    public ModelAndView doGraph2(){
        ModelAndView mv = new ModelAndView("graph2");

        List<Paciente> pacientesF = pacienterepo.findAllBySexoF();
        List<Paciente> pacientesM = pacienterepo.findAllBySexoM();


        System.out.println("Mulheres: " + pacientesF.size());
        System.out.println("Homens: " + pacientesM.size());

        mv.addObject("homens",pacientesM.size());
        mv.addObject("mulheres",pacientesF.size());

        return mv;
    }

    // Grupos Etnicos
    @RequestMapping("/graph3")
    public ModelAndView doGraph3(){
        ModelAndView mv = new ModelAndView("graph3");

        List<Paciente> pacientesBrancos   = pacienterepo.findAllByEtniaLike("Branco");
        List<Paciente> pacientesNegros    = pacienterepo.findAllByEtniaLike("Negro");
        List<Paciente> pacientesPardos    = pacienterepo.findAllByEtniaLike("Pardo");
        List<Paciente> pacientesIndigenas = pacienterepo.findAllByEtniaLike("Indígena");
        List<Paciente> pacientesAmarelos  = pacienterepo.findAllByEtniaLike("Amarelo");


        mv.addObject("brancos",pacientesBrancos.size());
        mv.addObject("negros",pacientesNegros.size());
        mv.addObject("pardos",pacientesPardos.size());
        mv.addObject("indigenas",pacientesIndigenas.size());
        mv.addObject("amarelos",pacientesAmarelos.size());


        return mv;
    }

    // Restaurações x Total
    @RequestMapping("/graph4")
    public ModelAndView doGraph4(){
        ModelAndView mv = new ModelAndView("graph4");

        List<Consulta> comRest = consultaRepo.findAllByRestauracaoTrue();
        List<Consulta> semRest = consultaRepo.findAllByRestauracaoFalse();
        List<Consulta> total     = consultaRepo.findAll();

        mv.addObject("total",total.size());
        mv.addObject("comRest",comRest.size());
        mv.addObject("semRest",semRest.size());

        return mv;
    }

    @RequestMapping("/graph")
    public ModelAndView doGraph (@RequestParam("grafico") String grafico){

        ModelAndView mv = new ModelAndView("graphEtnico");
        System.out.println(grafico);

        List<Consulta> pacienteComCarie = null;
        List<Consulta> pacienteTotal = null;

        switch (grafico){
            case "1":
                System.out.println("Brancos");
                pacienteComCarie = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Branco");
                pacienteTotal    = consultaRepo.findAllByPacienteEtniaContaining("Branco");
                break;
            case "2":
                System.out.println("Negros");
                pacienteComCarie = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Negro");
                pacienteTotal    = consultaRepo.findAllByPacienteEtniaContaining("Negro");
                break;
            case "3":
                System.out.println("Pardos");
                pacienteComCarie = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Pardo");
                pacienteTotal    = consultaRepo.findAllByPacienteEtniaContaining("Pardo");
                break;
            case "4":
                System.out.println("Indígenas");
                pacienteComCarie = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Indígena");
                pacienteTotal    = consultaRepo.findAllByPacienteEtniaContaining("Indígena");
                break;
            case "5":
                System.out.println("Amarelos");
                pacienteComCarie = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Amarelo");
                pacienteTotal    = consultaRepo.findAllByPacienteEtniaContaining("Amarelo");
                break;

        }

        int semCaries = pacienteTotal.size() - pacienteComCarie.size();

        mv.addObject("total",pacienteTotal.size());
        mv.addObject("comCaries",pacienteComCarie.size());
        mv.addObject("semCaries",semCaries);

        return mv;
    }

    //Comparativo
    @RequestMapping("/graph5")
    public ModelAndView doGraphComparativo (){

        ModelAndView mv = new ModelAndView("graphEtnicoComparativo");

        List<Consulta> brancosComCarie = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Branco");
        List<Consulta> brancosTotal     = consultaRepo.findAllByPacienteEtniaContaining("Branco");

        List<Consulta> negrosComCarie = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Negro");
        List<Consulta> negrosTotal    = consultaRepo.findAllByPacienteEtniaContaining("Negro");

        List<Consulta> pardosComCarie = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Pardo");
        List<Consulta> pardosTotal    = consultaRepo.findAllByPacienteEtniaContaining("Pardo");

        List<Consulta> indigenasComCarie = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Indígena");
        List<Consulta> indigenasTotal    = consultaRepo.findAllByPacienteEtniaContaining("Indígena");

        List<Consulta> amarelosComCarie = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Amarelo");
        List<Consulta> amarelosTotal    = consultaRepo.findAllByPacienteEtniaContaining("Amarelo");

        mv.addObject("brancosTotal",brancosTotal.size());
        mv.addObject("brancosComCarie",brancosComCarie.size());

        mv.addObject("negrosTotal",negrosTotal.size());
        mv.addObject("negrosComCarie",negrosComCarie.size());

        mv.addObject("pardosTotal",pardosTotal.size());
        mv.addObject("pardosComCarie",pardosComCarie.size());

        mv.addObject("indigenasTotal",indigenasTotal.size());
        mv.addObject("indigenasComCarie",indigenasComCarie.size());

        mv.addObject("amarelosTotal",amarelosTotal.size());
        mv.addObject("amarelosComCarie",amarelosComCarie.size());

        return mv;
    }

    @RequestMapping("/graphEscola")
    public ModelAndView doGraphEscola (@RequestParam("idescola") String idescola){

        ModelAndView mv = new ModelAndView("graphEscola");
        System.out.println(idescola);

        return mv;
    }

//    @RequestMapping("/graphBrancos")
//    public ModelAndView doGraphBranco(){
//        ModelAndView mv = new ModelAndView("graphEtnico");
//
//        List<Consulta> pacientesBrancosComCaries = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Branco");
//        List<Consulta> pacientesBrancos          = consultaRepo.findAllByPacienteEtniaContaining("Branco");
//
//        int semCaries = pacientesBrancos.size() - pacientesBrancosComCaries.size();
//
//        mv.addObject("total",pacientesBrancos.size());
//        mv.addObject("comCaries",pacientesBrancosComCaries.size());
//        mv.addObject("semCaries",semCaries);
//
//        return mv;
//    }
//
//    @RequestMapping("/graphNegros")
//    public ModelAndView doGraphNegro(){
//        ModelAndView mv = new ModelAndView("graphEtnico");
//
//        List<Consulta> pacientesBrancosComCaries = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Negro");
//        List<Consulta> pacientesBrancos          = consultaRepo.findAllByPacienteEtniaContaining("Negro");
//        List<Consulta> pacientesBrancosComRest   = consultaRepo.findAllByRestauracaoTrueAndPacienteEtniaContaining("Negro");
//
//        int semCaries = pacientesBrancos.size() - pacientesBrancosComCaries.size();
//
//        mv.addObject("total",pacientesBrancos.size());
//        mv.addObject("comCaries",pacientesBrancosComCaries.size());
//        mv.addObject("semCaries",semCaries);
//
//        return mv;
//    }
//
//    @RequestMapping("/graphIndigenas")
//    public ModelAndView doGraphIndigena(){
//        ModelAndView mv = new ModelAndView("graphEtnico");
//
//        List<Consulta> pacientesBrancosComCaries = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Indígena");
//        List<Consulta> pacientesBrancos          = consultaRepo.findAllByPacienteEtniaContaining("Indígena");
//        List<Consulta> pacientesBrancosComRest   = consultaRepo.findAllByRestauracaoTrueAndPacienteEtniaContaining("Indígena");
//
//        int semCaries = pacientesBrancos.size() - pacientesBrancosComCaries.size();
//
//        mv.addObject("total",pacientesBrancos.size());
//        mv.addObject("comCaries",pacientesBrancosComCaries.size());
//        mv.addObject("semCaries",semCaries);
//
//        return mv;
//    }
//
//    @RequestMapping("/graphPardos")
//    public ModelAndView doGraphPardo(){
//        ModelAndView mv = new ModelAndView("graphEtnico");
//
//        List<Consulta> pacientesBrancosComCaries = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Pardo");
//        List<Consulta> pacientesBrancos          = consultaRepo.findAllByPacienteEtniaContaining("Pardo");
//        List<Consulta> pacientesBrancosComRest   = consultaRepo.findAllByRestauracaoTrueAndPacienteEtniaContaining("Pardo");
//
//        int semCaries = pacientesBrancos.size() - pacientesBrancosComCaries.size();
//
//        mv.addObject("total",pacientesBrancos.size());
//        mv.addObject("comCaries",pacientesBrancosComCaries.size());
//        mv.addObject("semCaries",semCaries);
//
//        return mv;
//    }
//
//    @RequestMapping("/graphAmarelos")
//    public ModelAndView doGraphAmarelo(){
//        ModelAndView mv = new ModelAndView("graphEtnico");
//
//        List<Consulta> pacientesBrancosComCaries = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Amarelo");
//        List<Consulta> pacientesBrancos          = consultaRepo.findAllByPacienteEtniaContaining("Amarelo");
//        List<Consulta> pacientesBrancosComRest   = consultaRepo.findAllByRestauracaoTrueAndPacienteEtniaContaining("Amarelo");
//
//        int semCaries = pacientesBrancos.size() - pacientesBrancosComCaries.size();
//
//        mv.addObject("total",pacientesBrancos.size());
//        mv.addObject("comCaries",pacientesBrancosComCaries.size());
//        mv.addObject("semCaries",semCaries);
//
//        return mv;
//
//
//    }
}
