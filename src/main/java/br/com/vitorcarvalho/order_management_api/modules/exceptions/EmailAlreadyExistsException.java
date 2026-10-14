package br.com.vitorcarvalho.order_management_api.modules.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(){
        super("This e-mail is already registered.");
    }
}
