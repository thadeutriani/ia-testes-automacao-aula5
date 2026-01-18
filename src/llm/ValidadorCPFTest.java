import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ValidadorCPFTest {

    private final ValidadorCPF validador = new ValidadorCPF();

    @Test
    void deveRetornarFalsoQuandoCpfForNulo() {
        assertFalse(validador.isValido(null));
    }

    @Test
    void deveRetornarFalsoQuandoCpfTiverTamanhoInvalido() {
        assertFalse(validador.isValido("123"));
    }

    @Test
    void deveRetornarFalsoQuandoCpfPossuirTodosDigitosIguais() {
        assertFalse(validador.isValido("111.111.111-11"));
    }

    @Test
    void deveRetornarVerdadeiroParaCpfValido() {
        assertTrue(validador.isValido("529.982.247-25"));
    }

    @Test
    void deveRetornarFalsoParaCpfComDigitosVerificadoresInvalidos() {
        assertFalse(validador.isValido("529.982.247-26"));
    }
}

