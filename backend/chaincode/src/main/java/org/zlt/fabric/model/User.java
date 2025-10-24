package org.zlt.fabric.model;

import com.alibaba.fastjson.JSON;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@DataType
public class User {
    @Property
    private final String userId;

    @Property
    private final String name;

    @Property
    private final double password;

    public User(final String userId, final String name, final double password) {
        this.userId = userId;
        this.name = name;
        this.password = password;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        User other = (User) obj;
        return Objects.deepEquals(
                new String[] {getUserId(), getName()},
                new String[] {other.getUserId(), other.getName()})
                &&
                Objects.deepEquals(
                        new double[] {getpassword()},
                        new double[] {other.getpassword()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getName(), getpassword());
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public double getpassword() {
        return password;
    }
}
