package br.edu.puc.barragem;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.Tag;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.RegistryType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.edu.puc.barragem.domain.Barragem;
import br.edu.puc.barragem.domain.CategoriaRisco;
import br.edu.puc.barragem.domain.Classe;
import br.edu.puc.barragem.domain.NivelEmergencia;

@Path("/api/v1/barragens")
public class BarragemResource {

    private Set<Barragem> barragens = new HashSet<Barragem>(0);

    long maiorQuantidadeRejeito = 0L;
    long menorQuantidadeRejeito = 1000;

    @Inject
    @RestClient
    NotificationRestClient notificationRestClient;

    @Inject
    @RegistryType(type = MetricRegistry.Type.APPLICATION)
    MetricRegistry metricRegistry;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Barragem add(Barragem barragem){
        barragens.add(barragem);
        armazenaHistoricoBarragem(barragem);
        return barragem;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response get(){
        return Response.status(Status.OK).entity(barragens).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") final Integer id) {
        Barragem barragem = new Barragem();
        barragem.setId(id);
        if (barragens.contains(barragem)){
            for (Barragem barragemEntity : barragens) {
                if (barragem.equals(barragemEntity)){
                    return Response.status(Status.OK).entity(barragemEntity).build();
                }
            }   
        }
        return Response.noContent().status(Status.BAD_REQUEST).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Barragem barragem){
        if (barragens.contains(barragem)){
            for (Barragem barragemEntity : barragens) {
                if (barragem.equals(barragemEntity)){
                    if (!barragemEntity.getClasse().equals(barragem.getClasse())){
                        barragemEntity.setClasse(barragem.getClasse());
                    }
                    if (!barragemEntity.getCategoriaRisco().equals(barragem.getCategoriaRisco())){
                        barragemEntity.setCategoriaRisco(barragem.getCategoriaRisco());
                    }
                    if (!barragemEntity.getNivelEmergencia().equals(barragem.getNivelEmergencia())){
                        barragemEntity.setNivelEmergencia(barragem.getNivelEmergencia());
                        if (barragemEntity.getNivelEmergencia().equals(NivelEmergencia.NIVEL_1)){
                            notificationRestClient.addNotification(barragemEntity);
                        }
                    }
                    if (!barragemEntity.getQuantidadeRejeito().equals(barragem.getQuantidadeRejeito())){
                        barragemEntity.setQuantidadeRejeito(barragem.getQuantidadeRejeito());
                    }
                    if (menorQuantidadeRejeito > barragemEntity.getQuantidadeRejeito()){
                        menorQuantidadeRejeito = barragemEntity.getQuantidadeRejeito().longValue();
                    }
                    if (maiorQuantidadeRejeito < barragemEntity.getQuantidadeRejeito()){
                        maiorQuantidadeRejeito = barragemEntity.getQuantidadeRejeito().longValue();
                    }
                    barragens.remove(barragem);
                    barragens.add(barragemEntity);
                    armazenaHistoricoBarragem(barragemEntity);
                    return Response.status(Status.OK).entity(barragemEntity).build();
                }
            }
        }
        return Response.noContent().status(Status.BAD_REQUEST).build();
    }

    private void armazenaHistoricoBarragem(Barragem barragemEntity) {
        if (barragens.contains(barragemEntity)){
            for (Barragem barragem : barragens) {
                if (barragem.equals(barragemEntity)){
                    armazenaHistoricoClasse(barragemEntity);
                    armazenaHistoricoRisco(barragemEntity);
                    armazenaHistoricoEmergencia(barragemEntity);
                    armazenaHistoricoQuantidadeRejeito(barragemEntity);
                }
            }
        }
    }

    @GET
    @Path("/load")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response load(){
        for (int i = 0; i < 5; i++) {
            Barragem barragem = new Barragem();
            barragem.setId(i);
            barragem.setNome("Barragem" + i);
            barragem.setMunicipio("Municipio" + i);
            barragem.setCategoriaRisco(CategoriaRisco.values()[new Random().nextInt(CategoriaRisco.values().length)]);
            barragem.setClasse(Classe.values()[new Random().nextInt(Classe.values().length)]);
            barragem.setNivelEmergencia(NivelEmergencia.values()[new Random().nextInt(NivelEmergencia.values().length)]);
            barragem.setQuantidadeRejeito(ThreadLocalRandom.current().nextLong(500));
            if (menorQuantidadeRejeito > barragem.getQuantidadeRejeito()){
                menorQuantidadeRejeito = barragem.getQuantidadeRejeito().longValue();
            }
            if (maiorQuantidadeRejeito < barragem.getQuantidadeRejeito()){
                maiorQuantidadeRejeito = barragem.getQuantidadeRejeito().longValue();
            }
            barragens.add(barragem);   
        }
        return Response.status(Status.OK).entity(barragens).build();
    }
    
    private void armazenaHistoricoClasse(Barragem entity){
        Tag barragemTag = new Tag("barragem", entity.getNome());
        Metadata metadata = Metadata.builder().withName("BARRAGEM_CLASSE")
            .withDescription("BARRAGEM_CLASSE")
            .withType(MetricType.HISTOGRAM)
            .build();           
        Histogram histogram = metricRegistry.histogram(metadata, barragemTag);
        histogram.update(entity.getClasse().getClasse());
    }

    private void armazenaHistoricoRisco(Barragem entity){
        Tag barragemTag = new Tag("barragem", entity.getNome());
        Metadata metadata = Metadata.builder().withName("BARRAGEM_RISCO")
            .withDescription("BARRAGEM_RISCO")
            .withType(MetricType.HISTOGRAM)
            .build();           
        Histogram histogram = metricRegistry.histogram(metadata, barragemTag);
        histogram.update(entity.getCategoriaRisco().getRisco());
    }

    private void armazenaHistoricoEmergencia(Barragem entity){
        Tag barragemTag = new Tag("barragem", entity.getNome());
        Metadata metadata = Metadata.builder().withName("BARRAGEM_EMERGENCIA")
            .withDescription("BARRAGEM_EMERGENCIA")
            .withType(MetricType.HISTOGRAM)
            .build();           
        Histogram histogram = metricRegistry.histogram(metadata, barragemTag);
        histogram.update(entity.getNivelEmergencia().getNivel());
    }

    private void armazenaHistoricoQuantidadeRejeito(Barragem entity){
        Tag barragemTag = new Tag("barragem", entity.getNome());
        Metadata metadata = Metadata.builder().withName("BARRAGEM_REJEITO")
            .withDescription("BARRAGEM_REJEITO")
            .withType(MetricType.HISTOGRAM)
            .build();           
        Histogram histogram = metricRegistry.histogram(metadata, barragemTag);
        histogram.update(entity.getQuantidadeRejeito());
    }

    @Gauge(name = "PUC_MENOR_QUANTIDADE_REJEITOS", unit = MetricUnits.NONE, description = "Menor quantidade de rejeitos")
    public long verificaMenorQuantidadeRejeito(){
        return menorQuantidadeRejeito;
    }

    @Gauge(name = "PUC_MAIOR_QUANTIDADE_REJEITOS", unit = MetricUnits.NONE, description = "Maior quantidade de rejeitos")
    public long verificaMaiorQuantidadeRejeito(){
        return maiorQuantidadeRejeito;
    }

    @Gauge(name = "PUC_QUANTIDADE_BARRAGENS", unit = MetricUnits.NONE, description = "Quantidade de Barragens")
    public int verificaQuantidadeBarragens(){
        return barragens.size();
    }

}