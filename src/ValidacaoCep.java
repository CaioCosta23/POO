public class ValidacaoCep {
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
     * "0-9" representa a possibilidade de haver todo e qualquer algarismo de 0 à 9;
     * "$" indica que não pode haver mais nenhum caractere após isso;
     * 
     * OBS: Geralmente essa variável é nomeada como "REGEX";
     */
    private static final String REGEXCEP = "^([0-9][0-9][0-9][0-9][0-9])-([0-9][0-9][0-9])$";


    /*
     * Este trecho de código realiza a validação de um CEP, verificando se o mesmo está no formato padrão utilizado ou não;
     */
    public static void validacao(String cep) throws ExceptionFormato {
        // Verifica se o CPF foi preenchido na hora da captura dos dados ou se o campo está vazio;
        if ((cep == null) || cep.isBlank()) {
            throw new ExceptionFormato("CEP vazio ou inexistente.");
        }

        /*
         * Verifica se o CEP está no formato estipulado;
         * .matches(): verifica se a string (neste caso) segue 100% a regra estipulada no REGEXCEP; 
        */

        if (!(cep).matches(REGEXCEP)) {
            throw new ExceptionFormato("CEP Invalido.");
        }
    }
}
