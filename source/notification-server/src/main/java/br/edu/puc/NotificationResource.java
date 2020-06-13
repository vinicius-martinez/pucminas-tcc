package br.edu.puc;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;

import org.eclipse.microprofile.metrics.annotation.Counted;

import br.edu.puc.barragem.domain.Barragem;

@Path("/api/v1/notifications")
public class NotificationResource {
		    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Counted(name = "PUC_QUANTIDADE_NOTIFICACOES_EMERGENCIA", description = "Quantidade de Notificacoes de Emergencia Realizada")
    public Response addNotification(Barragem barragem) {
        if (notificaTelegram(barragem)){
            return Response.status(Status.OK).entity(barragem).build();
        }
        return Response.noContent().status(Status.BAD_REQUEST).build();
    }

    private boolean notificaTelegram(Barragem barragem){
        TelegramBot bot = new TelegramBot("");
        SendMessage message = new SendMessage("", "Isso é uma emergência: A barragem " + barragem.getNome() + " do Municipio: " + barragem.getMunicipio()  + " esta com problemas e voce deve procurar os Bombeiros ou Defesa Cívil");
        BaseResponse response = bot.execute(message);
        return response.isOk();
    }

}