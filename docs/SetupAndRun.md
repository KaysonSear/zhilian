以下步骤面向“第一次部署本系统的同学”，尽量把每一步写得像操作手册。请根据自己机器的系统（Windows / Linux）灵活调整路径。

## 1. 安装必备软件
1. 安装 **Java 17** 与 **Maven 3.9+**（用于运行 Spring Boot 后端）。  
2. 安装 **Node.js 16+**（带 npm，用于运行 Vue 前端）。  
3. 安装 **Python 3.10+**，并确保可以使用 `pip`。  
4. 搭建 **MySQL 8**，创建一个名为 `safe` 的空数据库。  
5. 安装并启动 **Redis 6+**。  
6. 如果需要体验“规则上链”，还需准备好 **Hyperledger Fabric** 的运行环境（Docker、docker-compose、Fabric 二进制文件）。

> 提示：仓库中没有 MySQL 建表脚本，需要向维护人员索要或通过现有运行环境导出。

## 2. 准备后端配置
1. 进入 `backend/back/src/main/resources/application.yaml`，把以下信息改成自己的环境：  
   - `spring.datasource.url/username/password`：连接你搭建的 MySQL。  
  - `spring.data.redis.host/port/password`：指向本地 Redis。  
  - `upload.path`：上传文件临时存放目录，建议改成一个真实存在的路径（例如 Linux `/opt/safe/uploads`）。  
  - `fabric.*`：如果暂时不用区块链，可以保持默认但不要调用相关接口；若需要上链，请把证书路径、节点地址改成正确的值，并确保 `connection.json` 里的 IP 能访问。

2. 如果你修改了 Python 服务的 URL，也要同步更新 `backend/back/src/main/java/com/chenyi/safe/controller/FileUploadController.java` 中的 `pythonEndpoint` 地址。

## 3. 启动 Python 服务
### 3.1 CSV 深度学习检测（`ipPredict`）
```bash
cd ids-services/EC-GAN_NIDS-main
python -m venv venv
source venv/bin/activate        # Windows 用 venv\Scripts\activate
pip install -r requirements.txt
cd mymodel/code/api
python nids_flask.py            # 默认监听 http://0.0.0.0:5000/ipPredict
```

确保 `mymodel/models/*.h5`、`pretreatment-model/*.pkl` 等文件存在；第一次运行会加载模型，可能较慢。

### 3.2 PCAP 规则检测（`/upload`）
```bash
cd ids-services/ids
python -m venv venv
source venv/bin/activate
pip install -r ../EC-GAN_NIDS-main/requirements.txt  # 复用同一份依赖
python ids.py         # 默认监听 http://0.0.0.0:5001/upload
```

根据需要修改 `ids.py` 中的 `UPLOAD_FOLDER` 路径，并保证 `protocol/`、`warning/`、`rules/` 下的文本文件存在。

### 3.3 主机状态上报（可选）
```bash
cd ids-services/ids
python system_status.py
```

该脚本会每 5 秒采集主机指标并通过 WebSocket 推送给后端。请把文件里的 `ws://...:9090` 地址改成你实际运行的后端地址。

## 4. 启动 Spring Boot 后端
```bash
cd backend/back
mvn clean package
mvn spring-boot:run        # 或 java -jar target/safe-0.0.1-SNAPSHOT.jar
```

首次运行会自动创建 `wallet/`（Fabric 钱包），并尝试连接 MySQL、Redis、Python 服务。若连接失败，请根据日志修正配置。

## 5. 启动 Hyperledger Fabric（仅在需要时）
1. 安装 Docker、docker-compose，并把 Fabric 官方示例环境准备好。  
2. 进入 `backend/test-network-mysel`，阅读 `network-myself.sh`，按需修改脚本中的 IP。  
3. 启动网络：
   ```bash
   cd backend/test-network-mysel
   ./network-myself.sh up createChannel
   ./network-myself.sh deployCC   # 部署链码
   ```
4. 确保链码 `MyAssetChaincode` 已部署，证书文件位置与 `application.yaml` 匹配。

如果暂时不打算上链，可跳过此步骤，后端的区块链相关接口将返回错误（前端功能依然可用）。

## 6. 一键启动脚本（推荐）
仓库根目录新增了 `scripts/start_services.sh` 与 `scripts/stop_services.sh`，会自动按前述顺序启动或停止全部服务。首次运行需授予执行权限（已经执行过可略过）：
```bash
cd /media/kayson/F0001DC5001D9426/Coding/zhilian
./scripts/start_services.sh    # 启动
# 使用完毕后：
./scripts/stop_services.sh     # 停止
```
脚本会将日志写入 `logs/`，PID 存放在 `run/`，便于查看或排错。如果某个服务已手动启动，脚本会跳过并提示。
运行脚本约 10 秒后会自动调用 `xdg-open` 打开浏览器访问 `http://localhost:8080/index`；若系统不支持该命令，会提示手动打开页面。

当然，也可以继续手动按以下步骤启动单独的前端：

## 7. 启动前端界面
```bash
cd frontend
npm install
npm run serve        # 默认端口 http://localhost:8080
```

打开浏览器访问 `http://localhost:8080`，使用登录页面进入后即可看到各类大屏和统计页面。前端默认把后端地址写在 `src/utils/Request.js` 里，如需变更（例如改成本机 9090 端口），请修改其中的 `baseURL`。

## 8. 验证
1. 上传一份示例 CSV（参考 `ids-services/EC-GAN_NIDS-main/mymodel/data/clean2.csv`）测试 AI 流量分析。  
2. 上传 PCAP 文件测试规则匹配。  
3. 观察“主机监控”页面是否显示 CPU/内存等指标。  
4. 测试规则新增、关键字搜索、上链功能是否正常。  
5. 如果使用区块链，可在 `backend/back/wallet` 查看是否生成用户证书，或查询区块链返回的规则数据。

只要以上环节都顺利，整套系统就算跑通了。后续请再根据团队提供的数据导入、运维脚本、监控要求进行完善。
