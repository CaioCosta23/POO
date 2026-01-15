public enum EnumQtdDados {
    QTD_DADOS_ADMINISTRADOR(6),
    QTD_DADOS_CLIENTES(7),
    QTD_DADOS_BARBEIRO(8),
    QTD_DADOS_DISPONIBILIDADE(4),
    QTD_DADOS_SERVICOS(5),
    QTD_DADOS_ENDERECO_SIMPLES(4),
    QTD_DADOS_ENDERECO_COMPLETO(7),
    QTD_DADOS_BARBEARIA(6),
    QTD_DADOS_RECURSOS(4);
    

    private final int quantidade;

    EnumQtdDados(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getValue() {
        return this.quantidade;
    }
}
