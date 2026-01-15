import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa uma notificacao relacionada a servicos/agendamentos.
 * A notificacao pode ser enviada por EMAIL, SMS ou PUSH e tambem pode ser armazenada em arquivo.
 */
public class NotificacaoServico {
    private int id;
    private EnumTipoNotificacao tipo;
    private String destinatarioCpf; // cpf do cliente (ou "TODOS")
    private String canal; // EMAIL | SMS | PUSH
    private LocalDate data;
    private LocalTime hora;

    // Construtor "simples" para manter compatibilidade com uso anterior
    public NotificacaoServico(int id, EnumTipoNotificacao tipo) {
        this(id, tipo, "TODOS", "PUSH", LocalDate.now(), LocalTime.now());
    }

    public NotificacaoServico(int id, EnumTipoNotificacao tipo, String destinatarioCpf, String canal, LocalDate data, LocalTime hora) {
        this.id = id;
        this.tipo = tipo;
        this.destinatarioCpf = destinatarioCpf;
        this.canal = canal;
        this.data = data;
        this.hora = hora;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EnumTipoNotificacao getTipo() {
        return this.tipo;
    }

    public void setTipo(EnumTipoNotificacao tipo) {
        this.tipo = tipo;
    }

    public String getDestinatarioCpf() {
        return this.destinatarioCpf;
    }

    public void setDestinatarioCpf(String destinatarioCpf) {
        this.destinatarioCpf = destinatarioCpf;
    }

    public String getCanal() {
        return this.canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return this.hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void enviarEmail() {
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.printf("[EMAIL] Para: %s | ", this.destinatarioCpf);
        this.tipo.notificacao();
        System.out.println("----------------------------------------------------------------------------------------------------------------");
    }

    public void enviarSMS() {
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.printf("[SMS] Para: %s | ", this.destinatarioCpf);
        this.tipo.notificacao();
        System.out.println("----------------------------------------------------------------------------------------------------------------");
    }

    public void enviarPush() {
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.printf("[PUSH] Para: %s | ", this.destinatarioCpf);
        this.tipo.notificacao();
        System.out.println("----------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public String toString() {
        // id;tipo;destinatario;canal;dd/MM/yyyy;HH:mm
        return String.format("%d;%s;%s;%s;%s;%s",
                this.id,
                this.tipo.name(),
                this.destinatarioCpf,
                this.canal,
                this.data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                this.hora.format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}