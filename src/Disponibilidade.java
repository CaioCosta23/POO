import java.time.LocalDate;
import java.time.LocalTime;

public class Disponibilidade{
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private boolean disponivel;

    public Disponibilidade(LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
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

    public LocalTime getHoraInicio() {
        return this.horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return this.horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public boolean isDisponivel() {
        return this.disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void exibirInformacoes() {
        System.out.printf("- Data: %02d/%02d/%04d\n", this.getData().getDayOfMonth(), this.getData().getMonthValue(), this.getData().getYear());
        System.out.printf("%02d:%02d - %02d:¨%02d", this.getHoraInicio().getHour(), this.getHoraInicio().getMinute(),
                                                            this.getHoraFim().getHour(), this.getHoraFim().getMinute());
    }
}
