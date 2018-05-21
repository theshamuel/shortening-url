/**
 * This project is a project which provide API for getting short url by long URI.
 *
 * Here is short description: ( for more detailed description please read README.md or
 * go to https://github.com/theshamuel/shortening-URL )
 *
 * Back-end: Spring (Spring Boot, Spring IoC, Spring Data, Spring Test), JWT library, Java8
 * DB: MongoDB
 * Tools: git,maven,docker.
 *
 */
package com.theshamuel.shrturl.statistics.dto;

import com.theshamuel.shrturl.exceptions.NotFoundParamsException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * The data transaction object entity of statistic info by particular short URL.
 *
 * @author Alex Gladkikh
 */
public class StatRecordDto {

    private String shortUrl;

    private String userLogin;

    private List<Country> country;

    private List<Browser> browser;

    private List<OperationSystem> operationSystem;

    private Long totalClicks;

    /**
     * Instantiates a new Stat record dto.
     */
    public StatRecordDto() {
    }

    /**
     * Instantiates a new Stat record dto.
     *
     * @param builder the builder
     */
    public StatRecordDto (Builder builder){

        setShortUrl(builder.shortUrl);

        setUserLogin(builder.userLogin);

        setCountry(builder.country);

        setBrowser(builder.browser);

        setOperationSystem(builder.operationSystem);

        setTotalClicks(builder.totalClicks);

    }

    /**
     * Gets short url.
     *
     * @return the short url
     */
    public String getShortUrl() {
        return shortUrl;
    }

    /**
     * Sets short url.
     *
     * @param shortUrl the short url
     */
    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    /**
     * Gets user login.
     *
     * @return the user login
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * Sets user login.
     *
     * @param userLogin the user login
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public List<Country> getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(List<Country> country) {
        this.country = country;
    }

    /**
     * Gets browser.
     *
     * @return the browser
     */
    public List<Browser> getBrowser() {
        return browser;
    }

    /**
     * Sets browser.
     *
     * @param browser the browser
     */
    public void setBrowser(List<Browser> browser) {
        this.browser = browser;
    }

    /**
     * Gets operation system.
     *
     * @return the operation system
     */
    public List<OperationSystem> getOperationSystem() {
        return operationSystem;
    }

    /**
     * Sets operation system.
     *
     * @param operationSystem the operation system
     */
    public void setOperationSystem(List<OperationSystem> operationSystem) {
        this.operationSystem = operationSystem;
    }


    /**
     * Gets total clicks.
     *
     * @return the total clicks
     */
    public Long getTotalClicks() {
        return totalClicks;
    }

    /**
     * Sets total clicks.
     *
     * @param totalClicks the total clicks
     */
    public void setTotalClicks(Long totalClicks) {
        this.totalClicks = totalClicks;
    }

    /**
     * Builder builder.
     *
     * @return the builder
     */
    public static Builder builder(){
        return new Builder();
    }

    /**
     * The type Statistic record dto (data transaction object) builder.
     *
     * @author Alex Gladkikh
     */
    public static final class Builder {

        private String shortUrl;

        private String userLogin;

        private List<Country> country;

        private List<Browser> browser;

        private List<OperationSystem> operationSystem;

        private Long totalClicks;

        /**
         * Instantiates a new Statistic record dto builder.
         */
        public Builder() {

        }
        public Builder userLogin (String userLogin){
            this.userLogin = userLogin;
            return this;
        }

        /**
         * Short url stat record dto builder.
         *
         * @param shortUrl the short url
         * @return the stat record dto builder
         */
        public Builder shortUrl(String shortUrl) {
            StringBuilder pathUrl = new StringBuilder();
            if (System.getenv("DOMAIN")!=null && System.getenv("DOMAIN").trim().length()>0) {
                pathUrl.append(System.getenv("DOMAIN"));
                pathUrl.append("/");
                pathUrl.append(shortUrl);
                this.shortUrl = pathUrl.toString();
            }else {
                throw new NotFoundParamsException("Not found variable $DOMAIN intro Environment");
            }
            return this;
        }

        /**
         * Countries stat record dto builder.
         *
         * @param country the countries
         * @return the stat record dto builder
         */
        public Builder country (List<Country> country){
            this.country = country;
            return this;
        }

        /**
         * Browser builder.
         *
         * @param browser the browser
         * @return the builder
         */
        public Builder browser (List<Browser> browser){
            this.browser = browser;
            return this;
        }

        /**
         * Operation system builder.
         *
         * @param os the os
         * @return the builder
         */
        public Builder operationSystem (List<OperationSystem> os){
            this.operationSystem = os;
            return this;
        }

        /**
         * Total clicks stat record dto builder.
         *
         * @param totalClicks the total clicks
         * @return the stat record dto builder
         */
        public Builder totalClicks(Long totalClicks) {
            this.totalClicks = totalClicks;
            return this;
        }

        /**
         * Build stat record dto.
         *
         * @return the stat record dto
         */
        public StatRecordDto build() {
            return new StatRecordDto(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof StatRecordDto)) return false;

        StatRecordDto that = (StatRecordDto) o;

        return new EqualsBuilder()
                .append(shortUrl, that.shortUrl)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(shortUrl)
                .toHashCode();
    }
}
