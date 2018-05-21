package com.theshamuel.shrturl.statistics.dto;

/**
 * The Operation system entity.
 *
 * @author Alex Gladkikh
 */
public class OperationSystem extends BaseMetric {
    /**
     * Instantiates a new Operation system.
     */
    public OperationSystem() {
    }

    /**
     * Instantiates a new Operation system.
     *
     * @param label  the label
     * @param clicks the clicks
     */
    public OperationSystem(String label, Long clicks) {
        super(label, clicks);
    }
}
