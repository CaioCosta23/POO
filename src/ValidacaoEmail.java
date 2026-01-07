public class ValidacaoEmail {
    /*
     * EXPRESSÃO REGULAR:
     *
     * Trata-se de uma expressão que define um padrão de escrita/validação de algum dado (geralmente também descritivo);
     * Não depende do objeto, sendo uma regra/expressão fixa, fazendo com que o proograma seja mais eficiente e sem a necessidade de instanciação de um objeto;
     * Não pode ser alterada, evitando bugs acidentais e garantindo consistência para o código;
     * 
     * Destrinchando a expressão abaixo (em si):
     * 
     * O "^" indica que não pode haver mais nada antes disso;
     * "A-Z" representa a possibilidade de haver toda e qualquer letra maiúscula do alfabeto;
     * "a-z" representa a possibilidade de haver toda e qualquer letra miniúscula do alfabeto;
     * "0-9" representa a possibilidade de haver todo e qualquer algarismo de 0 à 9;
     * "+_.-" Indica os simbolos permitidos em um endereço de e-mail;
     * "+" indica que pode haver um ou mais caracteres depois;
     * "$" indica que não pode haver mais nenhum caractere após isso;
     * 
     * OBS: Geralmente essa variável é nomeada como "REGEX";
     */
    private static final String FORMATO = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]$";


    /*
     * Este trecho de código realiza a validação de um e-mail, verificando se o mesmo está no formato padrão utilizado ou não;
     */
    public static void validacao(String email) throws ExceptionFormato {
        // Verifica se o e-mail foi preenchido na hora da captura dos dados ou se o campo está vazio;
        if ((email == null) || email.isBlank()) {
            throw new ExceptionFormato("Email vazio ou inexistente.");
        }

        /*
         * Verifica se o email está no formato estipulado;
         * .matches(): verifica se a string (neste caso) segue 100% a regra estipulada no FORMATO; 
        */

        if (!(email).matches(FORMATO)) {
            throw new ExceptionFormato("Email Invalido.");
        }
    }
}
