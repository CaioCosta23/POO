import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PoliticaCancelamento {
    private int id;
    private LocalDateTime prazoMinimoCancelamento;
    private double multa;
    private String descricao;

    public PoliticaCancelamento(int id, LocalDateTime prazoMinimoCancelamento, double multa, String descricao) {
        this.id = id;
        this.prazoMinimoCancelamento = prazoMinimoCancelamento;
        this.multa = multa;
        this.descricao = descricao;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getPrazoMinimoCancelamento() {
        return this.prazoMinimoCancelamento;
    }

    public void setPrazoMinimoCancelamento(LocalDateTime prazoMinimoCancelamento) {
        this.prazoMinimoCancelamento = prazoMinimoCancelamento;
    }

    public double getMulta() {
        return this.multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Valida se o cancelamento pode ser realizado com base no prazo mínimo
     * @param dataAgendamento Data e hora do agendamento que se deseja cancelar
     * @return true se o cancelamento é permitido, false caso contrário
     */
    public boolean validarCancelamento(LocalDateTime dataAgendamento) {
        LocalDateTime agora = LocalDateTime.now();
        long horasAteAgendamento = ChronoUnit.HOURS.between(agora, dataAgendamento);
        long horasPrazoMinimo = ChronoUnit.HOURS.between(agora, prazoMinimoCancelamento);
        
        // Permite cancelamento se ainda está dentro do prazo mínimo
        return horasAteAgendamento >= horasPrazoMinimo;
    }

    /**
     * Calcula o valor da multa baseado no valor do serviço e nas regras da política
     * @param valorServico Valor do serviço agendado
     * @param dataAgendamento Data e hora do agendamento
     * @return Valor da multa a ser aplicada (0 se não houver multa)
     */
    public double calcularMulta(double valorServico, LocalDateTime dataAgendamento) {
        if (validarCancelamento(dataAgendamento)) {
            // Cancelamento dentro do prazo - sem multa
            return 0.0;
        } else {
            // Cancelamento fora do prazo - aplica multa percentual
            return valorServico * (this.multa / 100.0);
        }
    }

    /**
     * Exibe as informações da política de cancelamento
     */
    public void exibirInformacoes() {
        System.out.println("===============================================");
        System.out.println("       POLITICA DE CANCELAMENTO");
        System.out.println("===============================================");
        System.out.println("- ID: " + this.getId());
        System.out.println("- Descricao: " + this.getDescricao());
        System.out.println("- Prazo Minimo para Cancelamento: " + this.getPrazoMinimoCancelamento());
        System.out.printf("- Multa: %.2f%%\n", this.getMulta());
        System.out.println("===============================================");
    }

    @Override
    public String toString() {
        return String.format("PoliticaCancelamento[id=%d, multa=%.2f%%, descricao=%s]", 
                           id, multa, descricao);
    }
}