package no.nibio.ipmdisipproxy.model;

import no.nibio.ipmdisipproxy.exception.BadRequestException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static no.nibio.ipmdisipproxy.model.Disease.*;

public enum Crop {

    WINTER_WHEAT("TRZAW", "winter wheat", BROWN_RUST, DTR, POWDERY_MILDEW, SEPTORIA_TRITICI, YELLOW_RUST),
    WINTER_BARLEY("HORVW", "winter barley", LEAF_BLOTCH, LEAF_RUST, NET_BLOTCH, POWDERY_MILDEW, RAMULARIA),
    WINTER_RYE("SECCW", "winter rye", BROWN_RUST, LEAF_BLOTCH, POWDERY_MILDEW),
    WINTER_TRITICALE("TTLWI", "winter triticale", BROWN_RUST, LEAF_BLOTCH, POWDERY_MILDEW, YELLOW_RUST),
    SUMMER_BARLEY("HORVS", "summer barley", LEAF_BLOTCH, LEAF_RUST, NET_BLOTCH, POWDERY_MILDEW, RAMULARIA);

    private final String eppoCode;
    private final String isipName;
    private final List<Disease> diseases;

    Crop(String eppoCode, String isipName, Disease... diseases) {
        this.eppoCode = eppoCode;
        this.isipName = isipName;
        this.diseases = Arrays.asList(diseases);
    }

    public static String getEppoCodes() {
        return Stream.of(Crop.values())
                .map(Crop::getEppoCode)
                .collect(Collectors.joining(", "));
    }

    public static Crop fromIsipName(String displayName) {
        for (Crop crop : Crop.values()) {
            if (crop.getIsipName().equalsIgnoreCase(displayName)) {
                return crop;
            }
        }
        throw new BadRequestException("Unknown crop with ISIP name: " + displayName);
    }

    public static Crop fromEppoCode(String eppoCode) {
        for (Crop crop : Crop.values()) {
            if (crop.getEppoCode().equalsIgnoreCase(eppoCode)) {
                return crop;
            }
        }
        throw new BadRequestException("Unknown crop with EPPO code: " + eppoCode);
    }

    public boolean isCropDisease(Disease disease) {
        return diseases.contains(disease);
    }

    public String getIsipName() {
        return isipName;
    }

    public String getEppoCode() {
        return eppoCode;
    }
}
