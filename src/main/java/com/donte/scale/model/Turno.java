package com.donte.scale.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Turno implements EntidadeBase {

    private static final int PRIMEIRO = 1;
    private static final int SEGUNDO = 2;
    private static final int TERCEIRO = 3;
    private static final int QUARTO = 4;

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String descricao;

    @Column(name = "horainicio", nullable = true)
    private String horaInicio;

    @Column(name = "horafim", nullable = true)
    private String horaFim;

    public Turno() {
    }

    private Turno(long id) {
        this.id = id;
    }

    public static Turno primeiro() {
        return new Turno(PRIMEIRO);
    }

    public static Turno segundo() {
        return new Turno(SEGUNDO);
    }

    public static Turno terceiro() {
        return new Turno(TERCEIRO);
    }

    public static Turno quarto() {
        return new Turno(QUARTO);
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public static int getPrimeiro() {
        return PRIMEIRO;
    }

    public static int getSegundo() {
        return SEGUNDO;
    }

    public static int getTerceiro() {
        return TERCEIRO;
    }

    public static int getQuarto() {
        return QUARTO;
    }

    public String getLabel() {
        return getDescricao();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Turno other = (Turno) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
