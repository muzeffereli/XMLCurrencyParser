package com.company.xml_currency_parser;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ValType {
    @XmlElement(name="Valute")
    private List<Valute> valutes;

    @XmlAttribute(name = "Type")
    private String type;

    public List<Valute> getValutes() {
        return valutes;
    }

    public void setValutes(List<Valute> valute) {
        this.valutes = valute;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ValType{" +
                "valute=" + valutes +
                ", type='" + type + '\'' +
                '}';
    }
}
