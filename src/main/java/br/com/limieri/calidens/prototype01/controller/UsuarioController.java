package br.com.limieri.calidens.prototype01.controller;

import br.com.limieri.calidens.prototype01.entity.Usuario;
import br.com.limieri.calidens.prototype01.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    final UsuarioRepo usuarioRepo;

    final MainController mainController;

    @Autowired
    public UsuarioController(UsuarioRepo usuarioRepo, MainController mainController) {
        this.usuarioRepo = usuarioRepo;
        this.mainController = mainController;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView doView(){
        ModelAndView mv = new ModelAndView("viewUsuario");
        mv.addObject("usuarios",usuarioRepo.findAll());


        return mv;
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ModelAndView doIndexPost(@RequestParam("buscar") String buscar){
        ModelAndView mv = new ModelAndView("viewUsuario");

        mv.addObject("usuarios",usuarioRepo.findAllByEmailContaining(buscar));


        return mv;
    }

    @RequestMapping("/add")
    public ModelAndView doAdd(){
        ModelAndView mv = new ModelAndView("addUsuario");
        return mv;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView doSave(@RequestParam("nome") String nome,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password) {
        ModelAndView mv = new ModelAndView("redirect:/usuarios/view");

        Usuario usuario = new Usuario();

        password = mainController.doMD5(password);

        // Criando novo usuario
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setPassword(password);

        usuarioRepo.save(usuario);
        return mv;
    }

    @RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
    public ModelAndView doDelete(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView("redirect:/usuarios/view");
        usuarioRepo.deleteById(id);

        return mv;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView doEditUsuario(@PathVariable("id") String id){
        Long idint = Long.parseLong(id);
        ModelAndView mv = new ModelAndView("editUsuario");
        mv.addObject("usuarios",usuarioRepo.findById(idint).orElse(null));

        return mv;
    }

    @RequestMapping(value = "/edit")
    public ModelAndView doEdit(@RequestParam("id") Long id,
                               @RequestParam("nome") String nome,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password){
        ModelAndView mv = new ModelAndView("redirect:/usuarios/view");

        Usuario usuario = usuarioRepo.findById(id).orElse(null);

        String password2 = usuario.getPassword();

        if (password.equals(password2)){
            System.out.println("password continua o mesmo");
        }
        else{
            password = mainController.doMD5(password);
            usuario.setPassword(password);
        }


        usuario.setEmail(email);
        usuario.setNome(nome);

        usuarioRepo.save(usuario);

        return mv;

    }


}
