package com.pb.ProjetoGrupo2.config;

import com.pb.ProjetoGrupo2.constants.Type;
import org.springframework.core.convert.converter.Converter;

public class ToUpperCaseEnumType implements Converter<String, Type> {

    @Override
    public Type convert(String value){
        return Type.valueOf(value.toUpperCase());
    }
}
