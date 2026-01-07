public class ValidacaoCnpj {
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
    private static final String REGEXCNPJ = "^([0-9][0-9]).([0-9][0-9][0-9]).([0-9][0-9][0-9])/([0-9][0-9][0-9][0-9])-([0-9][0-9])$";


    /*
     * Este trecho de código realiza a validação de um CNPJ, verificando se o mesmo está no formato padrão utilizado ou não;
     */
    public static void validacao(String cnpj) throws ExceptionFormato {
        // Verifica se o CNPJ foi preenchido na hora da captura dos dados ou se o campo está vazio;
        if ((cnpj == null) || cnpj.isBlank()) {
            throw new ExceptionFormato("CNPJ vazio ou inexistente.");
        }

        /*
         * Verifica se o CNPJ está no formato estipulado;
         * .matches(): verifica se a string (neste caso) segue 100% a regra estipulada no REGEXCNPJ; 
        */

        if (!(cnpj).matches(REGEXCNPJ)) {
            throw new ExceptionFormato("CNPJ Invalido.");
        }
    }
}
