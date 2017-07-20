/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.zyra.demos.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author mickael
 */
public class NewClass {



    public static void main(String[] args) {


        ExecutorService execService = Executors.newSingleThreadExecutor();

        Future<Integer> f = execService.submit(new CliTask("mise à jour de l'heure", new String[]{"ping", "127.0.0.1", "-c", "5"}));
        
        
//        System.out.println("suite");
//        try {
//            System.out.println("suite1");
//            Integer retour = f.get();
//            System.out.println("suite2");
//            System.out.printf("j'ai eu le retour %s%n", retour);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
        execService.shutdown();

    }
}
