package com.bolsadeideas.springboot.form.app.editors;

import com.bolsadeideas.springboot.form.app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class RolesEditor extends PropertyEditorSupport {

    @Autowired
    private RoleService roleService;

    @Override
    public void setAsText(String idString) throws IllegalArgumentException {
        try {
            Integer id = Integer.parseInt(idString);
            setValue(roleService.obtenerPorId(id));
        } catch (NumberFormatException e) {
            setValue(null);
        }
    }
}
