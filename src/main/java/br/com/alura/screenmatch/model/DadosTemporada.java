package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Season") int temporada,
                             @JsonAlias("Episodes") List<DadosEpisodio> episodios) {
}
