package org.zlt.fabric.model;

import com.alibaba.fastjson.JSON;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

/**
 * chaincode
 * 2024/6/18 上午10:42
 * 上链规则
 *
 * @author chenyi
 * @since
 **/

@DataType
public class Rule {

    @Property
    private String docType;

    @Property
    private String id;

    @Property
    private String pattern;

    @Property
    private String description;

    @Property
    private String userId;

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getPattern() {
        return pattern;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(docType, rule.docType) && Objects.equals(id, rule.id) && Objects.equals(pattern, rule.pattern) && Objects.equals(description, rule.description) && Objects.equals(userId, rule.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docType, id, pattern, description, userId);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
