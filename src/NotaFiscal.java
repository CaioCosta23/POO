import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NotaFiscal {
    private int id;
    private String numero;
    private LocalDate dataEmissao;
    private double valorTotal;
    private Agendamento agendamento;
    private EnumFormaPag formaPagamento;
    private EnumStatusPag statusPagamento;

    public NotaFiscal(int id, String numero, LocalDate dataEmissao, Agendamento agendamento, 
                      EnumFormaPag formaPagamento, EnumStatusPag statusPagamento) {
        this.id = id;
        this.numero = numero;
        this.dataEmissao = dataEmissao;
        this.agendamento = agendamento;
        this.formaPagamento = formaPagamento;
        this.statusPagamento = statusPagamento;
        this.valorTotal = calcularValorTotal();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDataEmissao() {
        return this.dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public double getValorTotal() {
        return this.valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Agendamento getAgendamento() {
        return this.agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
        // Recalcula o valor total quando o agendamento é alterado
        this.valorTotal = calcularValorTotal();
    }

    public EnumFormaPag getFormaPagamento() {
        return this.formaPagamento;
    }

    public void setFormaPagamento(EnumFormaPag formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public EnumStatusPag getStatusPagamento() {
        return this.statusPagamento;
    }

    public void setStatusPagamento(EnumStatusPag statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    /**
     * Calcula o valor total da nota fiscal baseado no serviço do agendamento
     * @return Valor total calculado
     */
    public double calcularValorTotal() {
        if (this.agendamento != null && this.agendamento.getServico() != null) {
            return this.agendamento.getServico().getPreco();
        }
        return 0.0;
    }

    /**
     * Gera a nota fiscal e exibe as informações
     */
    public void gerar() {
        // Recalcula o valor antes de gerar
        this.valorTotal = calcularValorTotal();
        
        System.out.println("\n===============================================");
        System.out.println("               NOTA FISCAL");
        System.out.println("===============================================");
        System.out.println("Numero da NF: " + this.getNumero());
        System.out.println("Data de Emissao: " + this.getDataEmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("-----------------------------------------------");
        
        if (this.agendamento != null) {
            System.out.println("DADOS DO AGENDAMENTO:");
            System.out.println("ID: " + this.agendamento.getId());
            System.out.println("Cliente: " + this.agendamento.getCliente().getNome());
            System.out.println("CPF: " + this.agendamento.getCliente().getCpf());
            System.out.println("Barbeiro: " + this.agendamento.getBarbeiro().getNome());
            
            if (this.agendamento.getServico() != null) {
                System.out.println("-----------------------------------------------");
                System.out.println("SERVICO PRESTADO:");
                System.out.println("Servico: " + this.agendamento.getServico().getNome());
                System.out.println("Descricao: " + this.agendamento.getServico().getDescricao());
                System.out.printf("Valor: R$ %.2f\n", this.agendamento.getServico().getPreco());
            }
        }
        
        System.out.println("-----------------------------------------------");
        System.out.printf("VALOR TOTAL: R$ %.2f\n", this.getValorTotal());
        System.out.println("-----------------------------------------------");
        System.out.println("Forma de Pagamento: " + formatarFormaPagamento(this.formaPagamento));
        System.out.println("Status do Pagamento: " + formatarStatusPagamento(this.statusPagamento));
        System.out.println("===============================================\n");
    }

    /**
     * Formata a forma de pagamento para exibição
     */
    private String formatarFormaPagamento(EnumFormaPag forma) {
        switch (forma) {
            case DINHEIRO:
                return "Dinheiro";
            case CARTAO:
                return "Cartao de Credito";
            case CARTAO_DEBITO:
                return "Cartao de Debito";
            case PIX:
                return "PIX";
            default:
                return "Nao especificado";
        }
    }

    /**
     * Formata o status de pagamento para exibição
     */
    private String formatarStatusPagamento(EnumStatusPag status) {
        switch (status) {
            case PENDENTE:
                return "Pendente";
            case PROCESSANDO:
                return "Processando";
            case APROVADO:
                return "Aprovado";
            case RECUSADO:
                return "Recusado";
            default:
                return "Nao especificado";
        }
    }

    /**
     * Exibe informações resumidas da nota fiscal
     */
    public void exibirInformacoes() {
        System.out.println("-----------------------------------------------");
        System.out.println("- ID da Nota Fiscal: " + this.getId());
        System.out.println("- Numero: " + this.getNumero());
        System.out.println("- Data de Emissao: " + this.getDataEmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.printf("- Valor Total: R$ %.2f\n", this.getValorTotal());
        System.out.println("- Forma de Pagamento: " + formatarFormaPagamento(this.formaPagamento));
        System.out.println("- Status: " + formatarStatusPagamento(this.statusPagamento));
        System.out.println("-----------------------------------------------");
    }

    /**
     * Processa o pagamento alterando o status
     */
    public void processarPagamento() {
        if (this.statusPagamento == EnumStatusPag.PENDENTE) {
            this.statusPagamento = EnumStatusPag.PROCESSANDO;
            System.out.println("Pagamento em processamento...");
        }
    }

    /**
     * Aprova o pagamento
     */
    public void aprovarPagamento() {
        if (this.statusPagamento == EnumStatusPag.PROCESSANDO) {
            this.statusPagamento = EnumStatusPag.APROVADO;
            System.out.println("Pagamento aprovado com sucesso!");
        } else {
            System.out.println("Erro: Pagamento nao esta em processamento.");
        }
    }

    /**
     * Recusa o pagamento
     */
    public void recusarPagamento(String motivo) {
        this.statusPagamento = EnumStatusPag.RECUSADO;
        System.out.println("Pagamento recusado. Motivo: " + motivo);
    }

    @Override
    public String toString() {
        return String.format("NotaFiscal[id=%d, numero=%s, valor=R$%.2f, status=%s]",
                           id, numero, valorTotal, statusPagamento);
    }
}