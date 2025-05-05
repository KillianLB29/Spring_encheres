package fr.eni.tp.spring_encheres.ihm.converter;

import fr.eni.tp.spring_encheres.bll.CategorieService;
import fr.eni.tp.spring_encheres.bo.Categorie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategorieConverter implements Converter<String, Categorie> {
    private CategorieService categorieService;

    public StringToCategorieConverter(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @Override
    public Categorie convert(String source) {
        return categorieService.consulterCategorieParId(Long.parseLong(source));
    }
}
