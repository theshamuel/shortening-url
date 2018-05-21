package com.theshamuel.shrturl.statistics.dto;

/**
 * The Browser entity.
 *
 * @author Alex Gladkikh
 */
public class Browser extends BaseMetric {

    /**
     * Instantiates a new Browser.
     */
    public Browser() {
    }

    /**
     * Instantiates a new Browser.
     *
     * @param label  the label
     * @param clicks the clicks
     */
    public Browser(String label, Long clicks) {

        super(label, clicks);
    }
}
