package br.com.alura.screenmatch.Principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO="http://www.omdbapi.com/?t=";
    private final String APIKEY="&apikey=736aaeff";

    public void exibeMenu(){
        System.out.println("digite o nome da série para busca: ");
        var nomeSerie = scanner.nextLine();
        String url=ENDERECO+nomeSerie.replace(" ","+")+APIKEY;
        var consumoApi = new ConsumoApi();
        var json = consumoApi.obterDados(url);

        DadosSerie dados= conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumoApi.obterDados(ENDERECO+nomeSerie.replace(" ","+")+"&Season="+i+APIKEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
//        temporadas.forEach(System.out::println);

//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
//
//        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream())
//                .collect(Collectors.toList());
//
//        System.out.println("top 10 episodios:");
//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .limit(10)
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()

                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.temporada(), d))
                ).collect(Collectors.toList());

        System.out.print("digite o nome do episodio: ");
        var trechoTitulo = scanner.nextLine();

        //episodios.forEach(System.out::println);

        Optional<Episodio> episodioBuscado=episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if(episodioBuscado.isPresent()){
            System.out.println(episodioBuscado);
        }
        else {
            System.out.println("eoisodio não encontrado");
        }

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesPorTemporada);

//        System.out.println("A partir de que ano você deseja ver os episódios? ");
//        var ano = scanner.nextInt();
//        scanner.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataDeLancamento().isAfter(dataBusca)&& e.getDataDeLancamento() != null)
//                .forEach(e -> System.out.println(
//                        "temporada = "+e.getTemporada() +
//                                " epidosdio: " + e.getTitulo() +
//                                " data de lançamento: " + formatter.format(e.getDataDeLancamento())
//                ));
    }
}
