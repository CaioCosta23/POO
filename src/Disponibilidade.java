import java.time.LocalDate;
import java.time.LocalDateTime;

public class Disponibilidade implements Impressao{
    private LocalDate data;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFim;

    public Disponibilidade(LocalDate data, LocalDateTime horaInicio, LocalDateTime horaFim) {
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDateTime getHoraInicio() {
        return this.horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFim() {
        return this.horaFim;
    }

    public void setHoraFim(LocalDateTime horaFim) {
        this.horaFim = horaFim;
    }

    @Override
    public void exibirInformacoes() {
        System.out.printf("- Data: %02d/%02d/%04d\n", this.getData().getDayOfMonth(), this.getData().getMonthValue(), this.getData().getYear());
        System.out.printf("%02d:%02d - %02d:¨%02d", this.getHoraInicio().getHour(), this.getHoraInicio().getMinute(),
                                                            this.getHoraFim().getHour(), this.getHoraFim().getMinute());
    }
}
