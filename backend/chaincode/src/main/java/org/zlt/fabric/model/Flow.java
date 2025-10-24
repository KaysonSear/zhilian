package org.zlt.fabric.model;

import com.alibaba.fastjson.JSON;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

/**
 * chaincode
 * 2024/6/18 下午2:57
 * 上链流量信息
 *
 * @author chenyi
 * @since
 **/

@DataType
public class Flow {

    @Property
    private String id;

    @Property
    private String srcIp;

    @Property
    private String dstIp;

    @Property
    private String srcPort;

    @Property
    private String dstPort;

    @Property
    private String protocol;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp;
    }

    public String getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(String srcPort) {
        this.srcPort = srcPort;
    }

    public String getDstPort() {
        return dstPort;
    }

    public void setDstPort(String dstPort) {
        this.dstPort = dstPort;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flow flow = (Flow) o;
        return Objects.equals(id, flow.id) && Objects.equals(srcIp, flow.srcIp) && Objects.equals(dstIp, flow.dstIp) && Objects.equals(srcPort, flow.srcPort) && Objects.equals(dstPort, flow.dstPort) && Objects.equals(protocol, flow.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, srcIp, dstIp, srcPort, dstPort, protocol);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
