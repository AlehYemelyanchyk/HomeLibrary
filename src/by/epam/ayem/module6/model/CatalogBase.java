package by.epam.ayem.module6.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleh Yemelyanchyk on 9/29/2019.
 */
public class CatalogBase {
    private List<Catalog> catalogs;

    public CatalogBase() {
        this.catalogs = new ArrayList<>();
    }

    public List<Catalog> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(List<Catalog> catalogs) {
        this.catalogs = catalogs;
    }
}
