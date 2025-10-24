#!/usr/bin/env bash

set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
PID_DIR="$PROJECT_ROOT/run"

if [[ ! -d "$PID_DIR" ]]; then
    echo "No PID directory found at $PID_DIR (nothing to stop?)."
    exit 0
fi

stopped=0
for pid_file in "$PID_DIR"/*.pid; do
    [[ -e "$pid_file" ]] || continue
    name="$(basename "$pid_file" .pid)"
    pid="$(cat "$pid_file")"
    if kill -0 "$pid" 2>/dev/null; then
        echo "[stop] $name (PID $pid)"
        kill "$pid" 2>/dev/null || true
        sleep 1
        if kill -0 "$pid" 2>/dev/null; then
            echo "[warn] $name did not stop gracefully, sending SIGKILL"
            kill -9 "$pid" 2>/dev/null || true
        fi
        stopped=$((stopped + 1))
    else
        echo "[info] $name (PID $pid) already stopped."
    fi
    rm -f "$pid_file"
done

if [[ $stopped -eq 0 ]]; then
    echo "No running services were found."
else
    echo "已停止 $stopped 个服务。"
fi
