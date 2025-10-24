|术语/缩写|简单解释|
|---|---|
|IDS（Intrusion Detection System）|入侵检测系统，持续观察网络或主机，发现异常行为并告警。|
|NIDS|Network IDS，侧重分析网络流量，本项目的 CSV/PCAP 分析属于这一类。|
|HIDS|Host IDS，关注单台主机的日志、CPU、内存等指标，这里由 `system_status.py` 与 `HIDS.py` 实现。|
|PCAP|网络抓包原始文件的标准格式，里面记录了完整的数据包。|
|CSV|逗号分隔的表格文件，用来批量上传网络流量特征。|
|MyFlow|后端数据库中的一张表，存储每条网络流量记录（源 IP、目标 IP、协议、置信度等）。|
|Rule|安全策略或匹配模式，既保存在 MySQL/Redis 中，也可以被写入区块链永久存证。|
|Hyperledger Fabric|企业级区块链框架，用于实现规则的“上链”与共识存储。|
|Chaincode|Fabric 的智能合约，这里指 `MyAssetChaincode.java`，负责读写链上规则与流量。|
|Wallet|Fabric 网关使用的身份钱包，里面保存访问区块链所需的证书和私钥。|
|Redis|高性能内存数据库，用作缓存与实时指标存储。|
|MySQL|关系型数据库，保存规则、流量等核心业务数据。|
|EC-GAN|“External Classifier GAN”的缩写，是本项目采用的深度学习模型，用于识别网络攻击。|
|GeoIP|根据 IP 地址估算城市/经纬度的数据库，本项目用来把可疑 IP 显示在地图上。|
|WebSocket|一种保持长连接的通信方式，用于实时推送主机状态或告警信息。|
|REST API|一种常见的网络接口风格，前后端通过 HTTP 调用传递 JSON 数据。|
|Swagger/Knife4j|自动生成接口文档的工具，后端访问 `http://后端地址:9090/swagger-ui.html` 即可查看。|
|CSVResult|后端返回给前端的一个数据结构，包含最近一次 CSV 分析的统计信息和具体流量列表。*** End Patch
