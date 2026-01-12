import java.time.LocalDateTime;

public class Cancelamento {
    private int id;
    private Cliente cliente;
    private Barbeiro barbeiro;
    private LocalDateTime dataHora;

    public Cancelamento(int id, Cliente cliente, Barbeiro barbeiro, LocalDateTime dataHora) {
        this.id = id;
        this.cliente = cliente;
        this.barbeiro = barbeiro;
        this.dataHora = dataHora;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = new Cliente(cliente);
    }

    public Barbeiro getBarbeiro() {
        return this.barbeiro;
    }

    public void setBarbeiro(Barbeiro barbeiro) {
        this.barbeiro = new Barbeiro(barbeiro);
    }

    public LocalDateTime getDataHora() {
        return this.dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    /**
     * Realiza o cancelamento de um agendamento
     * @param agendamento O agendamento a ser cancelado
     * @param politica A política de cancelamento da barbearia
     * @return true se o cancelamento foi bem-sucedido, false caso contrário
     */
    public boolean cancelarAgendamento(Agendamento agendamento, PoliticaCancelamento politica) {
        if (agendamento == null) {
            System.out.println("Erro: Agendamento invalido!");
            return false;
        }

        if (politica == null) {
            System.out.println("Aviso: Nenhuma politica de cancelamento definida.");
            // Permite cancelamento mesmo sem política
            registrarCancelamento(agendamento);
            return true;
        }

        // Valida se o cancelamento pode ser realizado
        LocalDateTime dataAgendamento = agendamento.getData().atStartOfDay(); // Converte LocalDate para LocalDateTime
        
        if (politica.validarCancelamento(dataAgendamento)) {
            System.out.println("Cancelamento dentro do prazo permitido.");
            registrarCancelamento(agendamento);
            return true;
        } else {
            // Calcula multa
            double valorMulta = politica.calcularMulta(agendamento.getServico().getPreco(), dataAgendamento);
            System.out.printf("ATENCAO: Cancelamento fora do prazo!\n");
            System.out.printf("Sera cobrada uma multa de R$ %.2f\n", valorMulta);
            
            // Aqui você poderia adicionar lógica para confirmar se o usuário aceita a multa
            registrarCancelamento(agendamento);
            return true;
        }
    }

    /**
     * Registra o cancelamento no sistema
     */
    private void registrarCancelamento(Agendamento agendamento) {
        System.out.println("-----------------------------------------------");
        System.out.println("CANCELAMENTO REGISTRADO COM SUCESSO!");
        System.out.println("-----------------------------------------------");
        System.out.println("ID do Cancelamento: " + this.getId());
        System.out.println("Cliente: " + this.getCliente().getNome());
        System.out.println("Barbeiro: " + this.getBarbeiro().getNome());
        System.out.println("Data/Hora do Cancelamento: " + this.getDataHora());
        System.out.println("Agendamento Cancelado (ID): " + agendamento.getId());
        System.out.println("-----------------------------------------------");
    }

    /**
     * Exibe as informações do cancelamento
     */
    public void exibirInformacoes() {
        System.out.println("===============================================");
        System.out.println("           INFORMACOES DO CANCELAMENTO");
        System.out.println("===============================================");
        System.out.println("- ID: " + this.getId());
        System.out.println("- Cliente: " + this.getCliente().getNome());
        System.out.println("- Barbeiro: " + this.getBarbeiro().getNome());
        System.out.println("- Data/Hora: " + this.getDataHora());
        System.out.println("===============================================");
    }

    @Override
    public String toString() {
        return String.format("Cancelamento[id=%d, cliente=%s, barbeiro=%s, dataHora=%s]",
                           id, cliente.getNome(), barbeiro.getNome(), dataHora);
    }
}