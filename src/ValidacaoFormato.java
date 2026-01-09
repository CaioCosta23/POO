public class ValidacaoFormato {
    /*
     * Este trecho de código realiza a validação de um dado, verificando se o mesmo está no formato padrão utilizado ou não;
     */
    public static void validacao(String dado, String formato) throws ExceptionFormato {
        // Verifica se o dado foi preenchido na hora da captura das informações ou se o campo está vazio;
        if ((dado == null) || dado.isBlank()) {
            throw new ExceptionFormato("CEP vazio ou inexistente.");
        }

        /*
         * Verifica se o dado está no formato estipulado;
         * .matches(): verifica se a string (neste caso) segue 100% a regra estipulada no "formato"; 
        */
        if (!(dado).matches(formato)) {
            throw new ExceptionFormato("CEP Invalido.");
        }
    }
}
