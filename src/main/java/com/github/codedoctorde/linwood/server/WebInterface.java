package com.github.codedoctorde.linwood.server;


import com.github.codedoctorde.linwood.Linwood;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.sentry.Sentry;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author CodeDoctorDE
 */
public class WebInterface {
    private final Javalin app;

    public WebInterface(){
        app = Javalin.create();
        register();
    }

    public void register(){
        app.get("", WebInterface::info);
        app.post("login", AuthController::login);
    }

    public static void info(@NotNull Context context) throws IOException, InterruptedException {
        context.json(new HashMap<>(){{
            put("name", "Linwood");
            put("version", Linwood.getInstance().getVersion());
            put("id", Linwood.getInstance().getJda().getSelfUser().getId());
            put("api-version", Collections.singletonList(0));
        }});
    }

    public Javalin getApp() {
        return app;
    }

    public void start(){
        try {
            if(Linwood.getInstance().getConfig().getPort() != null)
            app.start(Linwood.getInstance().getConfig().getPort());
        }catch(Exception e){
            e.printStackTrace();
            Sentry.capture(e);
        }
    }
    public void stop() {
        try{
            app.stop();
        }catch(Exception e){
            e.printStackTrace();
            Sentry.capture(e);
        }
    }
}
