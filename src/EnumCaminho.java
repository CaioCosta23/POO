public enum EnumCaminho {
    BARBEARIA("resources/config/infoBarbearia.txt"),
    ENDERECO("resources/config/endereco.txt"),
    ADMINISTRADOR("resources/config/administrador.txt"),
    AGENDAMENTO("dados/agendamentos.txt"),
    BARBEIROS("dados/barbeiros.txt"),
    CLIENTES("dados/clientes.txt"),
    SERVICOS("dados/servicos.txt"),
    ESPECIALIDADES("dados/especialidades.txt"),
    DISPONIBILIDADES("dados/disponibilidades.txt"),
    RECURSOS("dados/recursos.txt"),
    NOTIFICACOES("dados/notificacoes.txt");

    private final String value;
    
    EnumCaminho(String caminho) {
        this.value = caminho;
    }

    public String getValue() {
        return this.value;
    }
}
