public class ValidacaoQtdDados {
    public static final void validacao(String linha, String nomeArquivo, int qtdDados) throws IllegalArgumentException{
        String[]campos = linha.split(";");

        if (campos.length != qtdDados) {
            throw new IllegalArgumentException("com quantidade incorreta de dados do arquivo " + nomeArquivo);
        }
    }
}
