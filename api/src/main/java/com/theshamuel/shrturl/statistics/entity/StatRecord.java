/**
 * This project is a project which provide API for getting short url by long URI.
 *
 * Here is short description: ( for more detailed description please read README.md or
 * go to https://github.com/theshamuel/shortening-url )
 *
 * Back-end: Spring (Spring Boot, Spring IoC, Spring Data, Spring Test), JWT library, Java8
 * DB: MongoDB
 * Tools: git,maven,docker.
 *
 */
package com.theshamuel.shrturl.statistics.entity;

import com.theshamuel.shrturl.baseclasses.entity.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The StatRecord entity class.
 *
 * @author Alex Gladkikh
 */
@Document(collection = "statistics")
public class StatRecord extends BaseEntity {

    @Field("shortUrl")
    private String shortUrl;

    @Field("ipaddress")
    private String ipaddress;

    @Field("operationSystem")
    private String operationSystem;

    @Field("browser")
    private String browser;

    @Field("country")
    private String country;

    /**
     * Gets short URL.
     *
     * @return the short URL
     */
    public String getShortUrl() {
        return shortUrl;
    }

    /**
     * Sets short URL.
     *
     * @param shortUrl the short URL
     */
    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    /**
     * Gets ipaddress.
     *
     * @return the ipaddress
     */
    public String getIpaddress() {
        return ipaddress;
    }

    /**
     * Sets ipaddress.
     *
     * @param ipaddress the ipaddress
     */
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    /**
     * Gets operation system.
     *
     * @return the operation system
     */
    public String getOperationSystem() {
        return operationSystem;
    }

    /**
     * Sets operation system.
     *
     * @param operationSystem the operation system
     */
    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    /**
     * Gets browser.
     *
     * @return the browser
     */
    public String getBrowser() {
        return browser;
    }

    /**
     * Sets browser.
     *
     * @param browser the browser
     */
    public void setBrowser(String browser) {
        this.browser = browser;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {

        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof StatRecord)) return false;

        StatRecord that = (StatRecord) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(shortUrl, that.shortUrl)
                .append(ipaddress, that.ipaddress)
                .append(operationSystem, that.operationSystem)
                .append(browser, that.browser)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(shortUrl)
                .append(ipaddress)
                .append(operationSystem)
                .append(browser)
                .toHashCode();
    }
}
