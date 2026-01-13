import java.time.LocalDate;
import java.time.LocalTime;

public class Disponibilidade{
    private static int id = 0;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private boolean disponivel;

    public Disponibilidade(LocalDate data, LocalTime horaInicio, LocalTime horaFim, boolean disponivel) {
        Disponibilidade.id++;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.disponivel = disponivel;
    }

    public Disponibilidade(Disponibilidade original) {
        this.data = original.getData();
        this.horaInicio = original.getHoraInicio();
        this.horaFim = original.getHoraFim();
        this.disponivel = original.isDisponivel();
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Disponibilidade.id = id;
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

    @Override
    public String toString() {
        String disponibilidade;
        if (this.disponivel) {
            disponibilidade = "DISPONIVEL";
        }else {
            disponibilidade = "INDISPONIVEL";
        }
        return String.format("%02d/%02d/%04d;%02d:%02d;%s", this.getData().getDayOfMonth(), this.getData().getMonthValue(), this.getData().getYear(),
                            this.getHoraInicio().getHour(), this.getHoraInicio().getMinute(), disponibilidade);
    }

    public void exibirInformacoes() {
        System.out.printf("\t# Data: %02d/%02d/%04d | ", this.getData().getDayOfMonth(), this.getData().getMonthValue(), this.getData().getYear());
        System.out.printf("%02d:%02d - %02d:%02d ", this.getHoraInicio().getHour(), this.getHoraInicio().getMinute(),
                                                            this.getHoraFim().getHour(), this.getHoraFim().getMinute());
        if (this.isDisponivel()) {
            System.out.printf("(DISPONIVEL)");
        }else {
            System.out.printf("(INDISPONIVEL)");
        }
    }
}
