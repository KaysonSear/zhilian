#!/bin/bash

echo "🚀 正在启动智链平台..."
echo "📍 真实中国地图功能已就绪！"
echo ""

cd /media/kayson/F0001DC5001D9426/Coding/zhilian-next

# 安装依赖（如果需要）
if [ ! -d "node_modules" ]; then
    echo "📦 首次运行，正在安装依赖..."
    pnpm install
fi

# 启动Web应用
echo "🌐 启动Web应用..."
echo "   访问地址: http://localhost:3000"
echo "   地图页面: http://localhost:3000/dashboard/map"
echo ""
echo "💡 提示: 按 Ctrl+C 停止服务"
echo ""

pnpm --filter @zhilian/web dev

