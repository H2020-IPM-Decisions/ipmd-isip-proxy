package no.nibio.ipmdisipproxy.model;

import no.nibio.ipmdisipproxy.exception.BadRequestException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @since 1.0.0
 */
public enum Disease {

    BROWN_RUST("PUCCRE", "brown rust"),
    DTR("PYRNTR", "dtr"),
    POWDERY_MILDEW("ERYSGR", "powdery mildew"),
    SEPTORIA_TRITICI("SEPTTR", "septoria tritici"),
    YELLOW_RUST("PUCCST", "yellow rust"),
    LEAF_BLOTCH("RHYNSE", "leaf blotch"),
    LEAF_RUST("PUCCHD", "leaf rust"),
    NET_BLOTCH("PYRNTE", "net blotch"),
    RAMULARIA("RAMUCC", "ramularia");

    private final String eppoCode;
    private final String isipName;

    Disease(String eppoCode, String isipName) {
        this.eppoCode = eppoCode;
        this.isipName = isipName;
    }

    public static String getIsipNames() {
        return Stream.of(Disease.values())
                .map(Disease::getIsipName)
                .collect(Collectors.joining(", "));
    }

    public static Disease fromIsipName(String isipName) {
        for (Disease disease : Disease.values()) {
            if (disease.getIsipName().equalsIgnoreCase(isipName)) {
                return disease;
            }
        }
        throw new BadRequestException("Unknown disease with ISIP name: " + isipName);
    }

    public static Disease fromEppoCode(String eppoCode) {
        for (Disease disease : Disease.values()) {
            if (disease.getEppoCode().equalsIgnoreCase(eppoCode)) {
                return disease;
            }
        }
        throw new BadRequestException("Unknown disease with EPPO code: " + eppoCode);
    }

    public String getEppoCode() {
        return eppoCode;
    }

    public String getIsipName() {
        return isipName;
    }


}
