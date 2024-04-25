package exceptions;

public class ApiException extends Exception {

    public ApiException(String mensagem) {
        super(mensagem);
    }
}
