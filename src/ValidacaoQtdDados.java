public class ValidacaoQtdDados {
    public static final void validacao(String[] campos, String nomeArquivo, int qtdDados) throws IllegalArgumentException{

        if (campos.length != qtdDados) {
            throw new IllegalArgumentException("com quantidade incorreta de dados do arquivo " + nomeArquivo);
        }
    }
}
