package br.com.alura.screenmatch.service.traducao;

import br.com.alura.screenmatch.service.ConsumoApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ConsultaMyMemory {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final ConsumoApi consumo = new ConsumoApi();

    private static final String apiKey =
            System.getenv("APYMEMORY_KEY");

    public static String obterTraducao(String textoOriginal) {
        try {
            String texto = URLEncoder.encode(
                    textoOriginal,
                    StandardCharsets.UTF_8
            );

            String langpair = URLEncoder.encode(
                    "en|pt-BR",
                    StandardCharsets.UTF_8
            );

            String url = "https://api.mymemory.translated.net/get"
                    + "?q=" + texto
                    + "&langpair=" + langpair
                    + "&key=" + apiKey;

            String json = consumo.obterDados(url);

            DadosTraducao traducao =
                    mapper.readValue(json, DadosTraducao.class);

            return traducao
                    .dadosResposta()
                    .textoTraduzido();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    "Erro ao converter a resposta da API MyMemory.",
                    e
            );
        }
    }
}