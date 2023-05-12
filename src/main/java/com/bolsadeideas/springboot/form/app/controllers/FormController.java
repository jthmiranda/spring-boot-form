package com.bolsadeideas.springboot.form.app.controllers;

import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.RolesEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Role;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.services.PaisService;
import com.bolsadeideas.springboot.form.app.services.RoleService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("usuario")
public class FormController {

    @Autowired
    private UsuarioValidador validador;

    @Autowired
    private PaisService paisService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PaisPropertyEditor paisEditor;

    @Autowired
    private RolesEditor rolesEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validador); // con este método válido con clase personalizada y por entity bean
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        dateformat.setLenient(false);

        binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateformat, true));
        binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());


        binder.registerCustomEditor(Pais.class, "pais", paisEditor);
        binder.registerCustomEditor(Role.class, "roles", rolesEditor);
    }

    @ModelAttribute("genero")
    public List<String> genero() {
        return Arrays.asList("Hombre", "Mujer");
    }

    @ModelAttribute("listaRoles")
    public List<Role> listaRoles() {
        return roleService.listar();
    }

    @ModelAttribute("listaPaises")
    public List<Pais> listaPaises() {
        return paisService.listar();
    }

    @ModelAttribute("listaRolesString")
    public List<String> listaRolesString() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        roles.add("ROLE_MODERATOR");
        return roles;
    }

    @ModelAttribute("listaRolesMap")
    public Map<String, String> listaRolesMap() {
        Map<String, String> paises = new HashMap<>();
        paises.put("ROLE_ADMIN", "Administrador");
        paises.put("ROLE_USER", "Usuario");
        paises.put("ROLE_MODERATOR", "Moderador");
        return paises;
    }

    @ModelAttribute("paises")
    public List<String> paises() {
        return Arrays.asList("España", "Mexico", "Chile", "Argentina", "Perú", "Colombia", "Venezuela");
    }

    @ModelAttribute("paisesMap")
    public Map<String, String> paisesMap() {
        Map<String, String> paises = new HashMap<>();
        paises.put("ES", "España");
        paises.put("MX", "Mexico");
        paises.put("CH", "Chile");
        paises.put("AR", "Argentina");
        paises.put("PE", "Perú");
        paises.put("CL", "Colombia");
        paises.put("VZ", "Venezuela");
        return paises;
    }

    @GetMapping("/form")
    public String form(Model model) {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setApellido("Doe");
        usuario.setIdentificador("12.456.789-K");
        usuario.setHabilitar(true);
        usuario.setPais(new Pais(3, "CH", "Chile"));
        usuario.setRoles(Arrays.asList(new Role(2, "Usuario", "ROLE_USER")));
        usuario.setValorSecreto("Algún valor secreto ******");

        model.addAttribute("titulo", "Formulario Usuarios");
        model.addAttribute("usuario", usuario);
        return "form";
    }

    @PostMapping("/form")
    public String procesar(@Valid Usuario usuario, BindingResult result, Model model) {
        //validador.validate(usuario, result); de manera programatica



        if (result.hasErrors()) {
            model.addAttribute("titulo", "Resultado Form");
//            Map<String, String> errores = new HashMap<>();
//            result.getFieldErrors().forEach(field -> {
//                errores.put(field.getField(), "El campo ".concat(field.getField()).concat(" ").concat(field.getDefaultMessage()));
//            });
//            model.addAttribute("error", errores);
            return "form";
        }


        return "redirect:/ver";
    }

    @GetMapping("/ver")
    public String ver(@SessionAttribute(name="usuario", required = false) Usuario usuario, Model model, SessionStatus status) {

        if (usuario == null)
            return "redirect:/form";

        model.addAttribute("titulo", "Resultado Form");

        status.setComplete();
        return "resultado";
    }
}
