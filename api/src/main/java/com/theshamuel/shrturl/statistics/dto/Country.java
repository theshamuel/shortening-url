package com.theshamuel.shrturl.statistics.dto;

/**
 * The Country entity.
 *
 * @author Alex Gladkikh
 */
public class Country extends BaseMetric {
    /**
     * Instantiates a new Country.
     */
    public Country() {
    }

    /**
     * Instantiates a new Country.
     *
     * @param label  the label
     * @param clicks the clicks
     */
    public Country(String label, Long clicks) {
        super(label, clicks);
    }
}
