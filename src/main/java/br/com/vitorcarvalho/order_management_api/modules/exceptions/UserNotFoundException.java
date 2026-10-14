package br.com.vitorcarvalho.order_management_api.modules.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("User not found. Please verify the ID provided.");
    }
}
