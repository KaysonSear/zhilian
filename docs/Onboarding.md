项目是一套“网络入侵检测与安全运营平台”。它把多个子系统串在一起：网页端大屏负责展示，后端服务负责数据调度和区块链写入，Python 服务负责流量/日志分析，还有超账本区块链用来存储关键规则。下面这份“新手上路”希望让完全不懂开发的成员也能迅速看懂全局。

你可以把整个项目想成三个部分：
- **可视化前端（`frontend`）**：一个基于 Vue 的网页，用户看到的告警大屏、流量曲线、规则管理等都在这里。
- **Spring Boot 后端（`backend/back`）**：集中处理接口请求，连接 MySQL、Redis、Python 检测服务，并把已经确认的规则同步到区块链。
- **入侵检测工具集（`ids-services`）**：若干个独立运行的 Python 程序，负责「AI 流量识别」「基于规则的 PCAP 检测」「主机状态采集」等工作。

核心功能（按业务视角理解）：
- 上传网络流量数据（CSV 或 PCAP）交给后端，再由 Python 模型判别是否存在攻击，并把结果回写数据库与 Redis。
- 实时读取 Redis 中的最新分析结果，在可视化大屏展示攻击类型、流量趋势和可疑 IP。
- 采集主机的 CPU/内存/磁盘等指标，通过 WebSocket 推送给后端，再由前端实时呈现系统健康度。
- 管理本地规则：添加/查询/关键字搜索；已确认的规则还能“一键上链”，通过 Hyperledger Fabric 保留不可篡改的记录。
- 提供区块链同步入口，查询已经上链的规则或流量信息。

建议的阅读顺序（只看关键目录即可）：
1. **`frontend/src`**  
   先看 `pages/Index/index.vue`、`pages/Flow/Flow.vue`、`pages/Warning/Warning.vue`，了解大屏展示哪些数据，再看 `src/API/index.js` 明白页面向后端请求了哪些接口。
2. **`backend/back/src/main/java/com/chenyi/safe/controller`**  
   - `MyFlowController`：所有统计类接口的入口，关联 Redis 缓存。  
   - `FileUploadController`：文件上传、转发到 Python 服务。  
   - `BlockChainController`、`RuleController`：规则管理与上链逻辑。  
   读完控制器，再顺带浏览 `config`（数据库/Redis/区块链配置）与 `service`、`mapper`，知道数据流向。
3. **`ids-services/ids`**  
   - `ids.py`：PCAP 文件规则匹配。  
   - `system_status.py`：采集主机资源并推送给后端。  
   - `protocol/`、`warning/`：用于匹配的文本规则库。  
4. **`ids-services/EC-GAN_NIDS-main/mymodel/code/api`**  
   - `nids_flask.py`：CSV 流量的深度学习识别服务，对应后端上传接口 `type=1`。  
   - `nids_flask_critic.py`：带“未知攻击”判别的扩展版本。  
5. **`backend/chaincode` 与 `test-network-mysel`**  
   如果涉足区块链部署，需要熟悉 `MyAssetChaincode.java` 和网络脚本 `network-myself.sh`。

把以上路径过一遍，你就能弄清楚“谁在调用谁”“数据从哪里来、往哪里去”。这时再阅读具体业务逻辑时，就不会迷路。
