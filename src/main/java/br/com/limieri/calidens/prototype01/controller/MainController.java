package br.com.limieri.calidens.prototype01.controller;

import br.com.limieri.calidens.prototype01.entity.Consulta;
import br.com.limieri.calidens.prototype01.entity.Usuario;
import br.com.limieri.calidens.prototype01.repository.ConsultaRepo;
import br.com.limieri.calidens.prototype01.repository.EscolaRepo;
import br.com.limieri.calidens.prototype01.repository.PacienteRepo;
import br.com.limieri.calidens.prototype01.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class MainController {


    final UsuarioRepo usuarioRepo;
    final PacienteRepo pacienterepo;
    final ConsultaRepo consultaRepo;
    final EscolaRepo escolarepo;

    @Autowired
    public MainController(UsuarioRepo usuarioRepo, PacienteRepo pacienterepo, ConsultaRepo consultaRepo, EscolaRepo escolarepo) {
        this.usuarioRepo = usuarioRepo;
        this.pacienterepo = pacienterepo;
        this.consultaRepo = consultaRepo;
        this.escolarepo = escolarepo;
    }

    @RequestMapping("/")
    private ModelAndView login(){
        ModelAndView mv = new ModelAndView("login");

        return mv;
    }

    @RequestMapping("/home")
    private ModelAndView doHome(){
        ModelAndView mv = new ModelAndView("home");

        return mv;
    }


    @RequestMapping("/sobre")
    public ModelAndView doSobre(){
        ModelAndView mv = new ModelAndView("sobre");
        mv.addObject("name","Teste");

        return mv;
    }

    public String doMD5 (String password){
        try {

            MessageDigest messageDigest=MessageDigest.getInstance("MD5");

            messageDigest.update(password.getBytes());
            byte[] digest=messageDigest.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(Integer.toHexString((int) (b & 0xff)));
            }


            return sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
        }
        return "";
    }

    @GetMapping(value = "/doLogin")
    public ModelAndView doLogin(HttpServletRequest request) {

        String email = request.getParameter("email");
        String password  = request.getParameter("password");
        // testando login
//        String msg = AppService.validaLogin(firstName, lastName);

        password = doMD5(password);

        System.out.println(password);

        List<Usuario> appUsers = usuarioRepo.findUsuarioByEmailAndPassword(email,password);



        if(!appUsers.isEmpty()) {
            // se o login foi bem sucedido
            ModelAndView mv = new ModelAndView("redirect:/home");
            mv.addObject("lists", usuarioRepo.findAll());
            return mv;
        }else{
            // login mal sucedido
            ModelAndView mv = new ModelAndView("redirect:/invalid");
            return mv;
        }
    }

    @RequestMapping("/invalid")
    public ModelAndView doInvalid(){
        ModelAndView mv = new ModelAndView("invalid");

        return mv;
    }

    @RequestMapping("/teste")
    public ModelAndView doTeste(){
        ModelAndView mv = new ModelAndView("teste");

        long id = (long) 1;

        mv.addObject("escolas",escolarepo.findAll());
        mv.addObject("pacientes",pacienterepo.findAllByEscola_IdAndNomeContaining(id,"Limieri"));


        return mv;
    }

//    @RequestMapping("/graficos")
//    public ModelAndView doGraph(){
//        ModelAndView mv = new ModelAndView("graficos");
//
//        return mv;
//    }

    @RequestMapping("/mensagem")
    public ModelAndView doMensagem(){
        ModelAndView mv = new ModelAndView("redirect:/teste");

//        List<Paciente> pacientesBrancos   = pacienterepo.findAllByEtniaLike("Branco");
//        List<Paciente> pacientesNegros    = pacienterepo.findAllByEtniaLike("Negro");
//        List<Paciente> pacientesPardos    = pacienterepo.findAllByEtniaLike("Pardo");
//        List<Paciente> pacientesIndigenas = pacienterepo.findAllByEtniaLike("Indígena");
//        List<Paciente> pacientesAmarelos  = pacienterepo.findAllByEtniaLike("Amarelo");

//        System.out.println(" pacientesBrancos: " + pacientesBrancos.size());
//        System.out.println(" pacientesNegros: " + pacientesNegros.size());
//        System.out.println(" pacientesPardos: " + pacientesPardos.size());
//        System.out.println(" pacientesIndigenas: " + pacientesIndigenas.size());
//        System.out.println(" pacientesAmarelos: " + pacientesAmarelos.size());

        List<Consulta> pacientesBrancosComCaries = consultaRepo.findAllByCarieTrueAndPacienteEtniaContaining("Branco");
        List<Consulta> pacientesBrancos          = consultaRepo.findAllByPacienteEtniaContaining("Branco");
        List<Consulta> pacientesBrancosComRest   = consultaRepo.findAllByRestauracaoTrueAndPacienteEtniaContaining("Branco");

        System.out.println("Brancos total: " + pacientesBrancos.size());
        System.out.println("Brancos com cáries: " + pacientesBrancosComCaries.size());
        System.out.println("Brancos com rest: " + pacientesBrancosComRest.size());
        return mv;
    }



    @RequestMapping("/doMD5")
    public ModelAndView teste (){

        ModelAndView mv = new ModelAndView("teste");

        return mv;
    }


}
