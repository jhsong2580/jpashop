package jpashop.jpashop.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum ItemType {
    BOOK("BOOK", "B"), MOVIE("MOVIE", "M"), ALBUM("ALBUM", "A");

    private String webPageDype;
    private String dbDtype;

    public String getWebPageDype() {
        return webPageDype;
    }

    public String getDbDtype() {
        return dbDtype;
    }

    public static List<String> getItemTypeNamesForPage() {
        return Arrays.stream(ItemType.values()).map(ItemType::getWebPageDype)
            .collect(Collectors.toList());
    }
}
