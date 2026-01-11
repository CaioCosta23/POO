public enum EnumQtdDados {
    QTD_DADOS_CLIENTES(8),
    QTD_DADOS_BARBEIRO(7),
    QTD_DADOS_SERVICOS(6),
    QTD_DADOS_ENDERECO_SIMPLES(5),
    QTD_DADOS_EMDERECO_COMPLETO(8),
    QTD_DADOS_BARBEARIA(6);

    private final int quantidade;

    EnumQtdDados(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getValue() {
        return this.quantidade;
    }
}
