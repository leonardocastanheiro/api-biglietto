package br.com.laghettohoteis.api_biglietto.main.domain.guest;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum AgeRange {
    ADULT(1, "Adulto", "A"),
    CHILD_0_5(2, "Criança (0 a 5 anos)", "C0-5", "0-5"),
    CHILD_6_12(3, "Criança (6 a 11 anos)", "C6-11", "6-11");

    @Getter
    private final int code;
    private final String[] labels;

    AgeRange(int code, String... labels) {
        this.code = code;
        this.labels = labels;
    }

    public String[] getLabels() {
        return labels.clone();
    }

    @Override
    public String toString() {
        return labels[0];
    }

    private static final Map<String, AgeRange> BY_LABEL =
            Arrays.stream(values())
                    .flatMap(ar -> Arrays.stream(ar.labels)
                            .map(lbl -> Map.entry(lbl.trim().toUpperCase(), ar)))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    private static final Map<Integer, AgeRange> BY_CODE =
            Arrays.stream(values())
                    .collect(Collectors.toMap(AgeRange::getCode, ar -> ar));

    public static AgeRange fromString(String label) {
        if (label == null) {
            throw new IllegalArgumentException("Label não pode ser nulo");
        }
        AgeRange ar = BY_LABEL.get(label.trim().toUpperCase());
        if (ar == null) {
            throw new IllegalArgumentException("AgeRange desconhecido: " + label);
        }
        return ar;
    }

    public static AgeRange fromCode(int code) {
        AgeRange ar = BY_CODE.get(code);
        if (ar == null) {
            throw new IllegalArgumentException("AgeRange desconhecido para código: " + code);
        }
        return ar;
    }
}
