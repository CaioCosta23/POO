import java.time.LocalDate;

public class Agendamento {
    private int id;
    private Cliente cliente;
    private Barbeiro barbeiro;
    private Servico servico;
    private LocalDate data;
    private final EnumStatusAgend status;

    public Agendamento(int id, Cliente cliente, Barbeiro barbeiro, Servico servico, LocalDate data) {
        this.id = id;
        this.cliente = cliente;
        this.barbeiro = barbeiro;
        this.servico = servico;
        this.data = data;
        this.status = EnumStatusAgend.AGENDADO;
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
    public Servico getServico() {
        return this.servico;
    }

    public void setServico(Servico servico) {
        this.servico = new Servico(servico);
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%s;%d;%02d/%02d/%04d", this.getId(), this.getCliente().getCpf(), this.getBarbeiro().getCpf(), 
                            this.getServico().getId(), this.getData().getDayOfMonth(), this.getData().getMonthValue(), this.getData().getDayOfYear());
    }

    public void exibirInformacoes() {
        System.out.println("- ID: " + this.getId());
        System.out.println("- Cliente: " + this.getCliente().getNome());
        System.out.println("- Pretador de servico (barbeiro): " + this.getBarbeiro().getNome());
        System.out.printf("- Servico: %s(%s)\n", this.getServico().getNome(), this.getServico().getDescricao());
        System.out.println("- Cliente: " + this.getBarbeiro().getNome());
        System.out.println("Status: " + this.status);
    }
}
