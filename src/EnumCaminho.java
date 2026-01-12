public enum EnumCaminho {
    BARBEARIA("dados/infoBarbearia.txt"),
    ADMINISTRADOR("dados/administrador.txt"),
    AGENDAMENTO("dados/agendamento.txt"),
    BARBEIROS("dados/barbeiros.txt"),
    CLIENTES("dados/clientes.txt"),
    SERVICOS("dados/servicos.txt"),
    ENDERECO("endereco.txt");

    private final String value;
    
    EnumCaminho(String caminho) {
        this.value = caminho;
    }

    public String getValue() {
        return this.value;
    }
}
