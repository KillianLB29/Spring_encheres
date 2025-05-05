package fr.eni.tp.spring_encheres.ihm.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class StringToDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        LocalDate localDate = LocalDate.parse(source);

        // Étape 2 : Convertis LocalDate en java.util.Date

        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
