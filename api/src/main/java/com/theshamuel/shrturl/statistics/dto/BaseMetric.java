package com.theshamuel.shrturl.statistics.dto;

/**
 * The Base metric params entity.
 *
 * @author Alex Gladkikh
 */
public class BaseMetric {

    private String label;

    private Long clicks;

    /**
     * Gets label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets label.
     *
     * @param label the label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets clicks.
     *
     * @return the clicks
     */
    public Long getClicks() {
        return clicks;
    }

    /**
     * Sets clicks.
     *
     * @param clicks the clicks
     */
    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }
}
