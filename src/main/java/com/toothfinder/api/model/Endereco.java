package com.toothfinder.api.model;

import javax.persistence.Embeddable;

@Embeddable
public class Endereco {
    private String logradouro;
    private String bairro;
    private String numero;
    private String cep;
    private String cidade;
    private String estado;
    private String complemento;
}
