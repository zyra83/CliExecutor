/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.zyra.demos.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import lombok.extern.apachecommons.CommonsLog;

/**
 *
 * @author mickael
 */
@CommonsLog
public class CliTask implements Callable<Integer> {

    private String cmds[];
    private String name;

    public CliTask(String name, String[] cmds) {
        this.cmds = cmds;
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {

        log.debug(String.format("Démarrage de %s%n", name));

        ProcessBuilder pb = new ProcessBuilder(cmds);

        Process process = pb.start();

        AfficheurFlux fluxSortie = new AfficheurFlux(process.getInputStream());
        AfficheurFlux fluxErreur = new AfficheurFlux(process.getErrorStream());

        new Thread(fluxSortie).start();
        new Thread(fluxErreur).start();

        process.waitFor();
        log.debug(String.format("Fin de %s%n", name));
        return process.exitValue();

    }

    class AfficheurFlux implements Runnable {

        private final InputStream inputStream;

        AfficheurFlux(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        private BufferedReader getBufferedReader(InputStream is) {
            return new BufferedReader(new InputStreamReader(is));
        }

        @Override
        public void run() {
            BufferedReader br = getBufferedReader(inputStream);
            String ligne = "";
            try {
                while ((ligne = br.readLine()) != null) {
                   log.debug(ligne);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
