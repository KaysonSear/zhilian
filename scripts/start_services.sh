#!/usr/bin/env bash

set -euo pipefail

# Resolve project root (one level up from this scripts directory)
PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
LOG_DIR="$PROJECT_ROOT/logs"
PID_DIR="$PROJECT_ROOT/run"

mkdir -p "$LOG_DIR" "$PID_DIR"

# Helper to start a command via nohup and track its PID.
start_service() {
    local name="$1"
    shift
    local cmd="$*"
    local pid_file="$PID_DIR/${name}.pid"
    local log_file="$LOG_DIR/${name}.log"

    if [[ -f "$pid_file" ]] && kill -0 "$(cat "$pid_file")" 2>/dev/null; then
        echo "[skip] $name already running (PID $(cat "$pid_file"))."
        return 0
    fi

    echo "[start] $name -> $cmd"
    nohup bash -lc "source ~/.bashrc && $cmd" >>"$log_file" 2>&1 &
    local pid=$!
    echo "$pid" >"$pid_file"
    echo "[ok] $name started with PID $pid (logs: $log_file)"
}

# 1. CSV 深度学习检测服务
start_service "nids_flask" "
cd \"$PROJECT_ROOT/ids-services/EC-GAN_NIDS-main/mymodel/code/api\" &&
pyenv activate ids-ecgan-38 &&
python nids_flask.py
"

# 2. PCAP 规则检测服务
start_service "ids_py" "
cd \"$PROJECT_ROOT/ids-services/ids\" &&
pyenv activate ids-pcap-38 &&
python ids.py
"

# 3. 主机状态采集服务（可选，但默认启动便于大屏展示）
start_service "system_status" "
cd \"$PROJECT_ROOT/ids-services/ids\" &&
pyenv activate ids-pcap-38 &&
python system_status.py
"

# 4. Spring Boot 后端
start_service "backend" "
cd \"$PROJECT_ROOT/backend/back\" &&
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 &&
mvn spring-boot:run
"

# 5. 前端 Vue 服务
start_service "frontend" "
cd \"$PROJECT_ROOT/frontend\" &&
if [[ ! -d node_modules ]]; then npm install --legacy-peer-deps; fi &&
npm run serve
"

# 尝试在前端服务启动后自动打开浏览器访问页面
if command -v xdg-open >/dev/null 2>&1; then
    (
        sleep 10
        echo "[info] 自动打开浏览器访问 http://localhost:8080/index"
        xdg-open "http://localhost:8080/index" >/dev/null 2>&1 || true
    ) &
else
    echo "[info] xdg-open 不可用，请手动打开 http://localhost:8080/index"
fi

echo
echo "所有服务已尝试启动。"
echo "查看日志：tail -f $LOG_DIR/<service>.log"
echo "若要停止，请运行 scripts/stop_services.sh"
