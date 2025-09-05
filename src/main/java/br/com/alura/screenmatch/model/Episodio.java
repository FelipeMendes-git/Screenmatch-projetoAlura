package br.com.alura.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private int temporada;
    private String titulo;
    private int numeroEpisodio;
    private double avaliacao;
    private LocalDate dataDeLancamento;


    public Episodio(int temporada, DadosEpisodio e) {
        this.temporada = temporada;
        this.titulo = e.titulo();
        this.numeroEpisodio = e.numero();
        try {
            this.avaliacao = Double.parseDouble(e.avaliacao());
        } catch (NumberFormatException ex) {
            this.avaliacao = 0;
        }

        try {
            this.dataDeLancamento = LocalDate.parse(e.dataDeLancamento());
        }catch (DateTimeParseException ex) {
            this.dataDeLancamento = null;
        }


    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(int numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataDeLancamento() {
        return dataDeLancamento;
    }

    public void setDataDeLancamento(LocalDate dataDeLancamento) {
        this.dataDeLancamento = dataDeLancamento;
    }

    @Override
    public String toString() {
        return  "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", avaliacao=" + avaliacao +
                ", dataDeLancamento=" + dataDeLancamento;
    }
}
