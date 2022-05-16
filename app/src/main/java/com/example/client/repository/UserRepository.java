package com.example.client.repository;

import android.content.Context;

import com.example.client.models.User;
import com.example.client.services.AuthService;

public class UserRepository {
    private AuthService authService = AuthService.getInstance();

    private static UserRepository userRepository = null;

    /**
     * creation singleTon instance pour UserRepository
     * @return
     */
    public static synchronized UserRepository getInstance(){
        if(null == userRepository){
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    //Authentification Fonction

    /**
     * Fonction login
     * @param context (l'activité), user (instance d'une classe User)
     * @return
     */
    public User login(Context context, User user){
        try{
            return authService.functionLogin(context, user);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Reposiory login, appel du  fonctionRegister dans le authService
     * @param context
     * @param user
     */
    public void register(Context context,User user){
        try{
            authService.functionRegister(context,user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
