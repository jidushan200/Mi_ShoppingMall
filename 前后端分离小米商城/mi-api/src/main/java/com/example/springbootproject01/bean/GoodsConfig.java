package com.example.springbootproject01.bean;

public class GoodsConfig {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_config.id
     *
     * @mbggenerated Sat Jun 18 08:39:01 CST 2022
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_config.goods_id
     *
     * @mbggenerated Sat Jun 18 08:39:01 CST 2022
     */
    private Integer goodsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_config.config
     *
     * @mbggenerated Sat Jun 18 08:39:01 CST 2022
     */
    private String config;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_config.id
     *
     * @return the value of goods_config.id
     *
     * @mbggenerated Sat Jun 18 08:39:01 CST 2022
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_config.id
     *
     * @param id the value for goods_config.id
     *
     * @mbggenerated Sat Jun 18 08:39:01 CST 2022
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_config.goods_id
     *
     * @return the value of goods_config.goods_id
     *
     * @mbggenerated Sat Jun 18 08:39:01 CST 2022
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_config.goods_id
     *
     * @param goodsId the value for goods_config.goods_id
     *
     * @mbggenerated Sat Jun 18 08:39:01 CST 2022
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_config.config
     *
     * @return the value of goods_config.config
     *
     * @mbggenerated Sat Jun 18 08:39:01 CST 2022
     */
    public String getConfig() {
        return config;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_config.config
     *
     * @param config the value for goods_config.config
     *
     * @mbggenerated Sat Jun 18 08:39:01 CST 2022
     */
    public void setConfig(String config) {
        this.config = config == null ? null : config.trim();
    }
}