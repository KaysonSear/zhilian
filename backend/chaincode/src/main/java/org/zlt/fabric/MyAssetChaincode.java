package org.zlt.fabric;

import com.alibaba.fastjson.JSON;
import org.hyperledger.fabric.Logger;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyModification;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;
import org.zlt.fabric.model.Flow;
import org.zlt.fabric.model.Rule;
import org.zlt.fabric.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Contract(name = "mycc")
@Default
public class MyAssetChaincode implements ContractInterface {

    private static final Logger log = Logger.getLogger(MyAssetChaincode.class.getName());


    public  MyAssetChaincode() {}

    /**
     * 调用初始化函数
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void init(final Context ctx) {
        initFunctions(ctx);
    }

    /**
     * 初始化函数
     * @param ctx 入参
     * @return void
     * @author Chenyi
     * @date 2024/6/18 上午8:51
     **/
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void initFunctions(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        stub.putStringState("key","value");
    }

    /**
     * 上传规则
     * @param ctx
     * @param pattern
     * @param description 入参
     * @return String
     * @author Chenyi
     * @date 2024/6/18 上午10:55
     **/
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String insertRule(Context ctx, String pattern, String description, String userId, String ruleId) {
        ChaincodeStub stub = ctx.getStub();

        Rule rule = new Rule();
        rule.setId(ruleId);
        rule.setUserId(userId);
        rule.setDocType("RULE");
        rule.setPattern(pattern);
        rule.setDescription(description);

        stub.putStringState(rule.getId(), JSON.toJSONString(rule));
        log.info("insert rule: " + String.valueOf(rule));
        return "200";
    }

    /**
     * 获取全部规则
     * @param ctx 入参
     * @return List<Rule>
     * @author Chenyi
     * @date 2024/6/18 下午1:41
     **/
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String getAllRules(Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        String query = String.format("{\"selector\":{\"docType\":\"%s\"}}", "RULE");
        QueryResultsIterator<KeyValue> results = stub.getQueryResult(query);
        if (results.iterator().hasNext()) {
            StringBuilder output = new StringBuilder();
            for (KeyValue keyValue : results) {
                if (keyValue.getStringValue() != null || !keyValue.getStringValue().isEmpty()) {
                    output.append(keyValue.getStringValue());
                }
            }
            return output.toString();
        }
        return "404";
    }

    /**
     * 取消上链
     * @param ctx
     * @param ruleId 入参
     * @return String
     * @author Chenyi
     * @date 2024/6/19 下午2:09
     **/
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String deleteRule(Context ctx, String ruleId) {
        ChaincodeStub stub = ctx.getStub();
        stub.delState(ruleId);
        return "200";
    }

    /**
     * 上链流量
     * @param ctx
     * @param flowId
     * @param srcIp
     * @param dstIp
     * @param srcPort
     * @param dstPort
     * @param protocol 入参
     * @return String
     * @author Chenyi
     * @date 2024/6/19 下午2:33
     **/
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String insertFlow(Context ctx, String flowId, String srcIp, String dstIp, String srcPort, String dstPort, String protocol) {
        ChaincodeStub stub = ctx.getStub();
        Flow flow = new Flow();
        flow.setId(flowId);
        flow.setSrcIp(srcIp);
        flow.setDstIp(dstIp);
        flow.setSrcPort(srcPort);
        flow.setDstPort(dstPort);
        flow.setProtocol(protocol);
        stub.putStringState(flow.getId(), JSON.toJSONString(flow));
        log.info("insert flow: " + String.valueOf(flow));
        return "200";
    }

    /**
     * 取消上链流量
     * @param ctx
     * @param flowId 入参
     * @return String
     * @author Chenyi
     * @date 2024/6/19 下午2:38
     **/
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String deleteFlow(Context ctx, String flowId) {
        ChaincodeStub stub = ctx.getStub();
        stub.delState(flowId);
        return "200";
    }

}
