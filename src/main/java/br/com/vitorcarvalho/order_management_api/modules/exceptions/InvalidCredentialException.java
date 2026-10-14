package br.com.vitorcarvalho.order_management_api.modules.exceptions;

public class InvalidCredentialException extends RuntimeException{
    public InvalidCredentialException(){
        super("The password is incorrect.");
    }

    public InvalidCredentialException(String message){
        super(message);
    }
}
