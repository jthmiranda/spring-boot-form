package com.bolsadeideas.springboot.form.app.services;

import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PaisServiceImpl implements PaisService {

    private List<Pais> lista;

    public PaisServiceImpl(List<Pais> lista) {
        this.lista = Arrays.asList(
                new Pais(1, "ES", "España"),
                new Pais(2, "MX", "Mexico"),
                new Pais(3, "CH", "Chile"),
                new Pais(4, "AR", "Argentina"),
                new Pais(5, "PE", "Perú"),
                new Pais(6, "CL", "Colombia"),
                new Pais(7, "VZ", "Venezuela")
        );
    }

    @Override
    public List<Pais> listar() {
        return lista;
    }

    @Override
    public Pais obtenerPorPais(Integer id) {
        Pais resultado = null;
        for(Pais pais : this.lista) {
            if(id == pais.getId()) {
                resultado = pais;
                break;
            }
        }
        return resultado;
    }
}
