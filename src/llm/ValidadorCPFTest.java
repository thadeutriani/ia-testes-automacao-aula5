import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ValidadorCPFTest {

    private final ValidadorCPF sut = new ValidadorCPF();

    @Nested
    @DisplayName("Casos válidos")
    class CasosValidos {

        @Test
        @DisplayName("Deve aceitar CPF válido apenas com dígitos")
        void deveAceitarCpfValidoSomenteDigitos() {
            // CPF de exemplo amplamente usado em testes/demos
            assertTrue(sut.isValido("52998224725"));
        }

        @Test
        @DisplayName("Deve aceitar CPF válido com máscara e separadores (remove não-dígitos)")
        void deveAceitarCpfValidoComMascara() {
            assertTrue(sut.isValido("529.982.247-25"));
        }

        @Test
        @DisplayName("Deve aceitar CPF válido com espaços e caracteres variados (remove não-dígitos)")
        void deveAceitarCpfValidoComCaracteresVariados() {
            assertTrue(sut.isValido("  529 982 247-25  "));
        }
    }

    @Nested
    @DisplayName("Casos inválidos")
    class CasosInvalidos {

        @Test
        @DisplayName("Deve retornar false para entrada nula")
        void deveRejeitarNulo() {
            assertFalse(sut.isValido(null));
        }

        @ParameterizedTest(name = "Deve rejeitar vazio/branco: \"{0}\"")
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   ", "\t", "\n"})
        @DisplayName("Deve rejeitar string vazia ou em branco")
        void deveRejeitarVazioOuBranco(String cpf) {
            assertFalse(sut.isValido(cpf));
        }

        @ParameterizedTest(name = "Deve rejeitar tamanho incorreto: \"{0}\"")
        @ValueSource(strings = {
                "1234567890",       // 10 dígitos
                "123456789012",     // 12 dígitos
                "529.982.247-2",    // 10 dígitos após limpar
                "529.982.247-250"   // 12 dígitos após limpar
        })
        @DisplayName("Deve rejeitar CPF com tamanho diferente de 11 dígitos (após limpar)")
        void deveRejeitarTamanhoIncorreto(String cpf) {
            assertFalse(sut.isValido(cpf));
        }

        @ParameterizedTest(name = "Deve rejeitar todos dígitos iguais: {0}")
        @ValueSource(strings = {
                "00000000000",
                "11111111111",
                "22222222222",
                "33333333333",
                "44444444444",
                "55555555555",
                "66666666666",
                "77777777777",
                "88888888888",
                "99999999999",
                "000.000.000-00",
                "999.999.999-99"
        })
        @DisplayName("Deve rejeitar CPFs com todos os dígitos iguais")
        void deveRejeitarTodosDigitosIguais(String cpf) {
            assertFalse(sut.isValido(cpf));
        }

        @Test
        @DisplayName("Deve rejeitar CPF com dígitos verificadores incorretos")
        void deveRejeitarCpfComDigitosVerificadoresIncorretos() {
            // Mesmo prefixo do válido, alterando DV para ficar inválido
            assertFalse(sut.isValido("52998224724"));
            assertFalse(sut.isValido("52998224726"));
        }

        @Test
        @DisplayName("Deve rejeitar quando a limpeza remove tudo (sem dígitos)")
        void deveRejeitarQuandoNaoHaDigitos() {
            assertFalse(sut.isValido("abc.def-ghi"));
            assertFalse(sut.isValido("###-@@@"));
        }
    }
}
